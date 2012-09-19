package com.mercadolibre.apps.sim

class SignoutController {
    def index() {
        log.warn "User agent: " + request.getHeader("User-Agent")
        session.invalidate()
        redirect(url: "/")
    }
}