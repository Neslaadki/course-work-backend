package com.example.courseworkbackend.services;

import com.example.courseworkbackend.entities.ClassType;
import com.example.courseworkbackend.entities.Country;
import com.example.courseworkbackend.entities.Position;
import com.example.courseworkbackend.entities.Types;
import com.example.courseworkbackend.entities.dao.responses.CitiesR;
import com.example.courseworkbackend.entities.dao.responses.PositionR;
import com.example.courseworkbackend.entities.dao.responses.TypesR;
import com.example.courseworkbackend.repositories.CountryRepository;
import com.example.courseworkbackend.repositories.PositionRepository;
import com.example.courseworkbackend.repositories.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentLoaderService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TypesRepository typesRepository;

    public List<CitiesR> getCountriesMap() {
        List<Country> list = countryRepository.findAll();
        List<CitiesR> listN = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Country country : list) {
                listN.add(
                        new CitiesR()
                                .setName(country.getName())
                                .setId(country.getId_country()));
            }
        }
        return listN;
    }

    public List<PositionR> getPositionsMap() {
        List<Position> list = positionRepository.findAll();
        List<PositionR> listN = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Position position : list) {
                listN.add(
                        new PositionR()
                                .setPosition_id(position.getPosition_id())
                                .setPosition_name(position.getPosition_name()));
            }
        }
        return listN;
    }

    public List<TypesR> getTypesMap(String type) {
        List<Types> list = typesRepository.findAll();
        List<TypesR> listN = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Types types : list) {
                if (types.getClass_type().equals(ClassType.getClassType(type))) {
                    listN.add(
                            new TypesR()
                                    .setTypeId(types.getId_type())
                                    .setName(types.getName())
                                    .setDescription(types.getDescription()));
                }
            }

        }
        return listN;
    }


}
