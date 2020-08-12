package cn.hiboot.framework.research.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/4/11 0:14
 */
public class JwtDemo {


    @Test
    public void jwtToken() throws UnsupportedEncodingException {
        //签发时间
        Date iaDate = new Date();

        //过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 1);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = Maps.newHashMap();
        String token = JWT.create()
                .withHeader(map)
                .withClaim("userId", 1)
                .withExpiresAt(expireDate)
                .withIssuedAt(iaDate)
                .withIssuer("hiekn")
                .sign(Algorithm.HMAC384("SECRET"));

        JWTVerifier verifier = JWT.require(Algorithm.HMAC384("SECRET")).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();

        claims.forEach((k, v) -> System.out.println(k + "  " + v.asString()));
    }

}
