package com.mydao.nsop.client.exception;

/**
 * @author ZYW
 * @date 2018/5/11
 */
public class NSOPClientException extends RuntimeException {
    public NSOPClientException() {
    }

    public NSOPClientException(String message) {
        super(message);
    }

    public NSOPClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public NSOPClientException(Throwable cause) {
        super(cause);
    }
}
