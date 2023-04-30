package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT O FROM Order O WHERE O.orderId = ?1 and O.orderCode = ?2")
    Order findByIdAndOrderCode(Long orderId, String orderCode);
    Order findByOrderCode(String orderCode);

    Page<Order> findAllByUser_UserId(Long userId, Pageable pageable);

    //order list, items [] // only vendor vendors

//    @Query("SELECT COUNT(DISTINCT oi.order) FROM OrderItem oi "
//            + "JOIN oi.order o "
//            + "JOIN oi.product p "
//            + "WHERE p.vendor.vendorId = :vendorId")
//    Long countOrdersByVendorId(@Param("vendorId") Long vendorId);

    @Query("SELECT DISTINCT o FROM Order o " +
            "INNER JOIN OrderItem oi on oi.order.orderId = o.orderId " +
            "INNER JOIN Product p on p.productId = oi.product.productId " +
            "WHERE p.vendor.vendorId = :vendorId")
    Page<Order> findOrdersByVendorId(@Param("vendorId") Long vendorId, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o " +
            "INNER JOIN OrderItem oi on oi.order.orderId = o.orderId " +
            "INNER JOIN Product p on p.productId = oi.product.productId " +
            "WHERE p.vendor.vendorId = :vendorId and o.orderId = :orderId")
    Page<Order> findOrdersByVendorIdAndOrderId(@Param("vendorId") Long vendorId, @Param("orderId") Long orderId, Pageable pageable);

}
