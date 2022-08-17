package com.kafka.springboot.repository;

import com.kafka.springboot.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;

// repository to perform database operations
public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {



}
