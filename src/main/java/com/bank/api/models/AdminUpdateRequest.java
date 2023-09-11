package com.bank.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AdminUpdateRequest {
    String username;
    boolean admin;
}
