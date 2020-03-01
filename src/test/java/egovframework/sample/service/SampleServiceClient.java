package egovframework.sample.service;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SampleServiceClient {
	
//	private static final Log LOGGER = LogFactory.getLog(SampleServiceClient.class); 
	
	public static void main(String[] args) throws Exception {
		
		
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container =  
				new GenericXmlApplicationContext("egovframework/spring/context-*.xml");
		
		//2. Spring 컨테이너로부터 SampleService 타입의 객체를 Lookup 한다.
		SampleService sampleService = (SampleService) container.getBean("sampleService");
		
		SampleVO vo = new SampleVO();
		vo.setTitle("임시 제목");
		vo.setRegUser("테스트");
		vo.setContent("임시 내용입니다....");
		sampleService.insertSample(vo);
		
		List<SampleVO> sampleList = sampleService.selectSampleList(vo);
		System.out.println("[ Sample List ]");
		sampleList.forEach(sample->System.out.println(sample));
		
		vo.setId(7);
		sampleService.deleteSample(vo);
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
	
	}
}
