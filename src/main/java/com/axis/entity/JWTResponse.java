package com.axis.entity;

public class JWTResponse {
    private String token;

    public JWTResponse() {
    }

    public JWTResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JWTResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
