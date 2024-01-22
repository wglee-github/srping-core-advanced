package hello.advanced.trace.template;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Super;

/**
 * 템플릿 메서드 패턴 테스트
 * 템플릿이라는 큰 틀을 만든 후 변하지 않는 부분을 몰아 넣는다.
 * 템플릿안에서 변하는 부분만 호출하여 해결한다.
 * 
 * @author wglee
 *
 */
@Slf4j
public class TemplateMethodTest {

//	@Test
	void templateMethodVO() {
		logic1();
		logic2();
	}
	
	private void logic1() {
		long startTime = System.currentTimeMillis();
		// 비즈니스 로직 실행
		log.info("비즈니스 로직1 실행");
		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		
		log.info("resultTime={}", resultTime);
	}
	
	private void logic2() {
		long startTime = System.currentTimeMillis();
		// 비즈니스 로직 실행
		log.info("비즈니스 로직2 실행");
		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		
		log.info("resultTime={}", resultTime);
	}
	
	/**
	 * 템플릿 메서드 패턴 사용
	 * - 변하지 않는 로직과 변화를 로직을 분리한다.
	 * 1. 변하지 않는 로직을 정의할 AbstractTemplate class(abstract)를 만든다. 
	 *    그리고 변하는 로직을 재정의 할 call() method(abstact)를 정의한다.
	 *    변하지 않는 로직은 execute() 메서드에 정의한 후 call() 메서드는 호출한다.   
	 * 2. 변하는 로직을 정의할 SubClassLogic1,2 class를 만든다. 그리고 AbstractTemplate class(abstract)를 상속 받은 후 
	 *    call() method(abstact)를 오버라이딩 해서 변하는 로직을 정의한다. 
	 */
//	@Test
	void templateMethodV1() {
		AbstractTemplate template1 = new SubClassLogic1();
		template1.execute();
		
		AbstractTemplate template2 = new SubClassLogic2();
		template2.execute();
	}
	
	/**
	 * 익명 내부 클래스 사용
	 * - 추상 클래스를 상속 받을 클래스를 만들지 않고, 
	 *   추상 클래스 인스턴스를 생성 한 후 추상 클래스 안에 있는 추상 메서드를 바로 오버라드 해서 사용하는 방법이다.
	 *   이렇게 하면 굳이 자식 클래스를 만들지 않아도 된다. 
	 */
//	@Test
	void templateMethodV2() {
		AbstractTemplate template1 = new AbstractTemplate() {
			@Override
			protected void call() {
				log.info("비즈니스 로직1 실행");
			}
		};
		log.info("클래스1 name={}", template1.getClass());
		template1.execute();
		
		AbstractTemplate template2 = new AbstractTemplate() {
			@Override
			protected void call() {
				log.info("비즈니스 로직2 실행");
			}
		};
		log.info("클래스2 name={}", template2.getClass());
		template2.execute();
	}
	
	@Test
	void extendTest() {
		Children child = new Children();
		child.pTest1();	// 자식은 부모의 메서드에 접근 가능함.
		child.cTest1();
		
		Parent p = new Children();
		p.pTest1();
//		p.cTest1();	// 부모는 자식의 필드나, 메서드에 접근할 수 없다.
	}
	
}

//---------- 간단한 상속 상식 ---------

class Parent{

	private String item;
	protected String member;
	
	protected void pTest1() {
		System.out.println("부모 클래스 입니다.");
	}
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}

/**
 * 자식은 부모의 모든걸 사용할 수 있다.
 * 단, 부모가 private으로 선언한 필드, 메서드는 사용할 수 없다.
 */
class Children extends Parent{
	private String name;
	
	public void cTest1() {
		System.out.println("자식 클래스 입니다.");
//		System.out.println(this.item);	// 부모가 private으로 선언했기 때문에 접근 안됨.
		System.out.println(this.member); // protected, public으로 선언한 필드나, 메서드에 접근 가능함.	
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
