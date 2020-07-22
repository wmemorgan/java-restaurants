package com.lambdaschool.restaurants.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the restaurants table
 * Restaurant is considered the parent entity of all - the Grand Poobah!
 */

@Entity
@Table(name = "restaurants")
public class Restaurant
    extends Auditable
{
    /**
     * The primary key (long) of the restaurants table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long restaurantid;

    /**
     * The restaurant name (String). Cannot be null and must be unique
     */
    @Column(unique = true,
        nullable = false)
    private String name;

    /**
     * The address (String) of the restaurant
     */
    private String address;

    /**
     * The city (String) of the restaurant
     */
    private String city;

    /**
     * The state (String) of the restaurant. Two letter abbreviation
     */
    @Size(min = 2,
        max = 2,
        message = "State is the two letter abbreviation")
    private String state;

    /**
     * The telephone (String) of the restaurant
     */
    private String telephone;

    /**
     * Part of the join relationship between restaurants and payment types
     * connects restaurants to the restaurant payments combination
     */
    @OneToMany(mappedBy = "restaurant",
        cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "restaurant",
        allowSetters = true)
    private Set<RestaurantPayments> payments = new HashSet<>();

    /**
     * The list of menu items for this restaurant
     */
    @OneToMany(mappedBy = "restaurant",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "restaurant",
        allowSetters = true)
    private List<Menu> menus = new ArrayList<>();

    /**
     * Default constructor for restaurants. Required by the JPA
     */
    public Restaurant()
    {
    }

    /**
     * Given the params, create a new restaurant object
     * <p>
     * restaurantid is autogenerated
     *
     * @param name      The name (String) of the restaurant
     * @param address   The address (String) of the restaurant
     * @param city      The city (String) where the restaurant is located
     * @param state     The two letter abbreviation of the state (String) where the restaurant is located
     * @param telephone The telephone number (String) for the restaurant
     */
    public Restaurant(
        String name,
        String address,
        String city,
        String state,
        String telephone)
    {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.telephone = telephone;
    }

    /**
     * Getter for restaurant id
     *
     * @return the restaurant id (long) for this restaurant
     */
    public long getRestaurantid()
    {
        return restaurantid;
    }

    /**
     * Setter for restaurant id
     *
     * @param restaurantid the new restaurant id (long) for this restaurant
     */
    public void setRestaurantid(long restaurantid)
    {
        this.restaurantid = restaurantid;
    }

    /**
     * Getter for the name of this restaurant
     *
     * @return the name (String) of this restaurant
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for the name of this restaurant
     *
     * @param name the new name (String) of this restaurant
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Getter for the address of this restaurant
     *
     * @return the address (String) of this restaurant
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Setter for the address of this restaurant
     *
     * @param address the new address (String) of this restaurant
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Getter for the city of this restaurant
     *
     * @return the city (String) of this restaurant
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Setter for the city of this restaurant
     *
     * @param city the new city (String) of this restaurant
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Getter for the state of this restaurant
     *
     * @return the state (String) of this restaurant - two character abbreviation
     */
    public String getState()
    {
        return state;
    }

    /**
     * Setter for the state of this restaurant
     *
     * @param state the new state (String) of this restaurant - two character abbreviation
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Getter for the telephone of this restaurant
     *
     * @return the telephone (String) of this restaurant
     */
    public String getTelephone()
    {
        return telephone;
    }

    /**
     * Setter for the telephone of this restaurant
     *
     * @param telephone the new telephone (String) of this restaurant
     */
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    /**
     * Getter for the list of menu items for this restaurant
     *
     * @return the list of menu items for this restaurant
     */
    public List<Menu> getMenus()
    {
        return menus;
    }

    /**
     * Setter for the list of menu items for this restaurant
     *
     * @param menus Setter for the new list of menu items for this restaurant
     */
    public void setMenus(List<Menu> menus)
    {
        this.menus = menus;
    }

    /**
     * Getter for the list of payments for this restaurant
     *
     * @return the list of payments (Restaurant Payment Combination) for this restaurant
     */
    public Set<RestaurantPayments> getPayments()
    {
        return payments;
    }

    /**
     * Setter for the list of payment for this restaurant
     *
     * @param restaurantPayments the new list of payments (Restaurant Payment Combination) for this restaurant
     */
    public void setPayments(Set<RestaurantPayments> restaurantPayments)
    {
        this.payments = restaurantPayments;
    }
}