package com.login.pd.loginPD.service;

import com.login.pd.loginPD.entity.MyUserDetails;
import com.login.pd.loginPD.entity.User;

import com.login.pd.loginPD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return user.map(MyUserDetails::new).get();
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public void saveMyUser(User user) {
        userRepository.save(user);
    }

    public void deleteMyUser(int id) {
        userRepository.deleteById(id);
    }

    //For user list
    public List<User> showAllUsers(){
        List<User> users = new ArrayList<User>();
        for(User user : userRepository.findAll()){
            users.add(user);
        }
        return users;
    }

    public User editUser(int id) {
        return userRepository.findUserById(id);
    }

    //Encrypts password for DB
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        int strength = 10; // work factor of bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        return encodedPassword;
    }


}
