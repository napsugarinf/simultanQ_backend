package com.simultanq.base.service;

import com.simultanq.base.response.LoginMessage;
import com.simultanq.base.entity.User;
import com.simultanq.base.entity.dto.LoginDTO;
import com.simultanq.base.entity.dto.UserDTO;
import com.simultanq.base.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public String addUser(UserDTO userDTO){
//        User user = new User(userDTO.getId(),userDTO.getUsername(), this.passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail());
//
//        userRepository.save(user);
//        return user.getUsername();
//    }

    public User addUser(UserDTO userDTO){
        User user = new User(userDTO.getId(),userDTO.getUsername(), this.passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail());

        userRepository.save(user);
        return user;
    }

        public LoginMessage loginUser(LoginDTO loginDTO){

        String msg="";
        User user1 = userRepository.findByEmail(loginDTO.getEmail());
        if (user1 !=null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    Long id = user.get().getId();
                    return new LoginMessage("Login success", true, id);
                } else {
                    return new LoginMessage("Login failed!", false, null);
                }
            } else {
                return new LoginMessage("Password not match", false, null);
            }
        }
        else {
            return new LoginMessage("Email does not exists", false, null);
        }

    }
}
