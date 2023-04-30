package com.miu.onlinemarketplace.service.report;

import com.miu.onlinemarketplace.common.dto.AdminProductReportDto;
import com.miu.onlinemarketplace.common.dto.AdminVendorReportDto;
import com.miu.onlinemarketplace.common.dto.VendorProductReportDto;
import com.miu.onlinemarketplace.exception.AppSecurityException;
import com.miu.onlinemarketplace.repository.VendorRepository;
import com.miu.onlinemarketplace.utils.UserUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ReportDataRetrieveServiceImpl implements ReportDataRetrieveService {

    private final JdbcTemplate jdbcTemplate;
    private final VendorRepository vendorRepository;

    public ReportDataRetrieveServiceImpl(JdbcTemplate jdbcTemplate, VendorRepository vendorRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public List<VendorProductReportDto> productReportForVendor(LocalDate fromDate, LocalDate toDate) {
        Long vendorId = vendorRepository.findByUser_UserId(UserUtils.getCurrentUserId()).orElseThrow(() ->
                new AppSecurityException("User is not a vendor")).getVendorId();
        String sql = "SELECT p.name AS productName, " +
                "oi.price AS rate, " +
                "CAST(SUM(oi.quantity) AS DOUBLE) AS quantity, " +
                "(SUM(oi.quantity) * oi.price) AS subTotal, " +
                "SUM(oi.discount) AS discount, " +
                "SUM(oi.tax) AS tax, " +
                "(SUM(oi.quantity) * oi.price - SUM(oi.discount) + SUM(oi.tax)) AS total " +
                "FROM orders o JOIN order_item oi ON o.order_id = oi.order_id JOIN product p ON oi.product_id = p.product_id " +
                "WHERE p.vendor_id=? AND o.order_status = 'DELIVERED' AND o.order_date BETWEEN ? AND ? " +
                "GROUP BY p.product_id, oi.price, p.name;";
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql, vendorId, fromDate, toDate);
        AtomicInteger count = new AtomicInteger(1);
        return dataList.stream().map(mapObj ->
                new VendorProductReportDto(
                        count.getAndSet(count.get() + 1),
                        (String) mapObj.get("productName"),
                        (Double) mapObj.get("quantity"),
                        (Double) mapObj.get("rate"),
                        (Double) mapObj.get("subTotal"),
                        (Double) mapObj.get("discount"),
                        (Double) mapObj.get("tax"),
                        (Double) mapObj.get("total")
                )).collect(Collectors.toList());
    }

    @Override
    public List<AdminProductReportDto> productReportForAdmin(LocalDate fromDate, LocalDate toDate) {
        String sql = "SELECT p.name AS productName, " +
                "CAST(SUM(oi.quantity) AS DOUBLE) AS quantity, " +
                "(SUM(ac.vendor_commission) + SUM(ac.platform_commission)) AS totalRevenue, " +
                "(SUM(ac.vendor_commission) + SUM(ac.platform_commission))/SUM(oi.quantity) AS avgPrice, " +
                "SUM(ac.tax) AS tax, " +
                "(SUM(ac.platform_commission)) AS commission " +
                "FROM account_commission ac JOIN order_item oi ON ac.order_item_id = oi.order_item_id JOIN product p ON oi.product_id = p.product_id " +
                "WHERE ac.created_date BETWEEN ? AND ? " +
                "GROUP BY p.product_id, p.name;";
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql, fromDate, toDate);
        AtomicInteger count = new AtomicInteger(1);
        return dataList.stream().map(mapObj ->
                new AdminProductReportDto(
                        count.getAndSet(count.get() + 1),
                        (String) mapObj.get("productName"),
                        (Double) mapObj.get("quantity"),
                        (Double) mapObj.get("totalRevenue"),
                        (Double) mapObj.get("avgPrice"),
                        (Double) mapObj.get("commission"),
                        (Double) mapObj.get("tax")
                )).collect(Collectors.toList());
    }

    @Override
    public List<AdminVendorReportDto> vendorReportForAdmin(LocalDate fromDate, LocalDate toDate) {
        String sql = "SELECT v.vendor_name AS vendorName, " +
                "CAST(SUM(oi.quantity) AS DOUBLE) AS quantity, " +
                "(SUM(ac.vendor_commission) + SUM(ac.platform_commission)) AS totalRevenue, " +
                "SUM(ac.tax) AS tax, " +
                "(SUM(ac.platform_commission)) AS commission " +
                "FROM account_commission ac JOIN order_item oi ON ac.order_item_id = oi.order_item_id " +
                "JOIN product p ON oi.product_id = p.product_id JOIN vendor v on p.vendor_id = v.vendor_id " +
                "WHERE ac.created_date BETWEEN ? AND ? " +
                "GROUP BY v.vendor_id, v.vendor_name;";
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql, fromDate, toDate);
        AtomicInteger count = new AtomicInteger(1);
        return dataList.stream().map(mapObj ->
                new AdminVendorReportDto(
                        count.getAndSet(count.get() + 1),
                        (String) mapObj.get("vendorName"),
                        (Double) mapObj.get("quantity"),
                        (Double) mapObj.get("totalRevenue"),
                        (Double) mapObj.get("commission"),
                        (Double) mapObj.get("tax")
                )).collect(Collectors.toList());
    }
}
