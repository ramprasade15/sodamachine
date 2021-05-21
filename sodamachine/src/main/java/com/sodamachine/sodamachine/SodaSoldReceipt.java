package com.sodamachine.sodamachine;

import lombok.Data;

import java.io.Serializable;

@Data
public class SodaSoldReceipt implements Serializable {
    private String SodaMachineId;
    private String transactionId;
    private String TransactionDate;
    private String SodaName;
    private Double InsertedAmount;
    private Double sodaPrice;
    private Integer ReturnQuarters;

}
