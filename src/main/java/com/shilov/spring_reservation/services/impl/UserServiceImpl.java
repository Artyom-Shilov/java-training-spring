package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.exceptions.DataNotFoundException;
import com.shilov.spring_reservation.entities.User;
import com.shilov.spring_reservation.models.UserModel;
import com.shilov.spring_reservation.repository.UserRepository;
import com.shilov.spring_reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserModel getUser(Long id) throws DataNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new DataNotFoundException(User.class, id))
                .toUserModel();
    }

    @Override
    @Transactional
    public Long addUser(UserModel userModel) {
        return userRepository.save(new User(userModel)).getId();
    }
}
