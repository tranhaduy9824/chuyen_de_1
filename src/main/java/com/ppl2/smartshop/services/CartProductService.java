package com.ppl2.smartshop.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ppl2.smartshop.config.CartBean;
import com.ppl2.smartshop.entities.Cart;
import com.ppl2.smartshop.entities.Customer;
import com.ppl2.smartshop.entities.FeeOption;
import com.ppl2.smartshop.entities.LineItem;
import com.ppl2.smartshop.entities.OrderItem;
import com.ppl2.smartshop.entities.OrderShop;
import com.ppl2.smartshop.entities.ProductDetail;
import com.ppl2.smartshop.entities.ProductItem;
import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.entities.datatypes.constrains.Payment;
import com.ppl2.smartshop.model.CartItem;
import com.ppl2.smartshop.model.CartProduct;
import com.ppl2.smartshop.model.OptionDTO;
import com.ppl2.smartshop.repositories.IAddressRepo;
import com.ppl2.smartshop.repositories.ICartRepo;
import com.ppl2.smartshop.repositories.ICustomerRepo;
import com.ppl2.smartshop.repositories.IFeeOptionRepo;
import com.ppl2.smartshop.repositories.IOrderShopRepo;
import com.ppl2.smartshop.repositories.IProductDetailRepo;
import com.ppl2.smartshop.repositories.IProductItemRepo;

@Service
public class CartProductService implements IProductCartService {
	@Autowired
	private IProductDetailRepo detailRepo;
	@Autowired
	private ICartRepo cartRepo;
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private IFeeOptionRepo feeOptionRepo;
	@Autowired
	private CartBean cart;
	@Autowired
	private IOrderShopRepo orderShopRepo;
	@Autowired
	private IAddressRepo addressRepo;
	@Autowired
	private IProductItemRepo productItemRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<CartProduct> getAllProductCart(Set<CartItem> cartItems) {
		List<String> feeNames = List.of("VNDPerMet", "VNDPerKilogram");
		List<FeeOption> feeOps = feeOptionRepo.findAllById(feeNames);

		try {
			final Integer VNDPerMet;
			final Integer VNDPerKilogram;
			if (feeOps.size() == 2) {
				if (feeOps.get(0).getName().equals("VNDPerMet") && feeOps.get(0).getName().equals("VNDPerKilogram")) {
					VNDPerMet = Integer.parseInt(feeOps.get(0).getValue());
					VNDPerKilogram = Integer.parseInt(feeOps.get(1).getValue());
				} else if (feeOps.get(1).getName().equals("VNDPerMet")
						&& feeOps.get(0).getName().equals("VNDPerKilogram")) {
					VNDPerMet = Integer.parseInt(feeOps.get(1).getValue());
					VNDPerKilogram = Integer.parseInt(feeOps.get(0).getValue());
				} else {
					throw new Exception("Found variable(fee) in table fee_option is invalid");
				}
			} else {
				throw new Exception("Found variable(fee) in table fee_option is invalid");
			}
			List<ProductDetail> details = detailRepo
					.findAllById(cartItems.stream().map(item -> item.getDetailId()).collect(Collectors.toList()));
			Map<String, CartProduct> cartProducts = new HashMap<String, CartProduct>();
			for (ProductDetail detail : details) {
				CartItem item = null;
				for (CartItem cartItem : cartItems) {
					if (cartItem.getDetailId() == detail.getId()) {
						item = cartItem;
						break;
					}
				}
				if (item == null) {
					continue;
				}
				item.setQuantity(detail.getQuantity());
				item.setImage(detail.getProductItem().getImage());
				item.setPrice(detail.getProductItem().getOriginalPrice());
				item.setSalePrice(detail.getProductItem().getSalePrice());
				item.setTitle(detail.getProductItem().getTitle());
				item.setScope(detail.getProductItem().getScope());
				item.setDetailId(detail.getId());
				item.setOptions(detail.getProductOptions().stream().map(option -> mapper.map(option, OptionDTO.class))
						.collect(Collectors.toList()));
				item.setShippingCost(Math.round((detail.getSize() * VNDPerMet + detail.getWeight() * VNDPerKilogram)*item.getQuantityItems()));
				if (!cartProducts.containsKey(detail.getProductItem().getShop().getId())) {
					CartProduct cartProduct = new CartProduct();
					cartProduct.setId(detail.getProductItem().getId());
					cartProduct.setShopId(detail.getProductItem().getShop().getId());
					cartProduct.setShopName(detail.getProductItem().getShop().getName());
					cartProduct.setShopAvatar(detail.getProductItem().getShop().getAvatar());
					if (detail.getQuantity() > item.getQuantityItems()) {
						cartProduct.setShippingCost(item.getShippingCost());
						cartProduct
								.setSubTotal(cartProduct.getSubTotal() + item.getQuantityItems() * item.getSalePrice());
						cartProduct.setTotal(cartProduct.getSubTotal() + cartProduct.getShippingCost());
					}
					cartProducts.put(detail.getProductItem().getShop().getId(), cartProduct);

				} else {
					CartProduct cartProduct = cartProducts.get(detail.getProductItem().getShop().getId());
					if (detail.getQuantity() > item.getQuantityItems()) {
						cartProduct.setShippingCost(cartProduct.getShippingCost() + item.getShippingCost());
						cartProduct
								.setSubTotal(cartProduct.getSubTotal() + item.getQuantityItems() * item.getSalePrice());
						cartProduct.setTotal(cartProduct.getSubTotal() + cartProduct.getShippingCost());
					}
				}
				cartProducts.get(detail.getProductItem().getShop().getId()).getCartItems().add(item);
			}

			return new ArrayList<>(cartProducts.values());

		} catch (Exception e) {
			System.out.println(e);
			return new LinkedList<CartProduct>();
		}
	}

	@Override
	public CartBean getCart(Long userId, CartBean bean) {
		Cart cart = cartRepo.getCart(userId);
		if (cart != null) {
			bean.setCreatedTime(cart.getCreatedDate());
			bean.setId(cart.getId());
			bean.setCartItems(cart.getLineItems().stream().map(item -> {
				CartItem cartItem = new CartItem();
				cartItem.setId(item.getId());
				cartItem.setDetailId(item.getProductDetail().getId());
				cartItem.setQuantityItems(item.getQuantity());
				return cartItem;
			}).collect(Collectors.toSet()));
		}

		return bean;
	}

	@Override
	public void save(Long id, Long userId, Set<CartItem> cartItems) {
		Cart cart = id!=null? cartRepo.findById(id).orElse(null):null;
		if (cart == null) {
			cart = new Cart();
			Customer customer = customerRepo.findByUserUserId(userId);
			cart.setCustomer(customer);
			if (customer==null) {
				return;
			}
		    cart=cartRepo.save(cart);
		}

		cart.setCreatedDate(Timestamp.from(Instant.now()));
		cart.setLineItems(new HashSet<>());
		for(CartItem item:cartItems) {
			LineItem lineItem = new LineItem();
			ProductDetail detail = new ProductDetail();
			detail.setId(item.getDetailId());
			lineItem.setId(item.getId());
			lineItem.setProductDetail(detail);
			lineItem.setQuantity(item.getQuantityItems());
			lineItem.setCart(cart);
			cart.getLineItems().add(lineItem);
		}

		cartRepo.save(cart);
	}

	@Override
	public void addItem(Long productDetailId, Integer quantity) {
		CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getDetailId() == productDetailId).findAny()
				.orElse(new CartItem());
		cartItem.setDetailId(productDetailId);
		cartItem.setQuantityItems(cartItem.getQuantityItems() + quantity);
		cart.getCartItems().add(cartItem);
	}

	@Override
	public void removeItem(Long detailId) {
		cart.getCartItems().removeIf(item -> item.getDetailId() == detailId);

	}

	@Override
	public void updateQuantityCartItem(Long productDetailId, Integer quantity) {
		cart.getCartItems().forEach(item -> {
			if (item.getDetailId() == productDetailId) {
				item.setQuantityItems(quantity);
			}
		});

	}

	@Transactional
	@Override
	public void checkout(Long addressId, Payment payment, Long userId, List<Long> itemIds) {
		// Remove items in cart
		List<CartItem> cartItems = new ArrayList<>();
		for (Iterator<CartItem> iterator = cart.getCartItems().iterator(); iterator.hasNext();) {
			CartItem item = iterator.next();
			if (itemIds.contains(item.getDetailId())) {
				cartItems.add(item);
				iterator.remove();
			}
			
		}
		// save cart in db
		cart.save();

		// create orders both shop and customer
		Map<String, OrderShop> orders = new HashMap<String, OrderShop>();

		List<ProductDetail> details = detailRepo
				.findAllById(cartItems.stream().map(item -> item.getDetailId()).toList());
		
		//create list productItem then update numPurcharses +1
		List<ProductItem> productItems=new ArrayList<>();
		
		details.forEach(detail -> {
			OrderShop order = new OrderShop();
			// check shop
			if (!orders.containsKey(detail.getProductItem().getShop().getId())) {
				// create a new shop order if not found in orders
				order.setCreated(new Date());
				order.setCustomer(customerRepo.findByUserUserId(userId));
				order.setStatus(OrderStatus.NEW);
				order.setShop(detail.getProductItem().getShop());
				order.setAddress(addressRepo.findById(addressId).orElse(null));
				order.setPayment(payment);
				order.setItems(new ArrayList<OrderItem>());
				orders.put(detail.getProductItem().getShop().getId(), order);
			}
			OrderShop orderShop = orders.get(detail.getProductItem().getShop().getId());

			// create a new item order
			OrderItem orderItem = new OrderItem();
			
			CartItem item = cartItems.stream().filter(i -> i.getDetailId() == detail.getId()).findAny().get();
			orderItem.setMoney(detail.getProductItem().getSalePrice()*item.getQuantityItems());
			orderItem.setQuantity(item.getQuantityItems());
			// update quantity store of product
			detail.setQuantity(detail.getQuantity() - orderItem.getQuantity());
			orderItem.setProductDetail(detail);
			orderItem.setOrder(orderShop);
			
			// add item order into shop order
			orderShop.getItems().add(orderItem);
			//add list productItem
			detail.getProductItem().setNumPurchases(detail.getProductItem().getNumPurchases()+1);
			productItems.add(detail.getProductItem());
		});
		// save item orders and shop orders in db
		orderShopRepo.saveAll(orders.values());
		// save update productItem +1 purcharse
		productItemRepo.saveAll(productItems);
		
	}

}
