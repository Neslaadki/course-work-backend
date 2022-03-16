package com.example.courseworkbackend.controllers;


import com.example.courseworkbackend.entities.dao.responses.*;
import com.example.courseworkbackend.services.ComponentLoaderService;
import com.example.courseworkbackend.services.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController()
public class ComponentLoadController {

    @Autowired
    private ComponentLoaderService componentLoaderService;
    @Autowired
    private CoordinatorService coordinatorService;

    @GetMapping("/getCountryMap")
    public List<CitiesR> getCountryMap() {
        return componentLoaderService.getCountriesMap();
    }

    @GetMapping("/getPositionsMap")
    public List<PositionR> getPositionsMap() {
        return componentLoaderService.getPositionsMap();
    }

    @GetMapping("/getGroupsMap")
    public List<GroupR> getGroupsMap() {
        return coordinatorService.getGroupList();
    }

    @GetMapping("/getTypesMap/{type}")
    public List<TypesR> getTypesMap(@PathVariable(value = "type") String type) {
        return componentLoaderService.getTypesMap(type);
    }

    @GetMapping("/getRiftMap")
    public List<RiftR> getRiftsMap() {
        return componentLoaderService.getRiftsMap();
    }

    @GetMapping("/getAwakenersInGroupMap")
    public List<AwakenerInGroupR> getAwakenersInGroupMap() {
        return coordinatorService.getAwakenerInGroupList();
    }


}
