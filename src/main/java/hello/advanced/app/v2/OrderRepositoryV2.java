package hello.advanced.app.v2;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

	private final HelloTraceV2 trace;
	
	public void save(TraceId traceId, String itemId) {
		
		TraceStatus status = null;
		try {
			status = trace.beginSync(traceId, "OrderRepositoryV2.save()");
			
			if(itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생!");
			}
			sleep(1000);
			
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;	// 예외를 꼭 다시 던져줘야한다.
		}
		
		
	}

	private void sleep(int nillis) {
		try {
			Thread.sleep(nillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
