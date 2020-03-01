package egovframework.sample.service.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	public static Connection getConnection() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","book_ex3","book_ex3");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void close(Statement pstmt, Connection conn) {
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt = null;
		}
	
		try {
			if(!conn.isClosed() || conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	
	public static void close(ResultSet rs, Statement pstmt, Connection conn) {
		
		try {
			if(rs!=null) {rs.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
		
		
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt = null;
		}
	
		try {
			if(!conn.isClosed() || conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
		
	}
}
