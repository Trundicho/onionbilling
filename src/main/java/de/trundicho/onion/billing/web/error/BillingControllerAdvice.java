package de.trundicho.onion.billing.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BillingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(InvoiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invoiceNotFoundHandler(InvoiceNotFoundException ex) {
        return ex.getMessage();
    }

}