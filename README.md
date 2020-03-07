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
그래서 고안된 것이 바로 Model2 아키텍처이다. Model2 아키텍처에서 가장 중요한 것은 Controller의 등장이며 <br>
이 Controller는 서블릿 클래스를 중심으로 구현된다. <br>


## DispatcherServlet 등록
DispatcherServlet은 모든 클라이언트의 요청을 가장 먼저 받아들이는, MVC에서 가장 중요한 클래스로이다. <br>
그래서 실제로 스프링 MVC 적용에 있어서도 가장 먼저 WEB-INF/web.xml 파일에 스플링 프레임워크가 제공하는 <br>
DispatcherServlet을 등록해야 한다. <br><br>


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

위처럼만 하고 실행하면 당연히 안된다. 하지만 이 작업만으로도 많은 일이 일어난 것이다. <br>
처음에 서블릿 컨테이너에  \*.do 요청이 들어왔을 때 서블릿 컨테이너가 DispatcherServlet을 생성한다.<br>
그리고 곧바로 DispatcherServlet의 init() 메서드 ( Servlet이 생성되는 이후에 곧바로 실행되는 메서드) <br>
내에서 XmlWebApplicationContext ( 스프링 컨테이너 ) 를 생성한다. <br><br>

하지만 DispatcherServlet 혼자서는 클라이언트 요청을 처리하지 못하고 , <strong>반드시 HandlerMapping, Controller <br>
ViewResolver </strong> 객체들과 상호작용해야한다. 이러한 객체들을 생성하고 스프링컨테이너가 관리하기 위해서 <br>
DispatcherServlet에서 먼저 스프링 컨테이너를 생성해야만 가능한 것이다. <br><br>

그러면  HandlerMapping, Controller , ViewResolver 를 빈으로 등록하기 위한 설정 파일은 어떻게 설정되는 걸까? <br><br>

우리가 앞서 만들었던

```xml
<servlet>
	<servlet-name>action</servlet-name>
	<servlet-class>
		org.springframework.web.servlet.DispatcherServlet
	</servlet-class>
</servlet>
```

에는 \<servlet-name\>action\</servlet-name\> 이 있다. 여기서 "action" 이라는 이름 값이 있는데 <br>
이 이름 값에 "-servlet"을 붙이면, 그게 바로 DispatcherServlet이 참조하게 될 스프링 xml 설정 파일의 이름이다. <br>
그리고 파일의 위치는 기본적으로 WEB-INF 폴더 내이다. <br>

### WEB-INF/action-servlet.xml 파일 생성 (Spring Bean Configuration File으로 생성)

![image](https://user-images.githubusercontent.com/51431766/75683662-eacab300-5cda-11ea-9ffa-68277297d683.png)

(참고로 action-servlet.xml의 내용물은 작성하지 않은 상태다) <br><br>


### Tomcat Web Module Path 설정 변경

![image](https://user-images.githubusercontent.com/51431766/75683831-26657d00-5cdb-11ea-9886-26e05a436fbd.png) 

<br><br>

### index.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<jsp:forward page="/selectSampleList.do" />
```


<br><br>

### 서버 실행
그리고 실행하면 다음과 같은 로그를 볼 수 있다. <br>

![image](https://user-images.githubusercontent.com/51431766/75683967-5b71cf80-5cdb-11ea-8a46-9760c1f382a5.png)
![image](https://user-images.githubusercontent.com/51431766/75684162-983dc680-5cdb-11ea-8ae8-6e8776d53cdf.png)

비록 404 에러가 나지만 그래도 서블릿이 성공적으로 생성된 것을 확인할 수 있다.

<br><br>

### 스프링 설정파일 변경

<br>

위에서 처럼 DispatcherServlet이 default로 주는 설정파일 위치와 이름을 사용하는 것도 좋지만, <br>
설정파일의 이름을 바꾸거나 위치를 변경할 수도 있다. 이때 사용하는 것이 DispatcherServlet의 초기화 파라미터다 <br><br>

WEB-INF/config/dispatcher-servlet.xml  로 설정파일의 위치와 이름을 바꾸고, DispatcherServlet이 스프링 컨테이너를 <br>
생성할 때 사용토록 해보자. <br><br>

![image](https://user-images.githubusercontent.com/51431766/75684630-86a8ee80-5cdc-11ea-88ef-085317a77909.png)

<br><br>

web.xml 수정 <br>

```xml
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
```
<br>

테스트 하면 다음과 같다. <br>

![image](https://user-images.githubusercontent.com/51431766/75685122-6cbbdb80-5cdd-11ea-9518-97423e409aa3.png)

<br><br>

### 인코딩 설정

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

## 스프링 MVC 적용


<br>


### 컨트롤러를 구현해보자.

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

<br><br>

### HandlerMapping 등록

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

<br><br>

결과 <br>

![image](https://user-images.githubusercontent.com/51431766/75887401-17aecf80-5e6d-11ea-900d-c6563d037cc3.png)

<br><br>
