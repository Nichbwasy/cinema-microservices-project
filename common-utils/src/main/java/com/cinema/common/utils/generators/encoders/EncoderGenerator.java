package com.cinema.common.utils.generators.encoders;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderGenerator {
    public static BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
