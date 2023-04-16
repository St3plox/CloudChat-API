package ru.tvey.cloudserverapp.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.entity.User;
import ru.tvey.cloudserverapp.exception.user.UserExistsException;
import ru.tvey.cloudserverapp.repository.UserRepository;
import ru.tvey.cloudserverapp.utils.EntityService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EntityService entityService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, EntityService entityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.entityService = entityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return (User) entityService.unwrapEntity(user);
    }

    @Override
    public User getUser(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        return (User) entityService.unwrapEntity(user);
    }

    @Override
    public void createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistsException("User with the name " + user.getUsername() + " already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

/*    private User unwrapUser(Optional<User> user) {
        if (user.isPresent()) return user.get();
        else throw new RuntimeException("User not found");
    }*/
}
