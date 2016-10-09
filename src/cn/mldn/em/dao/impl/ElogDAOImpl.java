package cn.mldn.em.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.mldn.em.dao.IElogDAO;
import cn.mldn.em.vo.Elog;
import cn.mldn.util.dao.AbstractDAO;

public class ElogDAOImpl extends AbstractDAO implements IElogDAO {
	@Override
	public List<Elog> findAllByEmp(Integer empno) throws SQLException {
		List<Elog> all = new ArrayList<Elog>() ;
		String sql = "SELECT elid,empno,deptno,mid,lid,job,sal,comm,sflag,flag,note FROM elog WHERE empno=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setInt(1, empno);
		ResultSet rs = super.pstmt.executeQuery() ;
		while (rs.next()) {
			Elog vo = new Elog() ;
			vo.setElid(rs.getInt(1));
			vo.setEmpno(rs.getInt(2));
			vo.setDeptno(rs.getInt(3));
			vo.setMid(rs.getString(4));
			vo.setLid(rs.getInt(5));
			vo.setJob(rs.getString(6));
			vo.setSal(rs.getDouble(7));
			vo.setComm(rs.getDouble(8));
			vo.setSflag(rs.getInt(9));
			vo.setFlag(rs.getInt(10));
			vo.setNote(rs.getString(11));
			all.add(vo) ;
		}
		return all; 
	} 
	@Override
	public boolean doCreate(Elog vo) throws SQLException {
		String sql = "INSERT INTO elog(empno,deptno,mid,lid,job,sal,comm,sflag,flag,note) VALUES (?,?,?,?,?,?,?,?,?,?)" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setInt(1, vo.getEmpno());
		if (vo.getDeptno() == null) {
			super.pstmt.setNull(2, Types.NULL);
		} else {
			super.pstmt.setInt(2, vo.getDeptno());
		}
		super.pstmt.setString(3, vo.getMid());
		if (vo.getLid() == null) {
			super.pstmt.setNull(4, Types.NULL);
		} else {
			super.pstmt.setInt(4, vo.getLid());
		}
		super.pstmt.setString(5, vo.getJob());
		if (vo.getSal() == null) {
			super.pstmt.setNull(6, Types.NULL);
		} else {
			super.pstmt.setDouble(6, vo.getSal());
		}
		if (vo.getComm() == null) {
			super.pstmt.setNull(7, Types.NULL);
		} else {
			super.pstmt.setDouble(7, vo.getComm());
		}
		if (vo.getSflag() == null) {
			super.pstmt.setNull(8, Types.NULL);
		} else {
			super.pstmt.setInt(8, vo.getSflag());
		}
		super.pstmt.setInt(9, vo.getFlag());
		super.pstmt.setString(10, vo.getNote());
		return super.pstmt.executeUpdate() > 0 ; 
	}

	@Override
	public boolean doUpdate(Elog vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Elog findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Elog> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Elog> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Elog> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize)
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
