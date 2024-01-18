package hello.advanced.app.v6;

import org.springframework.stereotype.Component;

@Component
public class CmsStatus {

	private String progress_status = "대기";
	
	public void req() {
		progress_status = "진행";
	}
	
	public void complete() {
		progress_status = "완료";
	}
	
	public String getProgress_status() {
		return progress_status;
	}

	public void setProgress_status(String progress_status) {
		this.progress_status = progress_status;
	}
}
