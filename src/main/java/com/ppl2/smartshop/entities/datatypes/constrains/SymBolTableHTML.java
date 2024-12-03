package com.ppl2.smartshop.entities.datatypes.constrains;

public enum SymBolTableHTML {
	NEW_ROW("(&/n&)"),NEW_COLUMN("(&tab&)");
	private String symbol;
	private SymBolTableHTML(String symbol) {
		this.symbol=symbol;
	}
	public String getSymbol() {
		return symbol;
	}
	
}
