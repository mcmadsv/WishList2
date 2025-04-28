package org.example.wishlist6.Module;

public class Wishitem {
    private int wishItemId;
    private String wishItemName;
    private String wishItemDescription;
    private String wishUrl; // <-- matcher kolonnen 'wish_url' i databasen
    private int wishlistId;

    public Wishitem(String wishItemName, String wishItemDescription, String wishUrl, int wishlistId) {
        this.wishItemName = wishItemName;
        this.wishItemDescription = wishItemDescription;
        this.wishUrl = wishUrl;
        this.wishlistId = wishlistId;

    }

    public Wishitem() {}

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getWishItemId() {
        return wishItemId;
    }

    public void setWishItemId(int wishItemId) {
        this.wishItemId = wishItemId;
    }

    public String getWishItemName() {
        return wishItemName;
    }

    public void setWishItemName(String wishItemName) {
        this.wishItemName = wishItemName;
    }

    public String getWishItemDescription() {
        return wishItemDescription;
    }

    public void setWishItemDescription(String wishItemDescription) {
        this.wishItemDescription = wishItemDescription;
    }


    public String getWishUrl() {
        return wishUrl;
    }

    public void setWishUrl(String wishUrl) {
        this.wishUrl = wishUrl;
    }
}
