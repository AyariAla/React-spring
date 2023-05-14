package com.reactspring;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.reactspring.models.User;
import com.reactspring.repositories.UserRepository;

@DataJpaTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User("John", "Doe", "John.doe@gmail.com", "johnny", "strong-password");
        userRepository.save(user);
        userRepository.findById(new Long(1))
                .map(newUser -> {
                    Assert.assertEquals("John", newUser.getName());
                    return true;
                });

    }

    @Test
    public void getUser() {
        User user = new User("John", "Doe", "John.doe@gmail.com", "johnny", "strong-password");
        User user2 = new User("Daniel", "Marcus", "daniel@daniel.com", "danie", "super_strong_password");
        userRepository.save(user);
        userRepository.save(user2);

        userRepository.findById(new Long(1))
                .map(newUser -> {
                    Assert.assertEquals("danie", newUser.getUsername());
                    return true;
                });

    }

    @Test
    public void getUsers() {
        User user = new User("John", "Doe", "john.doe@email.com", "johhny", "strong-password");
        User user2 = new User("Daniel", "Marcus", "daniel@daniel.com", "danie", "super_strong_password");
        userRepository.save(user);
        userRepository.save(user2);

        Assert.assertNotNull(userRepository.findAll());
    }

    @Test
    public void deleteUser() {
        User user = new User("John", "Doe", "john.doe@email.com", "johhny", "strong-password");
        userRepository.save(user);
        userRepository.delete(user);
        Assert.assertTrue(userRepository.findAll().isEmpty());
    }

}
