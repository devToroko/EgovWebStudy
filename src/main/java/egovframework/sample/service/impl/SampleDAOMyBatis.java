package egovframework.sample.service.impl;

import egovframework.sample.service.SampleDAO;

public class SampleDAOMyBatis implements SampleDAO {

	public SampleDAOMyBatis() {
		System.out.println("===> SampleDAOMyBatis 생성");
	}

	public void insertSample() throws Exception {
		System.out.println("MyBatis로 insertSample() 기능처리 등록");
	}
	
	public void updateSample() throws Exception {
		System.out.println("MyBatis로  updateSample() 기능처리 수정");
	}
	
	public void deleteSample() throws Exception {
		System.out.println("MyBatis로  deleteSample() 기능처리 삭제");
	}

	public void selectSample() throws Exception {
		System.out.println("MyBatis로  selectSample() 기능처리 상세 조회");
	}
	
	public void selectSampleList() throws Exception {
		System.out.println("MyBatis로  selectSampleList() 기능처리 목록 검색");
	}
}
