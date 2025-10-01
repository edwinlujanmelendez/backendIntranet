/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.movilbus.intranet.beans;

import java.io.Serializable;

/**
 *
 * @author VIC
 */
public class ResponseNiubiz implements Serializable {

	private static final long serialVersionUID = 1L;

	private String purchaseNumber;
        private double amount;
        private String currency;
        private String externalTransactionId;
        private double authorizedAmount;
        private String authorizationCode;
        private String actionCode;
        private String status;
        private String traceNumber;
        private String transactionDate;
        private String transactionId;

    public ResponseNiubiz() {
    }

        
    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public double getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(double authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTraceNumber() {
        return traceNumber;
    }

    public void setTraceNumber(String traceNumber) {
        this.traceNumber = traceNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
        
        
    
}
