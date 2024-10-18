package com.example.bybitproject.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "launch_pool")
public class LaunchPool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String exchange;
    private String launchPool;

    @OneToMany(mappedBy = "launchPool")
    private List<Pool> pools;

    private String period;
    private String status;

    public LaunchPool() {
    }

    public LaunchPool(String exchange, String launchPool, List<Pool> pools, String period, String status) {
        this.exchange = exchange;
        this.launchPool = launchPool;
        this.pools = pools;
        this.period = period;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getExchange() {
        return exchange;
    }

    public String getLaunchPool() {
        return launchPool;
    }

    public String getPeriod() {
        return period;
    }

    public String getStatus() {
        return status;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setLaunchPool(String launchPool) {
        this.launchPool = launchPool;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
