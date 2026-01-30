package com.application.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.management.model.User;
import com.application.management.repo.UserRepository;
import com.application.management.utils.SecurityUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
    
	@Override
	public User getCurrentUser() {
	    String email = SecurityUtils.getCurrentUsername();
	    return getUserByEmail(email);
	}
    
}
