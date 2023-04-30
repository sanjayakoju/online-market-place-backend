package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "Select oi from OrderItem oi where oi.order.orderId = :orderId")
    List<OrderItem> findAllOrderItemByOrderId(Long orderId);

    @Query(value = "SELECT oi from OrderItem oi JOIN Order o on o.orderId = oi.order.orderId Where oi.isCommissioned = false and o.orderStatus = 'DELIVERED'")
    List<OrderItem> findAllByIsCommissionedFalseAndStatusIdDelivered();

    // TODO - recheck this query
    @Query(value = "Select oi from OrderItem oi where oi.order.orderId = :orderId and oi.product.vendor.vendorId = :vendorId")
    List<OrderItem> findAllOrderItemByOrderIdAndVendorId(Long orderId, Long vendorId);

}
