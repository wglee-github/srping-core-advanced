package hello.advanced.app.v5;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;

@Repository
public class OrderRepositoryV5 {

	private final TraceTemplate traceTemplate;
	
	public OrderRepositoryV5(LogTrace trace) {
		this.traceTemplate = new TraceTemplate(trace);
	}

	public void save(String itemId) {
		
		traceTemplate.execute("OrderServiceV5.orderItem()", () -> {
			if(itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생!");
			}
			sleep(1000);
			return null;
		});
	}

	private void sleep(int nillis) {
		try {
			Thread.sleep(nillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
