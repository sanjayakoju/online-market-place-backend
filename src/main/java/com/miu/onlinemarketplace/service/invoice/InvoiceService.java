package com.miu.onlinemarketplace.service.invoice;

import com.miu.onlinemarketplace.common.dto.InvoiceDto;

public interface InvoiceService {

    InvoiceDto generateInvoiceByOrderId(Long orderId);
}
