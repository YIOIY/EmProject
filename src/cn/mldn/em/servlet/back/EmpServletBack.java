package cn.mldn.em.servlet.back;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import cn.mldn.em.service.back.IDeptServiceBack;
import cn.mldn.em.service.back.IEmpServiceBack;
import cn.mldn.em.service.back.ILevelServiceBack;
import cn.mldn.em.service.back.impl.DeptServiceBackImpl;
import cn.mldn.em.service.back.impl.EmpServiceBackImpl;
import cn.mldn.em.service.back.impl.LevelServiceBackImpl;
import cn.mldn.em.servlet.abs.EMServlet;
import cn.mldn.em.vo.Dept;
import cn.mldn.em.vo.Elog;
import cn.mldn.em.vo.Emp;
import cn.mldn.em.vo.Level;
import cn.mldn.util.factory.ServiceFactory;
import cn.mldn.util.split.SplitPageUtils;

@SuppressWarnings("serial")
@WebServlet("/pages/back/emp/EmpServletBack/*")
public class EmpServletBack extends EMServlet {
	private Emp emp = new Emp();

	public Emp getEmp() {
		return emp;
	}

	public String remove() {
		if (super.auth("emp:remove")) { // 判断当前用户是否具备有指定的权限
			String ids = super.request.getParameter("ids");
			Set<Integer> empnos = new HashSet<Integer>();
			String result[] = ids.split("\\|"); // 按照“|”拆分
			for (int x = 0; x < result.length; x++) {
				empnos.add(Integer.parseInt(result[x])); // 保存要删除的雇员编号
			}
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class);
			try {
				if (empService.removeEmp(super.getMid(), empnos)) {
					super.setUrlAndMsg("emp.list.servlet", "emp.out.success.msg");
				} else {
					super.setUrlAndMsg("emp.list.servlet", "emp.out.failure.msg");
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setUrlAndMsg("emp.list.servlet", "emp.out.failure.msg");
			}
			return "forward.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public String show() {
		if (super.auth("emp:list")) { // 判断当前用户是否具备有指定的权限
			int empno = super.getIntParameter("empno") ;
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class) ;
			try {
				Map<String,Object> map = empService.getDetails(super.getMid(), empno) ;
				super.request.setAttribute("emp", map.get("emp"));
				super.request.setAttribute("dept", map.get("dept"));
				super.request.setAttribute("level", map.get("level"));
				super.request.setAttribute("allElogs", map.get("allElogs"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "emp.show.page" ;
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public String list() {
		if (super.auth("emp:list")) { // 判断当前用户是否具备有指定的权限
			String urlKey = "emp.list.servlet";
			int flag = 1;
			try {
				flag = super.getIntParameter("flag"); // 接收flag的内容
			} catch (Exception e) {
			}
			SplitPageUtils spu = new SplitPageUtils(super.request);
			int currentPage = spu.getCurrentPage();
			int lineSize = spu.getLineSize();
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class);
			try {
				Map<String, Object> map = empService.listByFlag(super.getMid(), flag, spu.getColumn(), spu.getKeyWord(),
						currentPage, lineSize);
				request.setAttribute("allEmps", map.get("allEmps")); // 这个值需要传递给JSP页面
				super.request.setAttribute("allDepts", map.get("allDepts"));
				super.request.setAttribute("allLevels", map.get("allLevels"));
				super.setSplitPage(urlKey, map.get("empCount"), spu); // 实现了分页的参数传递
				super.setSplitParam("flag", flag); // 为后续的分页传值做准备
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag == 1) {
				return "emp.list.page";
			} else {
				return "emp.list.out.page";
			}
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public void checkDept() {
		int deptno = super.getIntParameter("deptno"); // 接收发送来的部门编号
		String currDeptno = super.getStringParameter("currDeptno");
		IDeptServiceBack deptService = ServiceFactory.getInstance(DeptServiceBackImpl.class);
		try {
			Dept dept = deptService.get(deptno);
			if (currDeptno == null || "".equals(currDeptno)) {
				super.print(dept.getCurrnum() < dept.getMaxnum()); // true表示可以使用
			} else { // 如果现在有currDeptno
				if (dept.getDeptno().equals(Integer.parseInt(currDeptno))) {
					super.print(true);
				} else {
					super.print(dept.getCurrnum() < dept.getMaxnum()); // true表示可以使用
				}
			}
		} catch (Exception e) {
			super.print(false);
		}
	}

	public void checkSal() { // 检测工资是否在指定的范围之中
		int lid = super.getIntParameter("lid"); // 接收发送来的级别编号
		double sal = super.getDoubleParameter("sal"); // 接收发送来的工资数据
		ILevelServiceBack levelService = ServiceFactory.getInstance(LevelServiceBackImpl.class);
		try {
			Level lev = levelService.get(lid);
			super.print(sal <= lev.getHisal() && sal >= lev.getLosal()); // true表示可以使用
		} catch (Exception e) {
			super.print(false);
		}
	}

	public String editPre() {
		if (super.auth("emp:edit")) { // 判断当前用户是否具备有指定的权限
			int empno = super.getIntParameter("empno"); // 取得雇员编号
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class);
			try {
				Map<String, Object> map = empService.editPre(super.getMid(), empno);
				super.request.setAttribute("allDepts", map.get("allDepts"));
				super.request.setAttribute("allLevels", map.get("allLevels"));
				super.request.setAttribute("emp", map.get("emp"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "emp.edit.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public String addPre() {
		if (super.auth("emp:add")) { // 判断当前用户是否具备有指定的权限
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class);
			try {
				Map<String, Object> map = empService.addPre(super.getMid());
				super.request.setAttribute("allDepts", map.get("allDepts"));
				super.request.setAttribute("allLevels", map.get("allLevels"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "emp.add.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public String edit() {
		if (super.auth("emp:edit")) { // 判断当前用户是否具备有指定的权限
			if (super.isUploadFile()) { // 现在有文件上传
				if ("nophoto.png".equals(this.emp.getPhoto())) { // 原始没有图片
					this.emp.setPhoto(super.createSingleFileName()); // 创建文件名称
				}
			}
			this.emp.setMid(super.getMid()); // 保存当前操作的用户编号
			Elog log = new Elog(); // 定义雇员日志操作，目的是保存简介信息
			log.setNote(super.getStringParameter("note"));
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class);
			try {
				if (empService.edit(this.emp, log)) { // 实现雇员数据的追加操作
					super.setUrlAndMsg("emp.list.servlet", "vo.edit.success.msg");
					if (super.isUploadFile()) {
						super.saveUploadFile(this.emp.getPhoto()); // 保存图片
						super.saveScale(this.emp.getPhoto()); // 保存缩略图
					}
				} else {
					super.setUrlAndMsg("emp.list.servlet", "vo.edit.failure.msg");
				}
			} catch (Exception e) {
				super.setUrlAndMsg("emp.list.servlet", "vo.add.failure.msg");
				e.printStackTrace();
			}
			return "forward.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public String add() {
		if (super.auth("emp:add")) { // 判断当前用户是否具备有指定的权限
			if (super.isUploadFile()) { // 现在有文件上传
				this.emp.setPhoto(super.createSingleFileName()); // 创建文件名称
			} else {
				this.emp.setPhoto("nophoto.png"); // 创建文件名称
			}
			this.emp.setMid(super.getMid()); // 保存当前操作的用户编号
			Elog log = new Elog(); // 定义雇员日志操作，目的是保存简介信息
			log.setNote(super.getStringParameter("note"));
			IEmpServiceBack empService = ServiceFactory.getInstance(EmpServiceBackImpl.class);
			try {
				if (empService.add(this.emp, log)) { // 实现雇员数据的追加操作
					super.setUrlAndMsg("emp.add.servlet", "vo.add.success.msg");
					if (super.isUploadFile()) {
						super.saveUploadFile(this.emp.getPhoto()); // 保存图片
						super.saveScale(this.emp.getPhoto()); // 保存缩略图
					}
				} else {
					super.setUrlAndMsg("emp.add.servlet", "vo.add.failure.msg");
				}
			} catch (Exception e) {
				super.setUrlAndMsg("emp.add.servlet", "vo.add.failure.msg");
				e.printStackTrace();
			}
			return "forward.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	@Override
	public String getDefaultColumn() {
		return "雇员姓名:ename|雇员职位:job";
	}

	@Override
	public String getUploadDir() {
		return "/upload/emp/";
	}

	@Override
	public String getType() {
		return "雇员";
	}
}
