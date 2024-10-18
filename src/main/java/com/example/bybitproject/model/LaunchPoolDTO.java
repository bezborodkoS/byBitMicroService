package com.example.bybitproject.model;

import java.util.Map;

public class LaunchPoolDTO {
    private String exchange;
    private String launchPool;
    private Map<String, String> pools;
    private String period;
    private String status;

    public LaunchPoolDTO(String exchange, String launchPool, Map<String, String> pools, String period, String status) {
        this.exchange = exchange;
        this.launchPool = launchPool;
        this.pools = pools;
        this.period = period;
        this.status = status;
    }

    public String getExchange() {
        return exchange;
    }

    public String getLaunchPool() {
        return launchPool;
    }

    public Map<String, String> getPools() {
        return pools;
    }

    public String getPeriod() {
        return period;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "" +
                "exchange='" + exchange + '\'' +
                ", launchPool='" + launchPool + '\'' +
                ", pools=" + pools +
                ", period='" + period + '\'' +
                ", status='" + status + '\''
                ;
    }
}
