package com.sodamachine.sodamachine;


import lombok.Data;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="soda_sold_date")
public class SodaSoldDate implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String sodaMachineId;
    @NotNull
    private String  sodaName;
    @NotNull
    private Date sodaSoldDate;

}
