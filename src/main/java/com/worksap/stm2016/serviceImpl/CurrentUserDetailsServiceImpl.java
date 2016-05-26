package com.worksap.stm2016.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.CurrentUserDetailsService;
import com.worksap.stm2016.service.PersonService;

@Service
@Transactional
public class CurrentUserDetailsServiceImpl implements CurrentUserDetailsService{

	private final PersonService userService;

    @Autowired
    public CurrentUserDetailsServiceImpl(PersonService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException, InternalAuthenticationServiceException {
        Person user = userService.findByUserName(username);
        if (user != null) {
        	System.out.println("user: " + user.getUserName() + " " + user.getPassword() + " " + user.getRole().getRoleName());
        	return new CurrentUser(user);
        } else {
        	return new CurrentUser();
        }
    }

}
