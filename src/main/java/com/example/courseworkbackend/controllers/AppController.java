package com.example.courseworkbackend.controllers;

import com.example.courseworkbackend.entities.Awakener;
import com.example.courseworkbackend.entities.Group;
import com.example.courseworkbackend.entities.dao.requests.AwakenerD;
import com.example.courseworkbackend.entities.dao.requests.AwakenerInGroupD;
import com.example.courseworkbackend.entities.dao.requests.GroupD;
import com.example.courseworkbackend.services.AwakenerService;
import com.example.courseworkbackend.services.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class AppController {

    @Autowired
    private AwakenerService awakenerService;

    @Autowired
    private CoordinatorService coordinatorService;

    private HashMap<String, String> responseMap;

    @PostMapping(value = "/addAwakener", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> userAuth(@RequestBody AwakenerD awakenerD){
        responseMap = new HashMap<>();
        boolean result = false;
        System.out.println();
        System.out.println("Добавление проходит пробужденный: " + awakenerD.getFirstName());
        if (awakenerService.addAwakener(awakenerD.getFirstName(), awakenerD.getLastName(), awakenerD.getBirthday(),
                awakenerD.getCountryId(), awakenerD.getId_guild(), awakenerD.getRank(), awakenerD.getExperience(),
                awakenerD.getAwakeTime())){
            result = true;
        }
        responseMap.put("result", Boolean.toString(result));
        return responseMap;
    }

    @DeleteMapping(value = "/deleteAwakener/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> userAuth(@PathVariable(value = "id") Long awakenerId) {
        responseMap = new HashMap<>();
        try {
            awakenerService.deleteAwakener(awakenerId);
            responseMap.put("result", "true");
            return responseMap;
        } catch (Exception e){
            responseMap.put("result", "false");
            return responseMap;
        }


    }

    @GetMapping(value = "/getAwakenerInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AwakenerD getAwakenerInfoById(@PathVariable(value = "id") Long awakenerId){
        responseMap = new HashMap<>();
        Awakener awakener = awakenerService.getInfoById(awakenerId);
        if (awakener != null){
            return new AwakenerD()
                    .setFirstName(awakener.getHuman().getFirstName())
                    .setLastName(awakener.getHuman().getLastName())
                    .setRank(awakener.getRank())
                    .setAwakeTime(awakener.getAwakeTime())
                    .setBirthday(awakener.getHuman().getBirthday())
                    .setCountryId(awakener.getHuman().getCountry().getId_country())
                    .setExperience(awakener.getExperience())
                    .setId_guild(awakener.getGuild().getId());
        }
        else return null;
    }



    @GetMapping(value = "/getAwakenerInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AwakenerD> getAwakenerList(@PathVariable(value = "id") Long countryId){
        List <Awakener> awakeners = awakenerService.getAwakenersByCountry(countryId);
        List <AwakenerD> response = new ArrayList<>();
        for (Awakener awakener : awakeners) {
            response.add(new AwakenerD()
                    .setFirstName(awakener.getHuman().getFirstName())
                    .setLastName(awakener.getHuman().getLastName())
                    .setRank(awakener.getRank())
                    .setAwakeTime(awakener.getAwakeTime())
                    .setBirthday(awakener.getHuman().getBirthday())
                    .setCountryId(awakener.getHuman().getCountry().getId_country())
                    .setExperience(awakener.getExperience())
                    .setId_guild(awakener.getGuild().getId()));
        }
        return response;
    }

    @PostMapping(value = "/createGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createGroup(@RequestBody GroupD groupD){
        responseMap = new HashMap<>();
        coordinatorService.addGroup(groupD.getAccessLevel());
        responseMap.put("result", "true");
        return responseMap;
    }


    @PostMapping(value = "/addAwakenerToGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addAwakenerToGroup(@RequestBody AwakenerInGroupD awakenerInGroupD){
        responseMap = new HashMap<>();
        coordinatorService.addAwakenerToGroup(awakenerInGroupD.getHuman_id(), awakenerInGroupD.getGroup_id(), new Timestamp(System.currentTimeMillis()));
        responseMap.put("result", "true");
        return responseMap;
    }

    @PostMapping(value = "/removeAwakenerFromGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> removeAwakenerFromGroup(@RequestBody AwakenerInGroupD awakenerInGroupD){
        responseMap = new HashMap<>();
        coordinatorService.removeAwakenerFromGroup(awakenerInGroupD.getHuman_id(), awakenerInGroupD.getGroup_id());
        responseMap.put("result", "true");
        return responseMap;
    }










}
