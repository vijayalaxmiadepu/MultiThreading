package com.learning.springbootmultithreading.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learning.springbootmultithreading.model.User;
import com.learning.springbootmultithreading.repository.UsersRepository;

@Service
public class UserService {
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UsersRepository userRepository;
	
	@Async
	public CompletableFuture<List<User>> saveUser(MultipartFile file) throws Exception{
		long start = System.currentTimeMillis();
		List<User> users = this.parseCsvFile(file);
        logger.info("saving list of users of size {}", users.size(), "" + Thread.currentThread().getName());
        users = userRepository.saveAll(users);
		long end = System.currentTimeMillis();
		logger.info("Total time {}", (end - start));
		return CompletableFuture.completedFuture(users);
	}
	
	@Async
    public CompletableFuture<List<User>> findAllUsers(){
        logger.info("get list of user by "+Thread.currentThread().getName());
        List<User> users=userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }
	
	private List<User> parseCsvFile(final MultipartFile file) throws Exception{
		final List<User> users = new ArrayList<User>();
		try {
			try(final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				String line;
				while((line = br.readLine()) != null) {
					final String[] data = line.split(",");
					users.add(new User(data[0], data[1], data[2]));
				}
				return users;
			}
		}catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
		
	}
	
}
