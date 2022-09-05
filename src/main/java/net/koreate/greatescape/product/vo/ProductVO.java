package net.koreate.greatescape.product.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	
	private int product_num;
	private String product_continent;
	private String product_country;
	private String product_city;
	private String product_name;
	private String product_airplane;
	private	Date product_departure;
	private Date product_arrive;
	private String product_plan;
	private int product_adult;
	private int product_minor;
	private int product_seat;
	
}
