package com.worksap.stm2016.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.CurrentUserDetailsService;
import com.worksap.stm2016.service.PersonService;

@Service
@Transactional
public class CurrentUserDetailsServiceImpl implements UserDetailsService{

	private final PersonService userService;

    @Autowired
    public CurrentUserDetailsServiceImpl(PersonService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = userService.findByUserName(username);
        return new CurrentUser(user);
    }

}
