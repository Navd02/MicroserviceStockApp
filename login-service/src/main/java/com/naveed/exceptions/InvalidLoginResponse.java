package com.naveed.exceptions;


import lombok.Data;


@Data

public class InvalidLoginResponse {
    private String credentials;
    
    
    public InvalidLoginResponse() {
        this.credentials = "Invalid Credentials";
        
    }

    
}
