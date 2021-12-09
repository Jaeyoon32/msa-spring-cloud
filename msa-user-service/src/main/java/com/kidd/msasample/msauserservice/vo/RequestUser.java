package com.kidd.msasample.msauserservice.vo;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class RequestUser {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email cannot be less than two characters")
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name cannot be less than two characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Name cannot be equal or grater than 8 characters")
    private String pwd;

}
