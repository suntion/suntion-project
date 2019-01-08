package suns.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import suns.jwt.JwtUtil;


@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String index() {
		return "Hello World";
	}
	
	
	@PostMapping(value="login")
	public String login(String username, String password) {
		if ("admin".equals(username)) {
			String jwt = JwtUtil.generateToken(username);
			System.out.println(jwt);
			return jwt;
		}
		return "504";
	}
}
