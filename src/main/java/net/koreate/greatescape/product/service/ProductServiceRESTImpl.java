package net.koreate.greatescape.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.product.dao.ProductDAO;
import net.koreate.greatescape.product.vo.FullProductDTO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;

@Service
@RequiredArgsConstructor
public class ProductServiceRESTImpl implements ProductServiceREST {
	
	private final ProductDAO dao;

	@Override
	public List<ProductVO> getAllList() {
		return dao.getAllList();
	}

	@Override
	public List<ProductVO> getListByContinent(String continent) {
		return dao.getListByContinent(continent);
	}

	@Override
	public List<ProductVO> getListByCity(String city) {
		return dao.getListByCity(city);
	}

	@Override
	public FullProductDTO getFullProductById(String id) {
		return dao.getFullProductById(id);
	}

	@Override
	public ProductVO getProductById(String id) {
		return dao.getProductById(id);
	}

	@Override
	public List<ProductVO> getListBySearch(String country, String departure, String plan, String seat, String city, String money) {
		Map<String, String> map = new HashMap<>();
		map.put("country", country);
		map.put("departure", departure);
		map.put("plan", plan);
		map.put("seat", seat);
		map.put("city", city);
		map.put("money", money);
		return dao.getListBySearch(map);
	}
	
	@Override
	@Transactional
	public int createProduct(FullProductDTO dto, String departure, String arrive) throws ParseException {
		setDate(dto, departure, arrive);
		int result = 0;
		result += dao.createProduct(dto);
		if (result == 1) dto.setProduct_num(dao.getLastInsertId());
		result += dao.createDetail(dto);
		return result;
	}

	@Override
	@Transactional
	public int reserve(ReservationVO rvo) {
		int result = 0;
		result += dao.reserve(rvo);
		result += dao.seatMinus(rvo);
		return result;
	}

	@Override
	public int updateProduct(int id, FullProductDTO dto, String departure, String arrive) throws ParseException {
		dto.setProduct_num(id);
		setDate(dto, departure, arrive);
		return dao.updateProduct(dto);
	}
	
	private void setDate(FullProductDTO dto, String departure, String arrive) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		dto.setProduct_departure(formatter.parse(departure));
		dto.setProduct_arrive(formatter.parse(arrive));
	}

	@Override
	public int updateViewcnt(HttpServletRequest req, HttpServletResponse res, String id) {
		Cookie[] cookies = req.getCookies();
		String cookieName = "escape" + id;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					return 0;
				}
			}
		}
		int result = dao.updateViewcntById(id);
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setPath(req.getContextPath() + "/products/");
		cookie.setMaxAge(60 * 60 * 24);
		res.addCookie(cookie);
		return result;
	}

	@Override
	public ReservationVO getReservationOfMember(String member_id, String product_num) {
		return dao.getReservationOfMember(member_id, product_num);
	}

	@Override
	public int deleteProduct(int id) {
		return dao.deleteProduct(id);
	}

	@Override
	@Transactional
	public int createHtmlList(int id, List<String> imageNameList) {
		int result = 0;
		for (String fileName : imageNameList) {
			result += dao.createHtmlList(id, fileName);
		}
		return result;
	}

}
