package cn.mldn.em.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.mldn.em.dao.IEmpDAO;
import cn.mldn.em.vo.Emp;
import cn.mldn.util.dao.AbstractDAO;

public class EmpDAOImpl extends AbstractDAO implements IEmpDAO {
	@Override
	public List<Emp> findAllByDept(Integer deptno, Integer currentPage, Integer lineSize) throws SQLException {
		List<Emp> all = new ArrayList<Emp>();
		String sql = "SELECT empno,deptno,mid,lid,ename,job,sal,comm,hiredate,photo,flag FROM emp WHERE deptno=? LIMIT ?,?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, deptno);
		super.pstmt.setInt(2, (currentPage - 1) * lineSize);
		super.pstmt.setInt(3, lineSize);
		ResultSet rs = super.pstmt.executeQuery();
		while (rs.next()) {
			Emp vo = new Emp();
			vo.setEmpno(rs.getInt(1));
			vo.setDeptno(rs.getInt(2));
			vo.setMid(rs.getString(3));
			vo.setLid(rs.getInt(4));
			vo.setEname(rs.getString(5));
			vo.setJob(rs.getString(6));
			vo.setSal(rs.getDouble(7));
			vo.setComm(rs.getDouble(8));
			vo.setHiredate(new java.util.Date(rs.getDate(9).getTime()));
			vo.setPhoto(rs.getString(10));
			vo.setFlag(rs.getInt(11));
			all.add(vo);
		}
		return all;
	}
	@Override
	public Integer getAllCountByDept(Integer deptno) throws SQLException {
		String sql = "SELECT COUNT(*) FROM emp WHERE deptno=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, deptno); 
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getInt(1) ;
		}
		return 0;
	}
	@Override
	public boolean doUpdateFlag(Set<Integer> ids, Integer flag) throws Exception {
		String sql = "UPDATE emp SET flag=? WHERE empno=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		Iterator<Integer> iter = ids.iterator() ;
		while (iter.hasNext()) {
			super.pstmt.setInt(1, flag);
			super.pstmt.setInt(2, iter.next());
			super.pstmt.addBatch(); 	// 加入到批处理执行之中
		}
		int result [] = super.pstmt.executeBatch() ;	// 执行批处理
		for (int x = 0; x < result.length; x++) {
			if (result[x] < 1) {
				return false ; 
			}
		}
		return true;
	}
	@Override
	public boolean doCreate(Emp vo) throws SQLException {
		String sql = "INSERT INTO emp(deptno,mid,lid,ename,job,sal,comm,hiredate,photo,flag) VALUES (?,?,?,?,?,?,?,?,?,?)";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, vo.getDeptno());
		super.pstmt.setString(2, vo.getMid());
		super.pstmt.setInt(3, vo.getLid());
		super.pstmt.setString(4, vo.getEname());
		super.pstmt.setString(5, vo.getJob());
		super.pstmt.setDouble(6, vo.getSal());
		if (vo.getComm() == null) {
			super.pstmt.setNull(7, Types.NULL);
		} else {
			super.pstmt.setDouble(7, vo.getComm());
		}
		super.pstmt.setDate(8, new java.sql.Date(vo.getHiredate().getTime()));
		super.pstmt.setString(9, vo.getPhoto());
		super.pstmt.setInt(10, vo.getFlag());
		return super.pstmt.executeUpdate() > 0;
	}

	@Override
	public boolean doUpdate(Emp vo) throws SQLException {
		String sql = "UPDATE emp SET deptno=?,lid=?,ename=?,job=?,sal=?,comm=?,photo=? WHERE empno=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, vo.getDeptno());
		super.pstmt.setInt(2, vo.getLid());
		super.pstmt.setString(3, vo.getEname());
		super.pstmt.setString(4, vo.getJob());
		super.pstmt.setDouble(5, vo.getSal());
		if (vo.getComm() == null) {
			super.pstmt.setNull(6, Types.NULL);
		} else {
			super.pstmt.setDouble(6, vo.getComm());
		}
		super.pstmt.setString(7, vo.getPhoto());
		super.pstmt.setInt(8, vo.getEmpno());
		return super.pstmt.executeUpdate() > 0;
	}
 
	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Emp findById(Integer id) throws SQLException {
		Emp vo = null ;
		String sql = "SELECT empno,deptno,mid,lid,ename,job,sal,comm,hiredate,photo,flag FROM emp WHERE empno=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setInt(1, id);
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			vo = new Emp() ;
			vo.setEmpno(rs.getInt(1));
			vo.setDeptno(rs.getInt(2));
			vo.setMid(rs.getString(3));
			vo.setLid(rs.getInt(4));
			vo.setEname(rs.getString(5));
			vo.setJob(rs.getString(6));
			vo.setSal(rs.getDouble(7));
			vo.setComm(rs.getDouble(8));
			vo.setHiredate(rs.getDate(9));
			vo.setPhoto(rs.getString(10));
			vo.setFlag(rs.getInt(11));
		}
		return vo;
	} 

	@Override
	public List<Emp> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize)
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
	public List<Emp> findAllByFlag(Integer flag, String column, String keyWord, Integer currentPage, Integer lineSize)
			throws SQLException {
		List<Emp> all = new ArrayList<Emp>();
		String sql = "SELECT empno,deptno,mid,lid,ename,job,sal,comm,hiredate,photo,flag FROM emp WHERE " + column
				+ " LIKE ? AND flag=? LIMIT ?,?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setString(1, "%" + keyWord + "%");
		super.pstmt.setInt(2, flag);
		super.pstmt.setInt(3, (currentPage - 1) * lineSize);
		super.pstmt.setInt(4, lineSize);
		ResultSet rs = super.pstmt.executeQuery();
		while (rs.next()) {
			Emp vo = new Emp();
			vo.setEmpno(rs.getInt(1));
			vo.setDeptno(rs.getInt(2));
			vo.setMid(rs.getString(3));
			vo.setLid(rs.getInt(4));
			vo.setEname(rs.getString(5));
			vo.setJob(rs.getString(6));
			vo.setSal(rs.getDouble(7));
			vo.setComm(rs.getDouble(8));
			vo.setHiredate(rs.getDate(9));
			vo.setPhoto(rs.getString(10));
			vo.setFlag(rs.getInt(11));
			all.add(vo);
		}
		return all;
	}

	@Override
	public List<Emp> findAllByFlag(Integer flag, Integer currentPage, Integer lineSize) throws SQLException {
		List<Emp> all = new ArrayList<Emp>();
		String sql = "SELECT empno,deptno,mid,lid,ename,job,sal,comm,hiredate,photo,flag FROM emp WHERE flag=? LIMIT ?,?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, flag);
		super.pstmt.setInt(2, (currentPage - 1) * lineSize);
		super.pstmt.setInt(3, lineSize);
		ResultSet rs = super.pstmt.executeQuery();
		while (rs.next()) {
			Emp vo = new Emp();
			vo.setEmpno(rs.getInt(1));
			vo.setDeptno(rs.getInt(2));
			vo.setMid(rs.getString(3));
			vo.setLid(rs.getInt(4));
			vo.setEname(rs.getString(5));
			vo.setJob(rs.getString(6));
			vo.setSal(rs.getDouble(7));
			vo.setComm(rs.getDouble(8));
			vo.setHiredate(rs.getDate(9));
			vo.setPhoto(rs.getString(10));
			vo.setFlag(rs.getInt(11));
			all.add(vo);
		}
		return all;
	}

	@Override
	public Integer getAllCountByFlag(Integer flag, String column, String keyWord) throws SQLException {
		String sql = "SELECT COUNT(*) FROM emp WHERE " + column + " LIKE ? AND flag=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setString(1, "%" + keyWord + "%");
		super.pstmt.setInt(2, flag);
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getInt(1) ;
		}
		return 0;
	}

	@Override
	public Integer getAllCountByFlag(Integer flag) throws Exception {
		String sql = "SELECT COUNT(*) FROM emp WHERE flag=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setInt(1, flag);
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getInt(1) ;
		}
		return 0;
	}

}
