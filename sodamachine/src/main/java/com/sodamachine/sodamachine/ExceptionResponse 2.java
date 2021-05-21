package com.sodamachine.sodamachine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String status;
    private Date timeStamp;
    private List<String> errorMessage;

}
