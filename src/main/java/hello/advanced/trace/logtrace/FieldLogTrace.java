package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace{

	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";
	
	/**
	 * 서비스간 ID를 공유하기 위해서 파라미터를 넘기지 않고 ID를 공유하기 위해 FieldLogTrace 클래스 안에 보관하기위해 선언.
	 * FieldLogTrace 클래스가 스프링 빈으로 등록되면 싱글톤으로 적용되기 때문에 해당 필드는 모든 애플리케이션에서 동일한 값을 공유하게 된다.
	 * 이럴경우 동시성 이슈 발생한다.
	 * 따라서 http 요청별 따라 traceIdHolder 값이 변경되기를 원한다면 싱글톤스코프가 아닌 request 스코프를 사용하거나, 특정 시점에 null로 초기화 해주어야 한다.
	 * 하지만, 특정 시점에 null로 초기화 해준다고 하더라도 http 요청이 null 해주는 시점보다 요청이 빠른 경우 동시성 이슈는 여전히 존재한다. 예) 1초에 두번 호출 등
	 * (request 스코프를 사용하면 해결할 수 있으나, 싱글톤을 사용하지 못하는 문제가 있다. request 스코프에 대한 자세한 내용은 일단 패스)
	 */
	private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생
	
	/**
	 * FieldLogTrace 클래스가 스프링빈에 등록되는 시점에 호출된다.
	 */
	public FieldLogTrace() {
		log.info("FieldLogTrace 생성자 호출");
	}

	@Override
	public TraceStatus begin(String message) {
		syncTraceId();
		TraceId traceId = traceIdHolder;
		Long startTimeMs = System.currentTimeMillis();
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
		return new TraceStatus(traceId, startTimeMs, message);
	}

	/**
	 * ID를 공유하기 위해 TraceId객체 필드인 traceIdHolder에 새로운 TraceId 객체를 넣어준다.
	 * 단, 이미 객체가 존재하는 않는경우에만 새로운 TraceId 객체를 넣어준다. 
	 * traceIdHolder 가 null이 되는 상황은 FieldLogTrace 객체를 new 해줄때 마다 null이 된다.
	 */
	private void syncTraceId() {
		if(traceIdHolder == null) {
			traceIdHolder = new TraceId();
		}else {
			traceIdHolder = traceIdHolder.createNextId();
		}
	}
	
	@Override
	public void end(TraceStatus status) {
		complete(status, null);
	}

	@Override
	public void exception(TraceStatus status, Exception e) {
		complete(status, e);
	}

	private void complete(TraceStatus status, Exception e) {
		Long stopTimeMs = System.currentTimeMillis();
		long resultTimeMs =  stopTimeMs - status.getStartTimeMs();
		TraceId traceId = status.getTraceId();
		if(e == null) {
			log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
		}else {
			log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
		}
		
		releaseTraceId();
	}

	private void releaseTraceId() {
		if(traceIdHolder.isFirstLevel()) {
			traceIdHolder = null; //destroy. 약간의 동시성 문제를 해결할 수 있으나 근본적인 문제를 해결하지는 못한다.
		}else {
			traceIdHolder = traceIdHolder.createPreviousId();
		}
	}

	private Object addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append((i == level -1) ? "|"+prefix : "|   ");
		}
		return sb.toString();
	}
}
