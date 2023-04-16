package ru.tvey.cloudserverapp.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.entity.User;
import ru.tvey.cloudserverapp.repository.UserRepository;
import ru.tvey.cloudserverapp.service.user.UserService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user);
    }

    @Override
    public User getUser(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user);
    }

    @Override
    public User createUser(User user)  {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("user already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private User unwrapUser(Optional<User> user) {
        if (user.isPresent()) return user.get();
        else throw new RuntimeException("User not found");
    }
}
