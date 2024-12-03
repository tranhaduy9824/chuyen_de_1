package com.ppl2.smartshop.security.auth;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ppl2.smartshop.entities.Role;
import com.ppl2.smartshop.entities.User;
import com.ppl2.smartshop.model.Message;
import com.ppl2.smartshop.security.service.IRoleService;
import com.ppl2.smartshop.security.service.IUserService;
import com.ppl2.smartshop.services.ICustomerService;

@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/login")
    public String userLogin(@RequestParam(name = "error", required = false,defaultValue = "false") Boolean error,Model model) {
    	if (error) {
    		model.addAttribute("message",new Message("Đăng nhập thấy bại vui lòng kiểm tra lại username và password",false));
		}
        return "authentication/login";
    }
    @GetMapping("/register")
    public String register(Model model) {
    	model.addAttribute("user", new User());
        return "authentication/register";
    }
    @PostMapping("/register")
    public String addUser(Model model,@ModelAttribute User user,HttpServletRequest request) {
        try {
            if(userService.findByUsername(user.getEmail()!=null ? user.getEmail(): user.getPhone())!=null){
                throw new Exception("Đã tồn tại người dùng, vui lòng chọn tên đăng nhập khác");
            }
            //get roles in database by name to set for user
            Set<Role> roles= Set.of(roleService.findByName("ROLE_CUSTOMER"));
            user.setRoles(roles);
            String pass=user.getPassword();
            user=userService.save(user);
            customerService.createCustomerByUser(user);
            request.getSession().setAttribute("sizeCart", 0);
            request.login(user.getEmail(), pass);
        }
        catch (Exception e){
        	model.addAttribute("user", user);
        	System.out.println(e);
        	model.addAttribute("message",new Message(e.getMessage(),false));
            return "authentication/register"; 
            
        }
		return "redirect:/home";

    }
}