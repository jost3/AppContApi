package com.example.logapi2;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    // Otros campos de la respuesta, si los hay

    public String getToken() {
        return token;
    }
}
