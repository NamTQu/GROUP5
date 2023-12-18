package com.example.JWT.model.Enum;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.JWT.model.Enum.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.example.JWT.model.Enum.Permission.*;

@RequiredArgsConstructor
public enum Role {

    TENANT(Collections.emptySet()),

    LANDLORD(
            Set.of(
                    LANDLORD_READ,
                    LANDLORD_CREATE,
                    LANDLORD_DELETE,
                    LANDLORD_UPDATE
            )
    )
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
