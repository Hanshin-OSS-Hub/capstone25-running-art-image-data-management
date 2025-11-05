package com.aetheriadm.jwt.interfaces;

import com.aetheriadm.jwt.domain.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/auth")
public class AuthController {

    @GetMapping
    public ResponseEntity<?> check(@AuthUser Long runnerId) {
        return ResponseEntity.ok(runnerId);
    }
}
