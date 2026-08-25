package mff.oms.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mff.oms.user.dto.UserLoginDTO;
import mff.oms.user.dto.UserRegisterDTO;
import mff.oms.user.dto.UserResponseDTO;
import mff.oms.user.entity.User;
import mff.oms.user.service.UserService;
import mff.oms.user.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto) {
		return ResponseEntity.ok(userService.register(dto));
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO dto) {
		User user = userService.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

		if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			String token = jwtUtil.generateToken(user.getEmail());
			return ResponseEntity.ok(Map.of("token", token));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
	}
}
