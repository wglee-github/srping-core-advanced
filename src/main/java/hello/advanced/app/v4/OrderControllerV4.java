package hello.advanced.app.v4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

/**
 * 템플릿 메서드 패턴
 * - 작업에서 알고리즘의 골격을 정의하고 일부 단계를 하위 클래스로 연기한다.
 *   템플릿 메서드를 사용하면 하위 클래스가 알고리즘의 구조를 변경하지 않고도 알고리즘의 특정 단계를 재정의 할 수 있다.
 * 
 * - 부모 클래스에 알고리즘의 골격인 템플릿을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는 것이다.
 *   이렇게 하면 자식 클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의할 수 있다.
 *   결국 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것이다.
 * 
 * 
 * 상속의 단점
 * - 자식 클래스가 부모 클래스와 컴파일 시점에 강하게 결함되는 문제가 있다. 이것은 의존관계의 문제이다.
 *   자식 클래스는 실제로 부모 클래스의 기능을 전혀 사용하고 있지 않지만 템플릿 메서드 패턴을 위해 자식 클래스는 부모 클래스를 상속받고 있다.
 *   즉, 자식 클래스는 부모의 기능을 전혀 사용하고 있지 않는데 부모 클래스를 알아야 한다. 이것은 좋은 설계가 아니다.
 *   
 *   자식이 부모에 의존하고 있기 때문에 부모가 변경되는 상황에 그대로 영향을 받을 수 밖에 없다.
 *   추가로 템플릿 메서드는 패턴은 상속 구조를 사용하기 때문에 별도의 클래스나 익명 내부 클래스를 만들어야 하는 부분도 복잡하다. 
 * 
 * 
 * 좋은 설계란?
 * - 변경이 일어날 때 자연스럽게 드러난다.
 * - 로그는 남기는 부분을 모듈화 하고, 비즈니스 부분을 분리했다. 
 *   만약 로그 남기는 부분을 변경해야 한다고 했을 때 AbstractTemplate 부분만 수정해주면 된다.
 *
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

	private final OrderServiceV4 orderService;
	private final LogTrace trace;
	
	/**
	 * 템플릿 메서드 패턴 적용
	 * @param itemId
	 * @return
	 */
	@GetMapping("/v4/request")
	public String request(String itemId) {
		// 단일 책임 원칙(SRP)을 지키는 구조
		AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
			
			@Override
			protected String call() {
				orderService.orderItem(itemId);
				return "ok";
			}
		};
		
		return template.execute("OrderControllerV4.request()");
	}
}
