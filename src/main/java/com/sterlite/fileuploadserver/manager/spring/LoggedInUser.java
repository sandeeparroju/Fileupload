package com.sterlite.fileuploadserver.manager.spring;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoggedInUser extends User{ 

	private static final long serialVersionUID = -3531439484732724601L;

    private final String firstName;
    private final String lastName;
    private final LocalDateTime lastLoginTime;

    public LoggedInUser(String username, String password, boolean enabled,
        boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<GrantedAuthority> authorities,
        String firstName, String lastName,
        LocalDateTime lastLoginTime) {

            super(username, password, enabled, accountNonExpired,
               credentialsNonExpired, accountNonLocked, authorities);

            this.firstName = firstName;
            this.lastName = lastName;
            this.lastLoginTime = lastLoginTime;
    }

    public static long getSerialversionuid() {
       return serialVersionUID;
    }

    public String getFirstName() {
       return firstName;
    }

    public String getLastName() {
       return lastName;
    }
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }
    public String getFullName(){
    	return new StringBuffer().append(getFirstName()).append(" ").append(getLastName()).toString();
    }
}