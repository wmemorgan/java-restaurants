package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import com.lambdaschool.restaurants.repos.PaymentRepository;
import com.lambdaschool.restaurants.repos.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the restaurant service interface
 */
@Transactional
@Service(value = "restaurantService")
public class RestaurantServiceImpl
        implements RestaurantService
{
    /**
     * Connects this service to the restaurants table
     */
    @Autowired
    private RestaurantRepository restrepos;

    /**
     * Connects this service to the payments table
     */
    @Autowired
    private PaymentRepository payrepos;

    /**
     * Connects this service to the auditing service in order to get current user name
     */
    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Restaurant> findRestaurantByNameLike(String name)
    {
        List<Restaurant> list = restrepos.findByNameContainingIgnoreCase(name);
        return list;
    }

    @Override
    public List<Restaurant> findNameCity(
            String name,
            String city)
    {
        List<Restaurant> list = restrepos.findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(name, city);
        return list;
    }

    @Override
    public List<Restaurant> findAll()
    {
        List<Restaurant> list = new ArrayList<>();
        restrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Restaurant findRestaurantById(long id) throws
            EntityNotFoundException
    {
        return restrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant id " + id + " not found!"));
    }

    @Override
    public Restaurant findRestaurantByName(String name) throws
            EntityNotFoundException
    {
        Restaurant restaurant = restrepos.findByName(name);

        if (restaurant == null)
        {
            throw new EntityNotFoundException("Restaurant " + name + " not found!");
        }

        return restaurant;
    }

    @Override
    public void delete(long id)
    {
        if (restrepos.findById(id)
                .isPresent())
        {
            restrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Restaurant id " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant)
    {
        Restaurant newRestaurant = new Restaurant();

        if (restaurant.getRestaurantid() != 0)
        {
            Restaurant oldRestaurant = restrepos.findById(restaurant.getRestaurantid())
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant id " + restaurant.getRestaurantid() + " not found!"));

            newRestaurant.setRestaurantid(restaurant.getRestaurantid());
        }

        newRestaurant.setName(restaurant.getName());
        newRestaurant.setAddress(restaurant.getAddress());
        newRestaurant.setCity(restaurant.getCity());
        newRestaurant.setState(restaurant.getState());
        newRestaurant.setTelephone(restaurant.getTelephone());

        newRestaurant.getPayments()
                .clear();
        for (RestaurantPayments rp : restaurant.getPayments())
        {
            Payment newPayment = payrepos.findById(rp.getPayment()
                                                           .getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment id " + rp.getPayment()
                            .getPaymentid() + " not found!"));

            newRestaurant.getPayments()
                    .add(new RestaurantPayments(newRestaurant, newPayment));
        }

        newRestaurant.getMenus()
                .clear();
        for (Menu m : restaurant.getMenus())
        {
            newRestaurant.getMenus()
                    .add(new Menu(m.getDish(), m.getPrice(), newRestaurant));
        }

        return restrepos.save(newRestaurant);
    }

    @Transactional
    @Override
    public Restaurant update(
            Restaurant restaurant,
            long id)
    {
        Restaurant currentRestaurant = restrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant " + id + " not found"));

        if (restaurant.getName() != null)
        {
            currentRestaurant.setName(restaurant.getName());
        }

        if (restaurant.getAddress() != null)
        {
            currentRestaurant.setAddress(restaurant.getAddress());
        }

        if (restaurant.getCity() != null)
        {
            currentRestaurant.setCity(restaurant.getCity());
        }

        if (restaurant.getState() != null)
        {
            currentRestaurant.setState(restaurant.getState());
        }

        if (restaurant.getTelephone() != null)
        {
            currentRestaurant.setTelephone(restaurant.getTelephone());
        }

        if (restaurant.getPayments()
                .size() > 0)
        {
            for (RestaurantPayments rp : restaurant.getPayments())
            {
                Payment newPayment = payrepos.findById(rp.getPayment()
                                                               .getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment id " + rp.getPayment()
                                .getPaymentid() + " not found!"));

                currentRestaurant.getPayments()
                        .add(new RestaurantPayments(currentRestaurant, newPayment));
            }
        }

        if (restaurant.getMenus()
                .size() > 0)
        {
            currentRestaurant.getMenus()
                    .clear();
            for (Menu m : restaurant.getMenus())
            {
                currentRestaurant.getMenus()
                        .add(new Menu(m.getDish(), m.getPrice(), currentRestaurant));
            }
        }

        return restrepos.save(currentRestaurant);
    }
}
