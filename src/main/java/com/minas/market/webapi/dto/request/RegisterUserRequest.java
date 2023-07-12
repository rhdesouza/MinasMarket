package com.minas.market.webapi.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    UserDTO user;
    AddressRequest address;
}
