package net.koreate.greatescape.member.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private int member_num;
	private String member_id;
	private String member_pw;
	private String member_name;
	private String member_gender;
	private String member_birth;
	private String member_phone;
	private String member_address;
	private String member_address_detail;
	private String member_email;
	private String member_leave;
	private String member_master;
	private Date member_regdate;
	private Date member_updatedate;
	private int product_num;
	private List<String> authList;
	
	public MemberVO(String member_id, String member_pw, String member_name, String member_gender, String member_birth,
			String member_phone, String member_email, String member_master) {
		super();
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_name = member_name;
		this.member_gender = member_gender;
		this.member_birth = member_birth;
		this.member_phone = member_phone;
		this.member_email = member_email;
		this.member_master = member_master;
	}

	public MemberVO() {}
	
}
