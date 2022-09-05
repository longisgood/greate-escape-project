package net.koreate.greatescape.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import net.koreate.greatescape.member.vo.MemberVO;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
		GEMember gem = (GEMember) auth.getPrincipal();
		MemberVO loginMember = gem.getMember();
		HttpSession session = req.getSession();
		if (loginMember != null && loginMember.getMember_leave().equals("Y")) {
			session.setAttribute("userInfo", loginMember);
			if (session.getAttribute("url") != null) {
				String url = (String) session.getAttribute("url");
				if (url.indexOf("login") < 0) {
					String prevPage = url.replace("http://localhost:8080/greatescape/", "/");
					res.sendRedirect(req.getContextPath() + prevPage);
					return;
				}
			}
		}
		res.sendRedirect(req.getContextPath());
	}

}
