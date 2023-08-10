package hello.advanced.trace.template;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateMethodTest {

	@Test
	void templateMethodVO() {
		logic1();
		logic2();
	}
	
	private void logic1() {
		long startTime = System.currentTimeMillis();
		
		log.info("비즈니스 로직1 실행");
		
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		
		log.info("resultTime={}", resultTime);
	}
	
	private void logic2() {
		long startTime = System.currentTimeMillis();
		
		log.info("비즈니스 로직2 실행");
		
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		
		log.info("resultTime={}", resultTime);
	}
	
	/**
	 * 템플릿 메서드 패턴 사용
	 */
	@Test
	void templateMethodV1() {
		AbstractTemplate template1 = new SubClassLogic1();
		template1.execute();
		
		AbstractTemplate template2 = new SubClassLogic2();
		template2.execute();
	}
	
	/**
	 * 익명 내부 클래스 사용
	 */
	@Test
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
}
