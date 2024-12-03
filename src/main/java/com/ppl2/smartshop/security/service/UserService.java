package com.ppl2.smartshop.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppl2.smartshop.entities.Customer;
import com.ppl2.smartshop.entities.Shop;
import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.repositories.ICustomerRepo;
import com.ppl2.smartshop.repositories.IShopRepository;
import com.ppl2.smartshop.security.UserPrinciple;
import com.ppl2.smartshop.security.repo.IUserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICustomerRepo customerRepo;
    @Autowired
    private IShopRepository shopRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
    	try {
    		String username=user.getEmail()!=null ? user.getEmail(): user.getPhone();
			User existingUser=userRepository.findByEmailOrPhone(username,username).orElse(null);
	        if(existingUser!=null && user.getUserId()!=existingUser.getUserId()){
	            throw new Exception("Đã tồn tại người dùng, vui lòng chọn tên đăng nhập khác");
	        }
	    	user.setPassword(passwordEncoder.encode(user.getPassword()));
	    	user=userRepository.save(user);
	        return user;
		} catch (Exception e) {
			System.out.println(e);
			return user;
		}
    }
    
    @Override
    public boolean changePassword(Long id,String oldPass,String newPass) {
    	User user= userRepository.findById(id).get();
    	if(passwordEncoder.matches(oldPass,user.getPassword())){
    		user.setPassword(passwordEncoder.encode(newPass));
    		userRepository.save(user);
    		return true;
    	}
    	return false;
    	
    }
    @Override
    public void remove(Long id) {
    	userRepository.deleteUserTableJoin(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailOrPhone(username,username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
    }

    @Override
    public User findByUsername(String username) {
    	if(username==null) return null;
    	User user=userRepository.findByEmailOrPhone(username,username).orElse(null);
        return user;
    	
    }





	@Override
	public Page<User> getPageUser(Pageable page) {
		return userRepository.findAll(page);
	}




	@Override
	public User getByResetPasswordToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean updateLock(boolean locked) {
		try {
			userRepository.updateLock(locked);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void changePassword(Long id, String newPass) {
		User user= userRepository.findById(id).orElse(null);
		if(user!=null) {
	    	user.setPassword(passwordEncoder.encode(newPass));
	        userRepository.save(user);
		}
		
	}





}
