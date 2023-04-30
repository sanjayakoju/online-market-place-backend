package com.miu.onlinemarketplace.service.report;


import com.miu.onlinemarketplace.common.dto.AdminProductReportDto;
import com.miu.onlinemarketplace.common.dto.AdminVendorReportDto;
import com.miu.onlinemarketplace.common.dto.VendorProductReportDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportDataRetrieveService {
    List<VendorProductReportDto> productReportForVendor(LocalDate fromDate, LocalDate toDate);

    List<AdminProductReportDto> productReportForAdmin(LocalDate fromDate, LocalDate toDate);

    List<AdminVendorReportDto> vendorReportForAdmin(LocalDate fromDate, LocalDate toDate);
}
