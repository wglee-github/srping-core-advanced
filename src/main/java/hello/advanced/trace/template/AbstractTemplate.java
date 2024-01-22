package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

	private final LogTrace trace;
	
	public AbstractTemplate(LogTrace trace) {
		this.trace = trace;
	}

	public T execute(String message) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);
			
			/**
			 *  로직 실행 call 메서드에서 리턴을 받으면 된다.
			 */
			T result = call();
			trace.end(status);
			return result;
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;	// 예외를 꼭 다시 던져줘야한다.
		}
	}

	protected abstract T call();
}
