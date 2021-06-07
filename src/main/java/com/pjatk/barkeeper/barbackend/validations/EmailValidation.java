package com.pjatk.barkeeper.barbackend.validations;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidation implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //TODO regex to validate email
        return true;
    }
}
