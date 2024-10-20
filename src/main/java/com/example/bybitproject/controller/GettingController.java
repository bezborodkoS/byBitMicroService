package com.example.bybitproject.controller;

import com.example.bybitproject.model.LaunchPool;
import com.example.bybitproject.service.LaunchPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/get/")
public class GettingController {
    private final LaunchPoolService launchPoolService;

    public GettingController(LaunchPoolService launchPoolService) {
        this.launchPoolService = launchPoolService;
    }

    @GetMapping("/activeLaunchPool")
    public ResponseEntity<String> getActiveLaunchPools(){
        return new ResponseEntity<>(launchPoolService.getLaunchPoolsActive().toString(), HttpStatus.OK);
    }

    @GetMapping("/startSoonLaunchPool")
    public ResponseEntity<String> getStartSoonLaunchPools(){

        return new ResponseEntity<>(launchPoolService.getLaunchPoolsStartSoon().toString(), HttpStatus.OK);
    }

}
