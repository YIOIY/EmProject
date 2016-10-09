package cn.mldn.em.servlet.back;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import cn.mldn.em.service.back.IDeptServiceBack;
import cn.mldn.em.service.back.impl.DeptServiceBackImpl;
import cn.mldn.em.servlet.abs.EMServlet;
import cn.mldn.em.vo.Dept;
import cn.mldn.em.vo.Emp;
import cn.mldn.util.factory.ServiceFactory;
import cn.mldn.util.split.SplitPageUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/pages/back/dept/DeptServletBack/*")
public class DeptServletBack extends EMServlet {

	public void listEmp() {
		JSONObject obj = new JSONObject(); // 保存整体的json数据
		if (super.auth("dept:list")) {
			obj.put("flag", true); // 保存正确信息
			SplitPageUtils spu = new SplitPageUtils(super.request);
			int deptno = super.getIntParameter("deptno");
			IDeptServiceBack deptService = ServiceFactory.getInstance(DeptServiceBackImpl.class);
			try {
				Map<String, Object> map = deptService.listEmpByDept(super.getMid(), deptno, spu.getCurrentPage(),
						spu.getLineSize());
				System.out.println(map);
				obj.put("allRecorders", map.get("empCount")) ; 	// 直接将数量保存在JSON对象里面
				obj.put("allEmps", map.get("allEmps")) ;	// 自动保存所需要的对象数据
				obj.put("allLevels", map.get("allLevels")) ;	// 自动保存所需要的对象数据
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		} else {
			obj.put("flag", false); // 保存错误信息
		}
		super.print(obj); 
	} 

	public void editMaxnum() {
		if (super.auth("dept:edit")) { // 判断当前用户是否具备有指定的权限
			Dept vo = new Dept();
			vo.setDeptno(super.getIntParameter("deptno"));
			vo.setMaxnum(super.getIntParameter("maxnum"));
			IDeptServiceBack deptService = ServiceFactory.getInstance(DeptServiceBackImpl.class);
			try {
				super.print(deptService.editMaxnum(super.getMid(), vo));
			} catch (Exception e) {
				e.printStackTrace();
				super.print(false);
			}
		} else {
			super.print(false);
		}
	}

	public String list() {
		if (super.auth("dept:list")) { // 判断当前用户是否具备有指定的权限
			IDeptServiceBack deptService = ServiceFactory.getInstance(DeptServiceBackImpl.class);
			try {
				super.request.setAttribute("allDepts", deptService.list(super.getMid()));
			} catch (Exception e) {
				e.printStackTrace();
				return "error.page"; // 回到错误页上
			}
			return "dept.list.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}
}
