package com.shubham.LoanEligibilityAPI.controller;

import com.shubham.LoanEligibilityAPI.model.LoanRequest;
import com.shubham.LoanEligibilityAPI.model.LoanResponse;
import com.shubham.LoanEligibilityAPI.service.LoanEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanEligibilityService loanEligibilityService;

    @PostMapping("/eligibility")
    public ResponseEntity<LoanResponse> checkLoanEligibility(@RequestBody LoanRequest loanRequest) {
        LoanResponse response = loanEligibilityService.evaluateLoanEligibility(loanRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/loan-statistics")
    public ResponseEntity<Map<String, Object>> getLoanStatistics() {
        Map<String, Object> statistics = loanEligibilityService.getLoanStatistics();
        return ResponseEntity.ok(statistics);
    }
}
