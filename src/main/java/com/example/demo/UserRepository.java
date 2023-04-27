package com.example.demo;

import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "users")
public interface UserRepository extends JpaRepository<Users, Long> {
}
