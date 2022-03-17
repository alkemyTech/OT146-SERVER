package com.alkemy.ong.web.security.jwt;

//import com.alkemy.ong.domain.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

public class JwtUtils {

    private static Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public static String generateAccessToken(User user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
