package com.shubham.LoanEligibilityAPI.service;

import com.shubham.LoanEligibilityAPI.model.LoanRequest;
import com.shubham.LoanEligibilityAPI.model.LoanResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoanEligibilityService {

    public LoanResponse evaluateLoanEligibility(LoanRequest loanRequest) {
        LoanResponse response = new LoanResponse();

        // Rule 1: Monthly income should be at least ₹30,000
        if (loanRequest.getMonthlyIncome() < 30000) {
            response.setEligible(false);
            response.setReason("Monthly income is below the required minimum.");
            return response;
        }

        // Rule 2: Existing loan obligations should not exceed 40% of monthly income
        double maxObligations = loanRequest.getMonthlyIncome() * 0.4;
        if (loanRequest.getExistingLoanObligations() > maxObligations) {
            response.setEligible(false);
            response.setReason("Existing loan obligations exceed 40% of monthly income.");
            return response;
        }

        // Rule 3: Credit score should be at least 700
        if (loanRequest.getCreditScore() < 700) {
            response.setEligible(false);
            response.setReason("Credit score is below the required minimum.");
            return response;
        }

        // Rule 4: Maximum loan amount cannot exceed 10x the monthly income
        double maxLoanAmount = loanRequest.getMonthlyIncome() * 10;
        if (loanRequest.getRequestedLoanAmount() > maxLoanAmount) {
            response.setEligible(false);
            response.setReason("Requested loan amount exceeds the allowable maximum.");
            return response;
        }

        // If all rules pass
        response.setEligible(true);
        response.setApprovedLoanAmount(loanRequest.getRequestedLoanAmount());

        // Calculate EMI breakdown for different interest rates
        Map<String, Double> emiBreakdown = calculateEmi(loanRequest.getRequestedLoanAmount(), 36); // 36 months
        response.setEmiBreakdown(emiBreakdown);

        return response;
    }

    private Map<String, Double> calculateEmi(double loanAmount, int months) {
        Map<String, Double> emiMap = new HashMap<>();
        double[] interestRates = {8, 10, 12};

        for (double rate : interestRates) {
            double monthlyRate = rate / 100 / 12;
            double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, months))
                    / (Math.pow(1 + monthlyRate, months) - 1);
            // Convert the rounded result to Double
            emiMap.put(rate + "%", Math.round(emi * 100.0) / 100.0); // Keep 2 decimal places
        }
        return emiMap;
    }
}