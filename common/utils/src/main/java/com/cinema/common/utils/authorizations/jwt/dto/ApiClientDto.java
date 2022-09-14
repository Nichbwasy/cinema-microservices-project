package com.cinema.common.utils.authorizations.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiClientDto {

    private String apiClintName;
    private List<String> apiClientRoles;

}
