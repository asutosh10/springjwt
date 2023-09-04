package com.alsaher.springjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greet")
public class GreetingController {
  @GetMapping("/hello")
  public ResponseEntity<String> sayHello(){
    return ResponseEntity.ok("Hello from secure endpoint");
  }
}
