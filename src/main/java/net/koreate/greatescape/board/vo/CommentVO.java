package net.koreate.greatescape.board.vo;

import lombok.Data;

@Data
public class CommentVO {
	private int comment_num;
	private int qna_num;
	private String qna_answer;
	private String comment_writer;
	
}
