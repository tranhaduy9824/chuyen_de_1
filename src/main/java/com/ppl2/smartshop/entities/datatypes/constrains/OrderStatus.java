package com.ppl2.smartshop.entities.datatypes.constrains;

public enum OrderStatus {
	NEW("đang xử lí"),HOLD("đang đóng gói"),SHIPPING("đang chờ giao hàng"),DELIVERED("đã giao hàng"),CANCEL("đã hủy")
	,REVIEW("chờ đánh giá"),RETURNS("hoàn trả hàng"),COMPLETED("đã hoàn thành");
	private final String name;
	OrderStatus(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
