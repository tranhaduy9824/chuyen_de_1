package com.ppl2.smartshop.entities.datatypes.constrains;

public enum Gender {
	MALE("Nam"),FEMALE("Nữ"),OTHER("Khác");
	private String vi;
	Gender(String vi){
		this.vi=vi;
	}
	public String getVi() {
		return vi;
	}
}
