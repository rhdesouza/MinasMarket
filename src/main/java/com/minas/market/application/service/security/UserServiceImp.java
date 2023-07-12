package com.minas.market.application.service.security;

import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.infrastructure.persistence.repository.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImp {

    @Autowired
    private final UserRepository userRepository;

    public Boolean isUserExists(User user){
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    @Transactional
    public User saved(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(UUID id){
        return userRepository.findById(id);
    }

}
