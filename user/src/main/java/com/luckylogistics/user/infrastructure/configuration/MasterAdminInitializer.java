package com.luckylogistics.user.infrastructure.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MasterAdminInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.existsByUsername("MASTER_ADMIN")) {
			return;
		}

		User masterAdmin = User.createMasterAdmin(
			"MASTER_ADMIN",
			passwordEncoder.encode("admin1234!"),
			"masteradmin@luckylogistices.com"
		);

		userRepository.save(masterAdmin);
		System.out.println("Master Admin account crated!!!!!!!!!!!!!!!");
	}
}
