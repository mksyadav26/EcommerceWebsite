package com.manoj.major.configuration.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.manoj.major.model.Role;
import com.manoj.major.model.User;
import com.manoj.major.repository.RoleRepository;
import com.manoj.major.repository.UserRepository;

@Component
public class GoogleOAuth2SucessHandler implements AuthenticationSuccessHandler {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;

	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken token=(OAuth2AuthenticationToken) authentication;
		String email=token.getPrincipal().getAttributes().get("email").toString();
		if(userRepository.findUserByEmail(email).isPresent())
		{
		
	}
		else
	{
		User user= new User();
		user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
		user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
		user.setEmail(email);
		List<Role> roles= new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user); 
	
	}
	response.sendRedirect("/");
	
		
     }
}
