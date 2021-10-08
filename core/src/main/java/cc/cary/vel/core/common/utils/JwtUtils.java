package cc.cary.vel.core.common.utils;

import cc.cary.vel.core.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JwtUtils
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
@Slf4j
public class JwtUtils {
  private final String issuer = "Cary";
  @Autowired
  private JwtProperties jwtProperties;

  /**
   * 由字符串生成加密key
   *
   * @return
   */
  public SecretKey generalKey() {
    return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecret()));
  }

  /**
   * 生成jwt token
   *
   * @param userId
   * @return
   */
  public String generateToken(String userId) {
    Date nowDate = new Date();
    //过期时间
    Date expireDate = new Date(nowDate.getTime() + jwtProperties.getExpireTime() * 1000);
    return Jwts.builder()
        // 签发人
        .setIssuer(issuer)
        // 主题，也差不多是个人的一些信息
        .setSubject(userId)
        // 签发时间
        .setIssuedAt(nowDate)
        // 过期时间
        .setExpiration(expireDate)
        // 密匙
        .signWith(generalKey())
        .compact();
  }

  /**
   * 解密
   *
   * @param token
   * @return
   */
  public Claims getClaimByToken(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(generalKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (JwtException e) {
      log.debug("validate is token error ", e);
      return null;
    }
  }
}
