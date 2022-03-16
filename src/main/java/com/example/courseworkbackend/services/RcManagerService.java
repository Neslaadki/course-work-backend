package com.example.courseworkbackend.services;

import com.example.courseworkbackend.entities.RecyclingCenter;
import com.example.courseworkbackend.entities.dao.responses.RecyclingCenterR;
import com.example.courseworkbackend.repositories.CoordinateRepository;
import com.example.courseworkbackend.repositories.CountryRepository;
import com.example.courseworkbackend.repositories.RecyclingCenterRepository;
import com.example.courseworkbackend.repositories.TypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RcManagerService {

    @Autowired
    private RecyclingCenterRepository recyclingCenterRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CoordinateRepository coordinateRepository;
    @Autowired
    private TypesRepository typesRepository;

    public List<RecyclingCenterR> getRcList(Long id_country, Integer access) {
        List<RecyclingCenter> list = recyclingCenterRepository.getRecyclingCenterByCountry(
                countryRepository.getById(id_country));
        list = list.stream()
                .filter(e -> e.getAccess_level() <= access)
                .collect(Collectors.toList());
        List<RecyclingCenterR> answer = new ArrayList<>();
        for (RecyclingCenter recyclingCenter : list) {
            answer.add(new RecyclingCenterR()
                    .setCoordinateName("{" + recyclingCenter.getCoordinate().getLatitude() + " " + recyclingCenter.getCoordinate().getLongitude() + "}")
                    .setTypeName(recyclingCenter.getType().getName())
                    .setCountryName(recyclingCenter.getCountry().getName())
                    .setAccessLevel(recyclingCenter.getAccess_level()));

        }
        return answer;
    }

    public void addRc(Long id_coordinate, Long id_type, Long id_country, Integer accessLevel) {
        recyclingCenterRepository.save(
                new RecyclingCenter()
                        .setCoordinate(coordinateRepository.getById(id_coordinate))
                        .setType(typesRepository.getById(id_type))
                        .setCountry(countryRepository.getById(id_country))
                        .setAccess_level(accessLevel)
        );
    }


    public void deleteRc(Long id) {
        recyclingCenterRepository.deleteById(id);
    }


    public List<RecyclingCenterR> getRCListByEmployeeId(Long id) {
        List<RecyclingCenter> list = recyclingCenterRepository.getListRCByIdEmployeeAccessLevel(id);
        List<RecyclingCenterR> listN = new ArrayList<>();
        if (!list.isEmpty()) {
            for (RecyclingCenter recyclingCenter : list) {
                listN.add(
                        new RecyclingCenterR()
                                .setCoordinateName(recyclingCenter.getCoordinate().getLatitude()
                                        + ";" + recyclingCenter.getCoordinate().getLongitude())
                                .setCountryName(recyclingCenter.getCountry().getName())
                                .setTypeName(recyclingCenter.getType().getName())
                                .setAccessLevel(recyclingCenter.getAccess_level())
                );
            }
        }
        return listN;
    }

}
