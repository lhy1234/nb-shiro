package com.nb.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nb.shiro.constant.SysConstant;
import org.apache.commons.lang3.StringUtils;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author Scott
 * @Date 2018-07-12 14:23
 * @Desc JWT工具类
 **/
public class JwtUtil {

	/**
	 * token密钥，不能修改以及
	 */
	private static final String SECRET = "NB-javaxxoo";

	// 过期时间30分钟
	public static final long EXPIRE_TIME = 30 * 60 * 1000;


	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT
					.require(algorithm)
					.withClaim("username", username)
					.build();
			// 效验TOKEN
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名,5min后过期
	 *
	 * @param username 用户名
	 * @return 加密的token
	 */
	public static String sign(String username) {
		//token过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		//签名
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		// 附带username信息
		return JWT.create()
				.withClaim("username", username)
				.withExpiresAt(date)
				.sign(algorithm);

	}

	/**
	 * 根据request中的token获取用户账号
	 * 
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getUserNameByToken(HttpServletRequest request) throws RuntimeException {
		String accessToken = request.getHeader(SysConstant.AUTH_TOKEN);
		String username = getUsername(accessToken);
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("未获取到用户");
		}
		return username;
	}

	

	public static void main(String[] args) {
		 String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDAzMTM3MzAsInVzZXJuYW1lIjoiemhhZ254aWFvIn0.DlJBd-9RL7ryCDajREyeC26ARkWd3IIQgiBSb3PiEiE";
		 System.out.println(JwtUtil.verify(token,"zhagnxiao"));
	}
}
