package com.ppl2.smartshop.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.ProductDetail;
import com.ppl2.smartshop.model.ProductDetailDTO;
import com.ppl2.smartshop.repositories.IProductDetailRepo;

@Service
public class ProductDetailService implements IProductDetailService{
	@Autowired
	private IProductDetailRepo productDetailRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Override
	public Page<ProductDetailDTO> getAllByShop(String shopId,Pageable pageable) {
		return  productDetailRepo.findByProductItemShopId(shopId,pageable).map(p ->{
			ProductDetailDTO dto=modelMapper.map(p, ProductDetailDTO.class);
			dto.setTitle(p.getProductItem().getTitle());
			dto.setImage(p.getProductItem().getImage());
			return dto;
		});
	}
	@Override
	public void saveAll(List<ProductDetailDTO> detailDTOs) {
		productDetailRepo.saveAll(detailDTOs.stream().map(detail->modelMapper.map(detail, ProductDetail.class)).collect(Collectors.toList()));
		
	}
	@Override
	public void update(ProductDetailDTO detailDTO) {
		ProductDetail detail=productDetailRepo.findById(detailDTO.getId()).get();
		detail.setQuantity(detailDTO.getQuantity());
		detail.setSize(detailDTO.getSize());
		detail.setWeight(detailDTO.getWeight());
		productDetailRepo.save(detail);
		
	}
	@Override
	public Page<ProductDetailDTO> searchByShop(String shopId,String keyword, Pageable pageable) {
		return  productDetailRepo.findByProductItemShopIdAndProductItemTitleContaining(shopId,keyword,pageable).map(p ->{
			ProductDetailDTO dto=modelMapper.map(p, ProductDetailDTO.class);
			dto.setTitle(p.getProductItem().getTitle());
			dto.setImage(p.getProductItem().getImage());
			return dto;
		});
	}




}
