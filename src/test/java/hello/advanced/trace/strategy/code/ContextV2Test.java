package hello.advanced.trace.strategy.code;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.strategy.ContextV2;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2Test {

	/**
	 * 전략 패턴 적용
	 */
//	@Test
	void strategyV1() {
		ContextV2 context1 = new ContextV2();
		context1.execute(new StrategyLogic1());
		context1.execute(new StrategyLogic2());
		
	}
	
	/**
	 * 전략 패턴 익명 내부 클래스
	 */
	@Test
	void strategyV2() {
		ContextV2 context1 = new ContextV2();
		context1.execute(new Strategy() {
			
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		});
		context1.execute(new Strategy() {
			
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행");
			}
		});
		
	}
	
	/**
	 * 전략 패턴 익명 내부 클래스2 람다 적용
	 */
	@Test
	void strategyV3() {
		ContextV2 context1 = new ContextV2();
		context1.execute(() -> log.info("비즈니스 로직1 실행"));
		context1.execute(() -> log.info("비즈니스 로직2 실행"));
		
	}
}
