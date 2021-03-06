package com.worksap.stm2016.model;


import org.springframework.security.core.authority.AuthorityUtils;


public class CurrentUser extends org.springframework.security.core.userdetails.User{
	
	
	private static final long serialVersionUID = 1L;
	private Person user;
	
	public CurrentUser() {
		super(null, null, null);
	}
	
	public CurrentUser(Person user) {
		super(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().getRoleName()));
        
		this.user = user;
	}
	
	public Person getUser() {
		return user;
		//return userService.findById(user.getPersonId());
	}
	
	public void setUser(Person person) {
		user = person;
	}
	
	public Long getId() {
		return user.getPersonId();
	}
	
	public Role getRole() {
		return user.getRole();
	}
	
	@Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
