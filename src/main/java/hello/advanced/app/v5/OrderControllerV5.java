package hello.advanced.app.v5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@RestController
public class OrderControllerV5 {

	private final OrderServiceV5 orderService;
	private final TraceTemplate traceTemplate;
	
	
	
	public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
		this.orderService = orderService;
		this.traceTemplate = new TraceTemplate(logTrace);
	}



	@GetMapping("/v5/request")
	public String request(String itemId) {
		return traceTemplate.execute("OrderControllerV5.request()", new TraceCallback<String>() {
			@Override
			public String call() {
				orderService.orderItem(itemId);
				return "ok";
			}
		});
	}
}
