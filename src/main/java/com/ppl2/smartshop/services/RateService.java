package com.ppl2.smartshop.services;


import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ppl2.smartshop.entities.OrderItem;
import com.ppl2.smartshop.entities.OrderShop;
import com.ppl2.smartshop.entities.ProductItem;
import com.ppl2.smartshop.entities.Rate;
import com.ppl2.smartshop.entities.datatypes.constrains.OrderStatus;
import com.ppl2.smartshop.model.RateDTO;
import com.ppl2.smartshop.repositories.IOrderItemRepo;
import com.ppl2.smartshop.repositories.IOrderShopRepo;
import com.ppl2.smartshop.repositories.IProductItemRepo;
import com.ppl2.smartshop.repositories.IRateRepository;

@Service
public class RateService implements IRateService {
    @Autowired
    private IRateRepository rateRepository;

    
    @Autowired
    private IProductItemRepo productItemRepo;
    
    @Autowired
    private IOrderItemRepo orderItemRepo;
    @Autowired
    private IOrderShopRepo orderShopRepo;
    @Autowired
    private ModelMapper modelMapper;


	public List<Rate> findByProductId(long id) {
		List<Rate> rateList = rateRepository.findByProductItemId(id);
        return rateList;
	}



	@Override
	public RateDTO save(Long orderId, RateDTO t) {
	    
	    // find the product being rated
	    OrderItem order = orderItemRepo.findByIdAndOrderCustomerUserUserId(orderId, t.getUserId());
	    ProductItem productItem=order.getProductDetail().getProductItem();
	    // create a new Rate object
	    Rate rate = new Rate();
	    rate.setPoint(t.getPoint());
	    rate.setComment(t.getComment());
	    rate.setCreated(new Date());
	    rate.setCustomer(order.getOrder().getCustomer());
	    rate.setProductItem(productItem);
	    order.setRated(true);
	    RateDTO rateDTO=modelMapper.map(rateRepository.save(rate),RateDTO.class);
	    //check order is completed when all order item is rated
	    boolean rated=true;
	    OrderShop orderShop=order.getOrder();
	    for(OrderItem orderItem: orderShop.getItems()) {
	    	if(!orderItem.isRated()) {
	    		rated=false;
	    		break;
	    	}
	    }
	    if(rated) {
	    	orderShop.setStatus(OrderStatus.COMPLETED);
	    }
	    orderShopRepo.save(orderShop);
	    //update rating point for product
	    List<Rate> rates=rateRepository.findByProductItemId(t.getProductId());
	    double sum=0.0;
	    for (Rate rate2 : rates) {
			sum+=rate2.getPoint();
		}

	    sum=Math.round(sum/rates.size());
	    productItem.setRating((float) sum);

	    productItemRepo.save(productItem);

	    return rateDTO;
		
	}



	@Override
	public Long statisticsRateByShop(String shopId) {
		return rateRepository.countByProductItemShopId(shopId);
	}






}
