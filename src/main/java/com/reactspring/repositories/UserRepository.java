
package com.reactspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reactspring.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}