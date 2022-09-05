package net.koreate.greatescape.board.vo;

import lombok.Data;

@Data
public class FAQBoardVO {

	private int faq_num;
	private String faq_title;
	private String faq_content;
	private String faq_category;


	public FAQBoardVO(String faq_category, String faq_title, String faq_content ) {
		this.faq_category= faq_category;
		this.faq_title= faq_title;
		this.faq_content= faq_content;
	}

}
