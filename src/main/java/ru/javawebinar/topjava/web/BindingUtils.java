package ru.javawebinar.topjava.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.StringJoiner;

public abstract class BindingUtils {
    /**
     * @param - Spring binding result
     * @param - fields that are ignored by validation
     * @return - response entity result
     */
    public static ResponseEntity getBindingResult(BindingResult result, String... ignoreFields) {
        if (result.hasErrors()) {
            StringJoiner joiner = new StringJoiner("<br>");
            result.getFieldErrors().forEach(
                    fe -> {
                        String msg = fe.getDefaultMessage();
                        if (msg != null && !Arrays.asList(ignoreFields).contains(fe.getField())) {
                            if (!msg.startsWith(fe.getField())) {
                                msg = fe.getField() + ' ' + msg;
                            }
                            joiner.add(msg);
                        }
                    });
            if (joiner.length() != 0) {
                return ResponseEntity.unprocessableEntity().body(joiner.toString());
            }
        }
        return ResponseEntity.ok().build();
    }
}
