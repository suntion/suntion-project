package suns.jwt;

import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	static final String SECRET = "ThisIsASecret";

	public static String generateToken(String username) {
		HashMap<String, Object> map = new HashMap<>();
		// you can put any data in the map
		map.put("username", username);
		String jwt = Jwts.builder().setClaims(map).setExpiration(new Date(System.currentTimeMillis() + 3600_000_000L))// 1000
																														// hour
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return "Bearer " + jwt; // jwt前面一般都会加Bearer
	}

	public static void validateToken(String token) {
		try {
			// parse the token.
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace("Bearer ", "")).getBody();
		} catch (Exception e) {
			throw new IllegalStateException("Invalid Token. " + e.getMessage());
		}
	}
}
