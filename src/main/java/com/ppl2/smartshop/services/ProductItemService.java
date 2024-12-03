package com.ppl2.smartshop.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.ProductDetail;
import com.ppl2.smartshop.entities.ProductItem;
import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.entities.datatypes.constrains.City;
import com.ppl2.smartshop.entities.datatypes.constrains.ScopeVoucher;
import com.ppl2.smartshop.model.ItemStatisticModel;
import com.ppl2.smartshop.model.OptionDTO;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.model.ProductDetailDTO;
import com.ppl2.smartshop.model.RateDTO;
import com.ppl2.smartshop.repositories.ICategoryRepo;
import com.ppl2.smartshop.repositories.IOrderItemRepo;
import com.ppl2.smartshop.repositories.IProductItemRepo;
import com.ppl2.smartshop.repositories.IProductTagRepository;
import com.ppl2.smartshop.repositories.IShopRepository;

@Service
public class ProductItemService implements IProductItemService {
	@Autowired
	private IProductItemRepo productItemRepo;
	@Autowired
	private IProductTagRepository productTagRepository;
	@Autowired
	private ICategoryRepo categoryRepo;
	@Autowired
	private IShopRepository shopRepository;
	@Autowired
	private IOrderItemRepo orderItemRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ProductDTO> findAll() {
		return productItemRepo.findAll().stream().map(p -> mapper.map(p, ProductDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ProductDTO findById(Long id) {
		ProductItem p = productItemRepo.findById(id).orElse(null);
		ProductDTO pDto = mapper.map(p, ProductDTO.class);
		pDto.setCategory(p.getCategory().getName());
		pDto.setShopId(p.getShop().getId());
		pDto.setShopName(p.getShop().getName());
		pDto.setShopAvatar(p.getShop().getAvatar());
		pDto.setPosition(p.getShop().getPosition());
		pDto.setAddress(p.getShop().getAddress());
		// map list product detail to dtos
		pDto.setDetailList(p.getProductDetails().stream().map(detail -> {
			ProductDetailDTO detailDTO = new ProductDetailDTO();
			detailDTO.setId(detail.getId());
			detailDTO.setQuantity(detail.getQuantity());
			detailDTO.setSize(detail.getSize());
			detailDTO.setWeight(detail.getWeight());
			detailDTO.setOptions(detail.getProductOptions().stream().map(o -> mapper.map(o, OptionDTO.class)).toList());
			return detailDTO;
		}).collect(Collectors.toList()));
		pDto.setOptionsName(new ArrayList<>());
		if (p.getOptionsName() != null) {
			String[] optionsName = p.getOptionsName().split(";");
			pDto.setOptionsName(new ArrayList<>());
			for (String optionName : optionsName) {
				pDto.getOptionsName().add(optionName);
			}

			pDto.setOptions(p.getProductOptions().stream().map(opt -> mapper.map(opt, OptionDTO.class)).toList());
		}
		pDto.setCategoryId(p.getCategory().getId());
		pDto.setRateList(
				p.getRates().stream().map(rate -> mapper.map(rate, RateDTO.class)).collect(Collectors.toList()));
		return pDto;
	}

	public Page<ProductDTO> getProductsPage(Pageable p) {

		return productItemRepo.findAll(p).map(item -> {
			ProductDTO dto = mapper.map(item, ProductDTO.class);
			dto.setTitle(item.getTitle());

			return dto;
		});
	}

	@Override
	public Page<ProductDTO> filter(City city, ScopeVoucher scopeVoucher, Integer categoryId, Integer max, Integer min,
			Pageable page) {

		return productItemRepo.filterPage(city != null ? city.toString() : null,
				scopeVoucher != null ? scopeVoucher.toString() : null, categoryId, max, min, page).map(item -> {
					ProductDTO dto = mapper.map(item, ProductDTO.class);
					dto.setPosition(item.getShop().getPosition());
					dto.setTitle(item.getTitle());

					return dto;
				});
	}

	@Override
	public Page<ProductDTO> filter(String keyword, City city, ScopeVoucher scopeVoucher, Integer categoryId,
			Integer max, Integer min, Pageable page) {

		return productItemRepo.searchProduct(keyword, city != null ? city.toString() : null,
				scopeVoucher != null ? scopeVoucher.toString() : null, categoryId, max, min, page).map(item -> {
					ProductDTO dto = mapper.map(item, ProductDTO.class);
					dto.setPosition(item.getShop().getPosition());
					dto.setTitle(item.getTitle());

					return dto;
				});
	}

	@Override
	public List<ProductDTO> getTopProduct() {
		return productItemRepo.findTop10ByOrderByNumPurchasesDescRatingDesc().stream()
				.map(p -> mapper.map(p, ProductDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getlimit(Long number) {
		return productItemRepo.findLimit(number).stream().map(p -> mapper.map(p, ProductDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ProductDTO save(ProductDTO productDTO) {
		ProductItem p = productItemRepo.findById(productDTO.getId()).get();
		p.setCategory(categoryRepo.findById(productDTO.getCategoryId()).orElse(null));
		p.setTag(productTagRepository.findById(productDTO.getTagId()).orElse(null));
		p.setDescription(productDTO.getDescription());
		p.setId(productDTO.getId());
		p.setImage(productDTO.getImage());
		p.setNumPurchases(p.getNumPurchases());
		p.setRating(p.getRating());
		p.setScope(productDTO.getScope());
		p.setSalePrice(productDTO.getSalePrice());
		p.setOriginalPrice(productDTO.getOriginalPrice());
		p.setTitle(productDTO.getTitle());
		productItemRepo.save(p);
		return productDTO;
	}

	@Override
	public ProductDTO add(ProductDTO productDTO) {
		ProductItem p = mapper.map(productDTO, ProductItem.class);
		p.setCategory(categoryRepo.findById(productDTO.getCategoryId()).orElse(null));
		p.setTag(productTagRepository.findById(productDTO.getTagId()).orElse(null));
		p.setShop(shopRepository.findById(productDTO.getShopId()).orElse(null));
		p.setOptionsName(productDTO.getOptionsName() != null && !productDTO.getOptionsName().isEmpty()
				? String.join(";", productDTO.getOptionsName()).toLowerCase()
				: null);
		if (p.getOptionsName() == null) {
			ProductDetail detail = new ProductDetail();
			detail.setProductItem(p);
			p.setProductDetails(Set.of(detail));
		}
		p = productItemRepo.save(p);
		productDTO.setId(p.getId());
		return productDTO;
	}

	public ProductDTO getProductById(Long id) {
		ProductItem p = productItemRepo.findById(id).orElse(null);
		ProductDTO pDto = mapper.map(p, ProductDTO.class);
		pDto.setCategory(p.getCategory().getName());
		pDto.setCategoryId(p.getCategory().getId());
		pDto.setShopId(p.getShop().getId());
		pDto.setShopName(p.getShop().getName());
		pDto.setShopAvatar(p.getShop().getAvatar());
		pDto.setSalePrice(p.getSalePrice());
		pDto.setOriginalPrice(p.getOriginalPrice());
		pDto.setId(id);
		if (p.getOptionsName() != null) {
			String[] optionsName = p.getOptionsName().split(";");
			pDto.setOptionsName(new ArrayList<>());
			for (String optionName : optionsName) {
				pDto.getOptionsName().add(optionName);
			}

			pDto.setOptions(p.getProductOptions().stream().map(opt -> mapper.map(opt, OptionDTO.class)).toList());
		}

		return pDto;
	}

	public Page<ProductItem> findAllByTitleContaining(String searchTitle, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return productItemRepo.findAllByTitleContaining(searchTitle, pageRequest);
	}

	public Page<ProductItem> findAll(PageRequest pageRequest) {
		return productItemRepo.findAll(pageRequest);
	}

	@Override
	public void remove(Long id) {
		productItemRepo.deleteById(id);

	}

	public Page<ProductItem> findAllByTitleContainingAndShop(String searchTitle, Shop shop1, PageRequest of) {
		// TODO Auto-generated method stub
		return productItemRepo.findAllByTitleContainingAndShop(searchTitle, shop1.getId(), of);
	}

	public List<ProductItem> findAllByShop(Shop shop1) {
		// TODO Auto-generated method stub
		return productItemRepo.findAllByShop(shop1.getId());
	}

	public List<ProductItem> findAllByTag(Long tagId) {
		// TODO Auto-generated method stub
		return productItemRepo.findAllByTagId(tagId);
	}

	public Page<ProductItem> findAllByTag(Long tagId, PageRequest of) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<ProductItem> findAllByTag(Long tagId, Pageable pageable) {
		// TODO Auto-generated method stub
		return productItemRepo.findAllByTag(tagId, pageable);
	}

	public Page<ProductItem> findAllByShop(Shop shop, Pageable pageable) {
		// TODO Auto-generated method stub
		return productItemRepo.findAllByShop(shop.getId(), pageable);
	}

	@Override
	public Long countByShop(String shopId) {
		return productItemRepo.countByShopId(shopId);
	}

	@Override
	public List<ItemStatisticModel> statisticsProductByShop(String shopId) {
		return orderItemRepo.statisticsFinance(shopId).stream().map(objs -> {
			ItemStatisticModel model = new ItemStatisticModel();
			model.setTitle((String) objs[0]);
			model.setImage((String) objs[1]);
			model.setMoney((Long) objs[3]);
			model.setQuantity((Long) objs[2]);
			return model;
		}).collect(Collectors.toList());
	}

	@Override
	public Map<String, Long> statisticsSum(String shopId) {
		Map<String, Long> map = new HashMap<>();
		Object[] objects = orderItemRepo.statisticsQuanity(shopId).get(0);

		Long total =  objects[0] != null ? ((Number) objects[0]).longValue() : 0L;
		Long quantity = objects[1] != null ? ((Number) objects[1]).longValue() : 0L;

		map.put("total", total);
		map.put("quantity", quantity);
		return map;
	}

}
