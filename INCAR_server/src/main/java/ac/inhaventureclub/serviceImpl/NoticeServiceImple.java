package ac.inhaventureclub.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ac.inhaventureclub.repository.NoticeRepository;
import ac.inhaventureclub.service.NoticeService;

@Service("noticeservice")
public class NoticeServiceImple implements NoticeService{
	@Autowired
	NoticeRepository noticeRepository;
	
	/* find */
	@Override
	public String findAll() {
		return new Gson().toJson(noticeRepository.findAll());
	}
	@Override
	public String findNoticeWithNoticeindex(int notice_index) {
		return new Gson().toJson(noticeRepository.findNoticeWithNoticeindex(notice_index));
	}
	
	
	/* save */
}
