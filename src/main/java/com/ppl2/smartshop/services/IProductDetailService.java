package com.ppl2.smartshop.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ppl2.smartshop.model.ProductDetailDTO;

public interface IProductDetailService {
	void saveAll(List<ProductDetailDTO> detailDTOs);
	void update(ProductDetailDTO detailDTO);
	Page<ProductDetailDTO> getAllByShop(String shopId, Pageable pageable);
	Page<ProductDetailDTO> searchByShop(String shopId,String keyword, Pageable pageable);
}
