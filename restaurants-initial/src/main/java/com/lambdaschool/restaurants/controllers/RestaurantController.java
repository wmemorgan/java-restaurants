package com.lambdaschool.restaurants.controllers;

import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for clients to access restaurant data
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController
{
    @Autowired
    private RestaurantService restaurantService;

    /**
     * Returns a list of all restaurants
     * <br>Example: <a href="http://localhost:2019/restaurants/restaurants">http://localhost:2019/restaurants/restaurants</a>
      * @return
     */
    @GetMapping(value = "/restaurants",
        produces = {"application/json"})
    public ResponseEntity<?> listAllRestaurants()
    {
        List<Restaurant> myRestaurants = restaurantService.findAll();
        return new ResponseEntity<>(myRestaurants, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/name/{name}/city/{city}",
        produces = {"application/json"})
    public ResponseEntity<?> listRestaurantNameCity(
        @PathVariable
            String name,
        @PathVariable
            String city)
    {
        List<Restaurant> myRestaurants = restaurantService.findNameCity(name, city);
        return new ResponseEntity<>(myRestaurants, HttpStatus.OK);
    }


    @GetMapping(value = "/restaurants/namelike/{name}",
        produces = {"application/json"})
    public ResponseEntity<?> listRestaurantNameLike(
        @PathVariable
            String name)
    {
        List<Restaurant> myRestaurants = restaurantService.findRestaurantByNameLike(name);
        return new ResponseEntity<>(myRestaurants, HttpStatus.OK);
    }


    @GetMapping(value = "/restaurant/{restaurantId}",
        produces = {"application/json"})
    public ResponseEntity<?> getRestaurantById(
        @PathVariable
            Long restaurantId)
    {
        Restaurant r = restaurantService.findRestaurantById(restaurantId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @GetMapping(value = "/restaurant/name/{name}",
        produces = {"application/json"})
    public ResponseEntity<?> getRestaurantByName(
        @PathVariable
            String name)
    {
        Restaurant r = restaurantService.findRestaurantByName(name);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @PostMapping(value = "/restaurant",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> addNewRestaurant(
        @Valid
        @RequestBody
            Restaurant newRestaurant) throws
                                      URISyntaxException
    {
        newRestaurant = restaurantService.save(newRestaurant);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{restaurantid}")
            .buildAndExpand(newRestaurant.getRestaurantid())
            .toUri();
        responseHeaders.setLocation(newRestaurantURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(value = "/restaurant/{restaurantid}")
    public ResponseEntity<?> updateRestaurant(
        @RequestBody
            Restaurant updateRestaurant,
        @PathVariable
            long restaurantid)
    {
        restaurantService.update(updateRestaurant, restaurantid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/restaurant/{restaurantid}")
    public ResponseEntity<?> deleteRestaurantById(
        @PathVariable
            long restaurantid)
    {
        restaurantService.delete(restaurantid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

