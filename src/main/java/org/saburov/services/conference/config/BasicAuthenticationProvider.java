package org.saburov.services.conference.config;

import org.saburov.services.conference.entity.ConferenceUser;
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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        String password = String.valueOf(authentication.getCredentials());

        ConferenceUser user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(MD5Util.convertPasswordToHash(password))) {
            throw new BadCredentialsException("Неверные логин или пароль");
        }
        rolesList.add(user.getRole());
        rolesList.add("ROLE_" + user.getRole());
        ConferenceUser principal = new ConferenceUser(username, password);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(rolesList.toArray(new String[rolesList.size()]));
        return new UsernamePasswordAuthenticationToken(principal, password, authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
