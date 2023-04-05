package ru.tvey.cloudserverapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.entity.User;
import ru.tvey.cloudserverapp.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    private User unwrapUser(Optional<User> user, Long id) {
        if (user.isPresent()) return user.get();
        else throw new RuntimeException("User not found");
    }
}
