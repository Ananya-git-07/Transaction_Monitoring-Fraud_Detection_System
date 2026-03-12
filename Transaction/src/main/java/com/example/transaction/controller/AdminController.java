package com.example.transaction.controller;


import com.example.transaction.entity.FraudAlert;
import com.example.transaction.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "4. Admin Dashboard", description = "Endpoints for Compliance Officers to review fraud alerts. REQUIRES ADMIN ROLE.")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/alerts")
    @Operation(summary = "Get unreviewed fraud alerts", description = "Returns a list of all fraud alerts that need manual review")
    public ResponseEntity<List<FraudAlert>> getPendingAlerts() {
        return ResponseEntity.ok(adminService.getUnreviewedAlerts());
    }

    @PutMapping("/alerts/{alertId}/review")
    @Operation(summary = "Mark alert as reviewed", description = "Updates the status of a fraud alert to reviewed")
    public ResponseEntity<FraudAlert> reviewAlert(
            @PathVariable Long alertId,
            Authentication authentication
    ) {
        String adminEmail = authentication.getName();
        FraudAlert updatedAlert = adminService.markAlertAsReviewed(alertId, adminEmail);
        return ResponseEntity.ok(updatedAlert);
    }
}