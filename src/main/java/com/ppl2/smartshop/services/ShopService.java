package com.ppl2.smartshop.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.ProductTag;
import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.model.ProductDTO;
import com.ppl2.smartshop.model.ShopDTO;
import com.ppl2.smartshop.repositories.IProductItemRepo;
import com.ppl2.smartshop.repositories.IProductTagRepository;
import com.ppl2.smartshop.repositories.IShopRepository;
import com.ppl2.smartshop.security.repo.IRoleRepository;
import com.ppl2.smartshop.security.repo.IUserRepository;

@Service
public class ShopService implements IShopService {
	@Autowired
	private IShopRepository shopRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private IProductItemRepo productItemRepo;
	@Autowired
	private IProductTagRepository productTagRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ShopDTO addShop(ShopDTO shopDTO) {
		User user = userRepository.findById(shopDTO.getUserId()).get();
		Shop shop = modelMapper.map(shopDTO, Shop.class);
		user.getRoles().add(roleRepository.findByRoleName("ROLE_SHOP"));
		shop.setUser(userRepository.save(user));
		shop=shopRepository.save(shop);
		ProductTag tag=new ProductTag();
		tag.setTagName("Sản phẩm");
		tag.setShop(shop);
		productTagRepository.save(tag);
		return modelMapper.map(shop, ShopDTO.class);
	}

	@Override
	public List<ShopDTO> getTopShop() {
		return shopRepository.findTop10By().stream().map(shop -> modelMapper.map(shop, ShopDTO.class))
				.collect(Collectors.toList());
	}

	public Shop getShopById(String id) {
		// Optional<Shop> optionalProduct =
		// Optional.ofNullable(ShopRepository.findById(string));
		return shopRepository.findById(id).orElse(null);
	}

	public void saveShop(Shop shop) {
		shop.setUser(userRepository.findById(shop.getUser().getUserId()).get());
		shopRepository.save(shop);
	}

	public void deleteShopsById(Long id) {
		// TODO Auto-generated method stub
		shopRepository.deleteById(id);
	}

	public Shop getShopByUserId(Long id) {
		// TODO Auto-generated method stub
		return shopRepository.getShopByUserId(id);
	}
	@Override
	public ShopDTO getByUser(Long id) {
		// TODO Auto-generated method stub
		return modelMapper.map(shopRepository.getShopByUserId(id),ShopDTO.class);
	}
	@Override
	public List<Shop> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shop save(Shop t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shop findById(String id) {
		// TODO Auto-generated method stub
		return shopRepository.findById(id).orElse(null);
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProductDTO> getTopProductOfShop(String shopId) {
		return productItemRepo.findTop6ByShopIdOrderByNumPurchasesDescRatingDesc(shopId)
				.stream().map(p ->modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
	}

}
