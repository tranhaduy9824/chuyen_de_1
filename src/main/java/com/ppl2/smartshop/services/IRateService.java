package com.ppl2.smartshop.services;

import java.util.List;

import com.ppl2.smartshop.entities.Rate;
import com.ppl2.smartshop.model.RateDTO;

public interface IRateService {
	List<Rate> findByProductId(long id) ;
	RateDTO save(Long orderId,RateDTO rateDTO);
	Long statisticsRateByShop(String shopId);
}
