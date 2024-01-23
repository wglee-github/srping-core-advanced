package hello.advanced.trace.strategy.code;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 전략패턴
 * - 알고리즘 제품군을 정의하고 각각을 캡슐화하여 상호 교환 가능하게 만들자. 
 *   전략을 사용하면 알고리즘을 사용하는 클라이언트와 독립적으로 알고리즘을 변경할 수 있다.
 *   
 * - 변하지 않는 부분을 Context라는 곳에 두고, 변하는 부분을 Strategy라는 인터페이스를 만들고
 *   해당 인터페이스를 구현하도록 해서 문제를 해결한다. 상속이 아니라 위임으로 문제를 해결하는 패턴이다.
 *   
 * - 전략패턴에서는 Context는 변하지 않는 템플릿 역할을 하고, Strategy는 변하는 알고리즘 역할을 한다.   
 *   
 * 전략패턴 : 전략을 필드에 보관하는 방식
 * 
 * 선 조립 후 실행
 * - Context의 내부 필드에 Strategy를 두고 사용하는 부분을 보자.
 *   이 방식은 Context와 Strategy를 실행 전에 원하는 모양으로 조립해두고, 그 다음에 Context를 실행하는 선 조립, 후 실행 방식에서 유용하다.
 * - Context와 Strategy를 한번 조립하고 나면 이후로는 Context를 실행하기만 하면 된다. 
 * - 우리가 스프링으로 애플리케이션을 개발할 때 애플리케이션 로딩 시점에 의존관계 주입을 통해 필요한 의존관계를 맺어두고 난 다음에 실제 요청을
 *   처리하는것과 같은 원리이다. 
 *
 * 단점
 * - Context와 Strategy를 조립하고 난 후에는 전략을 변경하기 번거롭다는 점이다. 물론 Context에 Setter를 제공해서 Strategy를 넘겨받아
 *   변경하면 되지만, Context를 싱글톤으로 사용할 때는 동시성 이슈 등 고려할 점이 많다. 
 *   그래서 전략을 실시간으로 변경해야 하면 차라리 Context를 하나더 생성하고 그곳에 다른 Strategy를 주입하는것이 더 나은 선택일 수 있다.
 *   
 * 전략패턴 : 전략을 파라미터로 받는 방식
 * 
 * 실행 시점에 조립
 * - Context와 Strategy를 선 조립 후 실행하는 방식이 아니라 Context를 실행할 때 마다 전략을 인수로 전달한다.
 *   클라이언트는 Context를 실행하는 시점에 원하는 Strategy를 전달할 수 있다.
 *   따라서 이전 방식(선 조립 후 실행)과 비교하여 원하는 전략을 더욱 유연하게 변경할 수 있다.
 * - 또한 Context를 하나만 생성한다. 하나의 Context에 실행 시점에 여러 전략을 인수로 전달하여 유연하게 실행할 수 있다.
 * 
 *  실제 우리가 주로 원하는 것은 변하지 않는 부분은 템플릿에 넣고, 변하는 부분을 우리가 실행하는 시점에 살짝 다른 코드로 실행하고 싶은 경우가 많다.
 *  따러서 전략패턴 방식 중 파라미터로 전달하는 방식이 더 적합하다. 
 *   
 */
@Slf4j
public class ContextTest {

//	@Test
	void excute() {
		logic1();
		logic2();
	}
	
	void logic1() {
		try {
			long startTime = System.currentTimeMillis();
			
			// 비즈니스 로직
			log.info("비즈니스 로직 1 실행");
			
			long endTime = System.currentTimeMillis();
			log.info("실행 시간 = {}", (endTime - startTime));
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	void logic2() {
		try {
			long startTime = System.currentTimeMillis();
			
			// 비즈니스 로직
			log.info("비즈니스 로직 2 실행");
			
			long endTime = System.currentTimeMillis();
			log.info("실행 시간 = {}", (endTime - startTime));
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 전략패턴(필드 보관) : 기본
	 */
//	@Test
	void strategy1() {
		Strategy strategy1 = new StrategyLogic1();
		Context1 context1 = new Context1(strategy1);
		context1.excute();
		
		Strategy strategy2 = new StrategyLogic2();
		Context1 context2 = new Context1(strategy2);
		context2.excute();
	}
	
	/**
	 * 전략패턴(필드 보관) : 익명 내부 클래스 사용.
	 * - 익명 내부 클래스를 사용하면 인터페이스의 구현클래스를 만들지 않아도 된다.
	 */
//	@Test
	void strategy2() {
		Strategy strategy1 = new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직 1 실행");
			}
			
		};
		Context1 context1 = new Context1(strategy1);
		context1.excute();
		
		Strategy strategy2 = new Strategy() {

			@Override
			public void call() {
				log.info("비즈니스 로직 2 실행");
			}
		};
		Context1 context2 = new Context1(strategy2);
		context2.excute();
	}
	
	/**
	 * 전략패턴(필드 보관) : 익명 내부 클래스 코드 줄이기
	 * 인터페이스를 구현한 후 Context에 주입해주는걸 한번에 해결할 수 있다.
	 */
//	@Test
	void strategy3() {
		Context1 context1 = new Context1(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직 1 실행");
			}
			
		});
		context1.excute();
		
		Context1 context2 = new Context1(new Strategy() {

			@Override
			public void call() {
				log.info("비즈니스 로직 2 실행");
			}
		});
		context2.excute();
	}
	
	/**
	 * 전략패턴(필드 보관) : 람다 사용
	 * - 람다를 사용하려면 인터페이스에 메서드가 1개만 존재해야 한다.
	 */
//	@Test
	void strategy4() {
		Context1 context1 = new Context1(() -> log.info("비즈니스 로직 1 실행"));
		context1.excute();
		
		Context1 context2 = new Context1(() -> log.info("비즈니스 로직 2 실행"));
		context2.excute();
	}
	
	/**
	 * 전략 패턴(파라미터 전달) : 기본
	 */
//	@Test
	void strategy5() {
		Context2 context1 = new Context2();
		context1.excute(new StrategyLogic1());
		context1.excute(new StrategyLogic2());
	}
	
	/**
	 * 전략 패턴(파라미터 전달) : 익명 내부 클래스 사용
	 */
//	@Test
	void strategy6() {
		Context2 context1 = new Context2();
		context1.excute(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직 1 실행");
			}
		});
		
		context1.excute(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직 2 실행");
			}
		});
	}
	
	/**
	 * 전략 패턴(파라미터 전달) : 람다
	 */
	@Test
	void strategy7() {
		Context2 context1 = new Context2();
		context1.excute(() -> log.info("비즈니스 로직 1 실행"));
		context1.excute(() -> log.info("비즈니스 로직 2 실행"));
	}
}

/**
 * 전략패턴 : 전략을 필드에 보관하는 방식 
 */
@Slf4j
class Context1 {
	private Strategy strategy;

	public Context1(Strategy strategy) {
		this.strategy = strategy;
	}

	public void excute() {
		try {
			long startTime = System.currentTimeMillis();
			
			// 비즈니스 로직
			strategy.call();
			
			long endTime = System.currentTimeMillis();
			log.info("실행 시간 = {}", (endTime - startTime));
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
}

/**
 * 전략패턴 : 전략을 파라미터로 받는 방식 
 */
@Slf4j
class Context2 {
	
	public void excute(Strategy strategy) {
		try {
			long startTime = System.currentTimeMillis();
			
			// 비즈니스 로직
			strategy.call();
			
			long endTime = System.currentTimeMillis();
			log.info("실행 시간 = {}", (endTime - startTime));
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}

interface Strategy {
	void call();
}

@Slf4j
class StrategyLogic1 implements Strategy{

	@Override
	public void call() {
		log.info("비즈니스 로직 1 실행");
	}
	
}

@Slf4j
class StrategyLogic2 implements Strategy{

	@Override
	public void call() {
		log.info("비즈니스 로직 2 실행");
	}
	
}