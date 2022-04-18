package com.learning.springbootmultithreading.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.springbootmultithreading.model.User;

@Repository
public interface UsersRepository extends MongoRepository<User, String>{

}
