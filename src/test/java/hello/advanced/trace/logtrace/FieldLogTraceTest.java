package hello.advanced.trace.logtrace;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.TraceStatus;

class FieldLogTraceTest {

	FieldLogTrace fieldLogTrace = new FieldLogTrace();
	
	@Test
	void bigin_end_level2() {
		
		TraceStatus status1 = fieldLogTrace.begin("hello1");
		TraceStatus status2 = fieldLogTrace.begin("hello2");
		fieldLogTrace.end(status2);
		fieldLogTrace.end(status1);
	}

//	@Test
	void bigin_exception_level2() {
		TraceStatus status1 = fieldLogTrace.begin("hello1");
		TraceStatus status2 = fieldLogTrace.begin("hello2");
		fieldLogTrace.exception(status2, new IllegalStateException("에러 발생"));
		fieldLogTrace.exception(status1, new IllegalStateException("에러 발생"));
	}
}
