package com.miu.onlinemarketplace.attest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
public class AttestValidator implements ConstraintValidator<Attest,Object> {

    private List<String> xssStrings;
    private final String LOCATION = "XSSValidation/xssValidation.properties";
    private final String KEY = "xss.validation";

    @Override
    public void initialize(Attest attest) {
        xssStrings = Arrays.asList(PropertyFileReader.getValue(LOCATION, KEY).split(","));
    }

    @Override
    public boolean isValid(Object t, ConstraintValidatorContext cvc) {
        if (t == null) return true;
        if (isXSS(t.toString()))  return false;
        return true;
    }

    private boolean isXSS(String field) {
        if (xssStrings
                .stream()
                .anyMatch((xss) -> field.toLowerCase().contains(xss.toLowerCase())) )  return true;
        return false;
    }

}
