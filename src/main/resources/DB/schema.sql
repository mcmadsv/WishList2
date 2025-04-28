CREATE DATABASE Wishlist6;
USE Wishlist6;

CREATE TABLE userlist (
                           user_id INT auto_increment primary key,
                           user_name VARCHAR(255) NOT NULL,
                           user_email VARCHAR(255) UNIQUE NOT NULL,
                           user_password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist (
                          wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
                          wishlist_name VARCHAR(255) NOT NULL,
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES userlist(user_id) ON DELETE CASCADE
);
CREATE TABLE wish (
                      wish_id INT AUTO_INCREMENT PRIMARY KEY,
                      wish_name VARCHAR(255),
                      wish_description TEXT,
                      wish_price DOUBLE,
                      wish_url VARCHAR(255),
                      wishlist_id INT,
                      FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id) ON DELETE CASCADE
);




/* NOTE: HAR KUN TILFØJET NEDENUNDER VÆRDI FOR AT KUNNE TESTE - MAX
ALTER TABLE wishlist
    MODIFY user_id INT NULL;