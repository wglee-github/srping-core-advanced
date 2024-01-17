package hello.advanced.trace;

import java.util.UUID;

/**
 * 로그 추적기 관리 클래스
 * <p>HTTP 요청별 아이디(ID)를 관리하고, 요청 흐름을 추적(Level)한다.
 * @author wglee
 *
 */
public class TraceId {

	private String id;	// HTTP 요청별 유니크 ID
	private int level;	// 서비스 레벨

	public TraceId() {
		this.id = createId();
		this.level = 0;
	}
	
	private TraceId(String id, int level) {
		this.id = id;
		this.level =level;
	}
	
	private String createId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
	
	public TraceId createNextId() {
		return new TraceId(id, level + 1);
	}
	
	public TraceId createPreviousId() {
		return new TraceId(id, level - 1);
	}
	
	public boolean isFirstLevel() {
		return level == 0;
	}
	
	public String getId() {
		return id;
	}
	public int getLevel() {
		return level;
	}
}
