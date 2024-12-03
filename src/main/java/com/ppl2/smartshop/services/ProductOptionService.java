package com.ppl2.smartshop.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.ProductDetail;
import com.ppl2.smartshop.entities.ProductItem;
import com.ppl2.smartshop.entities.ProductOption;
import com.ppl2.smartshop.model.OptionDTO;
import com.ppl2.smartshop.repositories.IProductDetailRepo;
import com.ppl2.smartshop.repositories.IProductItemRepo;
import com.ppl2.smartshop.repositories.IProductOptionRepo;

@Service
public class ProductOptionService implements IProductOptionService {
	@Autowired
	private IProductDetailRepo productDetailRepo;
	@Autowired
	private IProductItemRepo productItemRepo;
	@Autowired
	private IProductOptionRepo productOptionRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void saveFeature(String newFeature, String oldFeature, Long productId) {
		List<ProductOption> options = productOptionRepo.findByFeatureAndProductItemId(oldFeature, productId);
		ProductItem productItem = productItemRepo.findById(productId).get();
		productItem.setOptionsName(productItem.getOptionsName().replaceFirst(oldFeature, newFeature));
		productOptionRepo.saveAll(options.stream().map(option -> {
			option.setFeature(newFeature);
			return option;
		}).toList());
	}

	@Override
	public void remove(Long id) {
		List<ProductDetail> productDetails = productDetailRepo.findByOption(id);
		productDetailRepo.saveAll(productDetails);
		productOptionRepo.deleteById(id);
	}

	@Override
	public OptionDTO findById(Long id) {
		return modelMapper.map(productOptionRepo.findById(id), OptionDTO.class);
	}

	@Override
	public void save(OptionDTO optionDTO) {
		ProductOption option = productOptionRepo.findById(optionDTO.getId()).orElse(null);
		option.setFeature(optionDTO.getFeature().strip().toLowerCase());
		option.setName(optionDTO.getName().strip());
		option.setProductItem(productItemRepo.findById(optionDTO.getProductItemId()).orElse(null));
		productOptionRepo.save(option);
	}

	@Override
	public OptionDTO addOption(OptionDTO optionDTO) {
		ProductItem productItem = productItemRepo.findById(optionDTO.getProductItemId()).get();
		if (productItem.getOptionsName() != null) {
			ProductOption option = new ProductOption();
			option.setFeature(optionDTO.getFeature().strip().toLowerCase());
			option.setName(optionDTO.getName().strip());
			option.setProductItem(productItem);
			option = productOptionRepo.save(option);
			optionDTO.setId(option.getId());
			List<ProductOption> options = productOptionRepo.findByProductItemId(productItem.getId());
			Map<String, List<ProductOption>> map = new HashMap<>();

			String[] optionsName = productItem.getOptionsName().split(";");
			for (String optionName : optionsName) {
				map.put(optionName, new LinkedList<>());
			}
			for (ProductOption optionIter : options) {
				map.get(optionIter.getFeature()).add(optionIter);
			}
			map.remove(option.getFeature());
			if(map.size()!=0) {
				Set<ProductDetail> details = new HashSet<>();
				LinkedList<List<ProductOption>> listOptions = new LinkedList<>( map.values());
				System.out.println(listOptions);
				createProductDetails(details, new LinkedList<>(List.of(option)), listOptions, productItem);
				details.stream().map(e -> e.getProductOptions()).forEach(System.out::println);
				productItem.getProductDetails().addAll(details);
			}else {
				ProductDetail detail=new ProductDetail();
				detail.setProductItem(productItem);
				if(optionsName.length==1)
				detail.setProductOptions(List.of(option));
				productItem.getProductDetails().add(detail);
			}
			
			productItemRepo.save(productItem);
		}

		return optionDTO;
	}

	private void createProductDetails(Set<ProductDetail> details, LinkedList<ProductOption> options,
			LinkedList<List<ProductOption>> list, ProductItem productItem) {
		List<ProductOption> current = list.pop();
		System.out.println(current);
		for (ProductOption option : current) {
			options.push(modelMapper.map(option, ProductOption.class));
			if (list.isEmpty()) {
				ProductDetail detail = new ProductDetail();
				detail.setProductOptions(List.copyOf(options));
				detail.setProductItem(productItem);
				System.out.println(detail.getProductOptions());
				details.add(detail);
			} else {
				createProductDetails(details, options, list, productItem);
			}
			options.pop();
			details.stream().map(e -> e.getProductOptions()).forEach(System.out::println);
		}
		list.push(current);
	}
}
