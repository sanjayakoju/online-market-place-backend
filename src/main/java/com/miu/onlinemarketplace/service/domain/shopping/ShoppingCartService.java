package com.miu.onlinemarketplace.service.domain.shopping;

import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;

import java.util.List;


public interface ShoppingCartService {

    //get
    public List<ShoppingCartDto> getCartItems();

    //post change qty from cart page
    boolean addQty(Long productId, Integer quantity);

    //put add product from product page
    boolean add(Long productId, Integer quantity);

    //remove Product
    boolean remove(Long productId);

    boolean removeProduct();

}
