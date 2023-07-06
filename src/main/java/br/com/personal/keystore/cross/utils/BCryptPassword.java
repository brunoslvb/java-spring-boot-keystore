package br.com.personal.keystore.cross.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptPassword {

    private final Integer SALT = 14;

    public String encode(String plainText){

        String saltGenerated = BCrypt.gensalt(SALT);

        return BCrypt.hashpw(plainText, saltGenerated);

    }

    public Boolean verify(String plainText, String password){

        return BCrypt.checkpw(plainText, password);

    }

}
