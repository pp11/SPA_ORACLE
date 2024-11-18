package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductInfoModel {

    private Long id;

    private String productCode;
    private String productName;
    private String productNameBengali;
    private String packSize;
    private String groupCode;
    private String groupName;
    private String brandCode;
    private  String brandName;
    private String categoryCode;
    private  String categoryName;
    private String baseProductCode;
    private  String baseProductName;
    private Integer shipperQty;
    private String cpFlag;
    private Integer prodUnitSlno;
    private String packSizeGram;
    private String status;
    private Integer versionNo;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
