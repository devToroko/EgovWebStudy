package egovframework.sample.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleService;
import egovframework.sample.service.SampleVO;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	
	@Resource(name="daoJDBC")
	private SampleDAO sampleDAO;
	
	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public void insertSample(SampleVO vo) throws Exception {
//		if(vo.getId() == 0 ) {
//			throw new IllegalArgumentException("0번 아이디는 등록할 수 없습니다.");
//		}
		sampleDAO.insertSample(vo);
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		sampleDAO.updateSample(vo);
	}
	
	public void deleteSample(SampleVO vo) throws Exception {
		sampleDAO.deleteSample(vo);
	}

	public SampleVO selectSample(SampleVO vo) throws Exception {
		return sampleDAO.selectSample(vo);
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		return sampleDAO.selectSampleList(vo);
	}
	
}
