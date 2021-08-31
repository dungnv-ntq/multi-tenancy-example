package dung.example.multitenancy.controllers;

import dung.example.multitenancy.entities.City;
import dung.example.multitenancy.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/city")
    public List<City> getAll() {
        return this.cityRepository.findAll();
    }

    @GetMapping("/city/{id}")
    public City findById(@PathVariable Long id) {
        return this.cityRepository.findById(id)
                .orElseThrow();
    }
}
