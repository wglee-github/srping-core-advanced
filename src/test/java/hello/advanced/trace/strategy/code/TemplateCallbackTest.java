package hello.advanced.trace.strategy.code;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.template.CallBack;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 콜백 
 * - 프로그래밍에서 콜백(callback) 또는 콜 애프터 함수(call-after-function)는 다른 코드의 인수로서 넘겨주는 실행 가능한 코드를 말한다.
 *   콜백을 넘겨받는 코드는 이 콜백을 바로 실행할 수도 있고, 나중에 실행할 수도 있다.
 * 
 * 템플릿 콜백 패턴
 * - 스프링에서는 Context라는 템플릿에 Strategy라는 콜백을 넘긴다.
 *   풀어서 설명하자면 클라이언트에서 Strategy를 실행하는 것이 아니라 Context에  Strategy라는 콜백을 넘기면 Context뒤에서 Strategy가 실행된다. 
 *   참고로 템플릿 콜백 패턴은 GOF 패턴은 아니고, 스프링 내부에서 이런 방식을 자주 사용하기 때문에
 *   스프링 안에서만 부르는 용어이다. 
 * - 전략 패턴에서 템플릿과 콜백 부분이 강조된 패턴이라 생각하면 된다.
 * 
 * 스프링 콜백 패턴의 사용
 * - JdbcTemplate, RestTemplate, TransactionTemplate, RedisTemplate 등 다양한 템플릿 콜백 패턴이 사용된다.
 *
 * 자바에서의 콜백
 * - 자바 8 이전에는 하나의 메서드를 가지는 인터페이스를 구현하고, 주로 익명 내부 클래스를 사용했다.
 *   자바 8 이후에는 람다를 주로 사용한다. 
 */
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
