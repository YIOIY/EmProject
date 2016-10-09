package cn.mldn.util.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.mldn.util.dbc.DatabaseConnection;

public class AbstractDAO {
	protected Connection conn ;
	protected PreparedStatement pstmt ;
	public AbstractDAO() {
		this.conn = DatabaseConnection.get() ;
	}
	public Integer getLastId() throws SQLException {
		String sql = "SELECT LAST_INSERT_ID()" ;
		this.pstmt = this.conn.prepareStatement(sql) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getInt(1) ;
		} else {
			return null ; 
		}
	}
}
