package com.example.bybitproject.repository;

import com.example.bybitproject.model.LaunchPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaunchPoolRepository extends JpaRepository<LaunchPool,Integer> {
    LaunchPool findByExchangeAndLaunchPoolAndPeriod(String exchange,String launchPool,String period);
}
