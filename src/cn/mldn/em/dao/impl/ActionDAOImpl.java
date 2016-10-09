package cn.mldn.em.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.mldn.em.dao.IActionDAO;
import cn.mldn.em.vo.Action;
import cn.mldn.util.dao.AbstractDAO;

public class ActionDAOImpl extends AbstractDAO implements IActionDAO {

	@Override
	public boolean doCreate(Action vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Action vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Action findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findAllByMember(String mid) throws SQLException {
		Set<String> all = new HashSet<String>() ;
		String sql = "SELECT flag FROM action WHERE actid IN ("
				+ "	SELECT actid FROM role_action WHERE rid IN ("
				+ "		SELECT rid FROM member_role WHERE mid=?))" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, mid);
		ResultSet rs = super.pstmt.executeQuery() ;
		while (rs.next()) {
			all.add(rs.getString(1)) ;
		}
		return all;
	}

	@Override
	public boolean findExists(String mid, String flag) throws SQLException {
		String sql = "SELECT flag FROM action WHERE actid IN ("
				+ "	SELECT actid FROM role_action WHERE rid IN ("
				+ "		SELECT rid FROM member_role WHERE mid=?)) "
				+ " AND flag=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, mid);
		super.pstmt.setString(2, flag);
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			return true ; 
		}
		return false;
	} 

}
