package com.isuwang.security.core.properties;

public class Oauth2Properties {

    private Oauth2ClientProperties[] clients = {};

    public Oauth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(Oauth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
