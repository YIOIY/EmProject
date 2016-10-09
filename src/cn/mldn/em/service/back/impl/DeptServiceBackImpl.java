package cn.mldn.em.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mldn.em.dao.IDeptDAO;
import cn.mldn.em.dao.IEmpDAO;
import cn.mldn.em.dao.ILevelDAO;
import cn.mldn.em.dao.impl.DeptDAOImpl;
import cn.mldn.em.dao.impl.EmpDAOImpl;
import cn.mldn.em.dao.impl.LevelDAOImpl;
import cn.mldn.em.service.abs.AbstractService;
import cn.mldn.em.service.back.IDeptServiceBack;
import cn.mldn.em.vo.Dept;
import cn.mldn.util.factory.DAOFactory;

public class DeptServiceBackImpl extends AbstractService implements IDeptServiceBack {
	@Override
	public Map<String, Object> listEmpByDept(String mid, int deptno, int currentPage, int lineSize) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		if (super.auth(mid, "dept:list")) {
			IEmpDAO empDAO = DAOFactory.getInstance(EmpDAOImpl.class) ;
			ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class) ;
			map.put("allEmps", empDAO.findAllByDept(deptno, currentPage, lineSize)) ;
			map.put("empCount", empDAO.getAllCountByDept(deptno)) ;
			map.put("allLevels", levelDAO.findAll()) ;
		}
		return map;
	}
	@Override
	public boolean editMaxnum(String mid, Dept vo) throws Exception {
		if(super.auth(mid, "dept:edit")) {
			// 1、取出当前部门的完整信息
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class) ;
			Dept dept = deptDAO.findById(vo.getDeptno()) ;	// 取得一个部门信息
			if (dept.getCurrnum() > vo.getMaxnum()) {	// 不符合要求
				return false ;
			} else if (dept.getCurrnum() < vo.getMaxnum()) { 
				return deptDAO.doUpdateMaxnum(vo) ;
			} else {	// 没有改变
				return true ;
			}
		}
		return false ;
	} 
	@Override
	public List<Dept> list(String mid) throws Exception {
		List<Dept> all = null ;
		if (super.auth(mid, "dept:list")) {
			IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class) ;
			all = deptDAO.findAll() ;	// 查询全部部门
		}
		return all ; 
	}
	@Override
	public Dept get(int id) throws Exception {
		IDeptDAO deptDAO = DAOFactory.getInstance(DeptDAOImpl.class);
		return deptDAO.findById(id); 
	}

}
