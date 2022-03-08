package com.example.courseworkbackend.services;

import com.example.courseworkbackend.entities.Awakener;
import com.example.courseworkbackend.entities.Human;
import com.example.courseworkbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AwakenerService {

    @Autowired
    private AwakenerRepository awakenerRepository;
    @Autowired
    private HumanRepository humanRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private EmployeeRepository employeeRepository;



    public boolean addAwakener(
            String firstName,
            String lastName,
            Timestamp birthday,
            Long id_country,
            Long id_guild,
            Integer rank,
            Integer experience,
            Timestamp awakeTime
    ){

        Human human = new Human()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBirthday(birthday)
                .setCountry(countryRepository.getById(id_country));

//        if (employeeRepository.findByHuman(human) != null)
//            return false;
//        else{
            humanRepository.save(human);
            awakenerRepository.save(
                    new Awakener()
                            .setHuman(human)
                            .setGuild(guildRepository.getById(id_guild))
                            .setRank(rank)
                            .setExperience(experience)
                            .setAwakeTime(awakeTime)
            );
            return true;
        }

//    }

    public boolean deleteAwakener(Long id){
        awakenerRepository.deleteById(id);
        //humanRepository.deleteById(id);
        return true;
    }

    public Awakener getInfoById(Long id){
        return awakenerRepository.getById(id);
    }

}
