package com.zosh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zosh.models.Post;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	@Query("select p from Post p where p.user.id=:userId")
	List<Post> findPostByUserId(Integer userId);

}
