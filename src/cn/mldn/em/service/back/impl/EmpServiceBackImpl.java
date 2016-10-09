package cn.mldn.em.service.back.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.em.dao.IDeptDAO;
import cn.mldn.em.dao.IElogDAO;
import cn.mldn.em.dao.IEmpDAO;
import cn.mldn.em.dao.ILevelDAO;
import cn.mldn.em.dao.impl.DeptDAOImpl;
import cn.mldn.em.dao.impl.ElogDAOImpl;
import cn.mldn.em.dao.impl.EmpDAOImpl;
import cn.mldn.em.dao.impl.LevelDAOImpl;
import cn.mldn.em.service.abs.AbstractService;
import cn.mldn.em.service.back.IEmpServiceBack;
import cn.mldn.em.vo.Dept;
import cn.mldn.em.vo.Elog;
import cn.mldn.em.vo.Emp;
import cn.mldn.em.vo.Level;
import cn.mldn.util.DateUtil;
import cn.mldn.util.factory.DAOFactory;

public class EmpServiceBackImpl extends AbstractService implements IEmpServiceBack {
	@Override
	public Map<String, Object> getDetails(String mid, int eid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (super.auth(mid, "emp:list")) {
			IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class);
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
			ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
			IElogDAO logDAO = DAOFactory.getInstance(ElogDAOImpl.class);
			Emp emp = empDAO.findById(eid);
			map.put("dept", deptDAO.findById(emp.getDeptno()));
			map.put("level", levelDAO.findById(emp.getLid()));
			map.put("allElogs", logDAO.findAllByEmp(emp.getEmpno()));
			map.put("emp", emp); 
		}
		return map;
	}

	@Override
	public boolean removeEmp(String mid, Set<Integer> ids) throws Exception {
		if (ids.size() > 0) {
			if (super.auth(mid, "emp:remove")) { // 判断是否有指定的权限
				IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class);
				if (empDAO.doUpdateFlag(ids, 0)) { // flag = 0表示离职处理
					// 进行日志的数据保存
					Iterator<Integer> enoIter = ids.iterator();
					IElogDAO elogDAO = DAOFactory.getInstance(ElogDAOImpl.class);
					IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
					while (enoIter.hasNext()) {
						int eid = enoIter.next();
						Elog log = new Elog();
						log.setEmpno(eid);
						log.setFlag(0); // 表示离职处理
						log.setMid(mid); // 操作者的mid数据
						log.setNote("【" + DateUtil.getFormatDatetime() + "】离职处理");
						if (elogDAO.doCreate(log)) {
							deptDAO.doUpdateCurrnumByEmp(eid, -1);
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> listByFlag(String mid, int flag, String column, String keyWord, int currentPage,
			int lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (super.auth(mid, "emp:list")) {
			// 查询所有的部门数据
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
			List<Dept> allDepts = deptDAO.findAll();
			Map<Long, String> deptMap = new HashMap<Long, String>();
			Iterator<Dept> iterDept = allDepts.iterator();
			while (iterDept.hasNext()) {
				Dept vo = iterDept.next();
				deptMap.put(vo.getDeptno().longValue(), vo.getDname());
			}
			// 查询出所有的雇员等级信息
			ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
			Iterator<Level> iterLevel = levelDAO.findAll().iterator();
			Map<Long, String> levelMap = new HashMap<Long, String>();
			while (iterLevel.hasNext()) {
				Level lev = iterLevel.next();
				levelMap.put(lev.getLid().longValue(), lev.getTitle() + "-" + lev.getFlag());
			}
			map.put("allDepts", deptMap);
			map.put("allLevels", levelMap);
			IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class);
			if (column == null || "".equals(column) || keyWord == null || "".equals(keyWord)) {
				map.put("allEmps", empDAO.findAllByFlag(flag, currentPage, lineSize));
				map.put("empCount", empDAO.getAllCountByFlag(flag));
			} else {
				map.put("allEmps", empDAO.findAllByFlag(flag, column, keyWord, currentPage, lineSize));
				map.put("empCount", empDAO.getAllCountByFlag(flag, column, keyWord));
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> addPre(String mid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (super.auth(mid, "emp:add")) {
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
			ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
			map.put("allDepts", deptDAO.findAllByEmpty());
			map.put("allLevels", levelDAO.findAll());
		}
		return map;
	}

	@Override
	public boolean add(Emp vo, Elog log) throws Exception {
		if (super.auth(vo.getMid(), "emp:add")) {
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
			Dept dept = deptDAO.findById(vo.getDeptno()); // 根据雇员所在的部门查询部门数据
			if (dept.getCurrnum() < dept.getMaxnum()) { // 现在部门有容量
				ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
				Level level = levelDAO.findById(vo.getLid()); // 取得指定的级别信息
				// 判断增加的雇员工资是否在指定的级别范围之内
				if (vo.getSal() >= level.getLosal() && vo.getSal() <= level.getHisal()) {
					IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class);
					vo.setFlag(1); // 表示当前员工在职
					vo.setHiredate(new Date()); // 雇佣日期为今天的日期
					if (empDAO.doCreate(vo)) { // 雇员保存成功，那么随后还需要进行部门人数的增加
						int empno = empDAO.getLastId(); // 取得当前操作的最后一次ID内容
						if (deptDAO.doUpdateCurrnum(vo.getDeptno(), 1)) { // 部门人数修改成功
							log.setEmpno(empno); // 保存对应雇员的编号
							log.setDeptno(vo.getDeptno()); // 保存雇员对应的部门编号
							log.setMid(vo.getMid()); // 保存操作的管理员帐号
							log.setLid(vo.getLid()); // 保存级别信息
							log.setJob(vo.getJob());
							log.setSal(vo.getSal());
							log.setComm(vo.getComm());
							log.setSflag(0); // 刚入职
							log.setFlag(1);
							log.setNote("【" + DateUtil.getFormatDatetime(vo.getHiredate()) + "】" + log.getNote());
							IElogDAO logDAO = DAOFactory.getInstance(ElogDAOImpl.class);
							return logDAO.doCreate(log);
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> editPre(String mid, int empno) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (super.auth(mid, "emp:edit")) {
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
			ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
			IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class);
			Emp emp = empDAO.findById(empno);
			map.put("allDepts", deptDAO.findAllByEmpty(emp.getDeptno()));
			map.put("allLevels", levelDAO.findAll());
			map.put("emp", emp);
		}
		return map;
	}

	@Override
	public boolean edit(Emp vo, Elog log) throws Exception {
		if (super.auth(vo.getMid(), "emp:add")) {
			// 1、为了确定工资的涨幅操作，首先必须知道原始的数据信息
			IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class);
			Emp oldEmp = empDAO.findById(vo.getEmpno());
			if (oldEmp.getFlag().equals(1)) {
				// 2、确认工资的涨幅
				if (vo.getSal() > oldEmp.getSal()) {
					log.setSflag(1);
				} else if (vo.getSal() < oldEmp.getSal()) {
					log.setSflag(2);
				} else {
					log.setSflag(3);
				}
				// 3、确定工资等级是否复合要求
				ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
				Level level = levelDAO.findById(vo.getLid()); // 取得当前的等级信息
				if (vo.getSal() >= level.getLosal() && vo.getSal() <= level.getHisal()) {
					// 4、确认部门是否有变化
					if (!vo.getDeptno().equals(oldEmp.getDeptno())) { // 部门有改变
						// 5、需要确认要转入的部门是否有名额
						IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
						Dept dept = deptDAO.findById(vo.getDeptno()); // 得到要转入的部门信息
						if (dept.getCurrnum() < dept.getMaxnum()) { // 要转入的部门有名额
							if (deptDAO.doUpdateCurrnum(vo.getDeptno(), 1)) { // 要转入的部门人数加1
								if (deptDAO.doUpdateCurrnum(oldEmp.getDeptno(), -1)) { // 原始部门人数要求减1
									if (empDAO.doUpdate(vo)) { // 新的雇员信息更新成功了
										// 6、进行日志数据的保存处理
										log.setEmpno(vo.getEmpno()); // 保存对应雇员的编号
										log.setDeptno(vo.getDeptno()); // 保存雇员对应的部门编号
										log.setMid(vo.getMid()); // 更新操作的管理员帐号
										log.setLid(vo.getLid()); // 保存级别信息
										log.setJob(vo.getJob());
										log.setSal(vo.getSal());
										log.setComm(vo.getComm());
										log.setFlag(1);
										log.setNote("【" + DateUtil.getFormatDatetime() + "】" + log.getNote());
										IElogDAO logDAO = DAOFactory.getInstance(ElogDAOImpl.class);
										return logDAO.doCreate(log);
									}
								}
							}
						}
					} else {
						if (empDAO.doUpdate(vo)) { // 新的雇员信息更新成功了
							// 6、进行日志数据的保存处理
							log.setEmpno(vo.getEmpno()); // 保存对应雇员的编号
							log.setDeptno(vo.getDeptno()); // 保存雇员对应的部门编号
							log.setMid(vo.getMid()); // 更新操作的管理员帐号
							log.setLid(vo.getLid()); // 保存级别信息
							log.setJob(vo.getJob());
							log.setSal(vo.getSal());
							log.setComm(vo.getComm());
							log.setFlag(1);
							log.setNote("【" + DateUtil.getFormatDatetime() + "】" + log.getNote());
							IElogDAO logDAO = DAOFactory.getInstance(ElogDAOImpl.class);
							return logDAO.doCreate(log);
						}
					}
				}
			}
		}
		return false;
	}

}
