package com.yibo.auth.service;

import com.yibo.auth.client.UserFeignClient;
import com.yibo.auth.config.OAuth2LogoutSuccessHandler;
import com.yibo.auth.domain.dto.UserDTO;
import com.yibo.auth.domain.dto.UserExt;
import com.yibo.auth.domain.entity.MeUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangyibo
 * @Date: 2021/8/18 23:12
 * @Description:
 */

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //???????????????????????????????????????????????????
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //????????????????????????httpbasic?????????httpbasic????????????client_id???client_secret???????????????client_id???client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //?????? client_secret
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret,AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }

        if (StringUtils.isEmpty(username)) {
            return null;
        }
        //????????????????????????????????????
        UserExt userExt = userFeignClient.selectUserByUsername(username);
        if(userExt == null){
            log.info("????????????["+username + "] ?????????");
            throw new BadCredentialsException("????????????["+username + "] ?????????");
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userExt,userDTO);

        /*GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);*/

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
        UserJwt userJwt = new UserJwt(username,userDTO.getPassword(),
                userDTO.isEnabled(),
                userDTO.isAccountNonExpired(),
                userDTO.isCredentialsNonExpired(),
                userDTO.isAccountNonLocked()
                ,authorities);
        BeanUtils.copyProperties(userDTO,userJwt);
        return userJwt;
    }
}
