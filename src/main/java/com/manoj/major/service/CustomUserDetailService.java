package com.manoj.major.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manoj.major.model.CustomUserDetail;
import com.manoj.major.model.User;
import com.manoj.major.repository.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService {
   @Autowired
   UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user= userRepository.findUserByEmail(email);
		user.orElseThrow(()-> new UsernameNotFoundException("User not Found"));
		return user.map(CustomUserDetail::new).get();
	}

}
