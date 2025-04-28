package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishListRestController {

    @Autowired
    private WishListService wishListService;

    public WishListRestController() {}

    @PostMapping("/add")
    public String addWish(
            @RequestParam String wishlistName,
            @RequestParam String wishItemName,
            @RequestParam String wishItemDescription) {
        wishListService.addWish(wishlistName, wishItemName, wishItemDescription);
        return "Wish added successfully!";
    }

    @PutMapping("/wishlist/update/{id}")
    public ResponseEntity<Void> updateWishItem(@PathVariable int id, @RequestBody Wishitem wishItem) {
        wishListService.updateWish(id, wishItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


