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
    @NotNull(message ="please insert exactly 8 Quarters")
    @Min(value= 8, message ="Please re-insert exactly 8 Quarters.")
    @Max(value= 8, message ="Please re-insert exactly 8 Quarters.")
    private Integer noOfQuarters;
    private String sodaName;

}
