package cn.mldn.em.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.mldn.em.dao.IMemberDAO;
import cn.mldn.em.vo.Member;
import cn.mldn.util.dao.AbstractDAO;

public class MemberDAOImpl extends AbstractDAO implements IMemberDAO {

	@Override
	public boolean doCreate(Member vo) throws SQLException {
		String sql = "INSERT INTO member(mid,password,name,sflag) VALUES (?,?,?,?)" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, vo.getMid()); 
		super.pstmt.setString(2, vo.getPassword()); 
		super.pstmt.setString(3, vo.getName());
		super.pstmt.setInt(4, vo.getSflag());
		return super.pstmt.executeUpdate() > 0 ; 
	}

	@Override
	public boolean doUpdate(Member vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<String> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Member findById(String id) throws SQLException {
		Member vo = null;
		String sql = "SELECT mid,password,name,sflag FROM member WHERE mid=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setString(1, id);
		ResultSet rs = super.pstmt.executeQuery();
		if (rs.next()) {
			vo = new Member() ;
			vo.setMid(rs.getString(1));
			vo.setPassword(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setSflag(rs.getInt(4));
		}
		return vo;
	}

	@Override
	public List<Member> findAll() throws SQLException {
		List<Member> all = new ArrayList<Member>() ;
		String sql = "SELECT mid,name,sflag FROM member " ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		ResultSet rs = super.pstmt.executeQuery() ;
		while (rs.next()) {
			Member vo = new Member() ;
			vo.setMid(rs.getString(1));
			vo.setName(rs.getString(2));
			vo.setSflag(rs.getInt(3));
			all.add(vo) ;
		}
		return all;
	}

	@Override
	public List<Member> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize)
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
