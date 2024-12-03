package com.ppl2.smartshop.entities.datatypes.constrains;

public enum City {
    HA_NOI("Hà Nội"),
    HO_CHI_MINH("Hồ Chí Minh"),
    HA_GIANG("Hà Giang"),
    CAO_BANG("Cao Bằng"),
    BAC_KAN("Bắc Kạn"),
    TUYEN_QUANG("Tuyên Quang"),
    LAO_CAI("Lào Cai"),
    DAK_LAK("Đắk Lắk"),
    DAK_NONG("Đắk Nông"),
    GIA_LAI("Gia Lai"),
    AN_GIANG("An Giang"),
    BR_VT("Bà Rịa - Vũng Tàu"),
    BAC_GIANG("Bắc Giang"),
    BAC_NINH("Bắc Ninh"),
    BAC_LIEU("Bạc Liêu"),
    BEN_TRE("Bến Tre"),
    BINH_DINH("Bình Định"),
    BINH_DUONG("Bình Dương"),
    BINH_PHUOC("Bình Phước"),
    BINH_THUAN("Bình Thuận"),
    CA_MAU("Cà Mau"),
    DIEN_BIEN("Điện Biên"),
    DONG_NAI("Đồng Nai"),
    DONG_THAP("Đồng Tháp"),
    DA_NANG("Đà Nẵng"),
    HA_NAM("Hà Nam"),
    HA_TINH("Hà Tĩnh"),
    HAI_DUONG("Hải Dương"),
    HAI_PHONG("Hải Phòng"),
    HAU_GIANG("Hậu Giang"),
    HUNG_YEN("Hưng Yên"),
    KHANH_HOA("Khánh Hòa"),
    KIEN_GIANG("Kiên Giang"),
    KON_TUM("Kon Tum"),
    LAI_CHAU("Lai Châu"),
    LANG_SON("Lạng Sơn"),
    LAM_DONG("Lâm Đồng"),
    LONG_AN("Long An"),
    NAM_DINH("Nam Định"),
    NGHE_AN("Nghệ An"),
    NINH_BINH("Ninh Bình"),
    NINH_THUAN("Ninh Thuận"),
    PHU_THO("Phú Thọ"),
    PHU_YEN("Phú Yên"),
    QUANG_BINH("Quảng Bình"),
    QUANG_NAM("Quảng Nam"),
    QUANG_NGAI("Quảng Ngãi"),
    QUANG_NINH("Quảng Ninh"),
    QUANG_TRI("Quảng Trị"),
    SOC_TRANG("Sóc Trăng"),
    SON_LA("Sơn La"),
    TAY_NINH("Tây Ninh"),
    THAI_BINH("Thái Bình"),
    THAI_NGUYEN("Thái Nguyên"),
    THANH_HOA("Thanh Hóa"),
    THUA_THIEN_HUE("Thừa Thiên Huế"),
    TIEN_GIANG("Tiền Giang"),
    TRA_VINH("Trà Vinh"),
    VINH_LONG("Vĩnh Long"),
	FOREIGN("Nước ngoài");
    private final String cityName;

    City(String cityName) {
        this.cityName=cityName;
    }
    public String getCityName() {
    	return cityName;
    }
}