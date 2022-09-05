package net.koreate.greatescape.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.product.service.ProductFileService;
import net.koreate.greatescape.product.service.ProductServiceREST;
import net.koreate.greatescape.product.vo.FullProductDTO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductControllerREST {
	
	private final ProductServiceREST ps;
	private final ProductFileService pfs;
	
	@GetMapping({"/", ""})
	public String renderIndex(Model model) {
		List<ProductVO> list = ps.getAllList();
		model.addAttribute("continent", "모든 상품 정보");
		listSplitAndAdd(model, list);
		return "/products/index";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping({"/", ""})
	public String createProduct(FullProductDTO dto, String departure, String arrive, MultipartFile titleImage, RedirectAttributes rttr, HttpSession session) throws Exception {
		String redirectUrl = "redirect:/products/";
		if (titleImage != null && !titleImage.isEmpty()) {
			String uploadedName = pfs.uploadFile(titleImage);
			if (uploadedName == null || uploadedName.equals("")) {
				rttr.addFlashAttribute("flashMessage", "타이틀 이미지 등록 중 오류가 발생하였습니다.");
				return redirectUrl + "new";
			}
			dto.setDetail_title_image(uploadedName);
		}
		int result = ps.createProduct(dto, departure, arrive);
		List<String> imageNameList = (List<String>) session.getAttribute("imageNameList");
		if (result == 2) {
			rttr.addFlashAttribute("flashMessage", "성공적으로 상품 등록이 완료되었습니다.");
			if (imageNameList != null) {
				ps.createHtmlList(dto.getProduct_num(), imageNameList);
			}
		} else {
			rttr.addFlashAttribute("flashMessage", "상품 등록 중 오류가 발생하였습니다.");
			if (imageNameList != null) {
				for (String fileName : imageNameList) {
					pfs.deleteFile(fileName);
				}
			}
		}
		int id = dto.getProduct_num();
		if (id != 0) return redirectUrl + id;
		session.removeAttribute("imageNameList");
		return redirectUrl + "new";
	}
	
	@GetMapping("/new")
	public String renderNewForm() {
		return "products/new";
	}
	
	@GetMapping("/search")
	public String productSearch(Model model, String country, String departure, String plan, String seat, String city, String money) {
		if (country == "" && departure == "" && plan == "" && seat == "" && city == "" && money == "") {
			return "redirect:/products";
		}
		List<ProductVO> list = ps.getListBySearch(country, departure, plan, seat, city, money);
		listSplitAndAdd(model, list);
		return "/products/index";
	}
	
	@GetMapping("/{id}")
	public String renderDetail(@PathVariable String id, HttpServletRequest req, HttpServletResponse res, Model model) {
		ps.updateViewcnt(req, res, id);
		FullProductDTO product = ps.getFullProductById(id);
		if (product == null) return "/products/index";
		model.addAttribute("product", product);
		return "/products/detail";
	}
	
	@PostMapping("/{id}")
	public String reservation(HttpSession session, @PathVariable int id, ReservationVO rvo, RedirectAttributes rttr) {
		MemberVO loginMember = (MemberVO) session.getAttribute("userInfo");
		if (loginMember != null && !loginMember.getMember_id().equals(rvo.getMember_id())) {
			rttr.addFlashAttribute("flashMessage", "비정상적인 접근입니다.");
			return "redirect:/";
		}
		rvo.setProduct_num(id);
		int result = ps.reserve(rvo);
		if (result > 0) {
			rttr.addFlashAttribute("flashMessage", "예약이 완료되었습니다.");
			if (loginMember != null) {
				return "redirect:/member/myPage";
			} else {
				rttr.addFlashAttribute("rev_name", rvo.getRev_name());
				rttr.addFlashAttribute("rev_email", rvo.getRev_email());
				rttr.addFlashAttribute("rev_phone", rvo.getRev_phone());
				return "redirect:/member/rev_check";
			}
		} else {
			rttr.addFlashAttribute("flashMessage", "예약 중 오류가 발생하였습니다.");
			return "redirect:/products/reserve";
		}
	}
	
	@GetMapping("/continent/{continent}")
	public String indexByContinent(@PathVariable String continent, Model model) {
		List<ProductVO> list = ps.getListByContinent(continent);
		model.addAttribute("continent", continent);
		listSplitAndAdd(model, list);
		return "/products/index";
	}
	
	@GetMapping("/city/{city}")
	@ResponseBody
	public List<ProductVO> getListByCity(@PathVariable String city, Model model) {
		List<ProductVO> list = ps.getListByCity(city);
		return list;
	}
	
	@GetMapping("/{id}/reservation")
	public String renderReservationForm(@PathVariable String id, Model model, HttpSession session, RedirectAttributes rttr) {
		MemberVO loginMember = (MemberVO) session.getAttribute("userInfo");
		if (loginMember != null) {
			if (ps.getReservationOfMember(loginMember.getMember_id(), id) != null) {
				rttr.addFlashAttribute("flashMessage", "이미 예약하신 상품입니다.");
				return "redirect:/products/" + id;
			}
		}
		model.addAttribute("product", ps.getProductById(id));
		return "/products/reserve";
	}
	
	@GetMapping("/{id}/update")
	public String renderUpdateForm(@PathVariable String id, Model model) {
		model.addAttribute("product", ps.getFullProductById(id));
		return "/products/update";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/{id}/update")
	public String updateProduct(@PathVariable int id, String originalTitleImage, MultipartFile titleImage, FullProductDTO dto, String departure, String arrive, RedirectAttributes rttr, HttpSession session) throws Exception {
		String redirectUrl = "redirect:/products/" + id; 
		if (titleImage != null && !titleImage.isEmpty()) {
			String uploadedName = pfs.updateFile(originalTitleImage, titleImage);
			if (uploadedName == null || uploadedName.equals("")) {
				rttr.addFlashAttribute("flashMessage", "타이틀 이미지 수정 중 오류가 발생하였습니다.");
				return redirectUrl;
			}
			dto.setDetail_title_image(uploadedName);
		} else {
			dto.setDetail_title_image(originalTitleImage);
		}
		int result = ps.updateProduct(id, dto, departure, arrive);
		List<String> imageNameList = (List<String>) session.getAttribute("imageNameList");
		if (result == 0) {
			redirectUrl += "/update";
			rttr.addFlashAttribute("flashMessage", "수정 중 오류가 발생하였습니다.");
			if (imageNameList != null) {
				for (String fileName : imageNameList) {
					pfs.deleteFile(fileName);
				}
			}
		} else {
			rttr.addFlashAttribute("flashMessage", "정상적으로 수정이 완료되었습니다.");
			if (imageNameList != null) {
				ps.createHtmlList(id, imageNameList);
			}
		}
		session.removeAttribute("imageNameList");
		return redirectUrl;
	}
	
	
	@PostMapping("/{id}/delete")
	public String deleteProduct(@PathVariable int id, RedirectAttributes rttr) throws Exception {
		String redirectUrl = "redirect:/products/";
		pfs.deleteFileById(id);
		int result = ps.deleteProduct(id);
		if (result > 0) {
			rttr.addFlashAttribute("flashMessage", "정상적으로 삭제가 완료되었습니다.");
			return redirectUrl;
		} else {
			rttr.addFlashAttribute("flashMessage", "삭제 중 오류가 발생하였습니다.");
			return redirectUrl + id;
		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/htmlImage")
	@ResponseBody
	public Location uploadHtmlImage(MultipartFile file, HttpSession session) throws Exception {
		List<String> imageNameList = (List<String>) session.getAttribute("imageNameList");
		if (imageNameList == null) {
			imageNameList = new ArrayList<String>();
			session.setAttribute("imageNameList", imageNameList);
		}
		String uploadedName = pfs.uploadHtmlImage(file, imageNameList);
		return new Location(uploadedName);
	}
	
	@Getter
	@Setter
	@RequiredArgsConstructor
	class Location {
		private final String location;
	}
	 
	private void listSplitAndAdd(Model model, List<ProductVO> list) {
		Set<String> countrySet = new HashSet<>();
		list.stream().forEach(p -> countrySet.add(p.getProduct_country()));
		
		Map<String, Set<String>> cityMap = new HashMap<>();
		countrySet.stream().forEach(country -> {
			Set<String> citySet = new HashSet<>();
			list.stream().filter(p -> p.getProduct_country().equals(country)).forEach(p -> citySet.add(p.getProduct_city()));
			cityMap.put(country, citySet);
		});
		if (model.getAttribute("continent") ==null) model.addAttribute("continent", "검색 결과");
		model.addAttribute("list", list);
		model.addAttribute("countrySet", countrySet);
		model.addAttribute("cityMap", cityMap);
	}

}
