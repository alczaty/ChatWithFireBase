package com.aty.loginandregister;

public class User {
    String message;
    String email;

    public User(String message, String email) {
        this.message = message;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }
    @Override
    public String toString(){
        return "email: "+ email+ '\''+
                "message: " +message;
    }

}
