package egovframework.sample.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SampleServiceClient {
	
	public static void main(String[] args) throws Exception {
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container =  
				new GenericXmlApplicationContext("egovframework/spring/context-common.xml");
		
		//2. Spring 컨테이너로부터 SampleServiceImpl 객체를 Lookup 한다.
		SampleService sampleService = (SampleService) container.getBean("sampleService");
		sampleService.insertSample();
		sampleService.selectSampleList();
		
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
		
	}
}
