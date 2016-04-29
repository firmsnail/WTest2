package com.worksap.stm2016.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.service.CurrentUserDetailsService;

public interface CurrentUserDetailsService extends UserDetailsService{

    public CurrentUser loadUserByUsername(String username);

}
