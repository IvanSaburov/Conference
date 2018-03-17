package org.saburov.services.conference.config;

import org.saburov.services.conference.entity.ConferenceUser;
import org.saburov.services.conference.entity.Presentation;
import org.saburov.services.conference.repository.ConferenceUserRepository;
import org.saburov.services.conference.repository.PresentationRepository;
import org.saburov.services.conference.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    ConferenceUserRepository userRepository;

    @Autowired
    PresentationRepository presentationRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<String> rolesList = new ArrayList<>();
        String username = authentication.getName();
        String password = MD5Util.convertPasswordToHash(String.valueOf(authentication.getCredentials()));

        ConferenceUser user  = userRepository.findByUsername(username);

        if(user== null || !user.getPassword().equals(password)){
            throw new BadCredentialsException("Неверные логин или пароль");
        }
        // Кладем данные о новом пользователе, вместе его подключением к БД
        ConferenceUser principal = new ConferenceUser(username,  password);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(rolesList.toArray(new String[rolesList.size()]));
        return new UsernamePasswordAuthenticationToken(principal, password, authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
