package com.miu.onlinemarketplace.common.enums;

public enum ReportType {
    VENDOR_SALES_REPORT_FOR_ADMIN("vendor_sales_report_admin"),
    PRODUCT_SALES_REPORT_FOR_ADMIN("product_sales_report_admin"),
    PRODUCT_SALES_REPORT_FOR_VENDOR("product_sales_report_vendor"),
    ;
    private String name;

    ReportType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
