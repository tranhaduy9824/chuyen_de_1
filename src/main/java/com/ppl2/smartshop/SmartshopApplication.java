package com.ppl2.smartshop;

import org.modelmapper.ModelMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//nếu muốn khởi tạo db mới thì xóa db cũ và tắt comment phía dưới sau khi bỏ comment khởi tạo thành công thì comment lại 
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.ppl2.smartshop.entities.Role;
//import com.ppl2.smartshop.entities.User;
//import com.ppl2.smartshop.security.service.RoleService;
//import com.ppl2.smartshop.security.service.UserService;

@SpringBootApplication
public class SmartshopApplication {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper model=new ModelMapper();
		model.getConfiguration().setSkipNullEnabled(true);
		return model;
	}
	public static void main(String[] args) {
		SpringApplication.run(SmartshopApplication.class, args);
	}
	//nếu muốn khởi tạo db mới thì xóa db cũ và tắt comment phía dưới sau khi bỏ comment khởi tạo thành công thì comment lại 
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private RoleService roleService;
//	@PostConstruct
//	public void init() {
//		String roleList[]= {"ADMIN","CUSTOMER","USER","SHOP"};
//		Set<Role> roles=new HashSet<Role>();
//		for(String name: roleList) {
//			Role role=new Role();
//			role.setRoleName(name);
//			roles.add(roleService.save(role));
//		}
//		User user=new User();
//		user.setEmail("admin@gmail.com");
//		user.setPhone("0123456789");
//		user.setPassword("123456");
//		user.setRoles(roles);
//		userService.save(user);
//	}

}
