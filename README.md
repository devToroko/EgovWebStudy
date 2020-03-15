# EgovWebTemplateMk

전자정부프레임워크에 대한 학습 프로젝트입니다.

전자정부프레임워크 퀵스타트(채규태 저) 라는 책을 기반으로 작성했습니다.

[표준프레임워크 실행환경 이해(p.225)] 부터 시작합니다.


제가 쓰느 프로젝트의 세부사항은
egov3.8 버전 ( 전자정부프레임워크에서 다운로드 가능) 입니다.

<br>
<br>
<br>

# 1. 프레임워크의 개념

프레임워크의 사전적 의미는 뼈대 혹은 틀이다. 소프트웨어의 관점에서 접근하면
프레임워크는 소프트웨어의 아키텍처에 해당하는 골격 코드를 의미한다.
이런 골격을 통해서 빠른 애플리케이션의 제작과 쉬운 유지보수가 가능하다.


### 프레임워크의 장점
- 빠른 구현시간
- 개발자들의 역량 획일화
- 관리의 용이
- 검증된 아키텍처의 재사용과 일관성 유지

### 스프링 프레임워크의 특징
- 경량(EJB 에 비해서)
- 제어의 역행
 
 - 낮은 결합도,즉 느슨한 결합이 가능하다.
  개발자가 직접 클래스 사이의 관계를 코드로 처리하면, 그 해당 객체의 변경시
  많은 힘을 들여야한다. 이러한 "관계"를 스프링 컨테이너에게 모두 맡긴다.
  
- 관점지향(AOP, Aspect Oriented Programming)
  - 핵심 비즈니스 로직과 각 비즈니스 메소드마다 반복해서 등장하는 로직을
  분리함으로써 응집도를 높게 개발할 수 있도록 지원한다.
  - 공통으로 사용하는 기능들을 오부의 독립된 "클래스"로 분리한다.
  - 코드에 직접적으로 명시하지 않고 선언적으로 처리하여, 코드 응집도를 높인다.
  
- 컨테이너
  - 애플리케이션 운용에 필요한 객체를 생성하고 객체간의 의존관계를 관리해준다.

<br>

그렇다면 표준프레임워크는 뭘까? 
대기업에 의한 SI 중소기업이 프레임워크 시장에 뛰어드는 것에 대한 부담을 덜기 위함이다.
참고로 표준프레임워크는 "스프링 프레임워크" 기반이다.

<br>

(이 다음부터 책에는 EgovFrame Web Project와 전자정부가 제공하는 여러템플릿을
같이 다운받아서 사용하는 모습을 보여주고 프로젝트의 구조를 분석한다)
(간단하게 구조 분석 내용을 정리하면 아래와 같다)

- src/main/java 
  - 자바소스들이 들어 있는 폴더로서 크게 비즈니스 컴포넌트와 웹 컴포넌트 패키지로 구성
  - service 패키지: 클라이언테에 공개할 인터페이스와 VO클래스
  - service.impl 패키지: 비즈니스 컴포넌트의 구현 클래스에 해당하는 클래스들
  - web : Controller 클래스들

- src/main/resources
  - Spring 설정 파일, MyBatis 설정 파일, Properties 파일, sql 파일 등.
  
- src/main/webapp
  - 웹관련 리소스


다음 장부터는 스프링의 개념과 함께
지금까지 본 전자정부가 제공하는 템플릿 프로젝트를 흉내내보는게 목적이다.


<br>
<br>

# 2. 표준프레임워크 실행환경 공통기반 레이어 

### 2-1 IOC

이제부터 실습 내용을 이미지를 통해서 보이고 간략한 설명만 붙이겠다.

<img src="https://user-images.githubusercontent.com/51431766/75105433-8c586180-5656-11ea-9e49-8650d7751034.png" width="50%"></img>

[File] -> [New] -> [eGovFrame Web Project] 를 선택해서 프로젝트 생성


<img src="https://user-images.githubusercontent.com/51431766/75105913-38e91200-565c-11ea-965f-8bc23e2d5c7d.png" width="50%"></img>

```java
package egovframework.sample.service.impl;

public class SampleServiceImpl {

	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public void insertSample() throws Exception {
		System.out.println("SampleService--Sample 등록");
	}
	
	public void updateSample() throws Exception {
		System.out.println("SampleService---Sample 수정");
	}
	
	public void deleteSample() throws Exception {
		System.out.println("SampleService---Sample 삭제");
	}

	public void selectSample() throws Exception {
		System.out.println("SampleService---Sample 상세 조회");
	}
	
	public void selectSampleList() throws Exception {
		System.out.println("SampleService---Sample 목록 검색");
	}
	
}
```

egovframework.sample.service.impl 에 클래스 생성 (VO 클래스는 무시하십쇼. 나중에 만듭니다)

<br>
<br>


---

<br>
<br>


![image](https://user-images.githubusercontent.com/51431766/75105965-c9bfed80-565c-11ea-8819-a298de5e1a80.png)
![image](https://user-images.githubusercontent.com/51431766/75106186-26230d00-565d-11ea-871e-ac8b2c7ec56e.png)

src/main/resources 폴더 하위에 egovframework 만들고, 그 하위에 spring 폴더 생성한다.
이 폴더에는 spring 관련 설정들을 모아놓을 것이다. 그리고 해당 폴더에 Spring Bean Configuration File을 생성한다.
이름은 context-common.xml로 하겠다. 그리고 xml의 내용은 아래와 같이 변경해주자.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl"></bean>
</beans>
```

<br>
<br>

---

<br>
<br>

![image](https://user-images.githubusercontent.com/51431766/75106294-38517b00-565e-11ea-93c8-b805970e59e8.png)

테스트를 위해서 src/test/java 에 egovframework.sample.service 패키지 생성후 SampleServiceClient.java 클래스를 생성해줍니다.
(정확한 이유는 모르겠지만 JUnit은 쓰지 않고 테스트를 합니다, 제 생각에는 스프링 컨테이너 자체를 직접 구동하는 것을 보여주기
위함이라고 생각합니다)

코드는 아래와 같이 작성하고 run java를 해주자
```java
package egovframework.sample.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SampleServiceClient {
	
	public static void main(String[] args) {
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container = 
				new GenericXmlApplicationContext("egovframework/spring/context-common.xml");
		
	}
}
```

그러면 Log4j 관련 에러가 뜨는데 일단 무시한다. 나중에 Log4j 설정파일을 작성하면 다 해결된다.

<br>
<br>

---

<br>
<br>


로그관련 파일을 작성하자

src/main/resources 바로 하위에  log4j2.xml 파일을 생성한다.
그리고 내용을 다음과 같이 한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration>
<Configuration>
	<Appenders>
		<Console name="console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d %5p [%c] %m%n" />
		</Console>
	</Appenders>
	<Loggers>
		 <Logger name="java.sql" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Logger name="egovframework" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Logger name="org.springframework" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Root level="INFO">
        	<AppenderRef ref="console" />
        </Root>
	</Loggers>
</Configuration>
```

<br>

설명
- \<Appender\> : 어디에 어떤 패턴으로 로그를 출력할지를 결정한다. 
로그 메시지는 크게 콘솔,파일, 데이터베이스에 출력할 수 있는데, \<Console\> 엘레맨트만 썻기에 콘솔만 출력한다.

- \<Logger\> : Appender에게 실질적으로 로그를 전달하여, Appender 가 출력을 하도록 한다.
	
이러고 나서 다시 테스트를 하면 로그가 제대로 나온다

![image](https://user-images.githubusercontent.com/51431766/75106392-5e2b4f80-565f-11ea-962d-fbdfb576a514.png)

<br>
참고로 위와 같은 로그가 찍히면 컨테이너를 생성했다고도 하지만, 컨테이너를 구동했다 라는 표현이 더 좋다.

<br>
<br>

---

<br>
<br>

스프링 컨테이너로부터 빈 객체를 검색하여서 비즈니스 메소드를 호출해보자
<br>
테스트 코드를 다음과 같이 바꾸고 실행해보자.

```java
package egovframework.sample.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import egovframework.sample.service.impl.SampleServiceImpl;

public class SampleServiceClient {
	
	public static void main(String[] args) throws Exception {
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container = 
				new GenericXmlApplicationContext("egovframework/spring/context-common.xml");
		
		//2. Spring 컨테이너로부터 SampleServiceImpl 객체를 Lookup 한다.
		//참고로 스프링 컨테이너에서 가져오는 객체가 정확히 무엇인지 모르기에 getBean은 Object를 반환한다.
		SampleServiceImpl sampleService = (SampleServiceImpl) container.getBean("sampleService");
		sampleService.insertSample();
		sampleService.selectSampleList();
		
		
		//3. Spring 컨테이너를 종료한다.
		//컨테이너는 close 메서드를 통해서 종료가 되는데, 이때 컨테이너는 종료되기 직전에 자신이
		//생성해서 관리했던 모든 객체들을 메모리에서 삭제한다.
		container.close();
		
	}
}
```
<br>

실행결과: <br>

![image](https://user-images.githubusercontent.com/51431766/75106463-4accb400-5660-11ea-8193-fd623b5e91b9.png)

<br>

앞선 과정을 정리하면 다음과 같다.
- 스프링 설정 파일(context-common.xml)을 로딩하여 컨테이너를 구동한다.
- 스프링 컨테이너는 <bean> 등록된 SampleServiceImpl 객체를 생성(Pre-Loading) 한다.
- 클라이언트가 getBean() 메소드로 아이디가 SampleService인 객체를 요청(Lookup) 한다.
- 컨테이너는 SampleServiceImpl 객체를 검색하여 리턴한다.
	
<br>
<br>

---
	
<br>
<br>

### 스프링 컨테이너의 종류
<br>
스프링에서는 BeanFactory와 ApplicatonContext 두 가지 타입의 컨테이너를 제공한다.
하지만 주로 더 많은 기능을 제공하는 ApplicationContext를 사용한다.(트랜잭션 관리, PreLoading 등)
이러한 ApplcationContext 를 구현한 클래스는 크게 두가지다.

- GenericXmlApplicationContext : 파일 시스템이나 클래스 패스에 있는 XML 설정 파일을 로딩하여 구동
- XmlWebApplicationContext : 웹 기반의 스프링 애플리케이션을 개발할 때 사용, 우리가 직접 생성할 일은 X

<br>
<br>

### 스프링 설정파일 (처음 보는 것 위주로 정리)
<br>

#### <\bean\> 의 id 와 name 속성
\<bean\>를 서로 구별하기 위해서 id 혹은 name이라는 속성을 준다.
id 속성값은 자바 변수명 선언 규칙과 동일하게 적용되지만, name은 이를 무시한다.
좀 더 규칙성이 있는 id 를 많이 사용한다고 한다.

#### <\bean\> 의 init-method와 destroy-method 속성
- 스프링 컨테이너가 객체를 생성한 후 init-method로 지정한 메소드가 실행된다.
- 스프링 컨테이너가 종료될 때 만들었던 객체를 없애는데, 이때 호출되는 메서드가 destory-method

아래 그림은 예제 코드와 테스트 실행 화면이다.

```java
package egovframework.sample.service.impl;

public class SampleServiceImpl {

	/* 생략 */
	
	// 아래 코드들 추가
	
	public String name;
	
	public void initMethod() {
		System.out.println("---> initMethod() 호출");
		name = "샘플 서비스 객체";
	}
	
	public void destoryMethod() {
		System.out.println("---> destoryMethod() 호출");
		name = null;
	}
}
```
<br>

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- context-common.xml 내용 수정 -->
	<bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl"
		init-method="initMethod" destroy-method="destoryMethod"	></bean>
</beans>
```
<br>

테스트 실행화면 <br>

![image](https://user-images.githubusercontent.com/51431766/75106792-79985980-5663-11ea-97b7-d5e1e0e329bd.png)

<br>

위의 \<bean\> 속성 말고도 scope, lazy-init 등 다양하지만, 다 설명은 X

<br>
<br>

### IOC를 이용한 비즈니스 컴포넌트
유지보수 과정에서 사용중인 객체를 다른 객체로 변경해야 한다면 객체를 사용하는 모든 클라이언트의
코드를 수정해야 한다. 이런 클라이언트가 많으면 많을수록 개체를 변경하는 것은 어려워지는데,
이런 문제를 다형성을 이용해서 간단하게 처리할 수 있다.

필요한 테스트 소스와 설정 파일은 다음과 같다.

<br>
<br>

1. SampleServiceImpl.java
```java
package egovframework.sample.service.impl;

public class SampleServiceImpl {

	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public void insertSample() throws Exception {
		System.out.println("SampleService--Sample 등록");
	}
	
	public void updateSample() throws Exception {
		System.out.println("SampleService---Sample 수정");
	}
	
	public void deleteSample() throws Exception {
		System.out.println("SampleService---Sample 삭제");
	}

	public void selectSample() throws Exception {
		System.out.println("SampleService---Sample 상세 조회");
	}
	
	public void selectSampleList() throws Exception {
		System.out.println("SampleService---Sample 목록 검색");
	}
	
}
```
<br>

2. AdvancedSampleServiceImpl.java

```java
package egovframework.sample.service.impl;

public class AdvancedSampleServiceImpl {
	
	public AdvancedSampleServiceImpl() {
		System.out.println("===> AdvancedAdvancedSampleServiceImplImpl 생성");
	}

	public void insertSample() throws Exception {
		System.out.println("AdvancedSampleServiceImpl--Sample 등록");
	}
	
	public void updateSample() throws Exception {
		System.out.println("AdvancedSampleServiceImpl---Sample 수정");
	}
	
	public void deleteSample() throws Exception {
		System.out.println("AdvancedSampleServiceImpl---Sample 삭제");
	}

	public void selectSample() throws Exception {
		System.out.println("AdvancedSampleServiceImpl---Sample 상세 조회");
	}
	
	public void selectSampleList() throws Exception {
		System.out.println("AdvancedSampleServiceImpl---Sample 목록 검색");
	}
}
```

<br>
<br>

3. context-common.xml 설정파일
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="sampleService" class="egovframework.sample.service.impl.AdvancedSampleServiceImpl"></bean>
	<!-- <bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl"></bean> -->
</beans>

```

<br>
<br>

4. 테스트 코드
```java
package egovframework.sample.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import egovframework.sample.service.impl.AdvancedSampleServiceImpl;

public class SampleServiceClient {
	
	public static void main(String[] args) throws Exception {
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container = 
				new GenericXmlApplicationContext("egovframework/spring/context-common.xml");
		
		//2. Spring 컨테이너로부터 SampleServiceImpl 객체를 Lookup 한다.
		AdvancedSampleServiceImpl sampleService = (AdvancedSampleServiceImpl) container.getBean("sampleService");
		sampleService.insertSample();
		sampleService.selectSampleList();
		
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
		
	}
}
```

<br>

===> 실행결과
![image](https://user-images.githubusercontent.com/51431766/75109244-f841c680-5664-11ea-9a08-4b6649c5c8ae.png)


<br>
<br>

잘된다. 하지만 만약에 수정하고 싶은 객체의 갯수가 다양할수록 어떤일이 일어날까?
그리고 이러한 호출을 이곳 저곳에서 하게 되면 그 코드를 다 어떻게 고칠 것인가?

이런 문제를 해결하기 위해서 다형성이 필요하다.

### 다형성 적용하기

순서는 다음과 같다

1. 인터페이스 추출
2. 해당 인터페이스를 구현한 객체를 생성
3. context-common.xml 설정파일에서는 구현 객체 중 원하는 객체를 선택
3. 빈 검색 후 반환 값의 형변환에서는 인터페이스로 형변환
4. 인터페이스 참조 변수를 통해서 구현 객체의 메서드를 호출!
5. 구현 객체는 context-common.xml bean의 class로 지정한 객체이다.

<br>
<br>

인터페이스를 추출할 때는 원하는 클래스에 마우스를 대고 Alt+Shift+t 를 누르고  \[Extract Interface...\] 를 선택한다.
<br>
<br>

![image](https://user-images.githubusercontent.com/51431766/75109402-e3196780-5665-11ea-9bcf-a9ac1e745d2b.png)


```java
package egovframework.sample.service;

public interface SampleService {

	void insertSample() throws Exception;

	void updateSample() throws Exception;

	void deleteSample() throws Exception;

	void selectSample() throws Exception;

	void selectSampleList() throws Exception;

}
```

<br>
<br>

이제 SampleServiceImpl 와 AdvancedSampleServiceImpl 클래스에 implements SampleService를 해준다.

그리고 나서 테스트 코드를 수정하고...
```java
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
```

<br>
<br>
context-common.xml 에서 원하는 빈 객체를 지정할 수 있다. 바꾸면서 실행하면? 다음과 같다.
<br>

![image](https://user-images.githubusercontent.com/51431766/75109448-c9c4eb00-5666-11ea-8b64-7385e41228e9.png)

<br>
<br>

---

<br>
<br>

### 스프링의 의존성 관리
스프링 프레임워크의 가장 중요한 특징은 <strong>객체의 생성과 의존관계를 컨테이너가 자동으로 관리된다는 점</strong>이다
IoC 는 Dependency Lookup과 Dependency Injection 두 가지로 나뉘며, 주로 Dependecy Injection(DI)를 많이 사용한다.
그리고 DI는 Setter Injection과 Constructor Injection으로 나뉜다.

DI은 객체 사이의 의존관계를 스프링 설정 파일에 등록된 정보를 바탕으로 컨테이너가 자동으로 처리해준다.

따라서 의존성 설정은 코드를 건들 필요없이 변경사항을 적용할 수 있다.

### 의존관계
의존관계란 다양한 연관 관계 중에서 어떤 객체가 다른 객체의 변수나 메소드를 이용하는 경우를 말한다.
그리고 이러한 변수나 메소드 사용을 위해서는 해당 객체의 래퍼런스 정보를 갖고 있어야한다.

실습으로 보자.

<br>
<br>
<br>
1. DAO 클래스 생성 , SampleServiceImpl 에 DI 할 예정

```java
package egovframework.sample.service.impl;

import egovframework.sample.service.SampleService;

public class SampleServiceImpl implements SampleService {

	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public void insertSample() throws Exception {
		System.out.println("SampleService--Sample 등록");
	}
	
	public void updateSample() throws Exception {
		System.out.println("SampleService---Sample 수정");
	}
	
	public void deleteSample() throws Exception {
		System.out.println("SampleService---Sample 삭제");
	}

	public void selectSample() throws Exception {
		System.out.println("SampleService---Sample 상세 조회");
	}
	
	public void selectSampleList() throws Exception {
		System.out.println("SampleService---Sample 목록 검색");
	}
	
}
```
<br>
<br><br>

2. DI 가 아닌 방식으로 의존성 확립
```java
package egovframework.sample.service.impl;

import egovframework.sample.service.SampleService;

public class SampleServiceImpl implements SampleService {
	
	private SampleDAOJDBC sampleDAO;
	
	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
		sampleDAO = new SampleDAOJDBC();
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
```
<br><br>

3. 실행결과
![image](https://user-images.githubusercontent.com/51431766/75109708-3fca5180-5669-11ea-95a8-e9073adc4436.png)

<br><br>

만약 SampleDAOJDBC 객체가 아닌 다른 객체로 의존성을 만들고 싶으면?
아마 SampleDAOJDBC를 사용하는 모~든 클래스의 소스를 뜯어 고쳐야할 거다.
그리고 이런 문제 발생하는 이유는 의존 관계에 있는 SampleDAOJDBC 클래스에 
대한 객체 생성 코드를 직접 SampleServiceImpl 소스에 명시했기 때문이다. 
스프링은 이 문제를 의존성 주입과 세터 인젝션 두 가지를 통해 해결한다.

<br><br><br>

### 의존성주입(생성자 / SETTER) + 다형성 적용

스프링 컨테이너는 XML 설정 파일에 드록된 클래스를 찾아서 객체를 생성할 때 기본적으로 
<strong>매개변수가 없는 생성자</strong>을 호출한다. 하지만 컨테이너가 기본 생성자 말고
다른 생성자를 호출하도록 설정할 수 있고, 이 과정에서 의존성 주입을 할 수 있다.

1. SampleServiceImpl.java
```java
package egovframework.sample.service.impl;

import egovframework.sample.service.SampleService;

public class SampleServiceImpl implements SampleService {
	
	private SampleDAOJDBC sampleDAO;
	
	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl()로 생성");
	}
	
	public SampleServiceImpl(SampleDAOJDBC sampleDAO) {
		System.out.println("===> SampleServiceImpl(SampleDAOJDBC)로 생성");
		this.sampleDAO = sampleDAO;
	}
	
	/* 이하 생략 */
}
```
<br><br>

2. context-common.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl">
		<constructor-arg name="sampleDAO" ref="jdbc"/>
	</bean>
	
	<bean id="jdbc" class="egovframework.sample.service.impl.SampleDAOJDBC"></bean>
	
</beans>
```

<br><br>

3. 테스트 코드 실행 <br>

![image](https://user-images.githubusercontent.com/51431766/75110055-8bcac580-566c-11ea-840b-7ad0fb494382.png)

<br><br>

4. 다형성 적용을 위한 인터페이스 추출 ( alt + shift + t => extract Interface )
```java
package egovframework.sample.service;

public interface SampleDAO {

	void insertSample() throws Exception;

	void updateSample() throws Exception;

	void deleteSample() throws Exception;

	void selectSample() throws Exception;

	void selectSampleList() throws Exception;

}
```
<br>

4-1. SampleDAOJDBC 도 implements 
```java
public class SampleDAOJDBC implements SampleDAO { /* 생략 */ }

//그리고
public class SampleServiceImpl implements SampleService {
	
	//  타입 변경!
	private SampleDAO sampleDAO;
	
	public SampleServiceImpl() {
//		System.out.println("===> SampleServiceImpl 생성");
//		sampleDAO = new SampleDAOJDBC();
	}
	
	//  파라미터 타입 변경!
	public SampleServiceImpl(SampleDAO sampleDAO) {
		System.out.println("===> SampleServiceImpl(SampleDAOJDBC)로 생성");
		this.sampleDAO = sampleDAO;
	}
	
	
	/* 이하 생략 */
}
```

<br><br>

5. SampleDAOMyBatis.java 생성
```java
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
```

<br><br>

6. context-common.xml 재작성
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl">
		<constructor-arg name="sampleDAO" ref="mybatis"/>
	</bean>
	
	<bean id="jdbc" class="egovframework.sample.service.impl.SampleDAOJDBC"></bean>
	<bean id="mybatis" class="egovframework.sample.service.impl.SampleDAOMyBatis"></bean>
</beans>
```

<br><br>

7. 테스트 코드 재 실행 <br>

![image](https://user-images.githubusercontent.com/51431766/75110238-393ed880-566f-11ea-92a3-b37dfe04d187.png)

<br><br>

참고로 constructor injection은 꼭 의존성 주입뿐만 아니라 단순한 문자열 같은 것도 생성자를 통해서 미리 
값을 넣을 수 있다.

<br>

ex)
```java
public class SampleServiceImpl implements SampleService {
	
	private SampleDAO sampleDAO;
	private String sampleString;
	
	public SampleServiceImpl(SampleDAO sampleDAO, String sampleString) {
		System.out.println("===> SampleServiceImpl(SampleDAOJDBC)로 생성");
		this.sampleDAO = sampleDAO;
		this.sampleString = sampleString;
		
	}
	/* 이하 생략 */
}
```
<br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl">
		<constructor-arg name="sampleDAO" ref="mybatis"/>
		<constructor-arg name="sampleString" value="wow"></constructor-arg>
	</bean>
	
	<bean id="jdbc" class="egovframework.sample.service.impl.SampleDAOJDBC"></bean>
	<bean id="mybatis" class="egovframework.sample.service.impl.SampleDAOMyBatis"></bean>
</beans>
```

<br><br><br>

### 세터 인젝션


1. 앞서 사용했던 파라미터가 있는 생성자들은 모두 지워주겠다. 그리고
게터 세터를 만들어주자. <br>


```java
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
	
	/* 이하 생략 */
}
```

<br><br>

2. context-common.xml 수정 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl">
		<property name="sampleDAO" ref="jdbc"/>
		<property name="sampleString" value="WOW"/>
	</bean>
	
	<bean id="jdbc" class="egovframework.sample.service.impl.SampleDAOJDBC"></bean>
	<bean id="mybatis" class="egovframework.sample.service.impl.SampleDAOMyBatis"></bean>
</beans>
```

<br><br>

3. 실행결과

![image](https://user-images.githubusercontent.com/51431766/75110421-5ecce180-5671-11ea-869f-732b6b51db97.png)


### 컬렉션 인젝션 (패스)
종류는 다음과 같다.

- \<list\>
- \<set\>
- \<map\>
- \<props\>

궁금하면 구글링!

<br><br>

# 어노테이션 기반 설정
앞서 학습을 통해서 XML 설정파일의 중요성은 알았을 것이다.
하지만 우리가 등록해야되는 빈이 너무 많아서 XML이 복잡해질 가능성이 크다.
이러면 XML을 나눠서 하는 것도 좋지만, 그러면 또 XML 파일이 많아져서 문제다.
결국은 XML의 "내용"을 줄일 필요가 있다. 이것을 위해서 <strong>어노테이션을 통한 설정</strong>
을 해야한다.

### 컴포넌트 스캔

<br>

![image](https://user-images.githubusercontent.com/51431766/75112618-d0fcf080-5688-11ea-9f54-53fe7d3558d2.png)

<br><br>

context-common.xml에 컴포넌트 스캔을 하도록 작성한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<!-- <bean id="sampleService" class="egovframework.sample.service.impl.SampleServiceImpl">
		<property name="sampleDAO" ref="jdbc"/>
		<property name="sampleString" value="WOW"/>
	</bean> -->
	
	<!-- 주석처리 -->
	<!-- <bean id="jdbc" class="egovframework.sample.service.impl.SampleDAOJDBC"></bean>
	<bean id="mybatis" class="egovframework.sample.service.impl.SampleDAOMyBatis"></bean> -->
	
	<context:component-scan base-package="egovframework">
		<context:exclude-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>
</beans>

```

### @Component 어노테이션

```java
package egovframework.sample.service.impl;

import org.springframework.stereotype.Component;

import egovframework.sample.service.SampleDAO;

@Component //추가!
public class SampleDAOJDBC implements SampleDAO {

	public SampleDAOJDBC() {
		System.out.println("===> SampleDAOJDBC 생성");
	}
	
	/* 이하 생략 */
}
```

<br><br>
테스트 코드 작성

```java
package egovframework.sample.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SampleServiceClient {
	
	public static void main(String[] args) throws Exception {
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container =  
				new GenericXmlApplicationContext("egovframework/spring/context-common.xml");
		
		//2. Spring 컨테이너로부터 SampleServiceImpl 객체를 Lookup 한다.
		SampleDAO sampleDAO = (SampleDAO) container.getBean("sampleDAOJDBC");
		sampleDAO.insertSample();
		sampleDAO.selectSampleList();
		
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
		
	}
}

```

<br><br>

테스트 결과 <br><br>

![image](https://user-images.githubusercontent.com/51431766/75112751-14a42a00-568a-11ea-9615-5f6b65c9ff66.png)

<br><br>

SampleDAOJDBC 객체를 검색(Lookup)할 때 적절한 아이디를 설정하고 싶으면 @Component 어노테이션에 다음과 같이 작성하면 된다.

```java
@Component("daoJDBC")
public class SampleDAOJDBC implements SampleDAO { ~~ }
```

<br>

이러고나서 lookup할 때 아이디를 "daoJDBC"를 주면 된다.


### 어노테이션의 확장
- @Service : 비즈니스 로직을 처리하는 Service 클래스
- @Repository : 데이터베이스 연동을 처리하는 DAO 클래스
- @Controller : 사용자 요청을 제어하는 Controller 클래스

단순히 이름만 이쁘게 표현하려고 저렇게 만든게 아니다.
@Controller 는 MVC 아키텍처에서 컨트롤러 객체로 인식하도록 해주며
@Repository는 DB 연동 과정에서 발생하는 예외를 변환해주는 특별한 기능이 추가되어 있다.


```java

import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;

@Repository("daoJDBC")
public class SampleDAOJDBC implements SampleDAO { ~ }
```

<br><br><br>

### 어노테이션을 이용한 의존성 주입
스프링은 의존관계 역시 XML 설정이 아닌 어노테이션을 이용하여 관리할 수 있게 해준다.

```java
@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	
	@Resource // 해당 변수의 타입을 체크, 해당 타입의 객체를 컨테이너가 갖고 있으면 DI 해준다. 
		// 없으면 NoSuchBeanDefinitionException!
	private SampleDAO sampleDAO;
	
	// 이하 생략
}
```
<br><br>

테스트 코드를 다음과 같이 수정후 실행해보자.

```java
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
```

<br>


테스트결과 <br><br>

![image](https://user-images.githubusercontent.com/51431766/75112889-df004080-568b-11ea-8b2e-b7283e5b0002.png)


<br><br>

### @Resource 어노테이션의 name 속성 사용하기


```java
@Repository("daoMyBatis")
public class SampleDAOMyBatis implements SampleDAO { ~~ }

-------------------------------------------------------------

@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	
	@Resource(name="daoMyBatis")
	private SampleDAO sampleDAO;
	//이하 생략
}
```

<br><br>

테스트 결과

![image](https://user-images.githubusercontent.com/51431766/75113360-a9aa2180-5690-11ea-8928-2e69479429ae.png)

<br><br>

### 어노테이션과 XML 병행하여 사용하기
어노테이션 덕분에 XML이 많이 축소되었지만, 의존관계가 변경되면 Java 소스를 건드려야한다.
이런 점을 감안하여 XML 설정과 어노테이션을 적절히 섞어서 써야한다.

- SampleServiceImpl 에 @Resource 어노테이션에 name 속성 제거
- SampleDAOJDBC , SampleDAOMyBatis 클래스 위에 선언된 어노테이션들은 모두 삭제하거 주석처리한다.
- context-common.xml 수정


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<context:component-scan base-package="egovframework">
		<context:exclude-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>

	<bean class="egovframework.sample.service.impl.SampleDAOJDBC" />
</beans>
```



그럼 어떤 기준으로 XML 과 어노테이션을 사용해야할까?
정해지진 않았지만 일반적으로 유지보수 과정에서 변경이 발생되는 클래스들은 
실제로 사용할 클래스 하나만 \<bean\> 등록한다. 그리고 변경이 발생되지 않는 클래스들은
어노테이션으로 처리하면 된다.

<br><br><br>


# 데이터베이스 연동

<br><br>

![image](https://user-images.githubusercontent.com/51431766/75603710-75ad8100-5b14-11ea-9dbd-c8e3170f7298.png)

<br><br>

pom.xml 작성 <br>

![image](https://user-images.githubusercontent.com/51431766/75603723-9249b900-5b14-11ea-9712-985cd6c82d3e.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/75603691-44cd4c00-5b14-11ea-929d-a2a5b0bc5ebb.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/75603735-c1f8c100-5b14-11ea-83c8-9c2c01ba51ea.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/75603761-f40a2300-5b14-11ea-8fce-3a6eb7ca0c68.png)

드래그해서 sql을 선택 후  alt + x 를 눌러서 실행한다.

<br><br>

결과: <br>
![image](https://user-images.githubusercontent.com/51431766/75603786-2025a400-5b15-11ea-903d-5c0561588de9.png)


## VO 클래스 만들기

<br><br>

```java
package egovframework.sample.service;

import java.sql.Date;

public class SampleVO {
	private int id;
	private String title;
	private String regUser;
	private String content;
	private Date regDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "SampleVO [id=" + id + ", title=" + title + ", regUser=" + regUser + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}	
}
```

<br><br><br>

### VO 클래스 적용하기

<br><br>

SampleVO를 이용하는 비즈니스 컴포넌트의 인터페이스와 구현 클래스를 각각 수정한다.
==> 인터페이스: SampleService, SampleDAO
==> 구현 클래스: SampleServiceImpl, SampleDAOJDBC, SampleDAOMyBatis

<br><br>

#### SampleDAO.java 
<br>

```java
package egovframework.sample.service;

import java.util.List;

public interface SampleDAO {

	void insertSample(SampleVO vo) throws Exception;

	void updateSample(SampleVO vo) throws Exception;

	void deleteSample(SampleVO vo) throws Exception;

	SampleVO selectSample(SampleVO vo) throws Exception;

	List<SampleVO> selectSampleList(SampleVO vo) throws Exception;

}
```

<br><br>

#### SampleDAOJDBC.java 
<br>

```java
package egovframework.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;

@Repository("daoJDBC")  // 다시 추가, 전자정부표준프레임워크와 비슷하게 만들기 위해서다.
public class SampleDAOJDBC implements SampleDAO {

	public SampleDAOJDBC() {
		System.out.println("===> SampleDAOJDBC 생성");
	}

	public void insertSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로 insertSample() 기능처리 등록");
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로  updateSample() 기능처리 수정");
	}
	
	public void deleteSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로  deleteSample() 기능처리 삭제");
	}

	public SampleVO selectSample(SampleVO vo) throws Exception {
		System.out.println("JDBC로  selectSample() 기능처리 상세 조회");
		return null;
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("JDBC로  selectSampleList() 기능처리 목록 검색");
		return null;
	}
	
}

```

<br><br>

#### SampleDAOMyBatis.java 
<br>

```java
package egovframework.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;

@Repository("daoMyBatis") // 다시 추가, 전자정부표준프레임워크와 비슷하게 만들기 위해서다.
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

```


<br><br>
다시 @Resource를 씀으로 context-common.xml 수정.


#### context-common.xml
<br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<context:component-scan base-package="egovframework">
		<context:exclude-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>

	<!-- <bean class="egovframework.sample.service.impl.SampleDAOJDBC" /> -->
</beans>
```


<br><br>



#### SampleService.java 
<br>


```java
package egovframework.sample.service;

import java.util.List;

public interface SampleService {

	void insertSample(SampleVO vo) throws Exception;

	void updateSample(SampleVO vo) throws Exception;

	void deleteSample(SampleVO vo) throws Exception;

	SampleVO selectSample(SampleVO vo) throws Exception;

	List<SampleVO> selectSampleList(SampleVO vo) throws Exception;
	
}
```

<br><br>


#### SampleServiceImpl.java 
<br>

```java
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
	
	public SampleDAO getSampleDAO() {
		return sampleDAO;
	}


	public void setSampleDAO(SampleDAO sampleDAO) {
		this.sampleDAO = sampleDAO;
	}

	public void insertSample(SampleVO vo) throws Exception {
		sampleDAO.insertSample(vo);
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		sampleDAO.updateSample(vo);
	}
	
	public void deleteSample(SampleVO vo) throws Exception {
		sampleDAO.deleteSample(vo);
	}

	public SampleVO selectSample(SampleVO vo) throws Exception {
		sampleDAO.selectSample(vo);
		return null;
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		sampleDAO.selectSampleList(vo);
		return null;
	}
	
}
```



#### SampleServiceClient.java 
테스트 코드 수정
<br>

```java
package egovframework.sample.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SampleServiceClient {
	
	private static final Log LOGGER = LogFactory.getLog(SampleServiceClient.class);
	
	public static void main(String[] args) throws Exception {
		//1. 스프링 컨테이너를 구동한다
		AbstractApplicationContext container =  
				new GenericXmlApplicationContext("egovframework/spring/context-common.xml");
		
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
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
		LOGGER.info("done");
		
	}
}
```
<br>

하지만 실행해도 현재 List를 읽어왔을 때 null이기 때문에 예외가 날라온다.


## DAO 클래스 구현

먼저 DAO클래스에서 공통으로 사용할 JDBCUtil 클래스를 만든다.

<br>
패키지 <br>

![image](https://user-images.githubusercontent.com/51431766/75604218-dd19ff80-5b19-11ea-995c-fe0576b1eadb.png)

<br><br>

### JDBCUtil

```java
package egovframework.sample.service.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	public static Connection getConnection() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","book_ex3","book_ex3");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void close(Statement pstmt, Connection conn) {
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt = null;
		}
	
		try {
			if(!conn.isClosed() || conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	
	public static void close(ResultSet rs, Statement pstmt, Connection conn) {
		
		try {
			if(rs!=null) {rs.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
		
		
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstmt = null;
		}
	
		try {
			if(!conn.isClosed() || conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
		
	}
}
```

<br><br>

#### SampleDAOJDBC

```java
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
```


### SampleServiceImpl
앞서 만든 SampleServiceImpl.java는 return 값들을 null로 줬다. 수정해주자.

```java
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
	
	public SampleDAO getSampleDAO() {
		return sampleDAO;
	}


	public void setSampleDAO(SampleDAO sampleDAO) {
		this.sampleDAO = sampleDAO;
	}

	public void insertSample(SampleVO vo) throws Exception {
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
```

<br><br>

### 결과

<br>

![image](https://user-images.githubusercontent.com/51431766/75604473-3b47e200-5b1c-11ea-9153-97dc7bc21386.png)

<br><br><br>

## 스프링 JDBC 

위에서 JDBCUtil 이라는 클래스를 통해서 많은 에러를 없앴지만 여전히 반복 코드가 등장한다. <br>
그런데 만약 데이터베이스 연동에 필요한 자바 코드를 누군가가 대신 처리해주고, 개발자는 실행되는 <br>
SQL 구문만 집중적으로 관리한다면 개발과 유지보수는 훨씬 편해질것이다. <br><br>

스프링은 JDBC 기반의 DB 연동 프로그램을 쉽게 개발할 수 있도록 <strong>JdbcTemplate</strong> 클래스를 제공한다.

<br><br><br>

### 스프링 JDBC 설정

<br><br>

pom.xml 추가

<br>

```xml
<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>
```

<br><br>

context-datasource.xml 추가

<br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- Oracle DataSource -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
		<property name="url" value="jdbc:oracle:thin:@127.0.0.1:1521:xe"/>
		<property name="username" value="book_ex3"/>
		<property name="password" value="book_ex3"/>
	</bean>
</beans>
```

<br><br>

 SampleRowMapper 생성

<br>

```java
package egovframework.sample.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import egovframework.sample.service.SampleVO;

public class SampleRowMapper implements RowMapper<SampleVO> {

	@Override
	public SampleVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SampleVO sample = new SampleVO();
		sample.setId(rs.getInt("ID"));
		sample.setTitle(rs.getString("TITLE"));
		sample.setRegUser(rs.getString("REG_USER"));
		sample.setContent(rs.getString("CONTENT"));
		sample.setRegDate(rs.getDate("REG_DATE"));
		return sample;
	}
	
}
```

<br><br>

 context-common.xml 생성

<br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
	

	<context:component-scan base-package="egovframework">
		<context:exclude-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>

	<!-- <bean class="egovframework.sample.service.impl.SampleDAOJDBC" /> -->
	
	<!-- JdbcTemplate -->
	<bean id="" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="dataSource"/>
	</bean>
</beans>
```

<br><br>

SampleDAOSpring 생성

<br>


```java
package egovframework.sample.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;


@Repository("daoSpring")
public class SampleDAOSpring implements SampleDAO {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate spring;
	
	// SQL 명령어들
	private final String SAMPLE_INSERT = "INSERT INTO SAMPLE(ID, TITLE, REG_USER, CONTENT, REG_DATE) VALUES "
			+ "(( SELECT NVL(MAX(ID),0) + 1 FROM SAMPLE), ?, ?, ?, SYSDATE)";
	
	private final String SAMPLE_UPDATE = "UPDATE SAMPLE SET TITLE=?, REG_USER=?, CONTENT=? WHERE ID=?";
	private final String SAMPLE_DELETE = "DELETE FROM SAMPLE WHERE ID = ?";
	private final String SAMPLE_GET = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE WHERE ID = ?";
	private final String SAMPLE_LIST = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE ORDER BY REG_DATE DESC";
	
	public SampleDAOSpring() {
		System.out.println("===> SampleDAOSpring 생성");
	}

	public void insertSample(SampleVO vo) throws Exception {
		System.out.println("Spring로 insertSample() 기능처리 등록");
		Object[] args = {vo.getTitle(),vo.getRegUser(),vo.getContent()};
		spring.update(SAMPLE_INSERT,args);
	}
	
	public void updateSample(SampleVO vo) throws Exception {
		System.out.println("Spring로  updateSample() 기능처리 수정");
		Object[] args = {vo.getTitle(),vo.getRegUser(),vo.getContent(),vo.getId()};
		spring.update(SAMPLE_UPDATE,args);
	}
	
	public void deleteSample(SampleVO vo) throws Exception {
		System.out.println("Spring로  deleteSample() 기능처리 삭제");
		spring.update(SAMPLE_DELETE,vo.getId());
	}

	public SampleVO selectSample(SampleVO vo) throws Exception {
		System.out.println("Spring로  selectSample() 기능처리 상세 조회");
		Object[] args = {vo.getId()};
		return spring.queryForObject(SAMPLE_GET, args ,new SampleRowMapper());
	}
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("Spring로  selectSampleList() 기능처리 목록 검색");
		return spring.query(SAMPLE_LIST, new SampleRowMapper());
	}	
}
```

<br><br> 

SampleServiceImpl 수정

<br>

```java
@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	
	@Resource(name="daoSpring") // 수정
	private SampleDAO sampleDAO;
	
	//이하 생략
	
}
```

<br><br> 

SampleServiceClient 수정

<br>

```java
AbstractApplicationContext container =  
	new GenericXmlApplicationContext("egovframework/spring/context-*.xml");
```

<br><br> 

결과: <br>
![image](https://user-images.githubusercontent.com/51431766/75612840-bb953400-5b6a-11ea-82fb-ac940a06c417.png)


<br><br>


# 스프링 AOP (Aspect Oriented Programming)

<br><br>

## AOP 개요

<br>

비즈니스 컴포넌트 개발에서 가장 중요한 두 가지 원칙은 낮은 결합도와 높은 응집도를 유지하는 것이다.
스프링의 의존성 주입(DI) 덕분에 비즈니스 컴포넌트를 구성하는 객체들의 결합도를 떨어뜨릴 수 있어서 <br> 
쉽게 <strong>변경</strong>이 가능했다. <br>
스프링의 IoC 가 결합도와 관련된 기능이라면, 지금부터 살펴볼 AOP(Aspect Oriented Programming)는 <br>
<strong>응집도</strong>와 관련되어 있다.

<br><br>

엔터프라이즈 애플리케이션은 무수히 많은 비즈니스 로직을 갖고 있다. 그리고 이런 비즈니스 로직에는 "부가적인 기능"을<br>
위한 코드들이 매번 모든 비즈니스 로직에서 나오기 일수이다. 이런 코드들을 소홀히 해서는 안 된다. <br>
이런 기능도 비즈니스 로직만큼이나 중요한 것이기 때문이다. 

<br>

AOP는 이렇게 부가적인 공통 코드들을 효율적으로 관리하는 것에 주목한다.

<br>

AOP 를 공부하면서 자주 나온느 단어가 있는데 아래와 같다. <br>
- 횡단 관심사(Crosscutting Concerns) : 비즈니스 메소드마다 공통으로 등장하는 로깅이나 예외 로직 
- 핵심 관심사(Core Concerns) : 사용자의 요청에 따라 실제로 수행되는 핵심 비즈니스 로직

<br>

## 스프링 AOP 설정

![image](https://user-images.githubusercontent.com/51431766/75620670-fcbf2f80-5bce-11ea-9733-42ef86475ad6.png)

![image](https://user-images.githubusercontent.com/51431766/75620435-5a05b180-5bcc-11ea-804f-ea311c2d5858.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">

	<!-- 횡단 관심에 해당하는 Advice 등록  -->
	<bean id="advice" class="egovframework.sample.service.common.SampleAdvice"></bean>
	
	<!-- AOP 설정 -->
	<aop:config>
		<aop:pointcut id="allPointcut" expression="execution(* egovframework.sample..*Impl.*(..))" />
		<aop:aspect ref="advice">
			<aop:before pointcut-ref="allPointcut" method="advancedBeforeLogic"/>
		</aop:aspect>
	</aop:config>
	
</beans>

```

```java
package egovframework.sample.service;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SampleServiceClient {
	
	
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
```

<br><br>

결과: <br>

![image](https://user-images.githubusercontent.com/51431766/75620684-1fe9df00-5bcf-11ea-8c2d-a72bb07b8b00.png)

(참고로 현재 log4j2.xml 의 내용을 약간 변경해서 위처럼 많은 내용이 안 나오는 것이다

```xml
<Logger name="org.springframework" level="WARN" additivity="false">
    <AppenderRef ref="console" />
</Logger>
```
)


<br><br>


## AOP 용어 및 기본 설정
<br>

1\. 조인포인트(JoinPoint) 
(포인트컷 대상이 될 수 있는) 모~든 비즈니스 로직들을 의미한다.

2\. 포인트컷(Pointcut)
필터링된 조인포인트를 의미한다. ex) 등록,삭제,수정은 트랜잭션 처리를 하는 공통기능 필요 / 조회는 필요 X
```xml
<!-- context-aspect.xml의 일부 -->
<!-- AOP 설정 -->
<aop:config>
	<!-- crud에 대해서 -->
	<aop:pointcut id="allPointcut" expression="execution(* egovframework.sample..*Impl.*(..))" />
	<aop:pointcut id="selectPointcut" expression="execution(* egovframework.sample..*Impl.select*(..))" />
	<aop:aspect ref="advice">
		<aop:before pointcut-ref="allPointcut" method="advancedBeforeLogic"/>
	</aop:aspect>
</aop:config>
```
포인트컷의 표현식 (expression) 태그는 중요함으로 검색해보는 것을 추천.


3\. 어드바이스(Advice)
횡단관심에 해당하는 공통 기능의 코드를 의미한다. 독립된 클래스의 메소드로 작성된다.
이러한 어드바이스로 구현된 메소드가 언제 동작할지는 스프링 설정파일로 지정할 수 있다.


```java
package egovframework.sample.service.common;

public class SampleAdvice {
	
	public void beforeLogic() {
		System.out.println("[사전 처리] 비즈니스 로직 수행 전 동작");
	}
	
	public void afterLogic() {
		System.out.println("[사후 처리] 비즈니스 로직 수행 후 동작");
	}
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">

	<!-- 횡단 관심에 해당하는 Advice 등록  -->
	<bean id="advice" class="egovframework.sample.service.common.SampleAdvice"></bean>
	
	<!-- AOP 설정 -->
	<aop:config>
		<aop:pointcut id="allPointcut" expression="execution(* egovframework.sample..*Impl.*(..))" />
		<aop:pointcut id="selectPointcut" expression="execution(* egovframework.sample..*Impl.select*(..))" />
		<aop:aspect ref="advice">
			<aop:before pointcut-ref="allPointcut" method="beforeLogic"/>
			<aop:after pointcut-ref="selectPointcut" method="afterLogic"/>
		</aop:aspect>
	</aop:config>
	
</beans>
```

<br>
결과: <br>

![image](https://user-images.githubusercontent.com/51431766/75621001-a5bb5980-5bd2-11ea-8805-107ce95d69af.png)

<br><br>

4\. 위빙(Weaving)

<br>

포인트컷으로 지정한 핵심 관심 메소드가 호출될 때, 어드바이스에 해당하는 관심 메소드가 삽입되는 과정을 의미한다.

<br><br>

5\. 애스팩트(Aspect) 또는 어드바이저(Advisor)

<br>

포인트컷 + 어드바이스의 결합이다, 즉 어떤 포인트컷 메소드에 대해서 어떤 어드바이스 메소드를 실행할지를 결정한다. <br>


6\. 정리

사용자가 비즈니스 컴포넌트의 여러 "조인포인트"를 호출 ==> 이때 특정 포인트컷으로 지정된 메소드가 호출되는 순간 <br>
==> 애스팩트에 설정한 대로 위빙이 일어난다.


## 어드바이스 동작 시점

<br>

Before :  비즈니스 로직 실행 전 동작
After  :  After-Return , After-Throwing, After 
Around :  실행 전후 처리

<br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">

	<!-- 횡단 관심에 해당하는 Advice 등록  -->
	<bean id="advice" class="egovframework.sample.service.common.SampleAdvice"></bean>
	
	<!-- AOP 설정 -->
	<aop:config>
		<aop:pointcut id="allPointcut" expression="execution(* egovframework.sample..*Impl.*(..))" />
		<aop:pointcut id="selectPointcut" expression="execution(* egovframework.sample..*Impl.select*(..))" />
		<aop:aspect ref="advice">
			<aop:before pointcut-ref="allPointcut" method="beforeLogic"/>
			<aop:after-returning pointcut-ref="selectPointcut" method="afterReturningLogic"/>
			<aop:after-throwing pointcut-ref="allPointcut" method="afterThrowingLogic"/>
			<aop:after pointcut-ref="allPointcut" method="afterLogic"/>
			<aop:around pointcut-ref="allPointcut" method="aroundLogic"/>
		</aop:aspect>
	</aop:config>
	
</beans>
```

<br><br>


```java
package egovframework.sample.service.common;

import org.aspectj.lang.ProceedingJoinPoint;

public class SampleAdvice {
	
	public void beforeLogic() {
		System.out.println("[사전 처리] 비즈니스 로직 수행 전 동작");
	}
	
	public void afterLogic() {
		System.out.println("[사후 처리] 비즈니스 로직 수행 후 무조건 동작");
	}
	
	public void afterReturningLogic() {
		System.out.println("[사후 처리] 비즈니스 로직 리턴 값을 받아서 동작");
	}
	
	public void afterThrowingLogic() {
		System.out.println("[예외 처리] 비즈니스 로직 수행 중 예외 발생");
	}
	
	public Object aroundLogic(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("[BEFORE]: 비즈니스 메소드 수행 전에 처리할 내용...");
		Object returnObj = pjp.proceed();
		System.out.println("[AFTER]: 비즈니스 메소드 수행 후에 처리할 내용...");
		return returnObj;
	}
	
}
```

<br>

결과: <br>
![image](https://user-images.githubusercontent.com/51431766/75622001-da351280-5bde-11ea-9d2a-e4fd26674a4b.png)


<br><br>

## JoinPoint와 바인드 변수

<br>

AOP 의 기능들을 사용해서 공통된 로직을 처리하는 것도 좋지만, 해당 로직에 대한 정보 또한 알고 싶으면 <br>
JoinPoint 인터페이스를 사용하면된다. JoinPoint에서 제공하는 융용한 메소드든 다음과 같다. <br>

| 메소드                   	| 설명                                                                                           |
|--------------------------	|--------------------------------------------------------------------------------------------------|
| Signature getSignature() 	| 클라이언트가 호출한 메소드의 시그니처(반환형,이름,매개변수)<br>정보가 저장된 Signature 객체 반환 	|
| Object getTarget()       	| 클라이언트가 호출한 비즈니스 메소드를 포함한 비즈니스<br>객체 반환                               	|
| Object[] getArgs()       	| 클라이언트가 메소드를 호출할 때 넘겨준 인자 목록을 Object <br>배열로 반환                        |


<br><br>

그리고  getSignature() 가 반환하는 Signature 객체를 이용하면 다양한 정보를 얻을 수 있다.


| 메소드                 	| 설명                                                                                   	|
|------------------------	|----------------------------------------------------------------------------------------	|
| String getName()       	| 클라이언트가 호출한 메서드 이름 반환                                                   	|
| String toLongString()  	| 클라이언트가 호출한 메소드의 반환형, 이름, 매개변수를 패키지 경로까지 포함하여 반환 	|
| String toShortString() 	| 클라이언트가 호출한 메소드 시그니처를 축약한 문자열로 반환                             	|

<br><br>

JoinPoint는 어드바이스의 종류에 따라 사용 방법이 다소 다르다. 상세하게 알아보자.


---


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">

	<!-- 횡단 관심에 해당하는 Advice 등록  -->
	<bean id="advice" class="egovframework.sample.service.common.SampleAdvice"></bean>
	
	<!-- AOP 설정 -->
	<aop:config>
		<aop:pointcut id="allPointcut" expression="execution(* egovframework.sample..*Impl.*(..))" />
		<aop:pointcut id="selectPointcut" expression="execution(* egovframework.sample..*Impl.select*(..))" />
		<aop:aspect ref="advice">
			<aop:before pointcut-ref="allPointcut" method="beforeLogic"/>
			<aop:after-returning pointcut-ref="selectPointcut" method="afterReturningLogic" returning="returnObj"/>
			<aop:after-throwing pointcut-ref="allPointcut" method="afterThrowingLogic" throwing="exceptObj"/>
			<aop:after pointcut-ref="allPointcut" method="afterLogic"/>
			<aop:around pointcut-ref="allPointcut" method="aroundLogic" />
		</aop:aspect>
	</aop:config>
	
</beans>

```

<br><br>

```java
package egovframework.sample.service.common;

import java.sql.SQLException;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

import egovframework.sample.service.SampleVO;

public class SampleAdvice {
	
	public void beforeLogic(JoinPoint jp) {
		String method = jp.getSignature().getName();
		Object[] args = jp.getArgs();
		
		System.out.println("[사전 처리] "+method+"() 메소드 ARGS 정보 : "+args[0].toString());
	}
	
	public void afterLogic() {
		System.out.println("[사후 처리] 비즈니스 로직 수행 후 무조건 동작");
	}
	
	public void afterReturningLogic(JoinPoint jp, Object returnObj) {
		String method = jp.getSignature().getName();
		System.out.println("[사후 처리] "+method+"() 리턴값을 받아서 동작");
		if(returnObj instanceof List) {
			@SuppressWarnings("unchecked")
			List<SampleVO> sampleList = (List<SampleVO>)returnObj;
			System.out.println("검색된 데이터: "+sampleList.size()+"건");
		}
	}
	
	public void afterThrowingLogic(JoinPoint jp, Exception exceptObj) {
		String method = jp.getSignature().getName();
		System.out.println("[예외 처리] "+method+"() 메소드 수행 중 예외 발생!");
		
		if(exceptObj instanceof IllegalArgumentException) {
			System.out.println("부적절한 아규먼트 정보가 입력되었습니다.");
		} else if (exceptObj instanceof SQLException) {
			System.out.println("데이터베이스 연동에 문제가 발생하였습니다.");
		} else {
			System.out.println("문제발생");
		}
		
	}
	
	public Object aroundLogic(ProceedingJoinPoint pjp) throws Throwable {
		String method = pjp.getSignature().getName();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object obj = pjp.proceed();
		
		stopWatch.stop();
		System.out.println(method+"() 메소드 수행에 걸린 시간: "+stopWatch.getTotalTimeMillis()+"(ms)초");
		
		return obj;
	}
	
}

```

<br><br>

결과: <br>

![image](https://user-images.githubusercontent.com/51431766/75622597-8da10580-5be5-11ea-8e8f-080bdd492b65.png)


---

<br><br><br>


# 실행환경 공통 기능

- 예외처리
- 트랜잭션 처리
- 아이디 제네레이션(전자정부프레임워크)
- 로깅 처리

<br><br>

## 예외처리
스프링의 AOP를 사용해서 예외처리 공통기능을 처리해보자. <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">

	<!-- 횡단 관심에 해당하는 Advice 등록  -->
	<!-- <bean id="advice" class="egovframework.sample.service.common.SampleAdvice"></bean> -->
	
	<!-- AOP 설정 -->
	<!-- <aop:config>
		<aop:pointcut id="allPointcut" expression="execution(* egovframework.sample..*Impl.*(..))" />
		<aop:pointcut id="selectPointcut" expression="execution(* egovframework.sample..*Impl.select*(..))" />
		<aop:aspect ref="advice">
			<aop:before pointcut-ref="allPointcut" method="beforeLogic"/>
			<aop:after-returning pointcut-ref="selectPointcut" method="afterReturningLogic" returning="returnObj"/>
			<aop:after-throwing pointcut-ref="allPointcut" method="afterThrowingLogic" throwing="exceptObj"/>
			<aop:after pointcut-ref="allPointcut" method="afterLogic"/>
			<aop:around pointcut-ref="allPointcut" method="aroundLogic" />
		</aop:aspect>
	</aop:config> -->
	
	
	<!-- 공통기능: 예외처리 -->
	<bean id="exceptionTransfer" class="egovframework.rte.fdl.cmmn.aspect.ExceptionTransfer"></bean>	
	
	<aop:config>
		<aop:pointcut expression="execution(* egovframework.sample..impl.*Impl)" id="exceptionPointcut"/>
		<aop:aspect ref="exceptionTransfer">
			<aop:after-throwing pointcut-ref="exceptionPointcut" method="transfer" throwing="exception"/>
		</aop:aspect>
	</aop:config>
</beans>
```
<br>

결과: <br>

![image](https://user-images.githubusercontent.com/51431766/75624044-d19b0700-5bf3-11ea-83ac-6a740a6a00aa.png)

<br><br><br>

## 트랜잭션 처리
스프링의 AOP를 사용한다. 다만 이전처럼 \<aop:aspect\> 가 아니라 \<aop:advisor\> 를 사용한다. <br>
일단 context-transaction.xml 을 하나 만들자. 그리고 namespace도 아래와 같이 설정해주자. <br>

![image](https://user-images.githubusercontent.com/51431766/75624116-b67cc700-5bf4-11ea-98af-f1586f5b1625.png)

<br><br>

### 트랜잭션 관리자 등록

<br>

트랜잭션 관련설정에서 가장 먼저 등록하는 것은 트랜잭션 관리자 클래스다. 스프링은 다양한 트랜잭션 관리자를 지원한다. <br>
(어떤 기술을 쓰냐에 따라 관리자가 바뀐다) <br>

스프링이 지원하는 모든 트랜잭션 관리자 클래스는 PlatformTransationManager 인터페이스를 구현하고 있다. <br>
이 인터페이스에는 commit(TransactionStatus status), rollback(TransactionStatus status) 메서드가 선언되어있다. <br>

우리는 구현 클래스 중에서 DataSourceTransactionManager  를 이용할 것이다.  이 클래스는 Spring JDBC를 사용하거나 <br>
IBatis 혹은 MyBatis 로 데이터베이스를 연동할 때 트랜잭션을 처리해주기 때문이다. <br>
(JPA의 경우는 JPATransactionManager 로 변경해주면 된다) <br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">

	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
</beans>
```

<br><br>

### 트랜잭션 어드바이스 등록

위처럼 xml을 고친것만으로는 자동으로 트랜잭션이 관리되는 것은 아니다! <br>
<strong>PlatformTransationManager 구현 객체 스스로 자신이 가진 메소드(commmit, rollback)를 실행할 수는 없다.</strong> <br>
이제 이 트랜잭션 관리자를 이용하여 트랜잭션을 제어하는 <strong>어드바이스</strong>를 추가로 등록해야 한다. <br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">

	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	<tx:advice id="txAdvice" transaction-manager="txManager">
		<tx:attributes>
			<tx:method name="*" rollback-for="Exception"/>
		</tx:attributes>
	</tx:advice>
</beans>
```

<br>

우리가 앞서했던 AOP에서는 어드바이스 클래스를 직접 구현했다. 하지만 트랜잭션 관리 어드바이스는 직접 구현할 <br>
없으며 , 스프링 컨테이너가 \<tx:advice\> 설정을 참조하여 <strong>자동으로 생성한다</strong>. <br>
여기에서 중요한 것은 컨테이너가 생성한 트랜잭션 관련 어드바이스 객체의 메소드 이름을 확인할 수 없다는 것이다<br>
우리가 알 수 있는 것은 단지 id 속성으로 지정된 어드바이스의 아이디와 transaction-manager 속성으로 어드바이스가<br>
참조하는 트랜잭션 관리자 정보뿐이다. 위 설정은 txAdvice 라는 어드바이스가 앞에서 지정한 txManager를 이용하여 <br>
트랜잭션을 관리한다는 설정이다. \<tx:attributes\>의 자식 앨리먼트로 \<tx:method\>로 트랜잭션을 적용할 메소드를 <br>
적용할 수 잇다. 

<br><br>


### AOP 설정을 통한 트랜잭션 적용
우리가 여태까지 써왔던 \<aop:aspect\> 는 ref 속성을 통해서 참조하려는 빈의 아이디를 알아야 했고 <br>
특히 해당 빈의 메서드 이름까지 알아야했다. 하지만 현재 우리가 만든 어드바이스는 그런 정보를 하나도 <br>
하나도 알 수 없다. 그러므로 \<aop:aspect\>를 쓸 수 없는 것이다. <br><br>

아무튼 지금까지 만든 어드바이스를 적용하기 위해서는 결국 \<aop:aspect\> 에 적용해야한다. <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">

	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	<tx:advice id="txAdvice" transaction-manager="txManager">
		<tx:attributes>
			<tx:method name="*" rollback-for="Exception"/>
		</tx:attributes>
	</tx:advice>
	
	<aop:config>
		<aop:pointcut id="requiredTx" expression="execution(* egovframework.sample..impl.*Impl.*(..))" />
		<aop:advisor advice-ref="txAdvice" pointcut-ref="requiredTx"/>
	</aop:config>
</beans>
```

---

<br>

### 테스트
테스트 내용은 하나의 insert 이후에 고의로 throw Exception을 할 것이다. 그래서 예외를 던지기 직전의 insert 된  <br>
정보가 없어졌는지를 확인하는 것이다. <br>

현재상황 Sample 테이블의 상황은 아래와 같다.

![image](https://user-images.githubusercontent.com/51431766/75624777-d57e5780-5bfa-11ea-9b81-fbfc0ccff710.png)

<br>

7번 데이터가 들어갈 것이고, 그 이후에 예외가 터져서 7번 데이터가 다시 없어지는 것을 확인 할 것이다.

<br>

```java
@Service("sampleService")
public class SampleServiceImpl implements SampleService {

	@Resource(name="daoSpring")
	private SampleDAO sampleDAO;
	
	public void insertSample(SampleVO vo) throws Exception {

		sampleDAO.insertSample(vo);
		throw new IllegalArgumentException();
	}
}
```

<br>

테스트 코드 내용

```java
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
		
//		vo.setId(7);
//		sampleService.deleteSample(vo);
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
	
	}
}
```

<br>

결과: <br>

![image](https://user-images.githubusercontent.com/51431766/75625030-6fdf9a80-5bfd-11ea-8dee-8e9ebb14a16b.png)

<br>

![image](https://user-images.githubusercontent.com/51431766/75625049-956ca400-5bfd-11ea-9bb1-b4ed369cd1b0.png)

7번 글이 등록되지 않았음을 확인했다.
(참고로 앞서 만들었던 예외처리 공통 기능 빈을 잠시 주석처리하고 했다)

<br><br><br>

## 아이디 제너레이션

<br>

Primary key를 단순히 숫자로 나타내는게 아니라 시퀀스나 서브쿼리를 이용하여 복잡한 문자열을 만들 수도 있다<br>

표준프레임워크는 이런 문제를 아이디 제너레이션 서비스를 통해 간단하게 해결한다. <br>

### 테이블 수정 및 생성

<br>

일단 테이블을 수정하자. <br><br>

![image](https://user-images.githubusercontent.com/51431766/75627587-c73d3500-5c14-11ea-8b59-936de180cc8f.png)

```sql
--DROP TABLE SAMPLE;

CREATE TABLE SAMPLE (
	ID VARCHAR2(12) PRIMARY KEY,
	TITLE VARCHAR2(200),
	REG_USER VARCHAR2(20),
	CONTENT VARCHAR2(2000),
	REG_DATE DATE DEFAULT SYSDATE
);

INSERT INTO SAMPLE VALUES('SAMPLE-00001','JAVA Programming','관리자','JAVA 관련 글만 등록하세요.',SYSDATE);

--DROP TABLE IDS;

CREATE TABLE IDS(
	TABLE_NAME VARCHAR2(16) PRIMARY KEY,
	NEXT_ID NUMBER(30) NOT NULL
);

INSERT INTO IDS VALUES('SAMPLE',2);

```

(다 작성하고 드래그 해서 <code>Alt + x </code>를 누르면 SQL이 실행된다.

<br><br>

SampleVO도 고쳐준다

<br>

```java
public class SampleVO {
	private String id;
	private String title;
	private String regUser;
	private String content;
	private Date regDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	// 이하 생략
	
}
```
<br>

이렇게 고치고 나오는 에러들을 싹다 고쳐주자. 이클립스에 잘 나오니 차근차근 고쳐나가면 된다. <br>
쉬우니까 스샷이나 소스는 올리지 않겠다.

<br><br>

## 아이디 제너레이션 서비스 설정

<br><br>

이제 아이디 제너레이션 서비스 관련 스프링 설정을 추가해야 한다. <br>
src/main/resources 소스 폴더에 context-idgen.xml 설정 파일을 생성하고 다음과 같이 작성한다. <br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
		<property name="basenames">
			<list>
				<value>
					classpath:/egovframework/rte/fdl/idgnr/messages/idgnr
				</value>
			</list>
		</property>
	</bean>
</beans>
```

<br><br>

두 번째로 등록할 클래스는 실제로 유일한 아이디를 생성해주는 클래스다. 다음과 같이 이어서 작성한다. <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
		<property name="basenames">
			<list>
				<value>
					classpath:/egovframework/rte/fdl/idgnr/messages/idgnr
				</value>
			</list>
		</property>
	</bean>
	
	<bean name="egovIdGnrService" class="egovframework.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl" destroy-method="destroy">
		<property name="dataSource" ref="dataSource"/>  
		<property name="strategy" ref="mixPrefixSample"/>
		<property name="blockSize" value="1" />
		<property name="table" value="IDS" />
		<property name="tableName" value="SAMPLE" />
	</bean>
	
	<bean name="mixPrefixSample" class="egovframework.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl">
		<property name="prefix" value="SAMPLE-"/>
		<property name="cipers" value="5"/>
		<property name="fillChar" value="0"/>
	</bean>
</beans>
```

<br><br>

```java
@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	
	@Resource(name="daoSpring")
	private SampleDAO sampleDAO;
	
	@Resource(name="egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;
	
	
	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public void insertSample(SampleVO vo) throws Exception {
		String id = egovIdGnrService.getNextStringId();
		vo.setId(id);
		sampleDAO.insertSample(vo);
	}
	
	// 이하 생략
}
```

<br><br>

DAO 클래스의 insert sql 문과 insert 자바 소스를 살짝 고쳐주자.

```java
package egovframework.sample.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import egovframework.sample.service.SampleDAO;
import egovframework.sample.service.SampleVO;


@Repository("daoSpring")
public class SampleDAOSpring implements SampleDAO {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate spring;
	
	// SQL 명령어들
	private final String SAMPLE_INSERT = "INSERT INTO SAMPLE(ID, TITLE, REG_USER, CONTENT, REG_DATE) VALUES "
			+ "(?, ?, ?, ?, SYSDATE)";
	
	private final String SAMPLE_UPDATE = "UPDATE SAMPLE SET TITLE=?, REG_USER=?, CONTENT=? WHERE ID=?";
	private final String SAMPLE_DELETE = "DELETE FROM SAMPLE WHERE ID = ?";
	private final String SAMPLE_GET = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE WHERE ID = ?";
	private final String SAMPLE_LIST = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE ORDER BY REG_DATE DESC";
	
	public SampleDAOSpring() {
		System.out.println("===> SampleDAOSpring 생성");
	}

	public void insertSample(SampleVO vo) throws Exception {
		System.out.println("Spring로 insertSample() 기능처리 등록");
		Object[] args = {vo.getId(),vo.getTitle(),vo.getRegUser(),vo.getContent()};
		spring.update(SAMPLE_INSERT,args);
	}
	
	// 다른 DAO도 똑같이 SQL을 바꿔주고 필요하면 코드도 살짝 바꿔준다.
	
	// 이하 생략
}
```

<br><br>

테스트 코드:

```java
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
		
		
		//3. Spring 컨테이너를 종료한다.
		container.close();
		
	}
}
```

<br><br>

결과 : <br>

![image](https://user-images.githubusercontent.com/51431766/75628208-146fd580-5c1a-11ea-9298-dd61b7c20bd9.png)

<br><br><br>

## 로깅 처리

<br><br>
로깅은 시스템의 개발이나 운용 시 발생하는 애플리켕션 내부 정보를 파일이나 콘솔에 출력하여 시스템의 상황을 <br>
쉽게 파악할 수 있도록 한다. <br><br>

### 로깅 서비스의 중요 컴포넌트

<br>

| 컴포넌트 	| 설명                                                                                                      	|
|----------	|-----------------------------------------------------------------------------------------------------------	|
| Appender 	| 어디에 출력할 것인지를 결정하는 컴포넌트                                                                  	|
| Layout   	| Appender에 출력되는 로그의 포맷(일자,시간,클래스명 등)을 지정한다                                         	|
| Logger   	| 애플리케이션 별로 사용할 로거(로거명 기반)를 정의하고 이에 대해 로그<br>레벨과 Appender를 지정할 수 있다. 	|

<br><br>

참고로 내가 작성한 src/main/resources/log4j2.xml 는 다음과 같다. <br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration>
<Configuration>
	<Appenders>
		<Console name="console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d %5p [%c] %m%n" />
		</Console>
	</Appenders>
	<Loggers>
		 <Logger name="java.sql" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Logger name="egovframework" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Logger name="org.springframework" level="INFO" additivity="false">
            <AppenderRef ref="console" />
        </Logger>
        <Root level="INFO">
        	<AppenderRef ref="console" />
        </Root>
	</Loggers>
</Configuration>
```

<br><br>

### 로그레벨

<br>

| 로그 레벨 	| 의미                                                                                                                     	|
|-----------	|--------------------------------------------------------------------------------------------------------------------------	|
| Error     	| 요청을 ㅓ리하는 중 일반적인 에러가 발생한 상태를 나타낸다.                                                               	|
| WARN      	| 처리 가능한 문제이지만, 향후 시스템 에러의 원인이 될 수 있는<br>경고성 메시지를 나타내다.                                	|
| INFO      	| 로그인, 상태변경과 같은 정보성 메시지를 나타낸다.                                                                        	|
| DEBUG     	| 개발시 디버깅 용도로 사용할 메시지를  나타낸다.                                                                          	|
| TRACE     	| log4j1.2.12 에서 신규 추가된 레벨로서, 디버그 레벨이 너무<br>광범위한 것을 해결하기 위해서 좀 더 상세한 상태를 나타낸다. 	|

<br><br>

### 간단한 로그 레벨 변경을 통해서 테스트

<br>

src/main/resources/log4j2.xml에서 egovframework 와 관련된 로그 레벨을 DEBUG로 고치고 ServiceImpl 클래스의 insert에 다음과 같은<br>
작성해보자.


```xml
<!-- src/main/resources/log4j2.xml 의 일부 -->
<!-- <Logger name="egovframework" level="INFO" additivity="false"> -->
Logger name="egovframework" level="DEBUG" additivity="false">
    <AppenderRef ref="console" />
</Logger>
```

<br><br>

```java
package egovframework.sample.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;		// 패키지 주의!
import org.slf4j.LoggerFactory;

@Service("sampleService")		// EgovAbstractServiceImpl 상속!
public class SampleServiceImpl extends EgovAbstractServiceImpl implements SampleService {
	
	// 로거 생성, 나중에 성능을 고려해서라도 static으로 만드는 습관을 길들여두는 게 좋다.
	// 종종 static으로 만들지 않는 사람도 있다, 물론 스프링 빈은 (주로) 싱글톤과 비슷한 생명주기라서 상관없지만
	// VO나 DTO 같이 계속 생성, 제거가 반복되는 객체에서는 좋지 않다.
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleServiceImpl.class);
	
	@Resource(name="daoSpring")
	private SampleDAO sampleDAO;
	
	public SampleServiceImpl() {
		System.out.println("===> SampleServiceImpl 생성");
	}
	
	public void insertSample(SampleVO vo) throws Exception {
		LOGGER.trace("TRACE");
		LOGGER.debug("DEBUG");
		LOGGER.info("DEBUG");
		LOGGER.warn("DEBUG");
		LOGGER.error("DEBUG");
		
		// 이하 생략
	}
	
	// 생략
	
}
```

<br><br>

EgovAbstractServiceImpl 추상 클래스를 상속받도록 했는데, 이 클래스는 비즈니스 메소드가 실행될 때 발생하는 <br>
예외를 처리하기 위한 processException 메소드와 leaveaTrace() 메소드를 가지고 있다. 이외에도 Logger 생성없이 <br>
protected로 선언된 egovLogger() 메소드를 사용할 수 있다. <br><br>

그런데 비즈니스 클래스인 EgovAbstractServiceImpl 추상 클래스를 상속하게 되면 내부적으로 LeaveaTrace 객체를 내부적을 <br>
사용하기 때문에 context-common.xml에 LeaveaTrace 클래스를 등록해야한다.

<br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
	
	<!-- <import resource="context-datasource.xml"/> -->

	<context:component-scan base-package="egovframework">
		<context:exclude-filter type="annotation" 
			expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>

	<!-- <bean class="egovframework.sample.service.impl.SampleDAOJDBC" /> -->
	
	<!-- JdbcTemplate -->
	<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	
	<bean id="leaveaTrace" class="egovframework.rte.fdl.cmmn.trace.LeaveaTrace"></bean>
</beans>
```

<br><br>

실행결과: <br>

![image](https://user-images.githubusercontent.com/51431766/76136327-852c4d00-6073-11ea-8315-c26cad6bbc88.png)

<br><br><br>

# 스프링 MVC 

<br><br>

## Model 1 아키텍처

<br>사용자와 커뮤니케이션을 위한 프레젠테이션 레이어를 개발하기 앞서 model1 아키텍처에 대해 알아보고 가자 <br>

90~2000년대 초반까지 자바 웹 어플레이케이션은 model1 을 아키텍처를 사용했다. <br>
model1 아키텍처는 JSP와 JavaBeans만 사용하여 웹을 개발하는 구조다. 그 구조는 아래와 같다. <br>

![image](https://user-images.githubusercontent.com/51431766/76136477-4eefcd00-6075-11ea-90db-a7fd4aedecf1.png)

<br><br>

앞서서 우리가 만들었던 VO와 DAO 클래스가 바로 Model 아키텍처의 JavaBeans에 해당한다. <br>
그리고 Controller의 의미는 JSP 내부의 자바코드(로직)를 의미하며, View는 말 그대로 화면의 외관(view)를 의미한다. <br>
간단한 프로젝트는 이런 Model1 의 기반으로 만들어도 상관 없지만, 후에 큰 웹 프로젝트에서는 어림도 없다. <br><br>

큰 웹 프로젝트는 Model2, 즉 MVC(Model View Controller) 아키텍처다. Model View Controller 요소로 기능을 분리하여 <br>
관리하기 때문에, 큰 프로젝트에 맞다. <br><br>

지금부터 model1 에서 시작해서 model2(MVC) 까지를 구현해보자. <br><br>


### 인덱스 페이지 만들기

<br> 파일 위치는 아래와 같다. <br>

![image](https://user-images.githubusercontent.com/51431766/76136619-bf4b1e00-6076-11ea-8473-bb44d4af9ad1.png)

<br><br>

내용은 다음과 같다.

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:forward page="/selectSampleList.jsp"></jsp:forward>
```

<br><br>

### 웹 프로젝트 실행

<br><br>

1\.  <br>

![image](https://user-images.githubusercontent.com/51431766/76136658-31bbfe00-6077-11ea-8784-93d98ca6c971.png)

<br><br>

2\. <br>

![image](https://user-images.githubusercontent.com/51431766/76136694-d5a5a980-6077-11ea-8f4b-acbcddc55035.png)

<br><br>

3\. <br>

![image](https://user-images.githubusercontent.com/51431766/76136860-8eb8b380-6079-11ea-87c7-c14628c1dc53.png)

<br><br>

4\.<br>

![image](https://user-images.githubusercontent.com/51431766/76136709-038aee00-6078-11ea-9bca-4d899681a20b.png)

<br><br>

5\. Run on Server 화면 창이 뜨면 그냥 Finish를 눌러준다. 매번 서버 실행마다 이 창을 보기 싫으면 밑에  <br>
Always use this server when running this project 체크 박스를 체크해준다. <br>


6\. (참고로 난 HTTP 포트 번호가 100001 이어서 브라우저에서 서버로 요청을 보낼 때는 다음과 같이 한다.) 

<br>

![image](https://user-images.githubusercontent.com/51431766/76136882-cc1d4100-6079-11ea-8e1a-85eb8063ec18.png)

<br>

![image](https://user-images.githubusercontent.com/51431766/76136902-10a8dc80-607a-11ea-8d95-f296ed0832f1.png)

( 아직 페이지를 안 만들었으니 당연히 404 에러가 난다 )

<br><br>


### 목록 기능 구현하기

<br>

selectSampleList.jsp 파일을 src/main/webapp 에 다음과 같이 작성한다. <br><br>

#### 파일위치 

<br>

![image](https://user-images.githubusercontent.com/51431766/76137078-3800a900-607c-11ea-9eeb-dd15dcae5a74.png)

<br><br>

#### jsp 작성
<br>

참고로 jsp 작성에 bootstrap을 사용했다. 이왕하는 거 좀 이쁘게(?) 해봤다. <br>
~~안 이쁘다고 생각하면 감성이 좀 다르다고 쳐주길 바란다.~~ <br><br>

```jsp
<%@page import="java.util.List"%>
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// 1. DB 연동 처리
	SampleVO vo = new SampleVO();
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	List<SampleVO> sampleList = sampleDAO.selectSampleList(vo);
	
	// 2. 응답 화면 구성
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 목록</title>
</head>
<body>
	
	<div class="container">
	  <h2>SAMPLE 목록</h2>
	  <p>등록한 모든 SampleVO 정보를 화면에 목록으로 보여줍니다.</p> <br><br>           
	  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th>아이디</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>등록일</th>
	      </tr>
	    </thead>
	    <tbody>
	    <% for(SampleVO sample : sampleList) { %>
	      <tr>
	        <td><a href="selectSample.jsp?id=<%= sample.getId() %>"><%=sample.getId()%></a></td>
	        <td> <%=sample.getTitle() %></td>
	        <td><%= sample.getRegUser() %></td>
	        <td><%= sample.getRegDate() %></td>
	      </tr>
	    <% } %>
	    </tbody>
	  </table>
	<br>
	<a class="btn btn-success" href="insertSample.jsp">샘플 등록</a>	  
	  
	</div>													
</body>
</html>
```

<br><br>

결과 화면 : <br>

![image](https://user-images.githubusercontent.com/51431766/76137113-7b5b1780-607c-11ea-9758-2106db2d4a2e.png)

<br><br>

### 상세 기능 구현 

목록 화면에서 사용자가 클릭한 샘플 데이터를 조회하고, 조회된 샘플의 상세 화면을 제공하는 selectSample.jsp를 작성하자. <br>
(파일 위치는 이전과 마찬가지로 src/main/webapp 이다)

```jsp
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	// 1. 검색할 아이디 추출
	String id = request.getParameter("id");
	
	// 2. DB 연동 처리
	SampleVO vo = new SampleVO();
	vo.setId(id);
	
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	SampleVO sample = sampleDAO.selectSample(vo);
	
	// 3. 응답 화면 구성
%>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 상세</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 상세</h2>
	  <p>SampleVO의 상세한 내용입니다.</p><br>
	  
	  <form action="updateSample_proc.jsp" method="post">
	    <div class="form-group">
	      <label for="id">아이디</label>
	      <input type="text" name="id" class="form-control" id="id" disabled="disabled" value="<%= sample.getId() %>">
	    </div>
	    <div class="form-group">
	      <label for="title">제목</label>
	      <input type="text" name="title" class="form-control" id="title" value="<%= sample.getTitle()%>">
	    </div>
	    <div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" value="<%= sample.getRegUser() %>" >
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content"><%= sample.getContent() %></textarea>
	    </div>
	    <br> 등록일 : <%= sample.getRegDate() %>
	    <br><br>
	    <button type="submit" class="btn btn-default">UPDATE</button>
	  </form>
	</div>
	
	<div class="container" style="margin-top:2em; text-align:right">
	  <a href="insertSample.jsp" class="btn btn-success" role="button">INSERT</a>
	  <a href="deleteSample_proc.jsp" class="btn btn-danger" role="button">DELETE</a>
	  <a href="selectSampleList.jsp" class="btn btn-info" role="button">LIST</a>
	</div>
</body>
</html>
```

참고로 input에서 disabled 는 서버에 해당 input을 전송 안하는 것이고, <br>
readonly는 서버에 해당 input이 전송이 된다. 둘 다 화면 상에서 수정은 안된다. 

<br><br>

결과: <br>

![image](https://user-images.githubusercontent.com/51431766/76137499-1a820e00-6081-11ea-9a42-1b1d47ed23be.png)


<br><br>

### 등록 기능 구현 (화면)

만든 파일위치는 위와 동일.

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 등록</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 등록</h2>
	  <p>SampleVO를 등록하는 화면입니다.</p><br>
	  
	  <form action="insertSample_proc.jsp" method="post">
		<div class="form-group">
			<label for="title">제목</label>
			<input type="text" name="title" class="form-control" id="title" placeholder="제목을  입력하세요" required="required">
		</div>
		<div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" placeholder="작성자를 입력하세요" required="required">
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content"></textarea>
	    </div>
	    <br><br>
	    <button type="submit" class="btn btn-success">INSERT</button>
	    <a href="selectSampleList.jsp" class="btn btn-info" role="button">LIST</a>
	  </form>
	</div>
		
</body>
</html>
```

<br><br>

결과 화면 : <br>

![image](https://user-images.githubusercontent.com/51431766/76137715-5b7b2200-6083-11ea-8d0a-883a8d7fa94e.png)

<br><br>

### 등록 기능 구현 (기능)
기능을 처리해줄 insertSample_proc.jsp 를 만들자. (위치는 아까와 동일~)

```jsp
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	// 1. 사용자 입력 정보 추출
	request.setCharacterEncoding("UTF-8");
	String title = request.getParameter("title");
	String regUser = request.getParameter("regUser");
	String content = request.getParameter("content");
	
	// 2. DB 연동 처리
	SampleVO vo = new SampleVO();
	vo.setId("SAMPLE-00009");  // 임시값이다. 겹치지 않는 ID 값을 지정해주자.
	vo.setTitle(title);
	vo.setRegUser(regUser);
	vo.setContent(content);
	
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	sampleDAO.insertSample(vo);
	
	// 3. 화면 네비게이션
	response.sendRedirect("selectSampleList.jsp");
	
%>
```

<br>

위처럼 하고 나서 다시 Sample 등록 화면에 가서 아무 글이나 쓰고 INSERT 버튼을 눌러주면 아래와 같이 제대로 입력이 <br>
된 것을 확인할 수 있다. <br><br>

![image](https://user-images.githubusercontent.com/51431766/76137935-b9a90480-6085-11ea-8a5b-cae6ffd17dda.png)

<br><br>

### 수정 기능 구현(기능)

<br>

잠시 selectSample.jsp 에서 input 중에 name = "id" 인 것을 hidden 타입으로 수정해주고 기능 구현 updateSample_proc.jsp <br>
를 작성하겠다. <br><br>

selectSample.jsp 중...<br>

```jsp
<%-- 
	<div class="form-group">
	<label for="id">아이디</label>
	<input type="text" name="id" class="form-control" id="id" disabled="disabled" value="<%= sample.getId() %>">
	</div>
--%>
	<input type="hidden" name="id" value="<%= sample.getId() %>">
```

<br><br>

updateSample_proc.jsp 작성 <br>

```jsp
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	//1. 사용자 입력 정보 추출
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String regUser = request.getParameter("regUser");
	String content = request.getParameter("content");
	
	// 2. DB 연동 처리
	SampleVO vo = new SampleVO();
	vo.setId(id);
	vo.setTitle(title);
	vo.setRegUser(regUser);
	vo.setContent(content);
	
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	sampleDAO.updateSample(vo);
	
	// 3. 화면 네비게이션
	response.sendRedirect("selectSampleList.jsp");
%>
```

결과 화면: <br>

![image](https://user-images.githubusercontent.com/51431766/76138068-46a08d80-6087-11ea-8a3c-1c1935c47de3.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/76138095-76e82c00-6087-11ea-8d1b-1e70704d71d8.png)

<br><br>

### 삭제 기능 구혀

<br>

잠시 selectSample.jsp 에서 DELETE 버튼에 대한 수정을 하고 , 삭제 기능인 deleteSample_proc.jsp 를 작성하겠다.

<br>

selectSample.jsp 수정. <br>

```html
<div class="container" style="margin-top:2em; text-align:right">
	<a href="insertSample.jsp" class="btn btn-success" role="button">INSERT</a>
	<!-- <a href="deleteSample_proc.jsp" class="btn btn-danger" role="button">DELETE</a> -->
	<a href="deleteSample_proc.jsp?id=<%=sample.getId() %>" class="btn btn-danger" role="button">DELETE</a>
	<a href="selectSampleList.jsp" class="btn btn-info" role="button">LIST</a>
</div>
```

<br><br>

deleteSample_proc.jsp 를 작성 <br>

```jsp
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	//1. 사용자 입력 정보 추출
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	
	// 2. DB 연동 처리
	SampleVO vo = new SampleVO();
	vo.setId(id);
	
	SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
	sampleDAO.deleteSample(vo);
	
	// 3. 화면 네비게이션
	response.sendRedirect("selectSampleList.jsp");
%>
```

<br><br>

결과화면: <br>

![image](https://user-images.githubusercontent.com/51431766/76138227-2ffb3600-6089-11ea-96df-2afaac5d2661.png)

<br><br>

<strong>이로서 Model1을 통한 CRUD를 구현해보았다.</strong> <br>
이제는 Spring을 통한 Model2 방식의 CRUD 를 구현해보자.

<br><br><br>

## Model2 아키텍처

<br><br>

Model1 아키텍처가 엔터프라이즈 시스템에 적합하지 않은 가장 큰 이유는 JSP 파일에 자바 로직과 화면 디자인이 <br>
통합되어 유지보수가 어렵기 때문이다. 개발자는 개발자대로 힘들고, 디자이너는 디자이너 대로 힘들어한다. <br>

<br>

그래서 고안된 것이 바로 Model2 아키텍처이다. Model2 아키텍처는 Model1에서 JSP 에 있는 자바 코드만 따로 분리한 <br>
것이다. 그리고 그 자바 코드, 즉 자바 클래스를 우리는 Controller라고 부른다. 이렇게 분리가 되면 JSP에는 <br>
View와 관련된 디자인만 남게되어 디자이너가 로직에 구애 받지 않을 수 있게된다. 개발자들은 Controller와 Model만 신경 쓰면된다. <br>

<br>

우리가 사용할 스프링 MVC는 DispatcherServlet을 시작으로 다양한 객체들이 상호작용하면서 클라이언트의 요청을 처리한다 <br><br>

![image](https://user-images.githubusercontent.com/51431766/76150072-a0d23a80-60e9-11ea-80cc-16257ec0cf18.png)

<br> 

그림에 나온 것들을 하나하나 알아가보자.

<br><br>

## DispatcherServlet 등록
DispatcherServlet은 모든 클라이언트의 요청을 가장 먼저 받아들이는, MVC에서 가장 중요한 클래스이다. <br>
그래서 스프링 MVC 프레임워크 적용의 첫 단추는 WEB-INF/web.xml 파일에 스프링이 제공하는 <br>
**DispatcherServlet**을 등록하는 것이다. <br><br>


**web.xml 수정**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>EgovWebTemplateMk</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  <servlet>
  	<servlet-name>action</servlet-name>
  	<servlet-class>
  		org.springframework.web.servlet.DispatcherServlet
  	</servlet-class>
  </servlet>
  
  <servlet-mapping>
  	<servlet-name>action</servlet-name>
  	<url-pattern>*.do</url-pattern>
  </servlet-mapping>
  
</web-app>
```

<br>

index.jsp를 수정해보고 실행해보자. 어떤 결과를 볼 수 있을까? <br><br>


**index.jsp 수정**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:forward page="/selectSampleList.do" />
```

<br><br>

**서버 실행**

![image](https://user-images.githubusercontent.com/51431766/76150187-0115ac00-60eb-11ea-85f2-e6a211424280.png)

<br>

**찍히는 예외 로그들**

![image](https://user-images.githubusercontent.com/51431766/76150235-73868c00-60eb-11ea-96a2-531257907d23.png)

<br><br>

이유를 차근차근 알아보자 <br>
서블릿 컨테이너에  \*.do 요청이 최초로 들어왔을 때 해당 요청을 처리해주는 서블릿, 즉 DispatcherServlet을 생성하고 <br>
이 서블릿이 생성될 때 자동으로 실행되는 init() 메서드가 내부에서 **스프링 컨테이너** 를 구동한다. 
이때 클라이언트의 요청에 대한 작업들은 DispatcherServlet의 init() 메서드에서 만든 스프링 컨테이너에 있는 <br>
**HandlerMapping, Controller, ViewResolver** 객체(Bean)들이 DispatcherServlet와 함께 상호작용하여 처리하게 된다. <br><br>

그런데 지금 우리는 **HandlerMapping, Controller, ViewResolver** 객체를 스프링 컨테이너에 생성하는 것은 결국 스프링
**설정파일**인데, 현재 그런 것을 만든 적이 없으니 지금 같은 에러가 나올 수 밖에 없는 것이다. 지금까지 설명을 정리해보자 <br>

![image](https://user-images.githubusercontent.com/51431766/76150370-1a1f5c80-60ed-11ea-8ab4-982c6d3f30f3.png)

(요약: 스프링 컨테이너가 기본으로 필요한 설정파일(xml)이 없어서 이 사단이 난 것이다)

그러면 이제 스프링 설정파일을 만드는 방법과 내부에 설정해야할 Bean 객체를 설정을 완성해보자.

---

### 스프링 설정 파일

앞서 말했듯이 DispatcherServlet은 Spring 컨테이너가 구동할 대, web.xml 파일에 등록된 서블릿 이름 뒤에 -servlet.xml을
붙여서 스프링 설정 파일을 찾는다. 찾는 시작점은 WEB-INF/ 부터 시작해서 찾는다.

그러면 한번 WEB-INF/action-servlet.xml 을 생성해보자. <br>

![image](https://user-images.githubusercontent.com/51431766/76157049-fd177780-6146-11ea-8337-82af90c06b1b.png)

(New ==> Spring Bean Configuration File 을 선택하여 생성함)

<br><br>

서버를 실행하면...?

![image](https://user-images.githubusercontent.com/51431766/76157076-7a42ec80-6147-11ea-92fe-f5062276fdd1.png)

500 에러는 이제 안 뜨지만 404 에러가 뜬다. 이것에 대한 해결은 잠시 보류다.

<br>

그런데 이렇게 Default 경로와 Default 설정파일 이름을 쓰는 경우는 흔치 않다. 주로 개발자들이 원하는 위치에 
원하는 이름으로 설정 파일을 만드는 경우가 많다. 

<br><br>


### 스프링 설정파일 변경

<br>

위에서 처럼 DispatcherServlet이 default로 주는 설정파일 위치와 이름을 사용하는 것도 좋지만, <br>
설정파일의 이름을 바꾸거나 위치를 변경할 수도 있다. 이때 사용하는 것이 **DispatcherServlet의 초기화 파라미터다.**

실습해보자. 일단 우리가 앞서 만들었던 action-servlet.xml 파일을 삭제하고 나서,
**WEB-INF/config/dispatcher-servlet.xml** 로 설정파일의 위치와 이름을 바꾸고, DispatcherServlet이 스프링 컨테이너를 <br>
생성할 때 사용토록 해보자. <br><br>

![image](https://user-images.githubusercontent.com/51431766/75684630-86a8ee80-5cdc-11ea-88ef-085317a77909.png)

<br><br>

web.xml 수정 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
  <display-name>EgovWebTemplateMk</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  
  
  <servlet>
  	<servlet-name>action</servlet-name>
  	<servlet-class>
  		org.springframework.web.servlet.DispatcherServlet
  	</servlet-class>
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>/WEB-INF/config/dispatcher-servlet.xml</param-value>
  	</init-param>
  	<load-on-startup>1</load-on-startup>
  </servlet>
  
  <servlet-mapping>
  	<servlet-name>action</servlet-name>
  	<url-pattern>*.do</url-pattern>
  </servlet-mapping>
</web-app>
```
<br>

테스트 하면 다음과 같다. <br>

![image](https://user-images.githubusercontent.com/51431766/75685122-6cbbdb80-5cdd-11ea-9518-97423e409aa3.png)

<br><br>

### 인코딩 설정

현재 상태로 계속 실행하면 클라이언트에서 서버로 오는 문자의 인코딩이 깨질 확률이 크다. 
이를 해결하기 위해서 다음과 같이 web.xml에 추가적으로 작성하자.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>EgovWebTemplateMk</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  
  
  <servlet>
  	<servlet-name>action</servlet-name>
  	<servlet-class>
  		org.springframework.web.servlet.DispatcherServlet
  	</servlet-class>
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>/WEB-INF/config/dispatcher-servlet.xml</param-value>
  	</init-param>
  </servlet>
  
  <servlet-mapping>
  	<servlet-name>action</servlet-name>
  	<url-pattern>*.do</url-pattern>
  </servlet-mapping>
  
	
  <filter>
  	<filter-name>characterEncoding</filter-name>
  	<filter-class>
  		org.springframework.web.filter.CharacterEncodingFilter
  	</filter-class>
  	<init-param>
  		<param-name>encoding</param-name>
  		<param-value>UTF-8</param-value>
  	</init-param>
  </filter>
  
  <filter-mapping>
  	<filter-name>characterEncoding</filter-name>
  	<url-pattern>*.do</url-pattern>
  </filter-mapping>
  
</web-app>
```

<br><br><br>

## 스프링 MVC 적용

지금까지 스프링 MVC를 적용하기 위해서 필요한 설정을 봤다. 이제 본격적으로 여태 만들었던 Model1을 Model2로 바꿔보자.

<br>

**목록 기능 개발**

1\. 컨트롤러 구현 : 우리가 기존에 만들었던 selectSampleList.jsp 파일에서 소스를 복사해오면 쉽다.


```java
package egovframework.sample.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class SelectSampleListController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("샘플 목록 검색 처리");
		
		// 1. 사용자 입력 정보 추출
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		List<SampleVO> sampleList = sampleDAO.selectSampleList(vo);
		
		// 3. 검색 결과를 세션에 저장하고 목록 화면으로 이동한다.
		HttpSession session = request.getSession();
		session.setAttribute("sampleList", sampleList);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("selectSampleList.jsp");
		
		return mav;
	}
		
}
```

보면 알겠지만 Controller 인터페이스를 구현하고 있다. 그리고 반환값이 ModelAndView 라는 점도 잘 봐놓자.
Model에는 우리가 보내고 싶은 값들을, View에는 우리가 보여주고 싶은 jsp를 넣어주면 된다.

<br><br>

2\. HandlerMapping 등록 : 클라이언트의 /selectSampleList.do 요청에 대해서 동작하게 하려면스프링 설정 파일인 
dispatcher-servlet.xml 파일에 HandlerMapping을 통해 적절히 매핑을 해줘야 한다.


<br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- HandlerMapping 등록 -->
	<bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/selectSampleList.do">selectSampleList</prop>
			</props>
		</property>
	</bean>
	
	<!-- Controller 등록 -->
	<bean id="selectSampleList" class="egovframework.sample.web.SelectSampleListController"></bean>
</beans>
```

SimpleUrlHandlerMapping 객체는 Setter Injection 으로 Properties 객체를 주입하고 있다. 
그리고 의존성 주입된 Properties 컬렉션에는 /selectSampleList.do 경로 요청에 대해 아이디가 
selectSampleList인 객체가 동작하도록 매핑했다.
그리고 아래 Controller 의 bean id 값은 반드시 Properties의 값과 같은 이름이어야 한다.

<br><br>

3\. JSP 화면 수정

```jsp
<%

	// 세션에 저장된 정보를 꺼낸다.	 
	 @SuppressWarnings("unchecked")
	 List<SampleVO> sampleList = (List<SampleVO>) session.getAttribute("sampleList");

%>
```

결과 <br>

![image](https://user-images.githubusercontent.com/51431766/76157497-e3792e80-614c-11ea-9fc2-1261ecc29659.png)

<br><br>

4\. HttpServletRequest 에 검색 결과 저장하기

현재는 우리가 클라이언트에 전송하는 정보를 세션에 넣어서 보낸다. 알다시피 세션은 웹 클라이언트인 브라우저 하나당
서버 메모리에 하나씩 생성되어 클라이언트의 상태 정보를 유지한다.
따라서 세션에 많은 정보를 넣는 것은 서버에 큰 부담을 준다. 그렇기 때문에 세션이 아닌 **HttpServletRequest 객체에 저장
해야 하며, 스프링의 ModelAndView 가 이러한 기능을 제공한다**.

<br><br>

SelectSampleListController 클래스를 다음과 같이 수정한다.


```java
package egovframework.sample.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class SelectSampleListController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("샘플 목록 검색 처리");
		
		// 1. 사용자 입력 정보 추출
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		List<SampleVO> sampleList = sampleDAO.selectSampleList(vo);
		
		//HttpSession session = request.getSession();
		//session.setAttribute("sampleList", sampleList);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("sampleList",sampleList);	// Model에 저장하는 것은 HttpServletRequest에 저장하는 것과 같다.
		mav.setViewName("selectSampleList.jsp");
		
		return mav;
	}
}
```

<br><br>

selectSampleList.jsp 도 수정해준다.

```jsp
<%

	// HttpServletRequest(Model)에 저장된 정보를 꺼낸다.	 
	@SuppressWarnings("unchecked")
	List<SampleVO> sampleList = (List<SampleVO>) request.getAttribute("sampleList");
%>
```

<br><br>

**결과** : <br>

![image](https://user-images.githubusercontent.com/51431766/76157670-2b995080-614f-11ea-928d-d6b1ae303a0a.png)

<br><br>

지금까지의 내용을 정리하면 다음과 같다.

![정리](https://user-images.githubusercontent.com/51431766/76157609-48815400-614e-11ea-81ec-7da430690467.png)

- 클라이언트가 /selectSampleList.do 요청을 전송하면 DsipatcherServlet이 요청을 받고 
- SimpleUrlHandlerMapping 을 통해 요청을 처리할 SelectSampleListController 을 검색한다. 
- DispatcherServlet 은 검색된 SelectSampleListController를 실행하여 요청을 처리한다. 
- SelectSampleListController 는 검색 결과인 List<SampleVO> 와 selectSampleList.jsp 이름을 ModelAndView객체에 저장하고 리턴
- DispatcherSevlet은 selectSampleList.jsp 를 실항한다. selectSampleList.jsp 에서는 ModelAndView를 통해 HttpServletRequest
  에 저장된 데이터로 목록 화면을 구성한다.

\

---

\

**EL과 JSTL을 이용한 화면 처리**

<br>

여전히 jsp파일에 자바코드가 보인다. 이것을 EL과 JSTL로 수정해보자. <br>

selectSampleList.jsp 수정 <br>
(참고로 jsp 상단에 `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>` 를 넣어줘야 한다!)

```jsp
<%@page import="java.util.List"%>
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 목록</title>
</head>
<body>
	
	<div class="container">
	  <h2>SAMPLE 목록</h2>
	  <p>등록한 모든 SampleVO 정보를 화면에 목록으로 보여줍니다.</p> <br><br>           
	  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th>아이디</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>등록일</th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="sample" items="${sampleList}">
	    	<tr>
			<%-- 참고로 selectSample.jsp --> .do 로 바꿨다. --%>
		        <td><a href="selectSample.do?id=${sample.id}">${sample.id}</a></td>
		        <td>${sample.title}</td>
		        <td>${sample.regUser}</td>
		        <td>${sample.regDate}</td>
	     	</tr>
	    </c:forEach>
	    </tbody>
	  </table>
	<br>
	<a class="btn btn-success" href="insertSample.jsp">샘플 등록</a>	  
	  
	</div>													
</body>
</html>
```

<br><br>

**상세 조회 기능 구현**

<br><br>

1\. 컨트롤러 구현 <br>

selectSampleController.java 생성 <br>

```java
package egovframework.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class SelectSampleController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("샘플 상세 조회 처리");
		
		// 1. 사용자 입력 정보 추출
		String id = request.getParameter("id");
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		vo.setId(id);
		
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		SampleVO sample = sampleDAO.selectSample(vo);
		
		// 3. 검색 결과를 ModelAndView 에 저장하여 리턴한다.
		ModelAndView mav = new ModelAndView();
		mav.addObject("sample", sample);
		mav.setViewName("selectSample.jsp");
		return mav;
	}
	
}

```

<br><br>

2\. HandlerMapping 등록 <br>

dispatcher-servlet.xml을 다음과 같이 추가작업 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- HandlerMapping 등록 -->
	<bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/selectSampleList.do">selectSampleList</prop>
				<prop key="/selectSample.do">selectSample</prop>
			</props>
		</property>
	</bean>
	
	<!-- Controller 등록 -->
	<bean id="selectSampleList" class="egovframework.sample.web.SelectSampleListController" />
	<bean id="selectSample" class="egovframework.sample.web.SelectSampleController"></bean>
	
</beans>
```
<br><br>

3\. selectSample.jsp 수정 <br>

```jsp
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 상세</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 상세</h2>
	  <p>SampleVO의 상세한 내용입니다.</p><br>
	  
	  <form action="updateSample_proc.do" method="post">
	    <input type="hidden" name="id" value="${sample.id}">
	    
	    <div class="form-group">
	      <label for="title">제목</label>
	      <input type="text" name="title" class="form-control" id="title" value="${sample.title}">
	    </div>
	    <div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" value="${sample.regUser}" >
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content">${sample.content}</textarea>
	    </div>
	    <br>
	        등록일 : ${sample.regDate}
		<br><br>
	    <button type="submit" class="btn btn-default">UPDATE</button>
	  </form>
	</div>
	
	<div class="container" style="margin-top:2em; text-align:right">
		  <a href="insertSample.jsp" class="btn btn-success" role="button">INSERT</a>
		  <a href="deleteSample_proc.do?id=${sample.id}" class="btn btn-danger" role="button">DELETE</a>
		  <a href="selectSampleList.do" class="btn btn-info" role="button">LIST</a>
	</div>
</body>
</html>
```

<br><br>

**등록 구현하기**

<br>

1\. 화면 수정

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 등록</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 등록</h2>
	  <p>SampleVO를 등록하는 화면입니다.</p><br>
	  
	  <%-- .jsp --> .do 로 수정 --%>
	  <form action="insertSample.do" method="post">
		<div class="form-group">
			<label for="title">제목</label>
			<input type="text" name="title" class="form-control" id="title" placeholder="제목을  입력하세요" required="required">
		</div>
		<div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" placeholder="작성자를 입력하세요" required="required">
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content"></textarea>
	    </div>
	    <br><br>
	    <button type="submit" class="btn btn-success">INSERT</button>
	    
	   	<%-- .jsp --> .do 로 수정 --%>
	    <a href="selectSampleList.do" class="btn btn-info" role="button">LIST</a>
	  </form>
	</div>
		
</body>
</html>
```

<br><br>

2\. InsertSampleController 추가

```java
package egovframework.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class InsertSampleController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("샘플 등록 처리");
		
		// 1. 사용자 정보 추출
		String title = request.getParameter("title");
		String regUser = request.getParameter("regUser");
		String content = request.getParameter("content");
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		vo.setId("SAMPLE-00008");//임시값!
		vo.setTitle(title);
		vo.setRegUser(regUser);
		vo.setContent(content);
		
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		sampleDAO.insertSample(vo);
		
		// 3. 화면 네비게이션
		ModelAndView mav = new ModelAndView();
		mav.setViewName("selectSampleList.do");
		
		return mav;
	}
	
}
```

여기서 주의해서 봐야 되는것은 `mav.setViewName("selectSampleList.do");` 이다. <br>
여태까지는 .jsp 라는 이름으로 View를 set했지만 지금은 .do 로 세팅했다. <br>
만약에 selectSampleList.jsp 화면으로 바로 이동하면 목록이 안 보인다. <br>

<br><br>

2\. 핸들러 등록 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- HandlerMapping 등록 -->
	<bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/selectSampleList.do">selectSampleList</prop>
				<prop key="/selectSample.do">selectSample</prop>
				<prop key="/insertSample.do">insertSample</prop>
			</props>
		</property>
	</bean>
	
	<!-- Controller 등록 -->
	<bean id="selectSampleList" class="egovframework.sample.web.SelectSampleListController" />
	<bean id="selectSample" class="egovframework.sample.web.SelectSampleController"></bean>
	<bean id="insertSample" class="egovframework.sample.web.InsertSampleController"></bean>
</beans>
```

<br><br>

그런데! 문제가 있다. 위에 작성한 대로 글을 새로 등록하고 목록 화면이 나온다. 그런데 그 상태에서 새로고침을 하면? <br>

![image](https://user-images.githubusercontent.com/51431766/76158332-41f7da00-6158-11ea-952b-457e9ffa97e5.png)

<br><br>

이것은 `mav.setViewName("selectSampleList.do");` 처럼 하면 내부적으로 forward를 사용하는 것을 의미한다. <br>
즉 `mav.setViewName("selectSampleList.do");` 는 `mav.setViewName('forward:/selectSampleList.do");` 와 같은 것이다 <br>
새글 등록과 같은 작업은 위처럼 forward를 통해 화면을 보일 경우 보안에 큰 구멍이 생긴다. <br>
저 상태에서 무한히 새로 고침을 반복하면 우리가 흔히 아는 "도배"가 되는 것이다. <br>
이것을 피하기 위해서는 Post 를 통해서 서버에서 작업이 완료된 이후에 send Redirect를 해줘야한다. <br>
그러면 새로 고침을 해도 큰 문제가 없다. <br>

이를 이해서 `mav.setViewName("selectSampleList.do");` 를 `mav.setViewName("redirect:/selectSampleList.do");` 로 수정하자. <br>

이제 새로고침을 해도 아까와 같은 메시지가 안 뜨는 것을 볼 수 있다. <br><br><br>


**수정 구현하기**

1\. 화면 수정 <br>

```jsp
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>SAMPLE 상세</title>
</head>
<body>
	<div class="container">
	  <h2>SAMPLE 상세</h2>
	  <p>SampleVO의 상세한 내용입니다.</p><br>
	  
	  <%-- action 속성값을 수정 --%>
	  <form action="updateSample.do" method="post">
	    <input type="hidden" name="id" value="${sample.id}">
	    
	    <div class="form-group">
	      <label for="title">제목</label>
	      <input type="text" name="title" class="form-control" id="title" value="${sample.title}">
	    </div>
	    <div class="form-group">
	      <label for="regUser">작성자</label>
	      <input type="text" name="regUser" class="form-control" id="regUser" value="${sample.regUser}" >
	    </div>
	    <div class="form-group">
	      <label for="content">내용</label>
	      <textarea class="form-control" name="content">${sample.content}</textarea>
	    </div>
	    <br>
	        등록일 : ${sample.regDate}
		<br><br>
	    <button type="submit" class="btn btn-default">UPDATE</button>
	  </form>
	</div>
	
	<div class="container" style="margin-top:2em; text-align:right">
		  <a href="insertSample.jsp" class="btn btn-success" role="button">INSERT</a>
		  <a href="deleteSample.do?id=${sample.id}" class="btn btn-danger" role="button">DELETE</a>
		  <a href="selectSampleList.do" class="btn btn-info" role="button">LIST</a>
	</div>
</body>
</html>
```

<br><br>

2\. 컨트롤러 구현 <br>

```java
package egovframework.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class UpdateSampleController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("샘플 수정 처리");
		
		// 1. 사용자 정보 추출
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String regUser = request.getParameter("regUser");
		String content = request.getParameter("content");
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		vo.setId(id);
		vo.setTitle(title);
		vo.setRegUser(regUser);
		vo.setContent(content);
		
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		sampleDAO.updateSample(vo);
		
		// 3. 화면 네비게이션
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/selectSampleList.do");
		return mav;
	}
	
}
```

<br><br>

3\. 스프링 설정 수정 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- HandlerMapping 등록 -->
	<bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/selectSampleList.do">selectSampleList</prop>
				<prop key="/selectSample.do">selectSample</prop>
				<prop key="/insertSample.do">insertSample</prop>
				<prop key="/updateSample.do">updateSample</prop>
			</props>
		</property>
	</bean>
	
	<!-- Controller 등록 -->
	<bean id="selectSampleList" class="egovframework.sample.web.SelectSampleListController" />
	<bean id="selectSample" class="egovframework.sample.web.SelectSampleController" />
	<bean id="insertSample" class="egovframework.sample.web.InsertSampleController" />
	<bean id="updateSample" class="egovframework.sample.web.UpdateSampleController" />
</beans>
```

<br><br><br>

**삭제 구현하기**

<br>

1\. 컨트롤러 구현 <br>


```java
package egovframework.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class deleteSampleController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("샘플 삭제 처리");
		
		// 1. 사용자 정보 추출
		String id = request.getParameter("id");
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		vo.setId(id);
		
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		sampleDAO.deleteSample(vo);
		
		// 3. 화면 네비게이션
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/selectSampleList.do");
		return mav;
	}
	
}
```

<br><br>

2\. 스프링 설정 수정 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- HandlerMapping 등록 -->
	<bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/selectSampleList.do">selectSampleList</prop>
				<prop key="/selectSample.do">selectSample</prop>
				<prop key="/insertSample.do">insertSample</prop>
				<prop key="/updateSample.do">updateSample</prop>
				<prop key="/deleteSample.do">deleteSample</prop>
			</props>
		</property>
	</bean>
	
	<!-- Controller 등록 -->
	<bean id="selectSampleList" class="egovframework.sample.web.SelectSampleListController" />
	<bean id="selectSample" class="egovframework.sample.web.SelectSampleController" />
	<bean id="insertSample" class="egovframework.sample.web.InsertSampleController" />
	<bean id="updateSample" class="egovframework.sample.web.UpdateSampleController" />
	<bean id="deleteSample" class="egovframework.sample.web.deleteSampleController" />
</beans>
```

<br>

이로써 우리는 \*\_proc.jsp 을 대체할 로직도 Controller 객체로 옮겼다. 이제 모든 \*\_proc.jsp 를 지우자 <br>


<br><br>

여태까지 만들어온 Controller를 보면 아래와 같다. <br>

![image](https://user-images.githubusercontent.com/51431766/76158883-fea06a00-615d-11ea-99ae-ded44ede554b.png)

<br><br>

그리고 여태 사용한 jsp 파일은 아래와 같다. <br>

![image](https://user-images.githubusercontent.com/51431766/76158928-6f478680-615e-11ea-84aa-ccf476601225.png)

<br><br><br>

## ViewResolver 활용하기

<br>

여태까지 한 것을 보면 어느정도 완성된 거 같지만, 사실 아직 스프링 MVC 에서 적용안 한 요소가 하나 있다. <br>
바로 ViewResolver이다. 그런데 만들기 이전에 왜 필요한지 생각을 하자. <br><br>

ex) <br>
만약 클라이언트가 selectSampleList.do  를 통해서 요청을 한 것이 아니라, selectSampleList.jsp를 통해서 jsp를 직접 <br>
호출하면 어떤 일이 생길까? selectSampleList.do 에 매핑된 Controller 의 로직을 수행하지 않고 바로 selectSampleList.jsp로 <br>
가기 때문에 화면에는 어떠한 목록도 보이지 않을 것이다. 아래와 같이 말이다 <br><br>

![image](https://user-images.githubusercontent.com/51431766/76159025-a5d1d100-615f-11ea-874a-6ceb2fcee6e8.png)

<br><br>

**JSP 감추기** <br>

이런 문제를 해결하기 위해서는 JSP 파일을 직접적으로 호출하지 못하게 해야한다. 그리고 다행히도 WEB-INF 파일 내에 넣은 <br>
자원들은 클라이언트의 직접적인 요청을 거부하기 때문에 이를 이용하면 된다. <br><br>

직접 호출을 거절해야하는 jsp 파일들을 WEB-INF/sample/ 에 넣도록 하자. 다음과 같이 말이다 <br>

![image](https://user-images.githubusercontent.com/51431766/76159086-2abcea80-6160-11ea-99d4-1241aa64cb1b.png)

<br>

브라우저의 직접 접근은 막았다. 그리고 ViewResolver를 사용하면 이제 Controller를 거쳐서 jsp 페이지를 열도록 강제할 수 있다.<br><br>

**ViewResolver 등록** <br>

dispatcher-servlet.xml 에 InternalResourceViewResolver 클래스를 추가한다. <br>

```xml
<!-- ~생략~ -->
<!-- ViewResolver 등록 -->
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="prefix" value="/WEB-INF/sample/"/>
	<property name="suffix" value=".jsp"></property>
</bean>
```

<br><br>

---
잠깐! 궁금한 것들이 나와서 자세히 봤다

<br>

![image](https://user-images.githubusercontent.com/51431766/76159212-63a98f00-6161-11ea-822c-55b3fd892cfc.png)

<br>

위에서 보면 알겠지만 우리가  `mav.setViewName("redirect:/selectSampleList.do");` 처럼 하면 우리가 정한 ServletContext<br>
가 앞에 자동으로 붙는다.

---

<br><br>

**Controller 클래스 수정하기** <br>

SelectSampleListController 의 코드를 약간만 수정해주자. <br>

```java
	ModelAndView mav = new ModelAndView();
	mav.addObject("sampleList",sampleList);	
	mav.setViewName("selectSampleList"); // .do가 사라졌다!
	return mav;
```

<br><br>

SelectSampleController 도 코드를 약간만 수정해주자. <br>

```java
	ModelAndView mav = new ModelAndView();
	mav.addObject("sample", sample);
	mav.setViewName("selectSample");
	return mav;
```

<br><br>

이러면 끝이다. 그런데! 한 가지 주의할 점이 있다. <br>


---

우리가 앞서 했던 .jsp 에서 .do 로 바꾼 것들이 있다. 현재 우리는 ViewResolver를 사용함으로 단순하게 selectSampleList.do 라고 하면 <br>
/WEB-INF/sample/selectSampleList.do.jsp (?) 파일을 찾게 된다. 예전처럼 selectSampleList.do 를 통해서 Controller를 호출하고 <br>
최종 페이지를 Controller가 결정하게 하기 위해서는 **반드시** <br>

`mav.setViewName("selectSampleList.do");` ==> `mav.setViewName("forward:selectSampleList.do");` 라고 명시적으로 꼭 써줘야한다. <br>
ViewResolver를 사용하지 않을 때는 명시하지 않아도 상관 없었지만, 쓰는 시점에서는 꼭 신경 써야한다. <br><br>

ex) <br><br>

![image](https://user-images.githubusercontent.com/51431766/76159517-cbada480-6164-11ea-8e7b-80679cad3507.png)

<br><br><br>

![image](https://user-images.githubusercontent.com/51431766/76159537-057eab00-6165-11ea-8ce3-a98ac436d432.png)

<br>
**INSERT 클릭!**

<br><br><br>

![image](https://user-images.githubusercontent.com/51431766/76159499-783b5680-6164-11ea-972b-1f0bb576bd79.png)

<br><br>

사실 이미 이 주의사항을 언급하기 전에 손을 써놨기 때문에 우리는 현재 문제가 없는 것이다.
우리는 forward 대신 redirect를 썼기 때문에 ViewResolver를 쓰나 안쓰나 결국은 redirect를 붙여줘야 한다.

---

<br><br><br>

# 어노테이션 이용한 스프링 MVC 개발

여태까지 한 Controller에 대한 작업을 생각해보면 귀찮은 작업이 참 많다. 그리고 이런 작업을 하면서 xml에 쌓이는 \<bean\> <br>
이 그렇게 반갑지는 않았다. 계속 이런 작업을 수행하면 개발자의 피로도는 극으로 오를 것이다. 그리고 xml의 내용이 복잡해진다. <br><br>

이를 해결하기 위해서 어노테이션 기반의 스프링 MVC 개발 방법으로 해결해보자. <br><br>

## \<context:component-scan\> 추가하기

<br><br>

1\. dispatcher-servlet.xml 파일 스크립트 추가 <br>

![image](https://user-images.githubusercontent.com/51431766/76159691-62c72c00-6166-11ea-97c6-b3f06c9318fb.png)

<br><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<!-- HandlerMapping 등록 -->
	<!-- <bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
		<property name="mappings">
			<props>
				<prop key="/selectSampleList.do">selectSampleList</prop>
				<prop key="/selectSample.do">selectSample</prop>
				<prop key="/insertSample.do">insertSample</prop>
				<prop key="/updateSample.do">updateSample</prop>
				<prop key="/deleteSample.do">deleteSample</prop>
			</props>
		</property>
	</bean> -->
	
	<!-- Controller 등록 -->
	<!-- <bean id="selectSampleList" class="egovframework.sample.web.SelectSampleListController" />
	<bean id="selectSample" class="egovframework.sample.web.SelectSampleController" />
	<bean id="insertSample" class="egovframework.sample.web.InsertSampleController" />
	<bean id="updateSample" class="egovframework.sample.web.UpdateSampleController" />
	<bean id="deleteSample" class="egovframework.sample.web.deleteSampleController" /> -->
	
	<context:component-scan base-package="egovframework">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
	</context:component-scan>
	
	<!-- ViewResolver 등록 -->
	<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/sample/"/>
		<property name="suffix" value=".jsp"></property>
	</bean>
	
</beans>
```

<br><br>

## 컨트롤러를 POJO로 만들기

<br>

이전까지는 Controller 인터페이스를 구현해야 했다. 그리고  해당 인터페이스가 구현을 강제하는 handlerRequest() 메소드를 <br>
구현해야했다. 하지만 이러한 방식은 스프링 프레임워크가 지향하는 POJO 스타일의 클래스가 아니다. <br>
POJO 스타일을 위해서 implements Controller 를 삭제해야한다. 그리고 메소드를 재정의할 필요없이  <br>
메소드 시그니처(리턴타입,이름,매개변수)를 마음대로 변경할 수 있어야 한다.

<br>

```java
package egovframework.sample.web;

import javax.servlet.http.HttpServletRequest;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

public class InsertSampleController {

	public void insertSample(HttpServletRequest request) throws Exception {
		System.out.println("샘플 등록 처리");
		
		// 1. 사용자 정보 추출
		String title = request.getParameter("title");
		String regUser = request.getParameter("regUser");
		String content = request.getParameter("content");
		
		// 2. DB 연동 처리
		SampleVO vo = new SampleVO();
		vo.setTitle(title);
		vo.setRegUser(regUser);
		vo.setContent(content);
		
		SampleDAOJDBC sampleDAO = new SampleDAOJDBC();
		sampleDAO.insertSample(vo);
		
	}
	
}
```

<br><br>

## 어노테이션 적용하기

1\. @Controller 어노테이션 <br>

```java
package egovframework.sample.web;

import org.springframework.stereotype.Controller;

@Controller
public class InsertSampleController {
  ~ 생략 ~
}
```

2\. @RequestMapping <br>

앞서 만들었던 HandlerMapping을 설정파일에 사용했던 것을 기억할 것이다. 이제 이것을 어노테이션 @RequestMapping <br>
으로 대체할 것이다. 사용법은 다음과 같다. <br><br>

```java
@Controller
public class InsertSampleController {

	@RequestMapping(value="/insertSample.do")
	public String insertSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		// 생략
	}
}
```

## 클라이언트의 요청처리 메서드를 더 깔끔하게 하기

클라이언트의 요청에 대한 매핑과 컨트롤러가 모두 구혔되었다. 하지만 @RequestMapping 메서드가 내부가 지저분하다 <br>

```java
package egovframework.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

@Controller
public class InsertSampleController {

	@RequestMapping(value="/insertSample.do")
	public String insertSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		
		// 잠시 후 공개~
	}
}
```

<br>

이렇게만 하면 \<form\> 에서 보내는 "name" 속성과 vo 객체의 파라미터 이름이 같으면, 스프링은  <br>
\<form\> 에서 보내는 "name" 속성값들을 vo 객체의 해당 파라미터에 넣어준다. <br><br>



## 어노테이션 기반의 스프링 MVC 구현

<br>

이제까지의 내용을 응용해서 나머지 모~든 컨트롤러를 수정해보면 아래와 같다.<br><br>

**InsertSampleController**

```java
package egovframework.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

@Controller
public class InsertSampleController {

	@RequestMapping(value="/insertSample.do")
	public String insertSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		System.out.println("샘플 등록 처리");
		
		sampleDAO.insertSample(vo);
		return "redirect:/selectSampleList.do";
	}
}
```

<br><br>

**SelectSampleListController**

```java
package egovframework.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

@Controller
public class SelectSampleListController {

	@RequestMapping(value="/selectSampleList.do")
	public ModelAndView selectSampleList(SampleVO vo, SampleDAOJDBC sampleDAO, ModelAndView mav) throws Exception {
		mav.addObject("sampleList",sampleDAO.selectSampleList(vo));
		mav.setViewName("selectSampleList");
		return mav;
	}
		
}
```

<br><br>


**SelectSampleController**

```java
package egovframework.sample.web;

// 생략

@Controller
public class SelectSampleController {
	
	@RequestMapping("/selectSample.do")
	public ModelAndView selectSample(SampleVO vo, SampleDAOJDBC sampleDAO, ModelAndView mav) throws Exception {
		
		mav.addObject("sample", sampleDAO.selectSample(vo));
		mav.setViewName("selectSample");
		return mav;
	}
}
```

<br><br>


**UpdateSampleController**

```java
package egovframework.sample.web;

// 생략

@Controller
public class UpdateSampleController {

	@RequestMapping("/updateSample.do")
	public String updateSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		sampleDAO.updateSample(vo);
		return "redirect:/selectSampleList.do";
	}
}
```

<br><br>


**deleteSampleController**

```java
package egovframework.sample.web;

//생략

@Controller
public class deleteSampleController {

	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		sampleDAO.deleteSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
}
```

<br><br>

실행결과 : <br><br>

![image](https://user-images.githubusercontent.com/51431766/76696838-2306c400-66d3-11ea-96f0-fe97d30066ec.png)

<br><br>


![image](https://user-images.githubusercontent.com/51431766/76696847-39ad1b00-66d3-11ea-8f3c-46d2849a7e45.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/76696871-9c061b80-66d3-11ea-8f6b-703b401057e4.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/76696919-f43d1d80-66d3-11ea-99d5-c1137f0efde5.png)

<br>

(등록 테스트는 억지로 ID를 주면서 테스트를 해야하는데, 그렇게 하고 싶지 않아서 굳이 하지 않았다)

---

<br><br>

## 컨트롤러 통합하기

여태까지 기능별로 Controller를 나눴다. 물론 이렇게 해도 되지만 조금 더 깔끔하게 정돈시키려면 <br>
하나의 Controller에 여태까지 만든 Controller의 @RequestMapping 메서드를 하나로 모으면 된다. <br>
다음과 같이 말이다. <br><br>

```java
package egovframework.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.sample.service.SampleVO;
import egovframework.sample.service.impl.SampleDAOJDBC;

@Controller
public class SampleController {
	
	@RequestMapping("/insertSample.do")
	public String insertSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		sampleDAO.insertSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/updateSample.do")
	public String updateSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		sampleDAO.updateSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		sampleDAO.deleteSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/selectSample.do")
	public ModelAndView selectSample(SampleVO vo, SampleDAOJDBC sampleDAO, ModelAndView mav) throws Exception {
		mav.addObject("sample", sampleDAO.selectSample(vo));
		mav.setViewName("selectSample");
		return mav;
	}
	
	@RequestMapping(value="/selectSampleList.do")
	public ModelAndView selectSampleList(SampleVO vo, SampleDAOJDBC sampleDAO, ModelAndView mav) throws Exception {
		mav.addObject("sampleList",sampleDAO.selectSampleList(vo));
		mav.setViewName("selectSampleList");
		return mav;
	}
	
}
```

<br><br>

작성이 완료된 후에는 SampleController를 제외한 모든 Controller를 삭제한다.<br><br>

![image](https://user-images.githubusercontent.com/51431766/76697020-23a05a00-66d5-11ea-8da6-78ba9ce1d7e8.png)


<br><br>

### 요청 방식(GET,POST)에 따른 처리

<br>

그냥 @RequestMapping에 method 값을 주면 된다. <br><br>

```java

@Controller
public class SampleController {
	
	@RequestMapping(value="/insertSample.do", method=RequestMethod.GET)
	public String insertSampleView() throws Exception {
		System.out.println("등록 화면으로 이동");
		return "insertSample";
	}

	@RequestMapping(value="/insertSample.do", method=RequestMethod.POST)
	public String insertSample(SampleVO vo, SampleDAOJDBC sampleDAO) throws Exception {
		System.out.println("샘플 등록 처리");
		sampleDAO.insertSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	// 이하 생략
}
```

<br><br>

이와 관련된 jsp 파일도 약간의 수정을 해주자. <br>

selectSampleList.jsp 파일 맨 아래 \[샘플 등록] 링크를 수정한다. <br>
`<a class="btn btn-success" href="insertSample.jsp">샘플 등록</a>` 
== 수정 ==> `<a class="btn btn-success" href="insertSample.do">샘플 등록</a>` <br>


selectSample.jsp 의 아래에 있는 링크도 수정,  <br>
`<a href="insertSample.do" class="btn btn-success" role="button">INSERT</a>` <br>

이러고 나서 insertSample.jsp 파일의 위치를 WEB-INF/samle/ 하위로 이동시킨다. <br><br>

![image](https://user-images.githubusercontent.com/51431766/76697142-821a0800-66d6-11ea-90ad-630712e33cd4.png) 

<br><br>


## 기타 어노테이션

<br>

### @RequestParam 어노테이션 (검색해보자, 매우 간단한 내용이고 앞으로 실습에도 영향이 없어서 생략한다)

<br><br>

### @ModelAttribute

<br>

@ModelAttribute은 JSP 에서 사용할 데이터를 미리 세팅하는 용도로 사용한다. <br>
@ModelAttribute이 설정된 메소드는 @RequestMapping 어노테이션이 적용된 메소드보다 먼저 호출된다. <br>
그리고 @ModelAttribute이 설정된 메소드의 리턴된 객체는 자동으로 Model에 저장되기 때문에, 이 리턴값을 <br>
JSP 에서 사용이 가능하다. SampleController에 searchConditionMap()이라는 메소드를 추가한다. <br><br>

```java
@Controller
public class SampleController {
	
	// ~생략~
	
	// 이렇게 하면 모든 @Controller의 메서드의 model에 자동으로 "conditionMap"이라는 이름의
	// 프로퍼티가 들어간다. 프로퍼티의 값은 return 값과 일치한다. 
	// 참고로 현재 추가한 값들은 "검색 조건" 들이다.
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}
	
	@RequestMapping(value="/selectSampleList.do")
	public ModelAndView selectSampleList(SampleVO vo, SampleDAOJDBC sampleDAO, ModelAndView mav) throws Exception {
		mav.addObject("sampleList",sampleDAO.selectSampleList(vo));
		mav.setViewName("selectSampleList");
		return mav;
	}
}
```


<br><br>

이제 model에 담긴 검색 조건들을 사용해보자. <br>

**selectSampleList.jsp** <br>

```jsp
<form class="form-inline">
	<select name="searchCondition" class="form-control">
	<c:forEach items="${conditionMap}" var="option">
		<option value="${option.value }">${option.key }
	</c:forEach>
	</select>
	<div class="form-group">
	   <input name="searchKeyword" type="text" class="form-control" >
	</div>
	<button type="submit" class="btn btn-default">검색</button>
 </form>
```
<br><br>

결과화면 : <br>

![image](https://user-images.githubusercontent.com/51431766/76697607-9e20a800-66dc-11ea-92aa-681f44441b51.png)

<br><br>

## @SessionAttribute 어노테이션 사용하기

<br>

주로 수정작업을 처리할 때 유용하게 사용할 수 있는 어노텡션이다. @SessionAttribute 어노테이션을 테스트하기 위해 <br>
상세 화면에서 작성자를 수정하지 못하게 변경한다 <br><br>

**selectSample.jsp 의 일부** <br>

```jsp
<label for="regUser">작성자</label>
 <input type="text" name="regUser" class="form-control" id="regUser" value="${sample.regUser}" disabled="disabled">
```

<br><br>

이 상태에서 UPDATE 버튼을 클릭하면 updateSample() 메소드가 실행된다. 그런데 문제는 사용자가 입력한 정보가 <br>
title, content 뿐이고, 작성자(regUser) 정보는 전달되지 않기 때문에 SampleVO 에  regUser 정보가 저장되지 않는다.<br>
이러면 sql이 수행될때, REG_USER 컬럼은 null로 수정된다. <br><br>

이런 문제를 방지하기 위해서 스프링에서는 @SessionAttribute 어노테이션을 사용한다. SampleController를 일단 수정한다 <br><br>

**SampleController** <br>

```java
@Controller
//Controller의 어느 메서드에서든 model에서 sample이라는 이름의 속성을 추가하면, 
//그 순간 세션에도 똑같은 속성과, 속성값을 저장한다.
@SessionAttributes("sample")
public class SampleController {
	// ~ 생략 ~
	
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("sample") SampleVO vo, 
								SampleDAOJDBC sampleDAO) throws Exception {
		System.out.println("< 수정되는 샘플 정보 >");
		System.out.println("제목 : "+vo.getTitle());
		System.out.println("작성자 : "+vo.getRegUser());
		System.out.println("내용 : "+vo.getContent());
		sampleDAO.updateSample(vo);
		return "redirect:/selectSampleList.do";
	}
}
```

<br><br>

수정실행 결과: <br>

![image](https://user-images.githubusercontent.com/51431766/76697782-e2ad4300-66de-11ea-95d9-b240a99a919f.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/76697826-5ea78b00-66df-11ea-8a49-37f6ee712e43.png)

(로그에 작성자가 잘 찍혀있는 것을 확인 할 수 있다)

<br><br>


어떻게 된 것일까? 차근차근 알아보자. <br><br>

먼저 사용자가 상세화면 요청하면 selectSample() 메소드는 SampleVO 객체를 sample이라는 이름으로 Model에 저장한다.<br>
이때, SampleController 상단에 선언된 @SessionAttributes("sample") 설정에 의해서, <br>
**Model 에 sample이라는 이름으로 저장된 데이터가 있다면 그 데이터를 세션(HttpSession)에도 자동으로 저장된다.**<br><br>

코드를 천천히 따라가면서 더 깊이 알아보자. <br.<br>

```java
@RequestMapping("/selectSample.do")
public ModelAndView selectSample(SampleVO vo, SampleDAOJDBC sampleDAO, ModelAndView mav) throws Exception {

	mav.addObject("sample", sampleDAO.selectSample(vo));
	mav.setViewName("selectSample");
	return mav;
}
```

<br>

일단 수정을 하기 위해서는 상세화면에 가게 된다. 이때 `mav.addObject("sample", sampleDAO.selectSample(vo));` 에 <br>
의해서 Model 뿐만 아니라 HttpSession에도 마찬가지로 "sample"이라는 이름으로 데이터가 저장된다. <br>
현재 이 "sample" 의 값에는 모든 값들 (id,title,regUser,content,regDate)이 저장되어 있는 상태다. <br><br>


<br><br><br>

```java
@RequestMapping("/updateSample.do")
public String updateSample(@ModelAttribute("sample") SampleVO vo, 
			SampleDAOJDBC sampleDAO) throws Exception {
	System.out.println("< 수정되는 샘플 정보 >");
	System.out.println("제목 : "+vo.getTitle());
	System.out.println("작성자 : "+vo.getRegUser());
	System.out.println("내용 : "+vo.getContent());
	sampleDAO.updateSample(vo);
	return "redirect:/selectSampleList.do";
}
```

<br>

다음으로 내용을 수정 후 UPDATE 버튼을 누르면, 스프링 컨테이너느 우선 updateSample 메서드의 <br>
`@ModelAttribute("sample")` 가 붙은 파라미터를 해석하여, 세션에 sample 이라는 이름으로 저장된 데이터가<br>
있는지 확인한다. 그리고 **있다면 해당 객체를 세션에서 꺼내서 매개변수로 선언된 vo 변수에 할당한다.** <br>
**그러고 나서야 사용자가 입력한 파라미터값을 vo 객체에 할당한다. 이때 사용가 입력한 수정 정보
(title,content) 값만 새롭ㄱ 할당되고, 나머지(regUser)는 상세 정보 보기를 했을 때 세션에 저장된 데이터가 유지된다!** <br><br>

---

<br>

# 프레젠테이션 레이어와 비즈니스 레이어 통합하기

<br>

지금까지는 프레젠테이션 레이어만 집중적으로 살펴봤는데, <br>
이제는 전에 만든 비즈니스 레이어(egovframework.sample.servie 패키지의 내용물) 와 프레젠테이션 레이어와 <br>
통합하는 작업을 해보겠다. <br><br>

## 비즈니스 컴포넌트 의존성 주입하기

<br>

현재는 우리가 JDBC를 스프링 컨테이너를 통해서 직접 생성해서 사용하는 상황이다.
하지만 이렇게 되면 Service 단을 거치지 않고 사용하기 때문에 트랜잭션이나, Service의 로직들이 모두 무시된다.<br> 
이런 문제를 해결하기 위해서는 일단 Service를 의존성 주입해서 JDBC를 사용해야한다. <br>
SampleController에 @Resource를 사용해서 의존성 주입을 해보자. <br><br>

```java
package egovframework.sample.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import egovframework.sample.service.SampleService;
import egovframework.sample.service.SampleVO;

@Controller
@SessionAttributes("sample")
public class SampleController {
	
	@Resource(name="sampleService")
	private SampleService sampleService;
	
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}
	
	@RequestMapping(value="/insertSample.do", method=RequestMethod.GET)
	public String insertSampleView() throws Exception {
		System.out.println("등록 화면으로 이동");
		return "insertSample";
	}
	
	@RequestMapping(value="/insertSample.do", method=RequestMethod.POST)
	public String insertSample(SampleVO vo) throws Exception {
		System.out.println("샘플 등록 처리");
		sampleService.insertSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("sample") SampleVO vo) throws Exception {
		sampleService.updateSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO vo) throws Exception {
		sampleService.deleteSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/selectSample.do")
	public String selectSample(SampleVO vo, Model model) throws Exception {
		model.addAttribute("sample", sampleService.selectSample(vo));
		return "selectSample";
	}
	
	@RequestMapping(value="/selectSampleList.do")
	public String selectSampleList(SampleVO vo, Model model) throws Exception {
		model.addAttribute("sampleList",sampleService.selectSampleList(vo));
		return "selectSampleList";
	}
	
}
```
(참고로 예전 코드와 달리 return  값을 String으로 통일했다, 그리고 그 과정에서 ModelAndView 대신 <br>
Model 을 사용했음으로 유의하기 바란다. 추가적으로 return이 String이면 ModelAndView가 무효화된다) 

<br><br>

파라미터에 있던 SampleJDBCDAO 를 모두 삭제하고 @Resource를 통해서 Service 객체를 의존성 주입했다. <br>
이 상태에서 실행해보자. <br><br>

![image](https://user-images.githubusercontent.com/51431766/76698370-581c1200-66e5-11ea-8e1d-4c3f4193eccf.png)

<br>

위와 같은 에러 메시지가 뜨는데 이는 @Resource 를 통해서 의존성 주인하려는 sampleService 빈 객체가 메모리에 없어서 <br>
나는 에러이다. @Resource 어노테이션을 사용하려면 의존성 주입 대상이 되는 빈 객체가 반드시 메모리에 올라갸야 한다.<br><br>

![이런상황이다](https://user-images.githubusercontent.com/51431766/76698391-a29d8e80-66e5-11ea-84cb-23a74b832144.png)

<br><br>

이를 해결하기 위해서는 Controller를 메모리에 생성하는 스프링 컨테이너보다  <br>
**비즈니스 컴포넌트를 생성하는 스프링 컨테이너가 먼저 구동해야 한다**.

<br><br>

## 리스너를 등록하여 비즈니스 컴포넌트를 호출

<br>

스프링에 제공하는 ContextLoaderListener를 사용하면 된다. DispatcherServlet과 마찬가지로 스프링 컨테이너를 구동한다.<br>
다만 DispatcherSerlvet이 생성되기 전에 WAS가 구동되는 시점 스프링 컨테이너를 구동한다. web.xml에 다음 내용을 추가하자. <br><br>

```xml
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>
  		classpath:egovframework/spring/context-*.xml
  	</param-value>
  </context-param>
  
  <listener>
  	<listener-class>
  		org.springframework.web.context.ContextLoaderListener
  	</listener-class>
  </listener>
```

<br><br>

서버를 실행하면 ? <br>

![image](https://user-images.githubusercontent.com/51431766/76698756-f4481800-66e9-11ea-8d9e-3464d00edd9d.png)

<br><br>

자세히 보면 Root WebApplication 와 WebApplication 이라는 두 개의 스프링 컨테이너가 초기화 되는 것을 볼 수 있다.<br>
그리고 이 두개의 컨테이너는 우리가 앞서 web.xml에서 설정했던 설정파일 경로의 xml을 사용해서 만들어진 스프링 컨테이너다.<br>
아래 그림을 보면서 이 두개의 스프링 컨테이너 관계를 정리해보자. <br><br>

![image](https://user-images.githubusercontent.com/51431766/76698492-d5945200-66e6-11ea-96e4-2e7151b79d22.png)

<br><br>


1\. 톰캣 서버를 구동하면 web.xml 파일을 로딩하여 서블릿 컨테이너가 구동된다.  <br><br>
2\. 서블릿 컨테이너는 web.xml 파일에 등록된 ContextLoaderListener 객체를 생성하고, 생성된 ContextLoaderListener는 <br>
   src/main/resources 소스 폴더에 있는 "context-\*.xml" 파일을 사용해서 스프링 컨테이너를 구동한다.  <br>
   이 스프링 컨테이너를 우리는 **"Root 컨테이너"**라고 한다.

3\. 스프링 컨테이너는 설정파일에 따라 ServiceImpl 이나 DAO 와 같은 객체들을 메모리에 생성한다. <br>
4\. 사용자가 selectSampleList.do 요청을 서버에 전달하면 서블릿 컨테이너는 DispatcherServlet 객체를 생성하고 <br>
5\. DispatcherServlet 객체는 web.xml에서 초기화 파라미터로 줬던 /WEB-INF/config 폴더에 있는 <br>
    dispatcher-servlet.xml 파일을 로딩하여 두 번째 스프링 컨테이너를 생성한다. 이 컨테이너를 **"자식 컨테이너"** <br>
    라고 부른다. <br>
6\. 자식 컨테이너는 Controller 객체를 메모리에 생성하고, **필요하면 Root 컨테이너에 있는 빈 객체를 참조한다**. <br><br>

![image](https://user-images.githubusercontent.com/51431766/76701305-637e3600-6703-11ea-9ebc-8e9cb9d6fa63.png)

<br><br>


---
Tip. 만약 properties 파일을 사용해서 bean 파일에 적용하고 싶다면? <br><br>


1\. 파일 위치 잡고 <br>

![image](https://user-images.githubusercontent.com/51431766/76701321-8f99b700-6703-11ea-8e71-eed00d3fc908.png)

<br><br>

2\. 내용은 아래와 같이 작성 <br>

```properties
# DB Properties
db.driver=oracle.jdbc.driver.OracleDriver
db.url=jdbc:oracle:thin:@127.0.0.1:1521:xe
db.username=book_ex3 
db.password=book_ex3
```

<br><br>

3\. 빈 설정 파일에 다음과 같이 작성 <br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="locations">
			<list>
				<value>classpath:config/config.properties</value>
			</list>
		</property>
		<property name="fileEncoding" value="UTF-8"></property>	
	</bean>

	<!-- Oracle DataSource -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="${db.driver}"/>
		<property name="url" value="${db.url}"/>
		<property name="username" value="${db.username}"/>
		<property name="password" value="${db.password}"/>
	</bean>
</beans>
```

<br>

끝! <br>

---

<br><br>

## 추가 기능(검색, 예외처리, 다국어) 구현

<br>

### 검색 기능 구현하기

<br>

1\. 검색화면(selectSampleList.jsp) <br>

```html
<form action="selectSampleList.do" method="post" class="form-inline">
	<select name="searchCondition" class="form-control">
	<c:forEach items="${conditionMap}" var="option">
		<option value="${option.value }">${option.key }
	</c:forEach>
	</select>
	<div class="form-group">
	   <input name="searchKeyword" type="text" class="form-control" >
	</div>
	<button type="submit" class="btn btn-default">검색</button>
 </form>   
```
이미 앞서서 작성했던 부분인데, `action="selectSampleList.do" method="post"` 를 form의 속성,속성값으로 추가했다.

<br><br>

2\. SampleVO 클래스 수정 <br>

```java
package egovframework.sample.service;

import java.sql.Date;

public class SampleVO {
	private String id;
	private String title;
	private String regUser;
	private String content;
	private Date regDate;
	
	// 아래 두 개의 변수 추가
	private String searchCondition;
	private String searchKeyword;
	
	// ~ 중간 생략 ~
	
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	@Override
	public String toString() {
		return "SampleVO [id=" + id + ", title=" + title + ", regUser=" + regUser + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}
	
}
```

<br><br>

3\. 컨트롤러(SampleController) 일부 수정 <br>

```java
@RequestMapping(value="/selectSampleList.do")
public String selectSampleList(SampleVO vo, Model model) throws Exception {
	// Null Check
	if(vo.getSearchCondition() == null) {vo.setSearchCondition("TITLE");}
	if(vo.getSearchKeyword() == null) {vo.setSearchKeyword("");}

	model.addAttribute("sampleList",sampleService.selectSampleList(vo));
	return "selectSampleList";
}
```

<br><br>

4\. DAO 클래스(SampleDAOJDBC , SampleDAOSpring)의 수정

```java
@Repository("daoJDBC")
public class SampleDAOJDBC implements SampleDAO {

	// ~ 생략 ~
	private final String SAMPLE_LIST_TITLE = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE"
			                          +" WHERE TITLE LIKE '%'||?||'%' ORDER BY REG_DATE DESC";
	
	private final String SAMPLE_LIST_CONTENT = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE"
						+" WHERE CONTENT LIKE '%'||?||'%' ORDER BY REG_DATE DESC";
	
	// ~ 중간 생략~
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("JDBC로  selectSampleList() 기능처리 목록 검색");
		List<SampleVO> sampleList = new ArrayList<SampleVO>();
		conn = JDBCUtil.getConnection();
		if(vo.getSearchCondition().equals("TITLE")) {
			pstmt = conn.prepareStatement(SAMPLE_LIST_TITLE);
		} else if(vo.getSearchCondition().equals("CONTENT")) {
			pstmt = conn.prepareStatement(SAMPLE_LIST_CONTENT);
		}
		pstmt.setString(1, vo.getSearchKeyword());
		rs = pstmt.executeQuery();
		while(rs.next()) {
			SampleVO sample = new SampleVO();
			sample.setId(rs.getString("ID"));
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
```

<br><br>

```java
@Repository("daoSpring")
public class SampleDAOJDBC implements SampleDAO {

	// ~ 생략 ~
	private final String SAMPLE_LIST_TITLE = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE"
			                          +" WHERE TITLE LIKE '%'||?||'%' ORDER BY REG_DATE DESC";
	
	private final String SAMPLE_LIST_CONTENT = "SELECT ID, TITLE, REG_USER, CONTENT, REG_DATE FROM SAMPLE"
						+" WHERE CONTENT LIKE '%'||?||'%' ORDER BY REG_DATE DESC";
	
	// ~ 중간 생략~
	
	public List<SampleVO> selectSampleList(SampleVO vo) throws Exception {
		System.out.println("Spring로  selectSampleList() 기능처리 목록 검색");
		Object[] args = {vo.getSearchKeyword()};
		if(vo.getSearchCondition().equals("TITLE")) {
			return spring.query(SAMPLE_LIST_TITLE,args, new SampleRowMapper());
		} else if(vo.getSearchCondition().equals("CONTENT")) {
			return spring.query(SAMPLE_LIST_CONTENT,args ,new SampleRowMapper());
		}
		return null;
	}	
}
```

서버를 실행시켜 접속해보자. 목록들이 잘 나올 것이다. 안 나오면, 원인 분석하고 해결해보자.

<br><br>

### 예외 처리

<br>

클라이언트 요청에 따라 웹 프로그램이 실행되다 보면 예기치 모한 예외가 발생할 수 있다. <br>
이때 사용자 브라우저에 발생된 예외에 따른 적절한 화면과 메시직 출려되는 것이 좋다.<br>
스프링은 ExceptionResolver 클래스를 이용하여 발생된 예외를 적절한 화면으로 처리할 수 있도록 한다.

<br><br>

1\. 예외 발생 <br>
클라이언트가 저장할 내용을 입력하지 않으면 IllegalAgumentException이 발생하도록 SampleController 를 수정한다.<br><br>

```java

@Controller
@SessionAttributes("sample")
public class SampleController {

	// ~ 중간 생략 ~ //

	@RequestMapping(value="/insertSample.do", method=RequestMethod.POST)
	public String insertSample(SampleVO vo) throws Exception {
		System.out.println("샘플 등록 처리");
		
		if(vo.getContent() == null || vo.getContent().length() == 0) {
			throw new IllegalArgumentException("내용이 입력되지 않았습니다");
		}
		
		sampleService.insertSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	// ~ 중간 생략 ~ //
	
}
```

<br><br>

2\. 예외 전용 화면(JSP) <br>

만약 위의 에러가 터져서 사용자의 브라우저에 나오면, 사용자는 무슨 의미인지 모르고 어리둥절할 것이다. <br>
이런식으로 냅둬어서는 안된다. 사용자들이 이해할 수 있도록 화면을 구성하자. <br><br>

일단 파일 위치와 이름은 다음과 같다.<br><br>

![image](https://user-images.githubusercontent.com/51431766/76702492-a34a1b00-670d-11ea-923e-14e358c30126.png)

<br><br>

**error.jsp** <br>

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Oops!</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
body { background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAxMC8yOS8xMiKqq3kAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzVxteM2AAABHklEQVRIib2Vyw6EIAxFW5idr///Qx9sfG3pLEyJ3tAwi5EmBqRo7vHawiEEERHS6x7MTMxMVv6+z3tPMUYSkfTM/R0fEaG2bbMv+Gc4nZzn+dN4HAcREa3r+hi3bcuu68jLskhVIlW073tWaYlQ9+F9IpqmSfq+fwskhdO/AwmUTJXrOuaRQNeRkOd5lq7rXmS5InmERKoER/QMvUAPlZDHcZRhGN4CSeGY+aHMqgcks5RrHv/eeh455x5KrMq2yHQdibDO6ncG/KZWL7M8xDyS1/MIO0NJqdULLS81X6/X6aR0nqBSJcPeZnlZrzN477NKURn2Nus8sjzmEII0TfMiyxUuxphVWjpJkbx0btUnshRihVv70Bv8ItXq6Asoi/ZiCbU6YgAAAABJRU5ErkJggg==);}
.error-template {padding: 40px 15px;text-align: center;}
.error-actions {margin-top:15px;margin-bottom:15px;}
.error-actions .btn { margin-right:10px; }
</style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>Oops!</h1>
                <div class="error-details">
                    Message: ${exception.message }
                </div>
                <div class="error-actions">
                    <a href="selectSampleList.do" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>Take Me Home </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
```

<br><br>

**illegalArgumentError.jsp**

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Oops!</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
body { background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAxMC8yOS8xMiKqq3kAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzVxteM2AAABHklEQVRIib2Vyw6EIAxFW5idr///Qx9sfG3pLEyJ3tAwi5EmBqRo7vHawiEEERHS6x7MTMxMVv6+z3tPMUYSkfTM/R0fEaG2bbMv+Gc4nZzn+dN4HAcREa3r+hi3bcuu68jLskhVIlW073tWaYlQ9+F9IpqmSfq+fwskhdO/AwmUTJXrOuaRQNeRkOd5lq7rXmS5InmERKoER/QMvUAPlZDHcZRhGN4CSeGY+aHMqgcks5RrHv/eeh455x5KrMq2yHQdibDO6ncG/KZWL7M8xDyS1/MIO0NJqdULLS81X6/X6aR0nqBSJcPeZnlZrzN477NKURn2Nus8sjzmEII0TfMiyxUuxphVWjpJkbx0btUnshRihVv70Bv8ItXq6Asoi/ZiCbU6YgAAAABJRU5ErkJggg==);}
.error-template {padding: 40px 15px;text-align: center;}
.error-actions {margin-top:15px;margin-bottom:15px;}
.error-actions .btn { margin-right:10px; }
</style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1> Oops!</h1>
                <div class="error-details">
                    Message: ${exception.message }
                </div>
                <div class="error-actions">
                    <a href="selectSampleList.do" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>Take Me Home </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
```

<br><br>

3\. 스프링 설정 추가 (dispatcher-servlet.xml)<br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">

	
	<context:component-scan base-package="egovframework">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
	</context:component-scan>
	
	<!-- ViewResolver 등록 -->
	<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/sample/"/>
		<property name="suffix" value=".jsp"></property>
	</bean>
	
	<!-- 예외 처리 설정 -->
	<bean id="exceptionResolver" class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
		<property name="exceptionMappings">
			<props>
				<prop key="java.lang.IllegalArgumentException">
					common/illegalArgumentError
				</prop>
			</props>
		</property>
		<property name="defaultErrorView" value="common/error"></property>
	</bean>
</beans>
```

<br>

IllegalArgumentException 이 발생하면 illegalArgumentError.jsp 화면이 사용자 브라우저에 전송되고 <br>
Exception 이 발생하면 defaultErrorView 로 설정한 error.jsp 화면이 전송되도록 SimpleMappingExceptionResolver <br>
클래스를 설정했다. <span style="color:red">에러 화면에 해당하는 jsp 파일의 이름을 등록할 때는 ViewResolver를 고려하여야 한다</span>.

<br><br>

실행결과: <br>

![image](https://user-images.githubusercontent.com/51431766/76702844-88c57100-6710-11ea-90ea-e253ea8bc25c.png)

<br><br>

잘되는 것을 확인했으니 insertSample() 메소드에 추가한 예외 발생 코드는 주석 혹은 삭제한다.<br>

```java
@RequestMapping(value="/insertSample.do", method=RequestMethod.POST)
public String insertSample(SampleVO vo) throws Exception {
	System.out.println("샘플 등록 처리");
	sampleService.insertSample(vo);
	return "redirect:/selectSampleList.do";
}
```

<br><br><br>

### 다국어 처리

<br>

다국어란 국제화라고도 하며 하나의 JSP 페이지를 다양한 언어로 서비스하는 것을 의미한다. <br>
프레임워크가 다국어를 지원하기 전에는 언어별로 JSP 파일들을 만들어야 했기 때문에 매우 불편했지만, <br>
스프링의 다국어 기능을 사용하면 그럴 필요가 없다. <br><br>

1\. 메시지 파일 생성 <br>

![image](https://user-images.githubusercontent.com/51431766/76702994-74ce3f00-6711-11ea-8722-a8eacb90bb44.png)

(참고로 파일 생성시에는 \[New] -> \[Untitled Text File] 로 생성했다. 확장자는 꼭 properties 로 한다)

<br><br>

**message-sample_en.properties** <br>

```properties
#UI resource#
list.mainTitle=LIST SAMPLE
list.mainIntro=Show All List that you have insert before.

list.search.title=TITLE
list.search.condition=CONTENT
list.search.button=Search

list.list.table.id=SAMPLE ID
list.list.table.title=TITLE
list.list.table.regUser=REG-USER
list.list.table.regDate=REG-DATE

list.link.locale.en=ENGLISH
list.link.locale.ko=KOREAN

list.link.create=Create Sample
```

<br><br>

**message-sample_ko.properties** <br>

```properties
#UI resource#
list.mainTitle=SAMPLE 목록
list.mainIntro=등록한 모든 SampleVO 정보를 화면에 목록으로 보여줍니다.

list.search.title=제목
list.search.condition=내용
list.search.button=검색

list.list.table.id=아이디
list.list.table.title=제목
list.list.table.regUser=작성자
list.list.table.regDate=작성일

list.link.locale.en=영어
list.link.locale.ko=한글

list.link.create=샘플 등록
```

<br><br>

2\. dispatcher-servlet.xml에 MessageSource 등록 <br>

```xml
<!-- 다국어 설정 -->
<!-- MessageSource 등록 -->
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	<property name="basenames">
		<list>
			<value>egovframework.message.message-sample</value>
		</list>
	</property>
</bean>
```

<br>

위에서 보면 메시지 파일들이 정확하게 등록되지 않아있는 것에 주목하길 바란다. <br>
생략된 부분이 확장자(.properties) 와 국가를 의미하는 것(\_en,\_ko) 도 생략되어 있다. <br>
이렇게 생략을 통해서 한번에 2개의 파일을 지정할 수 있기 때문에 편하다. 후에 더 추가되어도 <br>
이름 규칙만 잘 지키면 현재 스크립트를 건드리지 않고도 사용이 가능하다. <br><br>

다만 어떤 메시지 파일이 적용되는지는 기본 언어 설정과 Locale의 변화에 따라 해당 언어의 메시지가 <br>
자동으로 적용되도록 한다.<br><br>

3\. LocaleResolver 등록 <br>

웹 브라우저가 서버에 HTTP 요청을 전송하면 브라우저의 Locale 정보가 HTTP 요청 메시지 헤더에 자동으로 <br>
설정되어 전송된다. 이때, 스프링은 LocaleResolver를 통해서 클라이언트의 Locale 정보를 추출하고, <br>
이 Locale 정보에 해당하는 언어의 메시지를 적용한다. <br><br>

스프링은 다음과 같이 4개의 LocaleResolver를 지원한다. 만약 스프링 설정파일에 LocaleResolver가 등록<br>
되지 않았다면 기본으로 AcceptHeaderLocaleResolver가 적용된다. <br><br>

| LocaleResovler             | 설명                                                                                           |
|----------------------------|------------------------------------------------------------------------------------------------|
| AcceptHeaderLocaleResolver | 브라우저에서 전송된 HTTP 요청 헤더에서 Accept-Language에 설정된<br>Locale로 메시지를 적용한다. |
| CookieLocaleResolver       | Cookie 에 저장된 Locale 정보를 추출하여 메시지를 적용한다.                                     |
| SessionLocaleResolver      | HttpSession에 저장된 Locale 정보를 추출하여 메시지를 적용한다.                                 |
| FixedLocaleResolver        | 웹 요청과 무관하게 특정 Locale로 고정한다.                                                     |

<br>

스프링에서는 다양한 LocaleResolver를 지원하지만 세션으로부터 Locale 정보를 추출하고 유지하는 SessionLocaleResolver<br>
를 가장 많이 이용한다.<br><br>

dispatcher-servlet.xml 에 SessionLocaleResolver 을 등록하자. <br>

```xml
<!-- LocaleResolver 등록 -->
<bean id="localeResolver" class="org.springframework.web.servlet.i18n.SessionLocaleResolver"></bean>
```
(**주의!!! LocaleResolver 아이디를 localeResolver 로 등록해야 한다!!**)

<br><br>

4\. JSP 파일 작성 <br>

여태 작성한 것을 쓰기 위해선는 스프링에서 제공하는 태그라이브러리를 이용해야 한다. <br>
지금부터 selectSampleList.jsp를 수정해보자. <br><br>

(`<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>` 추가하는 것을 잊지말자)

<br>

전체 jsp 코드를 보면 아래와 같다. <br><br>

```html
<%@page import="java.util.List"%>
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title><spring:message code="list.mainTitle"/></title>
</head>
<body>
	<div class="container">
	  <h2><spring:message code="list.mainTitle"/></h2>
	  <p><spring:message code="list.mainIntro"/></p><br>
	  <div class="row">
	  	  <div class="col-6" style="text-align:right">
			  <form action="selectSampleList.do" method="post" class="form-inline">
				<select name="searchCondition" class="form-control">
					<option value="TITLE"><spring:message code="list.search.title"/></option>
					<option value="CONTENT"><spring:message code="list.search.condition"/></option>
				</select>
				<div class="form-group">
				   <input name="searchKeyword" type="text" class="form-control" >
				</div>
				<button type="submit" class="btn btn-default"><spring:message code="list.search.button"/></button>
			  </form>       
		  </div>
      </div>
      <br>
      
	  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th><spring:message code="list.list.table.id"/></th>
	        <th><spring:message code="list.list.table.title"/></th>
	        <th><spring:message code="list.list.table.regUser"/></th>
	        <th><spring:message code="list.list.table.regDate"/></th>
	      </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="sample" items="${sampleList}">
	    	<tr>
		        <td><a href="selectSample.do?id=${sample.id}">${sample.id}</a></td>
		        <td>${sample.title }</td>
		        <td>${sample.regUser}</td>
		        <td>${sample.regDate}</td>
	     	</tr>
	    </c:forEach>
	    </tbody>
	  </table>
	<br>
	
	<a class="btn btn-success" href="insertSample.do"><spring:message code="list.link.create"/></a>	  
	  
	</div>							
						
</body>
</html>
```

<br><br>


실행결과: <br>

![image](https://user-images.githubusercontent.com/51431766/76703867-4ef86880-6718-11ea-91bf-674b5cad265d.png)

<br>

우리가 사용 중인 브라우저의 Locale 이 한글이여서 현재는 한글로만 나온다.<br><br>

5\. Locale 변경하기 <br>

특정 언어로 화면을 보다가 해당 화면의 언어를 변경하고 싶을 때가 있다. 이를 위해서 스프링에서 제공하는 것이<br>
LocaleChangeInterceptor 클래스다. LocaleChangeInterceptor는 HandlerInterceptor 인터페이스를 구현한 클래스로 <br>
스프링 설정 파일에 인터셉터로 등록해야 한다. 그러기 위해서 dispatcer-servlet.xml 에 \[Namespace] 탭에서 mvc를 추가한다. <br><br>

![image](https://user-images.githubusercontent.com/51431766/76703979-2de44780-6719-11ea-8174-892b62085ca5.png)

<br><br>

그리고 xml 파일에 다음과 같이 스크립트를 추가한다. <br>

```xml
<!-- LocaleChangeInterceptor 등록 -->
<mvc:interceptors>
	<bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor">
		<property name="paramName" value="lang"/>
	</bean>
</mvc:interceptors>
```

<br><br>

`<property name="paramName" value="lang"/>` 이 중요한데, 클라이언트로부터 "lang" 이라는 파라미터로 특정 Locale이 <br>
이 전송되면 해당 Locale로 변경하겠다는 설정이다. <br><br>

여태까지의 다국어 관련 설정을 다시 한번에 보자. <br><br.

```xml
<!-- 다국어 설정 -->
<!-- MessageSource 등록 -->
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	<property name="basenames">
		<list>
			<value>egovframework.message.message-sample</value>
		</list>
	</property>
</bean>

<!-- LocaleResolver 등록 -->
<bean id="localeResolver" class="org.springframework.web.servlet.i18n.SessionLocaleResolver"></bean>

<!-- LocaleChangeInterceptor 등록 -->
<mvc:interceptors>
	<bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor">
		<property name="paramName" value="lang"/>
	</bean>
</mvc:interceptors>
```

<br><br>

이제 selectSampleList.jsp 파일에 다음과 같이 링크를 추가해서 다국어를 테스트해보자. <br>

```html
<%@page import="java.util.List"%>
<%@page import="egovframework.sample.service.impl.SampleDAOJDBC"%>
<%@page import="egovframework.sample.service.SampleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title><spring:message code="list.mainTitle"/></title>
</head>
<body>
	<div class="container">
	  <h2><spring:message code="list.mainTitle"/></h2>
	  <p><spring:message code="list.mainIntro"/></p><br>
	  <div class="row">
		  <div class="col-sm-6">
	  	  	<a class="btn btn-info" href="selectSampleList.do?lang=en"><spring:message code="list.link.locale.en"/></a>
	  	  	<a class="btn btn-info" href="selectSampleList.do?lang=ko"><spring:message code="list.link.locale.ko"/></a>
	  	  </div>
	  	  <div class="col-sm-6" style="text-align:right">
			  <form action="selectSampleList.do" method="post" class="form-inline">
				<select name="searchCondition" class="form-control">
					<option value="TITLE"><spring:message code="list.search.title"/></option>
					<option value="CONTENT"><spring:message code="list.search.condition"/></option>
				</select>
				<div class="form-group">
				   <input name="searchKeyword" type="text" class="form-control" >
				</div>
				<button type="submit" class="btn btn-default"><spring:message code="list.search.button"/></button>
			  </form>       
		  </div>
           </div>
		
	<!-- 이하 생략 -->	
```

<br><br>

실행 결과: <br>

![image](https://user-images.githubusercontent.com/51431766/76704225-d515ae80-671a-11ea-8007-2c121b4dfcbd.png)

<br><br>

![image](https://user-images.githubusercontent.com/51431766/76704248-fc6c7b80-671a-11ea-8b49-212cf346bc05.png)

<br><br>

---


<br><br><br>

# 표준프레임워크 실행환경 - 데이터처리 레이어

