package com.example.demo.repositories;

import com.example.demo.models.User;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    // @Query("{name:'?0'}")
    public User findByUsername(String username);

    public List<User> findAll();

    public User findByFirstName(String firstname);

    public User findByLastName(String lastname);

}