package net.koreate.greatescape.product.service;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.product.dao.ProductDAO;
import net.koreate.greatescape.product.vo.FullProductDTO;
import net.koreate.greatescape.utils.FileUtils;

@Service
@RequiredArgsConstructor
public class ProductFileServiceImpl implements ProductFileService {

	private final String uploadFolder;
	private final ServletContext context;
	private final ProductDAO dao;
	private String realPath;
	

	@PostConstruct
	public void initPath() {
		realPath = context.getRealPath(File.separator + uploadFolder);
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	@Override
	public String uploadFile(MultipartFile titleImage) throws Exception {
		String uploadedName = FileUtils.uploadFile(realPath, titleImage);
		return uploadedName;
	}

	@Override
	public String updateFile(String originalTitleImage, MultipartFile titleImage) throws Exception {
		if (originalTitleImage != null && !originalTitleImage.equals("")) {
			FileUtils.deleteFile(realPath, originalTitleImage);
		}
		return uploadFile(titleImage);
	}

	@Override
	public String uploadHtmlImage(MultipartFile file, List<String> imageNameList) throws Exception {
		String uploadedName = uploadFile(file);
		imageNameList.add(uploadedName);
		dao.addHTMLImage(uploadedName);
		uploadedName = context.getContextPath() + "/" + uploadFolder + uploadedName;
		return uploadedName;
	}

	@Override
	@Transactional
	public boolean deleteFileById(int id) throws Exception {
		FullProductDTO dto = dao.getFullProductById(String.valueOf(id));
		String fileName = dto.getDetail_title_image();
		if (fileName != null) {
			FileUtils.deleteFile(realPath, fileName);
		}
		List<String> fileNameList = dao.getFileNameList(id);
		for (String name : fileNameList) {
			deleteFile(name);
		}
		dao.deleteFileNameList(id);
		return true;
	}

	@Override
	public void deleteFile(String fileName) throws Exception {
		if (fileName != null) {
			FileUtils.deleteFile(realPath, fileName);
		}
	}

}
