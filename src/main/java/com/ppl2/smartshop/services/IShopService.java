package com.ppl2.smartshop.services;

import java.util.List;

import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.model.ShopDTO;

public interface IShopService extends IGeneralService<Shop, String>{
	ShopDTO addShop(ShopDTO shopDTO);
	List<ShopDTO> getTopShop();
	List<ProductDTO> getTopProductOfShop(String shopId);
	ShopDTO getByUser(Long id);
}
