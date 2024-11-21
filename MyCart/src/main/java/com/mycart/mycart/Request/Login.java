package com.mycart.mycart.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Login {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
