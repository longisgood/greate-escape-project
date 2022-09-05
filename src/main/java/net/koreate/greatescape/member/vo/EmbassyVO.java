package net.koreate.greatescape.member.vo;

import lombok.Data;

public class EmbassyVO {
	
	private String japan = "https://www.kr.emb-japan.go.jp/itprtop_ko/index.html";
	private String philippines = "https://overseas.mofa.go.kr/ph-ko/index.do"; 
	private String vietnam = "https://overseas.mofa.go.kr/vn-ko/index.do";
	private String italia = "https://overseas.mofa.go.kr/it-ko/index.do";
	private String france = "https://kr.ambafrance.org/-%ED%95%9C%EA%B5%AD%EC%96%B4-";
	private String uk = "https://www.gov.uk/world/organisations/british-embassy-seoul.ko";
	private String usa = "https://kr.usembassy.gov/ko/";
	private String canada = "https://overseas.mofa.go.kr/ca-ko/index.do";
	private String chile = "https://overseas.mofa.go.kr/cl-ko/index.do";
	private String australia = "https://southkorea.embassy.gov.au/seolkorean/home.html";
	private String newZealand = "https://overseas.mofa.go.kr/nz-ko/index.do";
	private String palau = "https://overseas.mofa.go.kr/ph-ko/brd/m_3628/list.do";
	
	
	public String findEmbassy(String country) {
		if(country.equals("일본")) {
			return japan;
		}else if(country.equals("필리핀")){
			return philippines;
		}else if(country.equals("베트남")){
			return vietnam;
		}else if(country.equals("이탈리아")){
			return italia;
		}else if(country.equals("프랑스")){
			return france;
		}else if(country.equals("영국")){
			return uk;
		}else if(country.equals("미국")){
			return usa;
		}else if(country.equals("캐나다")){
			return canada;
		}else if(country.equals("오스트레일리아")){
			return australia;
		}else if(country.equals("칠레")){
			return chile;
		}else if(country.equals("뉴질랜드")){
			return newZealand;
		}else if(country.equals("팔라우")){
			return palau;
		}
		
		return null;
	}

}
