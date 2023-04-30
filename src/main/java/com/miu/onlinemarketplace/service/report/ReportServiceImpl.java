package com.miu.onlinemarketplace.service.report;

import com.miu.onlinemarketplace.common.dto.AdminProductReportDto;
import com.miu.onlinemarketplace.common.dto.AdminVendorReportDto;
import com.miu.onlinemarketplace.common.dto.VendorProductReportDto;
import com.miu.onlinemarketplace.common.enums.ReportType;
import com.miu.onlinemarketplace.exception.JasperTemplateNotFound;
import com.miu.onlinemarketplace.exception.JasperTemplateProcessingException;
import com.miu.onlinemarketplace.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportExporterService reportExporterService;
    private final ReportDataRetrieveService reportService;

    public ReportServiceImpl(ReportExporterService reportExporterService, ReportDataRetrieveService reportService) {
        this.reportExporterService = reportExporterService;
        this.reportService = reportService;
    }

    @Override
    public byte[] getProductSalesReportForVendor(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> data = new HashMap<>();
        data.put("createdBy", UserUtils.getCurrentUsername());
        data.put("fromDate", fromDate.toString());
        data.put("toDate", toDate.toString());
        List<VendorProductReportDto> dataList = reportService.productReportForVendor(fromDate, toDate);
        JRDataSource listDataSource = new JRBeanCollectionDataSource(dataList);
        try {
            return reportExporterService.exportToPdf(ReportType.PRODUCT_SALES_REPORT_FOR_VENDOR, data, listDataSource);
        } catch (IOException e) {
            log.error("Jasper template " + ReportType.PRODUCT_SALES_REPORT_FOR_VENDOR + " not found!");
            throw new JasperTemplateNotFound("Jasper template " + ReportType.PRODUCT_SALES_REPORT_FOR_VENDOR + " not found!");
        } catch (JRException e) {
            log.error("Jasper template processing error:" + e.getMessage());
            throw new JasperTemplateProcessingException("Jasper template \"vendor_report\" processing error!!");
        }
    }

    @Override
    public byte[] getProductSalesReportForAdmin(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> data = new HashMap<>();
        data.put("createdBy", UserUtils.getCurrentUsername());
        data.put("fromDate", fromDate.toString());
        data.put("toDate", toDate.toString());
        List<AdminProductReportDto> dataList = reportService.productReportForAdmin(fromDate, toDate);
        JRDataSource listDataSource = new JRBeanCollectionDataSource(dataList);
        try {
            return reportExporterService.exportToPdf(ReportType.PRODUCT_SALES_REPORT_FOR_ADMIN, data, listDataSource);
        } catch (IOException e) {
            log.error("Jasper template " + ReportType.PRODUCT_SALES_REPORT_FOR_ADMIN + " not found!");
            throw new JasperTemplateNotFound("Jasper template " + ReportType.PRODUCT_SALES_REPORT_FOR_ADMIN + " not found!");
        } catch (JRException e) {
            log.error("Jasper template processing error:" + e.getMessage());
            throw new JasperTemplateProcessingException("Jasper template \"vendor_report\" processing error!!");
        }
    }

    @Override
    public byte[] getVendorSalesReportForAdmin(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> data = new HashMap<>();
        data.put("createdBy", UserUtils.getCurrentUsername());
        data.put("fromDate", fromDate.toString());
        data.put("toDate", toDate.toString());
        List<AdminVendorReportDto> dataList = reportService.vendorReportForAdmin(fromDate, toDate);
        JRDataSource listDataSource = new JRBeanCollectionDataSource(dataList);
        try {
            return reportExporterService.exportToPdf(ReportType.VENDOR_SALES_REPORT_FOR_ADMIN, data, listDataSource);
        } catch (IOException e) {
            log.error("Jasper template " + ReportType.VENDOR_SALES_REPORT_FOR_ADMIN + " not found!");
            throw new JasperTemplateNotFound("Jasper template " + ReportType.VENDOR_SALES_REPORT_FOR_ADMIN + " not found!");
        } catch (JRException e) {
            log.error("Jasper template processing error:" + e.getMessage());
            throw new JasperTemplateProcessingException("Jasper template \"vendor_report\" processing error!!");
        }
    }
}
