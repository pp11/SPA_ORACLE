package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductPriceInfoModel {
    private  Long id;
    private String productCode;
    private String productName;
    private String productNameBengali;
    private String packSize;
    private LocalDate PriceDate;
    private Double unitTp;
    private Double unitVat;
    private Double mrp;
    private Double employeePrice;
    private Double specialPrice;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;



}
