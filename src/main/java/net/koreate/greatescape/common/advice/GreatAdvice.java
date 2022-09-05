package net.koreate.greatescape.common.advice;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GreatAdvice {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String exceptionHandler(NoHandlerFoundException e, RedirectAttributes rttr, HttpSession session) {
		session.removeAttribute("imageNameList");
		e.printStackTrace();
		rttr.addFlashAttribute("flashMessage", "잘못된 요청입니다. 경로를 다시 확인해주세요.");
		return "redirect:/";
	}

	@ExceptionHandler
	public String exceptionHandler(Exception e, RedirectAttributes rttr, HttpSession session) {
		session.removeAttribute("imageNameList");
		e.printStackTrace();
		rttr.addFlashAttribute("flashMessage", "예상치 못한 오류가 발생하였습니다! 관리자에게 문의하여 주세요!");
		return "redirect:/";
	}
}
