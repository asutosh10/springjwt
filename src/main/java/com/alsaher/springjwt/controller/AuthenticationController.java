package com.alsaher.springjwt.controller;

import com.alsaher.springjwt.dao.AuthenticationRequest;
import com.alsaher.springjwt.dao.AuthenticationResponse;
import com.alsaher.springjwt.dao.AuthenticationService;
import com.alsaher.springjwt.dao.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest ){
    return ResponseEntity.ok(authenticationService.register(registerRequest));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest ){
    return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
  }
}
