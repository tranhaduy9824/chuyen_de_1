package com.ppl2.smartshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.ProductTag;
import com.ppl2.smartshop.repositories.IProductTagRepository;

@Service
public class ProductTagService {

	@Autowired
	private IProductTagRepository productTagRepository;

	public ProductTag getProductById(Long id) {
		Optional<ProductTag> optionalProduct = productTagRepository.findById(id);
		return optionalProduct.orElse(null);
	}

	public void saveProductTag(ProductTag productTag) {
		// TODO Auto-generated method stub
		productTagRepository.save(productTag);
	}

	public void deleteProductTagsById(Long id) {
		// TODO Auto-generated method stub
		productTagRepository.deleteById(id);
	}

	public List<ProductTag> findProductTagByShopId(String string) {
		return productTagRepository.findByShopId(string);
	}

//	public ProductTag findByTagName(String oldType) {
//		// TODO Auto-generated method stub
//		return productTagRepository.findByTagName(oldType);
//	}

}
