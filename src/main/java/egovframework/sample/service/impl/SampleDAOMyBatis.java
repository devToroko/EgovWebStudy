package egovframework.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;

@Repository("daoMyBatis")
public class SampleDAOMyBatis extends EgovAbstractMapper implements SampleDAO {

	public SampleDAOMyBatis() {
		System.out.println("===> SampleDAOMyBatis 생성");
	}

	public void insertSample(SampleVO vo) throws Exception {
		System.out.println("===> MyBatis로 insertSample() 기능처리");
		insert("insertSample",vo);
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		System.out.println("===> MyBatis로 updateSample() 기능처리");
		update("updateSample",vo);
	}

	public void deleteSample(SampleVO vo) throws Exception {
		System.out.println("===> MyBatis로 deleteSample() 기능처리");
		delete("deleteSample",vo);
	}
	
	public SampleVO selectSample(SampleVO vo) throws Exception {
		System.out.println("===> MyBatis로 selectSample() 기능처리");
		return (SampleVO) selectOne("selectSample", vo);
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("===> MyBatis로 selectSampleList() 기능처리");
		return selectList("selectSampleList",vo);
	}
}