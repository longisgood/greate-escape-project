package net.koreate.greatescape.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class FullProductDTO extends ProductVO {
	
	private String detail_info;
	private String detail_schedule;
	private String detail_title_image;
	private int detail_viewcnt;

}
