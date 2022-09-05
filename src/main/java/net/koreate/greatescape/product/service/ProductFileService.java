package net.koreate.greatescape.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductFileService {

	String uploadFile(MultipartFile titleImage) throws Exception;

	String uploadHtmlImage(MultipartFile file, List<String> imageNameList) throws Exception;
	
	public String updateFile(String originalTitleImage, MultipartFile titleImage) throws Exception;

	boolean deleteFileById(int id) throws Exception;

	void deleteFile(String fileName) throws Exception;

}
