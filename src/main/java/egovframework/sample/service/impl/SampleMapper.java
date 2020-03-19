package egovframework.sample.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.sample.service.SampleVO;

@Mapper("sampleMapper")
public interface SampleMapper {
	
	public void insertSample(SampleVO vo) throws Exception;
	
	public void updateSample(SampleVO vo) throws Exception;
	
	public void deleteSample(SampleVO vo) throws Exception;
	
	public SampleVO selectSample(SampleVO vo) throws Exception;
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception;
	
}
