package com.Market.MarketPlaceApplication.Service;

import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save (User user){
        return userRepository.save(user);
    }
    public List<User> list(){
        return userRepository.findAll();
    }
}
