package org.chungnamthon.zeroroad.domain.profilestore.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import org.chungnamthon.zeroroad.domain.member.entity.Role;

@Getter
public class UserPrincipal implements UserDetails {

    private Long memberId;
    private String socialId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long memberId, String socialId, String password, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.socialId = socialId;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(Long memberId, String socialId, Role role) {
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        return new UserPrincipal(memberId, socialId, "", authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return socialId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}