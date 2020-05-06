package com.lambdaschool.restaurants.repos;

import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The CRUD repository connecting Restaurant to the rest of the application
 */
public interface RestaurantRepository
    extends CrudRepository<Restaurant, Long>
{
    /**
     * Find a restaurant based off over restaurant name
     *
     * @param name the name (String) of restaurant you seek
     * @return the first restaurant object with the name you seek
     */
    Restaurant findByName(String name);

    /**
     * Find all restaurants whose name contains a given substring ignoring case
     *
     * @param name the substring of the names (String) you seek
     * @return List of restaurants whose name contain the given substring ignoring case
     */
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    /**
     * Find all restaurants whose name contains a given substring and is located in cities containing a given substring ignoring case
     *
     * @param likename the substring of the names (String) you seek
     * @param likecity the substring of the cities (String) you seek
     * @return List of restaurants whose name contains a given substring and is located in cities containing a given substring ignoring case
     */
    List<Restaurant> findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(
        String likename,
        String likecity);

    /**
     * Counts the number of restaurant payment combinations for the given restaurantid and paymentid. Answer should be only 0 or 1.
     *
     * @param restaurantid The restaurantid of the restaurant of the restaurant payment combination to check
     * @param paymentid    The paymentid of the payment of the restaurant payment combination to check
     * @return A single number, a count
     */
    @Query(value = "SELECT COUNT(*) as count FROM restaurantpayments WHERE restaurantid = :restaurantid AND paymentid = :paymentid",
        nativeQuery = true)
    JustTheCount checkRestaurantPaymentCombo(
        long restaurantid,
        long paymentid);

    /**
     * Deletes the given restaurant, payment combination
     *
     * @param restaurantid The restaurant id of the restaurant of this restaurant payment combination
     * @param paymentid    The payment id of the payment of this restaurant payment combination
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM restaurantpayments WHERE restaurantid = :restaurantid AND paymentid = :paymentid",
        nativeQuery = true)
    void deleteRestaurantPaymentCombo(
        long restaurantid,
        long paymentid);

    /**
     * Inserts the new restaurant, payment combination
     *
     * @param uname        The username (String) of the user adding the record
     * @param restaurantid The restaurant id of the restaurant of this restaurant payment combination
     * @param paymentid    The payment id of the payment of this restaurant payment combination
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO restaurantpayments(restaurantid, paymentid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:restaurantid, :paymentid, :uname, CURRENT_TIMESTAMP, :uname, CURRENT_TIMESTAMP)",
        nativeQuery = true)
    void insertRestaurantPayment(
        String uname,
        long restaurantid,
        long paymentid);
}
