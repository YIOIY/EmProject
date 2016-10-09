package cn.mldn.em.dao;

import java.sql.SQLException;
import java.util.Set;

import cn.mldn.em.vo.Action;
import cn.mldn.util.dao.IDAO;

public interface IActionDAO extends IDAO<Integer, Action> {
	/**
	 * 根据用户编号取得用户对应的所有权限数据
	 * @param mid
	 * @return
	 * @throws SQLException
	 */
	public Set<String> findAllByMember(String mid) throws SQLException;
	/**
	 * 判断指定的用户编号是否具备有指定的权限数据
	 * @param mid 用户编号
	 * @param flag 权限标记
	 * @return 存在返回true，否则返回false
	 * @throws SQLException
	 */
	public boolean findExists(String mid,String flag) throws SQLException ;
}
