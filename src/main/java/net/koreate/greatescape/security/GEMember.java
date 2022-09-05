package net.koreate.greatescape.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import net.koreate.greatescape.member.vo.MemberVO;

public class GEMember extends User {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private MemberVO member;
	
	public GEMember(MemberVO member) {
		super(member.getMember_id(), member.getMember_pw(), authorities(member.getAuthList()));
		this.member = member;
	}
	
	public GEMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public static Collection<? extends GrantedAuthority> authorities(List<String> authList) {
		List<GrantedAuthority> gList = new ArrayList<>();
		for (String auth : authList) {
			gList.add(new SimpleGrantedAuthority(auth));
		}
		return gList;
	}

	
	

	
}
