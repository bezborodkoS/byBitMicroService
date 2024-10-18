package com.example.bybitproject.controller;

import com.example.bybitproject.service.LaunchPoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddStringLaunchPoolController {
    private final LaunchPoolService launchPoolService;

    public AddStringLaunchPoolController(LaunchPoolService launchPoolService) {
        this.launchPoolService = launchPoolService;
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveLaunchPoolString(@RequestBody String str){
        launchPoolService.saveLaunchPool(str);
        return new ResponseEntity<>("Save", HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateLaunchPoolString(){
        launchPoolService.updateNoActive();
        launchPoolService.updateActive();
        return new ResponseEntity<>("update", HttpStatus.CREATED);
    }
}
