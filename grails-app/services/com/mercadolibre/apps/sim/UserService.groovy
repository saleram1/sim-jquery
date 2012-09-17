package com.mercadolibre.apps.sim

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import com.mercadolibre.apps.sim.data.bo.core.Shoppe
import com.mercadolibre.apps.sim.data.bo.core.User

class UserService {

    String getNickname(int userId) {
        String nickName

        def builder = new HTTPBuilder("https://api.mercadolibre.com")

        try {
            builder.contentType = ContentType.JSON
            builder.get(path: "/users/${userId}") { resp, json ->
                nickName = json['nickname']
            }
        }
        catch (Exception ex) {
            if (ex?.message == 'Not Found') {
                log.warn "User ${userId} not found !!"
            }
            else {
                log.error ex.message, ex
            }
        }
        return nickName
    }
}