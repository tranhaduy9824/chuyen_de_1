package com.ppl2.smartshop.services;

import com.ppl2.smartshop.model.OptionDTO;
public interface IProductOptionService {
	void saveFeature(String newFeature,String oldFeature,Long productId);
	void remove(Long id);
	void save(OptionDTO optionDTO);
	OptionDTO findById(Long id);
	OptionDTO addOption(OptionDTO optionDTO);
}
