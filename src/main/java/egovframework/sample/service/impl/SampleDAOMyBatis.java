package egovframework.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;

@Repository("daoMyBatis")
public class SampleDAOMyBatis implements SampleDAO {

	public SampleDAOMyBatis() {
		System.out.println("===> SampleDAOMyBatis 생성");
	}

	public void insertSample(SampleVO vo) throws Exception {
		System.out.println("MyBatis로 insertSample() 기능처리 등록");
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		System.out.println("MyBatis로  updateSample() 기능처리 수정");
	}
	
	public void deleteSample(SampleVO vo) throws Exception {
		System.out.println("MyBatis로  deleteSample() 기능처리 삭제");
	}

	public SampleVO selectSample(SampleVO vo) throws Exception {
		System.out.println("MyBatis로  selectSample() 기능처리 상세 조회");
		return null;
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("MyBatis로  selectSampleList() 기능처리 목록 검색");
		return null;
	}
}
