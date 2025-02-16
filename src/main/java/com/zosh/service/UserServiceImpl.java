package com.zosh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.models.User;
import com.zosh.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepo userRepo;

	@Override
	public User registerUser(User user) {
		
		User newUser=new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setId(user.getId());
		newUser.setGender(user.getGender());
		newUser.setFollowers(user.getFollowers());
		newUser.setFollowings(user.getFollowings());
		
		User savedUser=userRepo.save(newUser);
		return savedUser;
	}
	

	@Override
	public User findUserById(Integer userId) throws Exception {
		Optional<User> user=userRepo.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
			
			throw new Exception("user not exist with id: "+userId);
	}
	
	

	@Override
	public User findUserByEmail(String email) {
		User user=userRepo.findByEmail(email);
		
		return user;
	}
	
	

	@Override
	public User followUser(Integer userId1, Integer userId2) throws Exception {
		
		User user1=findUserById(userId1);
		User user2=findUserById(userId2);
		
		user2.getFollowers().add(user1.getId());
		user1.getFollowings().add(user2.getId());
		
		userRepo.save(user1);
		userRepo.save(user2);
		
		return user1;
	}

	
	@Override
	public User updateUser(User user, Integer userId) throws Exception {
		Optional<User> user1=userRepo.findById(userId);
		if (user1.isEmpty()) {
			throw new Exception("User not exist");
		}
		
			User oldUser=user1.get();
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setEmail(user.getEmail());
			oldUser.setGender(user.getGender());
			
			User updatedUser=userRepo.save(oldUser);
		
		return updatedUser;
	}

	
	@Override
	public List<User> searchUser(String query) {
	
		
		return userRepo.searchUser(query);
	}

}
