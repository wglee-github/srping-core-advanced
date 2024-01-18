package hello.advanced.app.v6;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderControllerV6 {

	private final OrderServiceV6 orderService;
	
	@GetMapping("/v6/request")
	public String reqCms(String id) {
		return orderService.reqCms(id);
	}
	
}
