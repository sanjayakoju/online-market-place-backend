package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.AdminProductReportDto;
import com.miu.onlinemarketplace.common.dto.AdminVendorReportDto;
import com.miu.onlinemarketplace.common.dto.VendorProductReportDto;
import com.miu.onlinemarketplace.service.report.ReportDataRetrieveService;
import com.miu.onlinemarketplace.service.report.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportDataRetrieveService reportDataRetrieveService;

    public ReportController(ReportService reportService, ReportDataRetrieveService reportDataRetrieveService) {
        this.reportService = reportService;
        this.reportDataRetrieveService = reportDataRetrieveService;
    }

    private static ResponseEntity<byte[]> getResponseEntity(byte[] bytes, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileName + ".pdf").build());
        return new ResponseEntity<>(bytes, headers, 200);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/vendor/product/sales")
    @ResponseBody
    public List<VendorProductReportDto> getProductSalesReportVendor(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reportDataRetrieveService.productReportForVendor(fromDate, toDate);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/vendor/sales")
    @ResponseBody
    public List<AdminVendorReportDto> getVendorSalesReportAdmin(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reportDataRetrieveService.vendorReportForAdmin(fromDate, toDate);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/product/sales")
    @ResponseBody
    public List<AdminProductReportDto> getProductSalesReportAdmin(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reportDataRetrieveService.productReportForAdmin(fromDate, toDate);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/vendor/product/sales/pdf")
    public ResponseEntity<byte[]> getProductSalesReportVendorPdf(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        byte[] bytes = reportService.getProductSalesReportForVendor(fromDate, toDate);
//        byte[] bytes = reportService.getVendorSalesReport(fromDate, toDate);
        return getResponseEntity(bytes, fromDate.toString() + "_" + toDate.toString());
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/admin/vendor/sales/pdf")
    public ResponseEntity<byte[]> getVendorSalesReportAdminPdf(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        byte[] bytes = reportService.getVendorSalesReportForAdmin(fromDate, toDate);
        return getResponseEntity(bytes, fromDate.toString() + "_" + toDate.toString());
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/product/sales/pdf")
    public ResponseEntity<byte[]> getProductSalesReportAdminPdf(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        byte[] bytes = reportService.getProductSalesReportForAdmin(fromDate, toDate);
        return getResponseEntity(bytes, fromDate.toString() + "_" + toDate.toString());
    }
}
