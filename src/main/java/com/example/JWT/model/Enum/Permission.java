package com.example.JWT.model.Enum;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    LANDLORD_READ("landlord:read"),

    LANDLORD_UPDATE("landlord:update"),

    LANDLORD_CREATE("landlord:create"),

    LANDLORD_DELETE("landlord:delete");


    @Getter
    private final String permission;
}
