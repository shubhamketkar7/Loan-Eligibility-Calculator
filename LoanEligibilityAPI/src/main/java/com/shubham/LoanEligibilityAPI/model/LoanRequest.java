package com.shubham.LoanEligibilityAPI.model;

import lombok.Data;

@Data
public class LoanRequest {
    private String userId;
    private double monthlyIncome;
    private double existingLoanObligations;
    private int creditScore;
    private double requestedLoanAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getExistingLoanObligations() {
        return existingLoanObligations;
    }

    public void setExistingLoanObligations(double existingLoanObligations) {
        this.existingLoanObligations = existingLoanObligations;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }

    public void setRequestedLoanAmount(double requestedLoanAmount) {
        this.requestedLoanAmount = requestedLoanAmount;
    }
}