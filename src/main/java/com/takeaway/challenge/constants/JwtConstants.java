package com.takeaway.challenge.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtConstants {

    public static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

}
