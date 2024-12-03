package com.ppl2.smartshop.services;

import java.util.List;
import java.util.Map;

import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.model.OrderDTO;

public interface IOrderService {
	List<OrderDTO> getAllOrderByStatusAndShop(List<OrderStatus> status,String shopId);
	List<OrderDTO> getAllOrderByShop(String shopid);
	List<OrderDTO> getAllOrderByStatus(List<OrderStatus> status);
	List<OrderDTO> getAllOrderByStatus(List<OrderStatus> status,Long userId);

	boolean updateStatusOrder(OrderStatus status, String orderId, Long userId);
	OrderDTO updateStatusOrderByShop(OrderStatus status, String orderId, String shopId);
	List<OrderDTO> getOrderNeedSolvingByShop(String shopId);
	Map<OrderStatus, Long> statisticsByShop(String shopId);
	OrderDTO findByShop(String orderId,String shopId);
	Map<OrderStatus, Long> statisticsAll();
	Object findById(String orderId);
	boolean updateStatusOrderAd(OrderStatus status, String orderId);
}
