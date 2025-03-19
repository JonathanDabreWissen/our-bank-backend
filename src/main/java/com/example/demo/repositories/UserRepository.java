package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
	User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
	
	@Modifying
    @Transactional
    @Query("UPDATE User u SET u.balance = :balance WHERE u.email = :email")
    void updateBalance(@Param("email") String email, @Param("balance") int balance);
	
	@Modifying
    @Transactional
    @Query("UPDATE User u SET u.balance = u.balance - :amount WHERE u.email = :email AND u.balance >= :amount")
    int deductBalance(@Param("email") String email, @Param("amount") int amount);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.balance = u.balance + :amount WHERE u.email = :email")
    int addBalance(@Param("email") String email, @Param("amount") int amount);
	
}
