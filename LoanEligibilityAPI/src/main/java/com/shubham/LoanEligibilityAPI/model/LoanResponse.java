package com.shubham.LoanEligibilityAPI.model;

import lombok.Data;
import java.util.Map;

@Data
public class LoanResponse {
    private boolean eligible;
    private double approvedLoanAmount;
    private Map<String, Double> emiBreakdown; // EMI values for different interest rates
    private String reason; // Reason for ineligibility

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public double getApprovedLoanAmount() {
        return approvedLoanAmount;
    }

    public void setApprovedLoanAmount(double approvedLoanAmount) {
        this.approvedLoanAmount = approvedLoanAmount;
    }

    public Map<String, Double> getEmiBreakdown() {
        return emiBreakdown;
    }

    public void setEmiBreakdown(Map<String, Double> emiBreakdown) {
        this.emiBreakdown = emiBreakdown;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}