package com.manoj.major.configuration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manoj.major.global.GlobalData;
import com.manoj.major.model.Role;
import com.manoj.major.model.User;
import com.manoj.major.repository.RoleRepository;
import com.manoj.major.repository.UserRepository;

@Controller
public class LoginController {
@Autowired
private BCryptPasswordEncoder bcryptPasswordEncoder;
@Autowired 
UserRepository userRepository;
@Autowired
RoleRepository roleRepository;

@GetMapping("/login")
public String login() {
	GlobalData.cart.clear();
	return "login";
}

@GetMapping("/register")
public String getRegister() {
	return "register";
}

@PostMapping("/register")
public String saveUser(@ModelAttribute("user") User user,HttpServletRequest request) throws ServletException
{
String password=user.getPassword();
user.setPassword(bcryptPasswordEncoder.encode(password));
List<Role> roles= new ArrayList<>();
roles.add(roleRepository.findById(2).get());
user.setRoles(roles);
userRepository.save(user);
request.login(user.getEmail(), password);
return "redirect:/";
}


}
