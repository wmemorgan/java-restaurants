package com.lambdaschool.restaurants.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Objects;

/**
 * The entity allowing interaction with the restaurantpayments table.
 * The join table between restaurants and payments.
 * <br>
 * Table enforces a unique constraint fo the combination of restaurantid and paymentid.
 * These two together form the primary key.
 */
@Entity
@Table(name = "restaurantpayments",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurantid", "paymentid"})})
public class RestaurantPayments
    extends Auditable
    implements Serializable
{
    /**
     * 1/2 of the primary key (long) for restaurantpayments.
     * Also is a foreign key into the restaurants table
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "restaurantid")
    @JsonIgnoreProperties(value = "payments",
        allowSetters = true)
    private Restaurant restaurant;

    /**
     * 1/2 of the primary key (long) for restaurantpayments.
     * Also is a foreign key into the payments table
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "paymentid")
    @JsonIgnoreProperties(value = "restaurants",
        allowSetters = true)
    private Payment payment;

    /**
     * Default constructor required by JPA
     */
    public RestaurantPayments()
    {
    }

    /**
     * Given a restaurant and payment object, create a join between them
     *
     * @param restaurant The restaurant of this relationship
     * @param payment    The payment of this relationship
     */
    public RestaurantPayments(
        Restaurant restaurant,
        Payment payment)
    {
        this.restaurant = restaurant;
        this.payment = payment;
    }

    /**
     * Getter for the restaurant of this restaurant payment relationship
     *
     * @return the restaurant of this restaurant payment relationship
     */
    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    /**
     * Setter for the restaurant of this restaurant payment relationship
     *
     * @param restaurant the new restaurant of this restaurant payment relationship
     */
    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    /**
     * Getter for the payment of this restaurant payment relationship
     *
     * @return the payment of this restaurant payment relationship
     */
    public Payment getPayment()
    {
        return payment;
    }

    /**
     * Setter for the payment of this restaurant payment relationship
     *
     * @param payment the new payment of this restaurant payment relationship
     */
    public void setPayment(Payment payment)
    {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof RestaurantPayments))
        {
            return false;
        }
        RestaurantPayments that = (RestaurantPayments) o;
        return Objects.equals(getRestaurant(), that.getRestaurant()) && Objects.equals(getPayment(), that.getPayment());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getRestaurant(), getPayment());
    }
}
