package ac.inhaventureclub.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.NoticeRepository;
import ac.inhaventureclub.service.NoticeService;
import ac.inhaventureclub.vo.Notice;

@RestController
public class NoticeController {
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Resource(name="noticeservice")
	NoticeService noticeService;
	
	/* find */
	// Find Notices
	@ResponseBody
	@GetMapping("/notice")
	public String getNotices() {
		return noticeService.findAll();
	}
	
	// Find Notice with NOTICE_INDEX
	@ResponseBody
	@PostMapping("/noticeWithNoticeindex")
	public String getNoticeWithNoticeindex(@RequestBody String vo) {
		Gson gson = new Gson();
		Notice notice = gson.fromJson(vo, Notice.class);
		return noticeService.findNoticeWithNoticeindex(notice.NOTICE_INDEX);
	}
	/* save */
}
	
	
	
