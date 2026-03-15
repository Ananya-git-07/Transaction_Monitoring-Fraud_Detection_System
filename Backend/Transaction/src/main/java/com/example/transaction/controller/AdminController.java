package com.example.transaction.controller;

import com.example.transaction.entity.FraudAlert;
import com.example.transaction.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/alerts")
    public ResponseEntity<List<FraudAlert>> getPendingAlerts() {
        return ResponseEntity.ok(adminService.getUnreviewedAlerts());
    }

    @PutMapping("/alerts/{alertId}/review")
    public ResponseEntity<FraudAlert> reviewAlert(
            @PathVariable Long alertId,
            Authentication authentication
    ) {
        String adminEmail = authentication.getName();
        FraudAlert updatedAlert = adminService.markAlertAsReviewed(alertId, adminEmail);
        return ResponseEntity.ok(updatedAlert);
    }
}