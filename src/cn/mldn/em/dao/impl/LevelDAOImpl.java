package cn.mldn.em.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.mldn.em.dao.ILevelDAO;
import cn.mldn.em.vo.Level;
import cn.mldn.util.dao.AbstractDAO;

public class LevelDAOImpl extends AbstractDAO implements ILevelDAO {

	@Override
	public boolean doCreate(Level vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Level vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Level findById(Integer id) throws SQLException {
		Level vo = null;
		String sql = "SELECT lid,losal,hisal,title,flag FROM level WHERE lid=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, id);
		ResultSet rs = super.pstmt.executeQuery();
		if (rs.next()) {
			vo = new Level();
			vo.setLid(rs.getInt(1));
			vo.setLosal(rs.getDouble(2));
			vo.setHisal(rs.getDouble(3));
			vo.setTitle(rs.getString(4));
			vo.setFlag(rs.getString(5));
		}
		return vo;
	}

	@Override
	public List<Level> findAll() throws SQLException {
		List<Level> all = new ArrayList<Level>();
		String sql = "SELECT lid,losal,hisal,title,flag FROM level";
		super.pstmt = super.conn.prepareStatement(sql);
		ResultSet rs = super.pstmt.executeQuery();
		while (rs.next()) {
			Level vo = new Level();
			vo.setLid(rs.getInt(1));
			vo.setLosal(rs.getDouble(2));
			vo.setHisal(rs.getDouble(3));
			vo.setTitle(rs.getString(4));
			vo.setFlag(rs.getString(5));
			all.add(vo);
		}
		return all;
	}

	@Override
	public List<Level> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Level> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize)
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

}
