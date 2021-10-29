package pl.com.kocielapki.cattery.auth;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

public class ShaPasswordEncoder extends MessageDigestPasswordEncoder {

    public ShaPasswordEncoder() {
        this(1);
    }

    public ShaPasswordEncoder(int strength) {
        super("SHA-" + strength);
    }
}
