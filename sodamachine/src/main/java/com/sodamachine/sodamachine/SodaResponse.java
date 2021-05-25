package com.sodamachine.sodamachine;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SodaResponse implements Serializable {
    private String sodaName;
    private Double sodaPrice;
    private Integer sodaCount;
    @NotNull
    private String sodaMachineId;
}
