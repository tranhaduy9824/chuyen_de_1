package com.ppl2.smartshop.entities.datatypes.constrains;

public enum UnitVoucher {
	PERCENT("%"),VND("Đ");
	private String code;
	private UnitVoucher(String code) {
		this.code=code;
	}
	public String getCode() {
		return code;
	}
}
