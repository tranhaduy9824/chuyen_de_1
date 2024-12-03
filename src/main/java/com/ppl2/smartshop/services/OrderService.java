package com.ppl2.smartshop.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.Customer;
import com.ppl2.smartshop.entities.OrderShop;
import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.model.CustomerDTO;
import com.ppl2.smartshop.model.OptionDTO;
import com.ppl2.smartshop.model.OrderDTO;
import com.ppl2.smartshop.model.OrderItemDTO;
import com.ppl2.smartshop.repositories.ICustomerRepo;
import com.ppl2.smartshop.repositories.IOrderShopRepo;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private IOrderShopRepo orderShopRepo;
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<OrderDTO> getAllOrderByStatus(List<OrderStatus> status, Long userId) {
		Customer customer = customerRepo.findByUserUserId(userId);
		return orderShopRepo.findByStatusInAndCustomerId(status, customer.getId()).stream().map(order -> {
			order.setCustomer(null);
			OrderDTO dto = modelMapper.map(order, OrderDTO.class);
			dto.setShopAvatar(order.getShop().getAvatar());
			dto.setShopId(order.getShop().getId());
			dto.setShopName(order.getShop().getName());
			dto.setTotal(0L);
			dto.setItems(order.getItems().stream().map(item -> {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setImage(item.getProductDetail().getProductItem().getImage());
				itemDTO.setMoney(item.getMoney());
				itemDTO.setQuantity(item.getQuantity());
				itemDTO.setProductItemId(item.getProductDetail().getProductItem().getId());
				itemDTO.setTitle(item.getProductDetail().getProductItem().getTitle());
				itemDTO.setOptions(item.getProductDetail().getProductOptions().stream()
						.map(option -> modelMapper.map(option, OptionDTO.class)).collect(Collectors.toList()));
				dto.setTotal(itemDTO.getMoney() + dto.getTotal());
				return itemDTO;
			}).collect(Collectors.toList()));

			return dto;
		}).collect(Collectors.toList());

	}

	@Override
	public boolean updateStatusOrder(OrderStatus status, String orderId, Long userId) {
		Optional<OrderShop> op = orderShopRepo.findByIdAndCustomerUserUserId(orderId, userId);
		if (op.isPresent()) {
			OrderShop order = op.get();
			order.setStatus(status);
			orderShopRepo.save(order);
			return true;
		}
		return false;
	}
	@Override
	public boolean updateStatusOrderAd(OrderStatus status,String orderId) {
		Optional<OrderShop> op=orderShopRepo.findById(orderId);
		if(op.isPresent()) {
			OrderShop order= op.get();
			order.setStatus(status);
			//System.out.println(status.toString());
			orderShopRepo.save(order);
			return true;
		}
		return false;
	}
	@Override
	public List<OrderDTO> getAllOrderByShop(String shopId) {
		return orderShopRepo.findByShopId(shopId).stream().map(order -> {
			order.setCustomer(null);
			OrderDTO dto = modelMapper.map(order, OrderDTO.class);
			dto.setShopAvatar(order.getShop().getAvatar());
			dto.setShopId(order.getShop().getId());
			dto.setShopName(order.getShop().getName());
			dto.setTotal(0L);
			dto.setItems(order.getItems().stream().map(item -> {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setImage(item.getProductDetail().getProductItem().getImage());
				itemDTO.setMoney(item.getMoney());
				itemDTO.setQuantity(item.getQuantity());
				itemDTO.setProductItemId(item.getProductDetail().getProductItem().getId());
				itemDTO.setTitle(item.getProductDetail().getProductItem().getTitle());
				itemDTO.setOptions(item.getProductDetail().getProductOptions().stream()
						.map(option -> modelMapper.map(option, OptionDTO.class)).collect(Collectors.toList()));
				dto.setTotal(itemDTO.getMoney() + dto.getTotal());
				return itemDTO;
			}).collect(Collectors.toList()));

			return dto;
		}).collect(Collectors.toList());
	}
	@Override
	public Object findById(String orderId) {
		// TODO Auto-generated method stub
		return orderShopRepo.findById(orderId);
	}

	@Override
	public Map<OrderStatus, Long> statisticsByShop(String shopId) {
		List<Object[]> objList=orderShopRepo.countOrder(shopId);
		Map<OrderStatus , Long> result=new HashMap<>();
		for(OrderStatus status:OrderStatus.values()) {
			result.put(status, 0L);
		}
		for(Object[] objs:objList) {
			result.put((OrderStatus) objs[0], (Long) objs[1]);
		}
		return result;
	}

	@Override
	public List<OrderDTO> getOrderNeedSolvingByShop(String shopId) {
		return orderShopRepo.findByStatusAndShopId(OrderStatus.NEW, shopId).stream().map(order -> {
			order.setCustomer(null);
			OrderDTO dto = modelMapper.map(order, OrderDTO.class);
			dto.setShopAvatar(order.getShop().getAvatar());
			dto.setShopId(order.getShop().getId());
			dto.setShopName(order.getShop().getName());
			dto.setTotal(0L);
			dto.setItems(order.getItems().stream().map(item -> {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setMoney(item.getMoney());
				itemDTO.setQuantity(item.getQuantity());

				dto.setTotal(itemDTO.getMoney() + dto.getTotal());
				return itemDTO;
			}).collect(Collectors.toList()));

			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public OrderDTO findByShop(String orderId,String shopId) {
		OrderShop order=orderShopRepo.findByIdAndShopId(orderId,shopId).get();
		
		OrderDTO dto = modelMapper.map(order, OrderDTO.class);
		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO.setAvatar(order.getCustomer().getUser().getAvatar() );
		customerDTO.setEmail(order.getCustomer().getUser().getEmail());
		customerDTO.setPhone(order.getCustomer().getUser().getPhone());
		customerDTO.setFullname(order.getCustomer().getUser().getFullname());
		customerDTO.setGender(order.getCustomer().getGender());
		customerDTO.setId(order.getCustomer().getId());
		dto.setCustomer(customerDTO);
		dto.setShopId(order.getShop().getId());
		dto.setTotal(0L);
		dto.setItems(order.getItems().stream().map(item -> {
			OrderItemDTO itemDTO = new OrderItemDTO();
			itemDTO.setId(item.getId());
			itemDTO.setImage(item.getProductDetail().getProductItem().getImage());
			itemDTO.setMoney(item.getMoney());
			itemDTO.setQuantity(item.getQuantity());
			itemDTO.setProductItemId(item.getProductDetail().getProductItem().getId());
			itemDTO.setTitle(item.getProductDetail().getProductItem().getTitle());
			itemDTO.setOptions(item.getProductDetail().getProductOptions().stream()
					.map(option -> modelMapper.map(option, OptionDTO.class)).collect(Collectors.toList()));
			dto.setTotal(itemDTO.getMoney() + dto.getTotal());
			return itemDTO;
		}).collect(Collectors.toList()));

		return dto;
	}

	@Override
	public OrderDTO updateStatusOrderByShop(OrderStatus status, String orderId, String shopId) {
		Optional<OrderShop> op = orderShopRepo.findByIdAndShopId(orderId, shopId);
		if (op.isPresent()) {
			OrderShop order = op.get();
			order.setStatus(status);
			orderShopRepo.save(order);
			OrderDTO dto = modelMapper.map(order, OrderDTO.class);
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setAvatar(order.getCustomer().getUser().getAvatar() );
			customerDTO.setEmail(order.getCustomer().getUser().getEmail());
			customerDTO.setPhone(order.getCustomer().getUser().getPhone());
			customerDTO.setFullname(order.getCustomer().getUser().getFullname());
			customerDTO.setGender(order.getCustomer().getGender());
			customerDTO.setId(order.getCustomer().getId());
			dto.setCustomer(customerDTO);
			dto.setShopId(order.getShop().getId());
			dto.setTotal(0L);
			dto.setItems(order.getItems().stream().map(item -> {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setImage(item.getProductDetail().getProductItem().getImage());
				itemDTO.setMoney(item.getMoney());
				itemDTO.setQuantity(item.getQuantity());
				itemDTO.setProductItemId(item.getProductDetail().getProductItem().getId());
				itemDTO.setTitle(item.getProductDetail().getProductItem().getTitle());
				itemDTO.setOptions(item.getProductDetail().getProductOptions().stream()
						.map(option -> modelMapper.map(option, OptionDTO.class)).collect(Collectors.toList()));
				dto.setTotal(itemDTO.getMoney() + dto.getTotal());
				return itemDTO;
			}).collect(Collectors.toList()));
			return dto;
		}
		return null;
	}

	@Override
	public Map<OrderStatus, Long> statisticsAll() {
		List<Object[]> objList=orderShopRepo.countOrder();
		Map<OrderStatus , Long> result=new HashMap<>();
		for(OrderStatus status:OrderStatus.values()) {
			result.put(status, 0L);
		}
		for(Object[] objs:objList) {
			result.put((OrderStatus) objs[0], (Long) objs[1]);
		}
		return result;
	}

	@Override
	public List<OrderDTO> getAllOrderByStatusAndShop(List<OrderStatus> status,String shopId) {
		return orderShopRepo.findByStatusInAndShopId(status,shopId).stream().map(order -> {
			order.setCustomer(null);
			OrderDTO dto = modelMapper.map(order, OrderDTO.class);
			dto.setShopAvatar(order.getShop().getAvatar());
			dto.setShopId(order.getShop().getId());
			dto.setShopName(order.getShop().getName());
			dto.setTotal(0L);
			dto.setItems(order.getItems().stream().map(item -> {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setImage(item.getProductDetail().getProductItem().getImage());
				itemDTO.setMoney(item.getMoney());
				itemDTO.setQuantity(item.getQuantity());
				itemDTO.setProductItemId(item.getProductDetail().getProductItem().getId());
				itemDTO.setTitle(item.getProductDetail().getProductItem().getTitle());
				itemDTO.setOptions(item.getProductDetail().getProductOptions().stream()
						.map(option -> modelMapper.map(option, OptionDTO.class)).collect(Collectors.toList()));
				dto.setTotal(itemDTO.getMoney() + dto.getTotal());
				return itemDTO;
			}).collect(Collectors.toList()));

			return dto;
		}).collect(Collectors.toList());

	}

	@Override
	public List<OrderDTO> getAllOrderByStatus(List<OrderStatus> status) {
		return orderShopRepo.findByStatusIn(status).stream().map(order -> {
			order.setCustomer(null);
			OrderDTO dto = modelMapper.map(order, OrderDTO.class);
			dto.setShopAvatar(order.getShop().getAvatar());
			dto.setShopId(order.getShop().getId());
			dto.setShopName(order.getShop().getName());
			dto.setTotal(0L);
			dto.setItems(order.getItems().stream().map(item -> {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setImage(item.getProductDetail().getProductItem().getImage());
				itemDTO.setMoney(item.getMoney());
				itemDTO.setQuantity(item.getQuantity());
				itemDTO.setProductItemId(item.getProductDetail().getProductItem().getId());
				itemDTO.setTitle(item.getProductDetail().getProductItem().getTitle());
				itemDTO.setOptions(item.getProductDetail().getProductOptions().stream()
						.map(option -> modelMapper.map(option, OptionDTO.class)).collect(Collectors.toList()));
				dto.setTotal(itemDTO.getMoney() + dto.getTotal());
				return itemDTO;
			}).collect(Collectors.toList()));

			return dto;
		}).collect(Collectors.toList());
	}


}
