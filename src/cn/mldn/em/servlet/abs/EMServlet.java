package cn.mldn.em.servlet.abs;

import java.util.Set;

import cn.mldn.util.servlet.DispatcherServlet;

@SuppressWarnings("serial")
public abstract class EMServlet extends DispatcherServlet {
	/**
	 * 验证当前的用户是否是超级管理员
	 * @return
	 */
	public boolean admin() {
		Integer sflag = (Integer) super.getSession().getAttribute("sflag") ;
		if (sflag == null) {
			return false ;
		}
		return sflag == 1 ;
	} 
	/**
	 * 登录之后所有的角色和权限都保存在session之中，所以此时直接通过session取得数据验证即可
	 * @param actionFlag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean auth(String actionFlag) { // 需要检测指定的用户是否具备有指定的权限
		Set<String> allActions = (Set<String>) super.getSession().getAttribute("allActions");
		return allActions.contains(actionFlag) ;	// 判断是否具备有指定的权限
	} 
	/**
	 * 取得当前用户的编号
	 * @return
	 */
	public String getMid() {
		return super.getSession().getAttribute("mid").toString() ;
	}

	@Override
	public String getDefaultColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUploadDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
