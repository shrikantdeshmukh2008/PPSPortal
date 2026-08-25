package mff.oms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mff.oms.user.dto.UserResponseDTO;
import mff.oms.user.entity.User;
import mff.oms.user.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// Example: get current logged-in user
	@GetMapping("/me")
	public ResponseEntity<UserResponseDTO> getCurrentUser(@RequestHeader("X-User-Email") String email) {
		
		User user = userRepository.findByEmail(email)
						.orElseThrow(() -> new RuntimeException("User not found"));

		UserResponseDTO response = new UserResponseDTO();
		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());

		return ResponseEntity.ok(response);
	}

	// Get user by ID (protected)
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		UserResponseDTO response = new UserResponseDTO();
		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());

		return ResponseEntity.ok(response);
	}
}
