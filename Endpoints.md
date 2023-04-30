# Followings are the Endpoints for Shopping Cart

Shopping Carts Related Products
```
GET         /shoppping-cart                             Get Product List from Shopping Cart
            return List
            
POST        /shoppping-cart?productId=4&quantity=4      Add Quantity from Cart Page
            return boolean
            
PUT         /shoppping-cart?productId=4&quantity=4      Add Product to Shopping Cart from product/details page
            return boolean
            
DELETE      /shoppping-cart?productId=4                 Remove Product From Shopping Cart
            return boolean

```

// Must require role
// EnumRole currentUserRole = AppSecurityUtils.getCurrentUserRole()
// .orElseThrow(new AppSecurityException("Required Role, but not available"));

        // Optional role, either guest or user
        Optional<EnumRole> optionalEnumRole = AppSecurityUtils.getCurrentUserRole();
        if(optionalEnumRole.isPresent()) {
            EnumRole currentRole = optionalEnumRole.get();
        } else {
            // logic for guest
        }
    }

    // TODO user
    // from Order by user id 1
    // OrderDto: [ { orderNo, orderedDate, items: [] } , .....  ]

    // TODO vendor
    // from Order join orderItem, join product and product.vendorId = 3
    // VendorOrderResponseDto: [ { orderNo, orderDate, vendorItems: [] }  ]

    // TODO admin
    // from all order
    // OrderDto: [ { orderNo, orderedDate, items: [] } , .....  ]
