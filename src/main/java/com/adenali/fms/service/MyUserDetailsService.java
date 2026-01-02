package com.adenali.fms.service;

import com.adenali.fms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
//@Profile({"local", "dev"})
public class MyUserDetailsService implements UserDetailsService {


    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }
    private final UserService  userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);
    }
}
