package com.codeword.snb.repository;

import com.codeword.snb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   User findByEmail(String email);
}
