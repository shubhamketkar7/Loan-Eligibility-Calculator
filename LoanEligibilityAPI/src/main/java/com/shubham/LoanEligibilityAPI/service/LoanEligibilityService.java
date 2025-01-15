package com.shubham.LoanEligibilityAPI.service;

import com.shubham.LoanEligibilityAPI.model.LoanRequest;
import com.shubham.LoanEligibilityAPI.model.LoanResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoanEligibilityService {

    private final List<LoanResponse> loanResponses = new ArrayList<>();

    public LoanResponse evaluateLoanEligibility(LoanRequest loanRequest) {
        LoanResponse response = new LoanResponse();

        // Rule 1: Monthly income should be at least ₹30,000
        if (loanRequest.getMonthlyIncome() < 30000) {
            response.setEligible(false);
            response.setReason("Monthly income is below the required minimum.");
            loanResponses.add(response);
            return response;
        }

        // Rule 2: Existing loan obligations should not exceed 40% of monthly income
        double maxObligations = loanRequest.getMonthlyIncome() * 0.4;
        if (loanRequest.getExistingLoanObligations() > maxObligations) {
            response.setEligible(false);
            response.setReason("Existing loan obligations exceed 40% of monthly income.");
            loanResponses.add(response);
            return response;
        }

        // Rule 3: Credit score should be at least 700
        if (loanRequest.getCreditScore() < 700) {
            response.setEligible(false);
            response.setReason("Credit score is below the required minimum.");
            loanResponses.add(response);
            return response;
        }

        // Rule 4: Maximum loan amount cannot exceed 10x the monthly income
        double maxLoanAmount = loanRequest.getMonthlyIncome() * 10;
        if (loanRequest.getRequestedLoanAmount() > maxLoanAmount) {
            response.setEligible(false);
            response.setReason("Requested loan amount exceeds the allowable maximum.");
            loanResponses.add(response);
            return response;
        }

        // If all rules pass
        response.setEligible(true);
        response.setApprovedLoanAmount(loanRequest.getRequestedLoanAmount());

        // Calculate EMI breakdown for different interest rates
        Map<String, Double> emiBreakdown = calculateEmi(loanRequest.getRequestedLoanAmount(), 36); // 36 months
        response.setEmiBreakdown(emiBreakdown);

        loanResponses.add(response);
        return response;
    }

    public Map<String, Object> getLoanStatistics() {
        double totalApprovedLoanAmount = 0;
        int approvedCount = 0;
        Map<String, Integer> rejectionReasons = new HashMap<>();

        for (LoanResponse response : loanResponses) {
            if (response.isEligible()) {
                totalApprovedLoanAmount += response.getApprovedLoanAmount();
                approvedCount++;
            } else {
                String reason = response.getReason();
                rejectionReasons.put(reason, rejectionReasons.getOrDefault(reason, 0) + 1);
            }
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("averageApprovedLoanAmount", approvedCount > 0 ? totalApprovedLoanAmount / approvedCount : 0);
        statistics.put("rejectionReasons", rejectionReasons);

        return statistics;
    }

    private Map<String, Double> calculateEmi(double loanAmount, int months) {
        Map<String, Double> emiMap = new HashMap<>();
        double[] interestRates = {8, 10, 12};

        for (double rate : interestRates) {
            double monthlyRate = rate / 100 / 12;
            double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, months))
                    / (Math.pow(1 + monthlyRate, months) - 1);
            emiMap.put(rate + "%", Math.round(emi * 100.0) / 100.0); // Round to 2 decimal places
        }
        return emiMap;
    }
}
