package com.mydao.nsop.client.exception;

/**
 * @author ZYW
 * @date 2018/5/8
 */
public class CallRemoteAPIException extends RuntimeException {

    public CallRemoteAPIException() {
    }

    public CallRemoteAPIException(String message) {
        super(message);
    }

    public CallRemoteAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public CallRemoteAPIException(Throwable cause) {
        super(cause);
    }
}
