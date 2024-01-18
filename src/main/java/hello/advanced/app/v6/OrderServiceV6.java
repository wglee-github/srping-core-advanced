package hello.advanced.app.v6;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceV6 {

	private final CmsStatus cmsStatus;
	private static String CMS_STATUS = "대기";
	
	public String reqCms(String id) {
		if(CMS_STATUS.equals("진행"))
			return "요청 처리 진행중 입니다.";
//		if(cmsStatus.getProgress_status().equals("진행"))
//			return "요청 처리 진행중 입니다.";
		
//		cmsStatus.req();
		CMS_STATUS = "진행";
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				log.info("CMS 요청 상태 {}", CMS_STATUS);
//				log.info("CMS 요청 상태 {}", cmsStatus.getProgress_status());
				sleep(5000);
				CMS_STATUS = "완료";
				log.info("CMS 요청 상태 {}", CMS_STATUS);
//				cmsStatus.complete();
//				log.info("CMS 요청 상태 {}", cmsStatus.getProgress_status());
			}
		});
		
		thread.start();
		
		return "CMS 요청";
	}

	private void sleep(int million) {
		try {
			Thread.sleep(million);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
