package net.koreate.greatescape.common.task;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.product.dao.ProductDAO;
import net.koreate.greatescape.product.service.ProductFileService;

@Component
@RequiredArgsConstructor
public class GreatTask {
	
	private final ProductDAO dao;
	private final ProductFileService pfs;
	
	@Scheduled(cron = "* * 4 * * *")
	@Transactional
	public void deleteTrashFile() throws Exception {
		List<String> trashList = dao.getTrashList();
		for (String s : trashList) {
			pfs.deleteFile(s);
		}
		dao.deleteTrashList();
	}

}

