package hello.advanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LogTraceConfig {

	@Bean
	public LogTrace logTrace() {
		log.info("logTrace bean 등록");
		return new FieldLogTrace();
//		return new ThreadLocalLogTrace();
	}
}
