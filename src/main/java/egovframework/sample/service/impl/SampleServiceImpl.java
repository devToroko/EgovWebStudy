package egovframework.sample.service.impl;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleService;

public class SampleServiceImpl implements SampleService {
	
	private SampleDAO sampleDAO;
	private String sampleString;
	
	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public SampleDAO getSampleDAO() {
		return sampleDAO;
	}


	public void setSampleDAO(SampleDAO sampleDAO) {
		this.sampleDAO = sampleDAO;
	}


	public String getSampleString() {
		return sampleString;
	}


	public void setSampleString(String sampleString) {
		this.sampleString = sampleString;
	}

	
	

	public void insertSample() throws Exception {
//		System.out.println("SampleService--Sample 등록");
		sampleDAO.insertSample();
	}
	
	public void updateSample() throws Exception {
//		System.out.println("SampleService---Sample 수정");
		sampleDAO.updateSample();
	}
	
	public void deleteSample() throws Exception {
//		System.out.println("SampleService---Sample 삭제");
		sampleDAO.deleteSample();
	}

	public void selectSample() throws Exception {
//		System.out.println("SampleService---Sample 상세 조회");
		sampleDAO.selectSample();
	}
	
	public void selectSampleList() throws Exception {
//		System.out.println("SampleService---Sample 목록 검색");
		sampleDAO.selectSampleList();
	}
	
}
