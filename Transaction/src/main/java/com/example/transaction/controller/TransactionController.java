package com.example.transaction.controller;


import com.example.transaction.dto.TransactionRequest;
import com.example.transaction.entity.Transaction;
import com.example.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Tag(name = "3. Transactions", description = "Endpoints for transferring money and viewing history")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    // POST request to http://localhost:8080/transaction/transfer
    @PostMapping("/transfer")
    @Operation(summary = "Transfer money", description = "Transfers exact amounts between two accounts. Triggers Fraud Engine automatically.")
    public ResponseEntity<Transaction> transferMoney(
            @Valid @RequestBody TransactionRequest request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        Transaction processedTransaction = transactionService.processTransfer(request, userEmail);
        return ResponseEntity.ok(processedTransaction);
    }
    @GetMapping("/history/{accountId}")
    @Operation(summary = "Get transaction history", description = "Returns all transactions sent or received by a specific account")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @PathVariable Long accountId,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId, userEmail));
    }
}
