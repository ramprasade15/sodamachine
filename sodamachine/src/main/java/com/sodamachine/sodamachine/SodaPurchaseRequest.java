package com.sodamachine.sodamachine;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
public class SodaPurchaseRequest implements Serializable {
    private Double sodaPrice;
    @NotNull(message ="please insert Quarters or  1 dollar  or 5 dollar bills")
    private Double moneyInserted;
    private String sodaName;

}
