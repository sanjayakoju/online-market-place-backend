package com.miu.onlinemarketplace.service.report;

import java.time.LocalDate;

public interface ReportService {

    byte[] getProductSalesReportForVendor(LocalDate fromDate, LocalDate toDate);

    byte[] getProductSalesReportForAdmin(LocalDate fromDate, LocalDate toDate);

    byte[] getVendorSalesReportForAdmin(LocalDate fromDate, LocalDate toDate);

//    byte[] getVendorSalesReport(LocalDate fromDate, LocalDate toDate);
}
