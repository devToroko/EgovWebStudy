package egovframework.sample.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;
import egovframework.sample.service.common.JDBCUtil;

@Repository("daoJDBC")
public class SampleDAOJDBC implements SampleDAO {
	
	// JDBC 관련 변수
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// SQL 명령어들
	private final String SAMPLE_INSERT = "INSERT INTO SAMPLE(ID, TITLE, REG_USER, CONTENT, REG_DATE) VALUES "
			+ "(( SELECT NVL(MAX(ID),0) + 1 FROM SAMPLE), ?, ?, ?, SYSDATE)";
	
	private final String SAMPLE_UPDATE = "UPDATE SAMPLE SET TITLE=?, REG_USER=?, CONTENT=? WHERE ID=?";
	private final String SAMPLE_DELETE = "DELETE FROM SAMPLE WHERE ID = ?";
	private final String SAMPLE_GET = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE WHERE ID = ?";
	private final String SAMPLE_LIST = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE ORDER BY REG_DATE DESC";
		
	public SampleDAOJDBC() {
		System.out.println("===> SampleDAOJDBC 생성");
	}

	public void insertSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로 insertSample() 기능처리 등록");
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(SAMPLE_INSERT);
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getRegUser());
		pstmt.setString(3, vo.getContent());
		pstmt.executeUpdate();
		JDBCUtil.close(pstmt, conn);
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로  updateSample() 기능처리 수정");
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(SAMPLE_UPDATE);
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getRegUser());
		pstmt.setString(3, vo.getContent());
		pstmt.setInt(4, vo.getId());
		pstmt.executeUpdate();
		JDBCUtil.close(pstmt, conn);
	}
	
	public void deleteSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로  deleteSample() 기능처리 삭제");
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(SAMPLE_DELETE);
		pstmt.setInt(1, vo.getId());
		pstmt.executeUpdate();
		JDBCUtil.close(pstmt, conn);
	}

	public SampleVO selectSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로  selectSample() 기능처리 상세 조회");
		SampleVO sample = null;
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(SAMPLE_GET);
		pstmt.setInt(1, vo.getId());
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			sample = new SampleVO();
			sample.setId(rs.getInt("ID"));
			sample.setTitle(rs.getString("TITLE"));
			sample.setRegUser(rs.getString("REG_USER"));
			sample.setContent(rs.getString("CONTENT"));
			sample.setRegDate(rs.getDate("REG_DATE"));
		}
		JDBCUtil.close(rs,pstmt, conn);
		return sample;
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("JDBC로  selectSampleList() 기능처리 목록 검색");
		List<SampleVO> sampleList = new ArrayList<SampleVO>();
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(SAMPLE_LIST);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			SampleVO sample = new SampleVO();
			sample.setId(rs.getInt("ID"));
			sample.setTitle(rs.getString("TITLE"));
			sample.setRegUser(rs.getString("REG_USER"));
			sample.setContent(rs.getString("CONTENT"));
			sample.setRegDate(rs.getDate("REG_DATE"));
			sampleList.add(sample);
		}
		JDBCUtil.close(rs,pstmt, conn);
		return sampleList;
	}
	
}
