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
    public List<User> findByUsername(String username);

    public List<User> findAll();

    public List<User> findByFirstName(String firstname);

    public List<User> findByLastName(String lastname);

    public List<User> findByEmail(String email);

    public User findByUserId(String userId);

}