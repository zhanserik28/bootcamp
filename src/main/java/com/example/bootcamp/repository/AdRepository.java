package com.example.bootcamp.repository;

import com.example.bootcamp.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {
}
