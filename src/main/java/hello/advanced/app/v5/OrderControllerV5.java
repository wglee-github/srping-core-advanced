package hello.advanced.app.v5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;

/**
 * 템플릿 콜백 패턴 적용 
 *
 */
@RestController
public class OrderControllerV5 {

	private final OrderServiceV5 orderService;
	private final TraceTemplate traceTemplate;
	
	/**
	 * TraceTemplate 를 빈에 등록한 후 싱글톤으로 사용하자.
	 * TraceTemplate 코드를 보면 생성자에서 LogTrace 를 주입받는다.
	 * 1. 생성자를 통해 Service와 LogTrace를 주입 받은 후 빈에 등록한다. 
	 * 2. TraceTemplate 에 @Component 를 선언하여 자동으로 빈에 등록해서 사용한다.
	 *    이러면 LogTrace도 자동 주입 받는다. (TraceTemplate 생성자를 통해)
	 *  
	 * 1번으로 사용하는 이유는 테스트 시 유리한 면이 있다.
	 * 하지만 1번을 사용하든 2번을 사용하든 전혀 문제 없음.
	 * 
	 * LogTrace가 빈에 등록되어 있으면 이 처럼 생성자를 통해 주입해 줄 수 있다.(당연한 건데 어색함)
	 */
	public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
		this.orderService = orderService;
		this.traceTemplate = new TraceTemplate(logTrace);
	}

	/**
	 * 익명 내부 클래스 사용
	 * @param itemId
	 * @return
	 */
	@GetMapping("/v5/request")
	public String request(String itemId) {
		return traceTemplate.execute("OrderControllerV5.request()",new TraceCallback<>() {
			@Override
			public String call() {
				orderService.orderItem(itemId);
				return "ok";
			}
		});
	}
	
	/**
	 * 람다 사용
	 * @param itemId
	 * @return
	 */
	@GetMapping("/v5.1/request")
	public String request2(String itemId) {
		return traceTemplate.execute("OrderControllerV5.request()", () -> {
			orderService.orderItem(itemId);
			return "ok";
		});
	}
	
	
}
