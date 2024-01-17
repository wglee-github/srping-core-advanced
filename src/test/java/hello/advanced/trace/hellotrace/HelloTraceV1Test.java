package hello.advanced.trace.hellotrace;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.TraceStatus;

class HelloTraceV1Test {

	@Test
	void begin_end() {
		HelloTraceV1 trace = new HelloTraceV1();
		TraceStatus status = trace.begin("hello");
		TraceStatus status1 = trace.begin("hello1");
		trace.end(status1);
		trace.end(status);
	}

//	@Test
	void begin_exception() {
		HelloTraceV1 trace = new HelloTraceV1();
		TraceStatus status = trace.begin("hello");
		trace.exception(status, new IllegalStateException());
	}
}
