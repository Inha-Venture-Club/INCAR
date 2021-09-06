package ac.inhaventureclub.service;

import org.springframework.stereotype.Service;

@Service
public interface NoticeService {
	/* find */
	public String findAll();
	public String findNoticeWithNoticeindex(int notice_index);
	
	/* save */
}
