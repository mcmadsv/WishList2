package org.example.wishlist6.Service;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
 public class WishListService {

    @Autowired
    public WishListRepository wishlistRepository;

    //Tilføler en wishlist
    public void addWishlist(Wishlist wishlist) {
        System.out.println("Saving Wishlist: " + wishlist.getWishListName() + " for User ID: " + wishlist.getUserId());

        wishlistRepository.addWishlist(wishlist);

        System.out.println("Wishlist saved successfully!");
    }

    //Tilføjer ønske til en wishlist
    public void addWishToWishlist(int wishlistId, Wishitem wish) {
        wish.setWishlistId(wishlistId);
        wishlistRepository.addWish(wish);
    }

    //Metode for Restcontroller
    public void addWish(String wishlistName, String wishItemName, String wishItemDescription) {
    }

    //Opdaterer wishlist
    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.updateWishlist(wishlist);
    }

    //Update-metode for wish i restcontroller
    public void updateWish(int id, Wishitem wishItem) {
    }

    //Sletter wishlist gennem id'et
    public void deleteWishlistById(int id) {
        wishlistRepository.deleteWishlistById(id);
    }

    //en ID Getter for Wishlist
    public Wishlist getWishlistById(int id) {
        return wishlistRepository.getWishlistById(id);
    }

    //en ID Getter for wishlist til at finde wish
    public List<Wishitem> getWishesByWishlistId(int wishlistId) {
        return wishlistRepository.getWishesByWishlistId(wishlistId);
    }
    //en ID getter for wish
    public Wishitem getWishById(int wishId) {
        return wishlistRepository.getWishById(wishId);
    }

    //en ID getter for at slette wish
    public void deleteWishById(int wishId) {
        wishlistRepository.deleteWishById(wishId);
    }

    //Opdaterer et wishs information
    public void updateWishItem(Wishitem wish) {
        wishlistRepository.updateWish(wish);
    }
    //en ID Getter for User til at finde wishlist
    public List<Wishlist> getWishlistsByUserId(Integer userId) {
        return wishlistRepository.getWishlistsByUserId(userId);
    }
}
