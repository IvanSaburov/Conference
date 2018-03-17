package org.saburov.services.conference.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RolesConfig {

    private static String listenerRole;
    private static String presenterRole;
    private static String adminRole;

    @Value("${roles.listener}")
    public void setListenerRole(String listenerRole) {
        RolesConfig.listenerRole = listenerRole;
    }
    @Value("${roles.presenter}")
    public void setPresenterRole(String presenterRole) {
        RolesConfig.presenterRole = presenterRole;
    }
    @Value("${roles.admin}")
    public void setAdminRole(String adminRole) {
        RolesConfig.adminRole = adminRole;
    }

    public static String getListenerRole() {
        return listenerRole;
    }

    public static String getPresenterRole() {
        return presenterRole;
    }

    public static String getAdminRole() {
        return adminRole;
    }
}
