package com.moonyue.sleeve.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenDTO {
    @NotBlank
    private String token;
}
