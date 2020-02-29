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

### 세터 인젝션 + 다형성 적용

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
