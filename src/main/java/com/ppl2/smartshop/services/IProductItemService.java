package com.ppl2.smartshop.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.ScopeVoucher;
import com.ppl2.smartshop.model.ItemStatisticModel;
import com.ppl2.smartshop.model.ProductDTO;

public interface IProductItemService extends IGeneralService<ProductDTO , Long> {
	Page<ProductDTO> getProductsPage(Pageable p) ;
	Page<ProductDTO> filter(City city, ScopeVoucher scopeVoucher, Integer categoryId, Integer max, Integer min,
			Pageable page);
	Page<ProductDTO> filter(String keyword, City city, ScopeVoucher scopeVoucher, Integer categoryId, Integer max, Integer min,
			Pageable page);
	
	List<ProductDTO> getTopProduct();
	List<ProductDTO> getlimit(Long number);
	ProductDTO add(ProductDTO productDTO);
	Long countByShop(String shopId);
	List<ItemStatisticModel> statisticsProductByShop(String shopId);
	Map<String, Long> statisticsSum(String shopId);
}
