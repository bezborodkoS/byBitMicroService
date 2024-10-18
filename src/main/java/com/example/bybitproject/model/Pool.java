package com.example.bybitproject.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pool")
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "launchPool_id")
    private LaunchPool launchPool;
    private String typePool;
    //    private Map<String, String> pools;
    private String percentTypePool;

    public Pool() {
    }

    public Pool(String typePool, String percentTypePool) {
        this.typePool = typePool;
        this.percentTypePool = percentTypePool;
    }

    public int getId() {
        return id;
    }

    public String getTypePool() {
        return typePool;
    }

    public String getPercentTypePool() {
        return percentTypePool;
    }

    public void setTypePool(String typePool) {
        this.typePool = typePool;
    }

    public void setPercentTypePool(String percentTypePool) {
        this.percentTypePool = percentTypePool;
    }

    public void setLaunchPool(LaunchPool launchPool) {
        this.launchPool = launchPool;
    }
}
