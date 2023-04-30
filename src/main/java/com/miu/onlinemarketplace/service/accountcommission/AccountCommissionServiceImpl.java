package com.miu.onlinemarketplace.service.accountcommission;

import com.miu.onlinemarketplace.entities.AccountCommission;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.repository.AccountCommissionRepository;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountCommissionServiceImpl implements AccountCommissionService {

    private AccountCommissionRepository accountCommissionRepository;
    private OrderItemRepository orderItemRepository;

    public AccountCommissionServiceImpl(AccountCommissionRepository accountCommissionRepository, OrderItemRepository orderItemRepository) {
        this.accountCommissionRepository = accountCommissionRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public boolean saveCommission() {

       try {
           List<OrderItem> orderItem = orderItemRepository.findAllByIsCommissionedFalseAndStatusIdDelivered();
           if (orderItem.size() > 0) {
               List<AccountCommission> accountCommissions = new ArrayList<>();
               for (OrderItem o : orderItem) {
                   AccountCommission accountCommission = new AccountCommission();
                   accountCommission.setOrderItem(o);
                   accountCommission.setVendorCommission(o.getPrice() * o.getQuantity() * 0.80 - o.getDiscount());
                   accountCommission.setPlatformCommission(o.getPrice() * o.getQuantity() * 0.20 - o.getDiscount());
                   accountCommission.setTax(o.getTax());
                   accountCommissions.add(accountCommission);
                   o.setIsCommissioned(true);
                   orderItemRepository.save(o);

               }
               accountCommissionRepository.saveAll(accountCommissions);
               return true;
           }
       } catch (Exception ex) {
           System.out.println(ex);
       }

        return false;
    }

    @Override
    public Double getOrderDetail(Long orderId) {

        List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(orderId);
        for (OrderItem o : orderItemList) {

        }
        return null;
    }

}
