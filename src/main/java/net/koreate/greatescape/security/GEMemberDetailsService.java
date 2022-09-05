package net.koreate.greatescape.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.koreate.greatescape.common.dao.TempDAO;
import net.koreate.greatescape.member.vo.MemberVO;

public class GEMemberDetailsService implements UserDetailsService {

	@Inject
	TempDAO dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO vo = null;
		try {
			vo = dao.getMemberById(username);
			if (vo != null) {
				vo.setAuthList(dao.getAuthList(username));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo == null ? null : new GEMember(vo);
	}

	
	
	
}
