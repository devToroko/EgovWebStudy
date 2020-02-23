package egovframework.sample.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;

@Repository("daoJDBC")
public class SampleDAOJDBC implements SampleDAO {

	public SampleDAOJDBC() {
		System.out.println("===> SampleDAOJDBC 생성");
	}

	public void insertSample() throws Exception {
		System.out.println("JDBC로 insertSample() 기능처리 등록");
	}
	
	public void updateSample() throws Exception {
		System.out.println("JDBC로  updateSample() 기능처리 수정");
	}
	
	public void deleteSample() throws Exception {
		System.out.println("JDBC로  deleteSample() 기능처리 삭제");
	}

	public void selectSample() throws Exception {
		System.out.println("JDBC로  selectSample() 기능처리 상세 조회");
	}
	
	public void selectSampleList() throws Exception {
		System.out.println("JDBC로  selectSampleList() 기능처리 목록 검색");
	}
	
}
