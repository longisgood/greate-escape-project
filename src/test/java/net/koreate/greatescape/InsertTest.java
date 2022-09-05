package net.koreate.greatescape;



import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.greatescape.member.dao.MemberDAO;
import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.product.dao.ProductDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context/root-context.xml", "classpath:/context/security/security-context.xml"})
public class InsertTest {
	
	@Autowired
	MemberDAO mdao;
	
	@Autowired
	ProductDAO pdao;
	
	@Autowired
	PasswordEncoder encoder;
	MemberVO vo;
	
//	@Test
	public void test() throws Exception{
		vo = new MemberVO("master","master","총관리자","남성","19930330","01064800945","ceo@greatescape.net","Y");
		vo.setMember_pw(encoder.encode(vo.getMember_pw()));
		mdao.join(vo);
	}
	
//	@Test
	public void encode() {
		boolean matches = encoder.matches("pw006","$2a$10$gfe9z3vhFS2koWnFQQFVCu4CpCVIHT.skcCpWpIQ3fX3YrMve9JtK");
		System.out.println(matches);
	}
	
}
