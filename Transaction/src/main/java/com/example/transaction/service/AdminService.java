package com.example.transaction.service;

import com.example.transaction.Repository.FraudAlertRepository;
import com.example.transaction.entity.FraudAlert;
import com.example.transaction.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final FraudAlertRepository fraudAlertRepository;
    private final AuditService auditService;

    public List<FraudAlert> getUnreviewedAlerts() {
        return fraudAlertRepository.findByReviewedFalse();
    }

    public FraudAlert markAlertAsReviewed(Long alertId, String adminEmail) {
        FraudAlert alert = fraudAlertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud alert not found"));
        alert.setReviewed(true);
        FraudAlert savedAlert = fraudAlertRepository.save(alert);
        auditService.logAction("FRAUD_REVIEWED_ID_" + alert.getId(), adminEmail);

        return savedAlert;
    }
}
