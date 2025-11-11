package com.Market.MarketPlaceApplication.DTOs;

import com.Market.MarketPlaceApplication.Model.Role;
import lombok.Data;

@Data
public class CreateUserDto {

    private String name;
    private String email;
    private String password;
    private Role role; // optional: SUPER_ADMIN, ADMIN, VENDOR, USER
    private String shopName;
}
