package net.koreate.greatescape.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class QNABoardVO {

	private int qna_num;
	private int member_num;
	private String qna_title;
	private String qna_question;
	private int master_num;
	private String qna_answer;
	private String qna_writer;
	private Date qna_regdate;

	
	public QNABoardVO(String qna_title, String qna_question, String qna_writer) {
		this.qna_title=qna_title;
		this.qna_question= qna_question;
		this.qna_writer=qna_writer;
	}


}
