package org.example.wishlist6.Module;

public class Wishlist {
    private int wishListID;
    private String wishListName;
    private int userId;

    public Wishlist(String wishListName, int wishListID, int userId) {
        this.wishListName = wishListName;
        this.wishListID = wishListID;
        this.userId = userId;
    }
    public Wishlist(){

    }

    public int getWishListID() {
        return wishListID;
    }
    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public String getWishListName() {
        return wishListName;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
