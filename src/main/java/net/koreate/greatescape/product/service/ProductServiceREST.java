package net.koreate.greatescape.product.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.koreate.greatescape.product.vo.FullProductDTO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;

public interface ProductServiceREST {

	List<ProductVO> getAllList();

	List<ProductVO> getListByContinent(String continent);

	List<ProductVO> getListByCity(String city);

	FullProductDTO getFullProductById(String id);

	ProductVO getProductById(String id);

	List<ProductVO> getListBySearch(String country, String departure, String plan, String seat, String city, String money);
	
	int createProduct(FullProductDTO dto, String departure, String arrive) throws ParseException;

	int reserve(ReservationVO rvo);

	int updateProduct(int id, FullProductDTO dto, String departure, String arrive) throws ParseException;

	int updateViewcnt(HttpServletRequest req, HttpServletResponse res, String id);

	ReservationVO getReservationOfMember(String member_id, String product_num);

	int deleteProduct(int id);

	int createHtmlList(int id, List<String> imageNameList);
}
