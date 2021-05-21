package com.sodamachine.sodamachine;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.util.List;

@Data
@Entity
public class Soda implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String sodaName;
    private Double sodaPrice;
    private Integer sodaCount;
    @NotNull
    private String sodaMachineId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SodaSoldDate> sodaSoldDateList;

}
