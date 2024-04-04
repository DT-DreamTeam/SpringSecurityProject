package com.example.Zeta.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @Size(min = 3,max = 10,message = "Invalid first name!(3-10 character)")
    private String firstName;
    @Size(min = 3,max = 10,message = "Invalid last name!(3-10 character)")
    private String lastName;

    private String username;
    @Size(min = 5,max = 15,message = "Invalid password name!(5-15 symbol)")
    private String password;

    private String repeatPassword;
}
