package com.example.gameflixbackend.usermanagement.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents all information tied to a gameflix user.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    public String username;

    @Column(name = "password", nullable = false, length = 255)
    public String password;

    @Column(name = "isAdmin", nullable = false)
    public Boolean isAdmin = false;

    @Column(name = "isSubscribed", nullable = false)
    public Boolean isSubscribed = false;

    @Column(name = "street_address")
    public String streetAddress;

    @Column(name = "city_address")
    public String cityAddress;

    @Column(name = "zip_address")
    public String zipAddress;

    @Column(name = "phone_number")
    public String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "user_rentals_current", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "game_id")
    private List<Long> currentlyRentingIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_rentals_history", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "game_id")
    private List<Long> rentalHistoryIds = new ArrayList<>();

    @Column

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getZipAddress() {
        return zipAddress;
    }

    public void setZipAddress(String zipAddress) {
        this.zipAddress = zipAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Long> getCurrentlyRentingIds() {
        return currentlyRentingIds == null ? new ArrayList<>() : currentlyRentingIds;
    }

    public List<Long> getRentalHistoryIds() {
        return rentalHistoryIds == null ? new ArrayList<>() : rentalHistoryIds;
    }

    public void addRental(Long gameId) {
        this.currentlyRentingIds.add(gameId);
        this.rentalHistoryIds.add(gameId);
    }

    public void removeRental(Long gameId) {
        this.currentlyRentingIds.remove(gameId);
    }
}
