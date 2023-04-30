package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;
import com.miu.onlinemarketplace.service.domain.shopping.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shopping-cart")
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    //get
    @GetMapping()
    public ResponseEntity<?> getShoppingCartItems() {
//        Map<String, Object> res = Map.of("res", shoppingCartService.getCartItems());
        List<ShoppingCartDto> cartItems = shoppingCartService.getCartItems();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }


    // post
    @PostMapping()
    public ResponseEntity<?> addProductQtyToCart(@RequestParam Long productId, @RequestParam Integer quantity){
        Map<String, Object> res = Map.of("res", shoppingCartService.addQty(productId, quantity));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //put
    @PutMapping()
    public ResponseEntity<?> addProductToCart(@RequestParam Long productId, @RequestParam Integer quantity){
        boolean res = shoppingCartService.add(productId, quantity);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    //delete product item from shopping cart
    @DeleteMapping()
    public ResponseEntity<?> removeProductItemFromCart(@RequestParam Long productId){
        boolean res = shoppingCartService.remove(productId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //delete product from shopping cart
    @DeleteMapping("/clear")
    public ResponseEntity<?> removeProductFromCart(){
        boolean res = shoppingCartService.removeProduct();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
