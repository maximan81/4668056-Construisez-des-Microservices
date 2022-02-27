package com.microcommerce.ecommerce.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason ="le produit est introuvable")
public class NotFoundProductException extends RuntimeException {
    public NotFoundProductException(String s) {
        super(s);
    }
}
