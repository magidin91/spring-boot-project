package org.example.sweater.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роли юзера: админ, юзер, привелигированный юзер итд
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();  // Returns the name of this enum constant
    }
}
