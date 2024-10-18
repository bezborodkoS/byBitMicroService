package com.example.bybitproject.repository;

import com.example.bybitproject.model.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository extends JpaRepository<Pool,Integer> {
}
