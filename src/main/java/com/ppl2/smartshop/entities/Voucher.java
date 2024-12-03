//package com.ppl2.smartshop.entities;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.OneToOne;
//
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Parameter;
//
//import com.ppl2.smartshop.entities.datatypes.constrains.UnitVoucher;
//
//@MappedSuperclass
//public class Voucher {
//	@Id
//    @GeneratedValue(generator = "shop-generator")
//    @GenericGenerator(name = "shop-generator", 
//    parameters=@Parameter(name = "prefix", value = "KM"), 
//    strategy = "com.ppl2.smartshop.until.MyGeneratorId")
//	private String id;
//	
//	private int value;
//	
//	@Enumerated(EnumType.STRING)
//	@Column(length = 5)
//	private UnitVoucher unit;
//	
//	private Date expiratedDate;
//	
//	private int minimumValue;
//	
//	@Column(columnDefinition = "integer default 0")
//	private int maxValue;
//	
//	@OneToOne
//	@JoinColumn(nullable = true,name = "customer_id")
//	private Customer customer;
//	
//	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public int getValue() {
//		return value;
//	}
//	public void setValue(int value) {
//		this.value = value;
//	}
//	public Date getExpiratedDate() {
//		return expiratedDate;
//	}
//	public void setExpiratedDate(Date expiratedDate) {
//		this.expiratedDate = expiratedDate;
//	}
//	public int getMinimumValue() {
//		return minimumValue;
//	}
//	public void setMinimumValue(int minimumValue) {
//		this.minimumValue = minimumValue;
//	}
//	public int getMaxValue() {
//		return maxValue;
//	}
//	public void setMaxValue(int maxValue) {
//		this.maxValue = maxValue;
//	}
//	public UnitVoucher getUnit() {
//		return unit;
//	}
//	public void setUnit(UnitVoucher unit) {
//		this.unit = unit;
//	}
//	public Customer getCustomer() {
//		return customer;
//	}
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//	public long calculateMoney(long money) {
//		if(money<minimumValue) {
//			return 0;
//		}
//		switch (unit) {
//		case VND: {
//			return money - value;
//		}
//		case PERCENT:{
//			if(money*value/100 < maxValue) {
//				return money*(100-value)/100;
//			}else {
//				return money - maxValue;
//			}
//		
//		}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + unit);
//		}
//	}
//}
