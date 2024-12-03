package com.ppl2.smartshop.services;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.Address;
import com.ppl2.smartshop.entities.Customer;
import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.model.AddressDTO;
import com.ppl2.smartshop.model.CustomerDTO;
import com.ppl2.smartshop.repositories.ICustomerRepo;

@Service
public class CustomerService implements ICustomerService{
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private ModelMapper mapper;
	@Override
	public CustomerDTO updateProfile(CustomerDTO customer) {
		Customer validCustomer= customerRepo.findById(customer.getId()).orElse(null);
		if(validCustomer!=null) {
			validCustomer.getUser().setAvatar(customer.getAvatar());
			validCustomer.getUser().setFullname(customer.getFullname());
			validCustomer.setBirthday(customer.getBirthday());
			validCustomer.setGender(customer.getGender());
			validCustomer.setAddresses(customer.getAddresses().stream().map(address->{
				Address dto= mapper.map(address, Address.class);
				dto.setCustomer(validCustomer);
				return dto;
			}).collect(Collectors.toList()));
			Customer newCustomer= customerRepo.save(validCustomer);
			CustomerDTO customerDTO=mapper.map(newCustomer, CustomerDTO.class);
			customerDTO.setAddresses(newCustomer.getAddresses().stream()
					.map(address->mapper.map(address, AddressDTO.class)).collect(Collectors.toList()));
			customerDTO.setFullname(newCustomer.getUser().getFullname());
			customerDTO.setEmail(newCustomer.getUser().getEmail());
			customerDTO.setAvatar(newCustomer.getUser().getAvatar());
			customerDTO.setUserId(newCustomer.getUser().getUserId());
			customerDTO.setPhone(newCustomer.getUser().getPhone());
			return customerDTO;
		}
		return null;

	}
	@Override
	public CustomerDTO findByUserId(Long userId) {
		
		Customer customer= customerRepo.findByUserUserId(userId);
		CustomerDTO customerDTO=mapper.map(customer, CustomerDTO.class);
		customerDTO.setAddresses(customer.getAddresses().stream()
				.map(address->mapper.map(address, AddressDTO.class)).collect(Collectors.toList()));
		customerDTO.setEmail(customer.getUser().getEmail());
		customerDTO.setAvatar(customer.getUser().getAvatar());
		customerDTO.setFullname(customer.getUser().getFullname());
		customerDTO.setUserId(customer.getUser().getUserId());
		customerDTO.setPhone(customer.getUser().getPhone());
		return customerDTO;
	}
	@Override
	public void createCustomerByUser(User user) {
		System.out.println("createCustomerByUser");
		Customer customer=new Customer();
		customer.setUser(user);
		customerRepo.save(customer);
		
	}

}
