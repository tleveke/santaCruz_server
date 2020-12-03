package com.ynov.nantes.soap.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.nantes.soap.entity.Wishlist;
import com.ynov.nantes.soap.entity.User;
import com.ynov.nantes.soap.repository.UserRepository;
import com.ynov.nantes.soap.repository.WishlistRepository;

@RestController
public class WishlistController {

    private UserRepository userRepository;
    private WishlistRepository wishlistRepository;

    public WishlistController(UserRepository userRepository, WishlistRepository wishlistRepository) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping("/wishlists")
    List<Wishlist> getWishlists() {
        return this.wishlistRepository.findAll();
    }

    @GetMapping("/wishlists/user/{emailUser}")
    List<Wishlist> getWishlistsUser(@PathVariable String emailUser) {

        List<Wishlist> wishlists = this.wishlistRepository.findWishlistByUserEmail(emailUser);
        Collections.sort(wishlists, new Comparator<Wishlist>() {
            @Override
            public int compare(Wishlist o1, Wishlist o2) {
                return Float.compare(o2.getLatitude(), o1.getLatitude());
            }
        });
        return wishlists;
    }

    @GetMapping("/wishlists/user/distance/{emailUser}")
    float getDistanceWishlistsUser(@PathVariable String emailUser) {

        int caribou = 0;
        List<Wishlist> wishlists = this.wishlistRepository.findWishlistByUserEmail(emailUser);

        Collections.sort(wishlists, new Comparator<Wishlist>() {
            @Override
            public int compare(Wishlist o1, Wishlist o2) {
                return Float.compare(o2.getLatitude(), o1.getLatitude());
            }
        });
        
        for (int i = 0; i < wishlists.size() - 1; i++) {
            System.out.println("Calcul entre " + wishlists.get(i).getNom() + " et " + wishlists.get(i + 1).getNom()
                    + " Distance : " + distance(wishlists.get(i).getLatitude(), wishlists.get(i).getLongitude(),
                            wishlists.get(i + 1).getLatitude(), wishlists.get(i + 1).getLongitude(), "K"));
            caribou += caribou + distance(wishlists.get(i).getLatitude(), wishlists.get(i).getLongitude(),
                    wishlists.get(i + 1).getLatitude(), wishlists.get(i + 1).getLongitude(), "K");
        }
        System.out.println(caribou);

        return caribou;
    }

    @GetMapping("/wishlist/{id}")
    Wishlist getWishlistById(@PathVariable int id) {
        return this.wishlistRepository.findWishlistById(id);
    }

    @GetMapping("/wishlist/{designation}")
    Wishlist getWishlistByDesignation(@PathVariable String nom) {
        return this.wishlistRepository.findWishlistByNom(nom);
    }

    @PostMapping("/wishlist")
    Wishlist newWishlist(@RequestBody Wishlist wishlist) {
        return this.wishlistRepository.save(wishlist);
    }

    @PutMapping("/wishlist")
    List<Wishlist> editWishlist(@RequestBody Wishlist wishlist) {
        this.wishlistRepository.save(wishlist);
        return this.wishlistRepository.findWishlistByUserEmail(wishlist.getUser().getEmail());
    }

    @DeleteMapping("/wishlist/{id}")
    void rmWishlistById(@PathVariable int id) {
        this.wishlistRepository.deleteById(id);
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

}
