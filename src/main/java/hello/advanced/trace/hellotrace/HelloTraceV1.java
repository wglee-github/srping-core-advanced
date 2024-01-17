package hello.advanced.trace.hellotrace;

import org.springframework.stereotype.Component;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 실제 로그를 출력해주는 클래스
 * <p>시작로그, 종료로그, 에러로그를 남긴다. 
 * 시작 및 종료, 에러 로그를 남길때 시간도 출력해준다.
 * <p>싱글톤 클래스로 사용하기위해 스프링 빈에 등록해주자.
 * 왜 싱글톤으로 사용해야하는가?
 * 
 * @author wglee
 *
 */
@Slf4j
@Component
public class HelloTraceV1 {

	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";

	/**
	 * @desc 로그 시작 정보 리턴
	 * @param message
	 * @return
	 */
	public TraceStatus begin(String message) {
		TraceId traceId = new TraceId();
		Long startTimeMs = System.currentTimeMillis();
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
		return new TraceStatus(traceId, startTimeMs, message);
	}
	
	/**
	 * @desc 로그 종료 정보 리턴
	 * @param status
	 */
	public void end(TraceStatus status) {
		complete(status, null);
	}

	/**
	 * @desc 로그 에러 정보 리턴
	 * @param status
	 * @param e
	 */
	public void exception(TraceStatus status, Exception e) {
		complete(status, e);
	}
	
	/**
	 * @desc 실제 로그를 남김
	 * @param status
	 * @param e
	 */
	private void complete(TraceStatus status, Exception e) {
		Long stopTimeMs = System.currentTimeMillis();
		long resultTimeMs =  stopTimeMs - status.getStartTimeMs();
		TraceId traceId = status.getTraceId();
		if(e == null) {
			log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
		}else {
			log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
		}
	}

	/**
	 * @desc 서비슬 레벨을 구분
	 * @param prefix
	 * @param level
	 * @return
	 */
	private Object addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append((i == level -1) ? "|"+prefix : "|   ");
		}
		return sb.toString();
	}
}
