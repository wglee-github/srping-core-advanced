package hello.advanced.trace;

/**
 * 로그 상태정보 관리 클래스
 * <p>로그의 시작과 끝을 구분하기 위해 로그가 시작할 때의 정보를 관리한다.
 * @author wglee
 *
 */
public class TraceStatus {

	private TraceId traceId;
	private Long startTimeMs;
	private String message;

	public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
		this.traceId = traceId;
		this.startTimeMs = startTimeMs;
		this.message = message;
	}

	public TraceId getTraceId() {
		return traceId;
	}
	public Long getStartTimeMs() {
		return startTimeMs;
	}
	public String getMessage() {
		return message;
	}
}
