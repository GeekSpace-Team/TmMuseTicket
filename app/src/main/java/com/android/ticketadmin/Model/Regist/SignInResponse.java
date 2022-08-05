package com.android.ticketadmin.Model.Regist;

public class SignInResponse {
    private Integer id;
    private String token;
    private String type;

    public SignInResponse(Integer id, String token, String type) {
        this.id = id;
        this.token = token;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
