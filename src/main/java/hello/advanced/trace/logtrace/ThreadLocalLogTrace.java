package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";
	
//	private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생
	private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();	// 동시성 이슈 발생 안함.
	
	@Override
	public TraceStatus begin(String message) {
		syncTraceId();
		TraceId traceId = traceIdHolder.get();
		Long startTimeMs = System.currentTimeMillis();
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
		return new TraceStatus(traceId, startTimeMs, message);
	}

	private void syncTraceId() {
		TraceId traceId = traceIdHolder.get();
		if(traceId == null) {
			traceIdHolder.set(new TraceId());
		}else {
			traceIdHolder.set(traceId.createNextId());
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

	/**
	 * ThreadLocal 사용 시 주의 사항
	 * Http로 [A]요청이 들어온 경우 Was가 쓰레드풀에 1번 쓰레드를 할당해서 요청 처리를 진행한다. 이때  1번 쓰레드의 ThreadLocal 전용 방이 생성된다. 
	 * 이후 Http [B]요청이 들어오면 Was가 쓰레드풀에서 2번 쓰레드를 할당해서 요청 처리를 진행한다. 이때 2번 쓰레드의 ThreadLocal 전용 방이 생성된다.
	 * 자 그런데 [A] 요청 처리가 완료가 되면 Was는 사용했던 쓰레드를 다시 쓰레드 풀에 반환하게 되는데 이때 1번 쓰레드가 사용한 ThreadLocal를 제거해주지 않으면 정보가 사라지지 않는다.
	 * Was가 쓰레드 풀에 사용했던 쓰레드를 반환하는 이유는 쓰레드 생성 비용이 비싸기 때문에 재사용하기 위해서다. 따라서 살아있는 쓰레드라는 말이다. 
	 * 꼭 ThreadLocal를 사용하는 경우 요청이 완료되면 remove()를 해주자. 
	 * 
	 */
	private void releaseTraceId() {
		TraceId traceId = traceIdHolder.get();
		if(traceId.isFirstLevel()) {
			traceIdHolder.remove(); //destroy
		}else {
			traceIdHolder.set(traceId.createPreviousId());
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
