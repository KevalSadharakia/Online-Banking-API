package com.bank.api.repositories;
import com.bank.api.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepositories extends JpaRepository<User, String> {
}
