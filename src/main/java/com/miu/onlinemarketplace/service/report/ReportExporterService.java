package com.miu.onlinemarketplace.service.report;

import com.miu.onlinemarketplace.common.enums.ReportType;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Map;

public interface ReportExporterService {
    byte[] exportToPdf(ReportType reportType, Map<String, Object> data, JRDataSource listDataSource) throws IOException, JRException;

    byte[] exportToHtml(ReportType reportType, Map<String, Object> data, JRDataSource listDataSource) throws IOException, JRException;
}
