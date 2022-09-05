package net.koreate.greatescape.common.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.common.dao.TempDAO;
import net.koreate.greatescape.product.vo.FullProductDTO;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final TempDAO dao;
	
	@GetMapping("/")
	public String home(Model model) {
		List<FullProductDTO> list = dao.getIndexList();
		model.addAttribute("list", list);
		return "index";
	}
	
	@GetMapping("/logoff")
	public String logoff(RedirectAttributes rttr) {
		rttr.addFlashAttribute("flashMessage", "다른 페이지에서 로그인을 시도하여 로그아웃 되었습니다.");
		return "redirect:/";
	}
	
	@GetMapping("/error/403")
	public void accessDenied() {}
	
}
