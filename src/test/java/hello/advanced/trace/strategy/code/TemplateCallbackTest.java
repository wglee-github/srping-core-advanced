package hello.advanced.trace.strategy.code;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.template.CallBack;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateCallbackTest {

	/**
	 * 템플릿 콜백 패턴 - 익명 내부 클랙스
	 */
	@Test
	void callbackV1() {
		TimeLogTemplate logTemplate = new TimeLogTemplate();
		logTemplate.execute(new CallBack() {
			
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		});
		logTemplate.execute(new CallBack() {
			
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행");
			}
		});
	}
	
	/**
	 * 템플릿 콜백 패턴 - 람다
	 */
	@Test
	void callbackV2() {
		TimeLogTemplate logTemplate = new TimeLogTemplate();
		logTemplate.execute(() -> log.info("비즈니스 로직1 실행"));
		logTemplate.execute(() -> log.info("비즈니스 로직2 실행"));
	}
}
