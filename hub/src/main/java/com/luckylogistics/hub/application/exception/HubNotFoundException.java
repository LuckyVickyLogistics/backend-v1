// hub-application 모듈
package com.luckylogistics.hub.application.exception;

public class HubNotFoundException extends RuntimeException {
    public HubNotFoundException(String message) {
        super(message);
    }
}
