package com.zosh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.config.JwtProvider;
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
	public User followUser(Integer reqUserId, Integer userId2) throws Exception {
		
		User reqUser=findUserById(reqUserId);
		User user2=findUserById(userId2);
		
		user2.getFollowers().add(reqUser.getId());
		reqUser.getFollowings().add(user2.getId());
		
		userRepo.save(reqUser);
		userRepo.save(user2);
		
		return reqUser;
	}

	
	@Override
	public User updateUser(User user, Integer userId) throws Exception {
		Optional<User> user1=userRepo.findById(userId);
		if (user1.isEmpty()) {
			throw new Exception("User not exist");
		}
		
			User oldUser=user1.get();
			
			if(user.getFirstName()!=null) {
				oldUser.setFirstName(user.getFirstName());
			}
			if(user.getLastName()!=null) {
				oldUser.setLastName(user.getLastName());
			}
			if(user.getEmail()!=null) {
				oldUser.setEmail(user.getEmail());
			}
			if(user.getGender()!=null) {
				oldUser.setGender(user.getGender());
			}
			
			
			User updatedUser=userRepo.save(oldUser);
		
		return updatedUser;
	}

	
	@Override
	public List<User> searchUser(String query) {
	
		
		return userRepo.searchUser(query);
	}


	@Override
	public User findUserByJwt(String Jwt) {

		String email=JwtProvider.getEmailFromJwtToken(Jwt);
		User user=userRepo.findByEmail(email);
		return user;
	}

}
