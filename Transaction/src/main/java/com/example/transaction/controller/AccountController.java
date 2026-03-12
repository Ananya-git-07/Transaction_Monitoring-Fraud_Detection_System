package com.example.transaction.controller;

import com.example.transaction.entity.Account;
import com.example.transaction.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "2. Account Management", description = "Endpoints for creating and viewing bank accounts")

@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;
    @PostMapping("/create")
    @Operation(summary = "Create a new bank account", description = "Creates a new account with a starting balance of $10,000 for the logged-in user")
    public ResponseEntity<Account> createAccount(
            Authentication authentication
    ) {
        String userEmail = authentication.getName();

        Account newAccount = accountService.createAccount(userEmail);
        return ResponseEntity.ok(newAccount);
    }
    @GetMapping("/my-accounts")
    @Operation(summary = "Get all accounts", description = "Returns a list of all accounts belonging to the logged-in user")
    public ResponseEntity<List<Account>> getMyAccounts(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(accountService.getUserAccounts(userEmail));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get specific account details", description = "Returns balance and details of a specific account ID")
    public ResponseEntity<Account> getAccountById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
}
