package ir.ac.ut.joboonja.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTUtils {

    private final static String SECRET_KEY = "joboonja-joboonja-joboonja-joboonja-joboonja-joboonja-joboonja";
    private final static long JWT_TTL = 15*60*1000L;

    public static String createJWT(String issuer) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // three mandatory claims
        JwtBuilder builder = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + JWT_TTL))
            .setIssuer(issuer)
            .signWith(signingKey, signatureAlgorithm);

        return builder.compact();
    }

    public static boolean verifyJWT(String jwt) {

        try {
            Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        } catch (
            ExpiredJwtException |
            UnsupportedJwtException |
            SignatureException |
            MalformedJwtException |
            IllegalArgumentException e
            ) {
            return false;
        }
        return true;
    }
}
