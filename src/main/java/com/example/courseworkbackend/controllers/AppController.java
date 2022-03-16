package com.example.courseworkbackend.controllers;

import com.example.courseworkbackend.entities.Awakener;
import com.example.courseworkbackend.entities.Employee;
import com.example.courseworkbackend.entities.dao.requests.*;
import com.example.courseworkbackend.entities.dao.responses.*;
import com.example.courseworkbackend.services.*;
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

    @Autowired
    private RcManagerService rcManagerService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private EmployeeService employeeService;

    private HashMap<String, String> responseMap;


    /*
        Добавление координат в БД
     */

    @PostMapping(value = "/addCoordinate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addCoordinate(@RequestBody CoordinateD coordinateD) {
        responseMap = new HashMap<>();
        try {
            responseMap.put("id", employeeService.addCoordinate(coordinateD).toString());
        } catch (Exception e) {
            responseMap.put("id", null);
        }
        return responseMap;
    }

    /*
        Добавление нового материала
     */

    @PostMapping(value = "addMaterial", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addMaterials(@RequestBody MaterialD materialD) {
        responseMap = new HashMap<>();
        try {
            registerService.addMaterial(materialD);
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }
        return responseMap;
    }

    /*
        Добавление нового статуса разлому
     */

    @PostMapping(value = "addRiftStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addRiftStatus(@RequestBody RiftStatusD riftStatusD) {
        responseMap = new HashMap<>();
        try {
            registerService.addStatusRift(riftStatusD);
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }
        return responseMap;
    }


    @PostMapping(value = "/addAwakener", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> userAuth(@RequestBody AwakenerD awakenerD) {
        try {
            responseMap = new HashMap<>();
            boolean result = false;
            System.out.println();
            System.out.println("Добавление проходит пробужденный: " + awakenerD.getFirstName().trim());
            if (awakenerService.addAwakener(awakenerD.getFirstName().trim(), awakenerD.getLastName().trim(), awakenerD.getBirthday(),
                    awakenerD.getCountryId(), awakenerD.getGuildId(), awakenerD.getRank(), awakenerD.getExperience(),
                    awakenerD.getAwakeTime())) {
                result = true;
            }
            responseMap.put("result", Boolean.toString(result));
        } catch (IllegalArgumentException e1) {
            responseMap.put("result", "Некорректно заполнены поля");
        } catch (Exception e2) {
            responseMap.put("result", "false");
        }

        return responseMap;
    }

    @DeleteMapping(value = "/deleteAwakener/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> deleteAwakener(@PathVariable(value = "id") Long awakenerId) {
        responseMap = new HashMap<>();
        try {
            awakenerService.deleteAwakener(awakenerId);
            responseMap.put("result", "true");
            return responseMap;
        } catch (Exception e) {
            responseMap.put("result", "false");
            return responseMap;
        }


    }

    @GetMapping(value = "/getHumansInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HumanR> getHumansInfo(@PathVariable(value = "id") Long countryId) {
        return awakenerService.getHumansList(countryId);
    }

    @GetMapping(value = "/getAwakenerInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AwakenerR getAwakenerInfoById(@PathVariable(value = "id") Long awakenerId) {
        responseMap = new HashMap<>();
        Awakener awakener = awakenerService.getInfoById(awakenerId);
        if (awakener != null) {

            return new AwakenerR()
                    .setId(awakener.getId_awakener())
                    .setFirstName(awakener.getHuman().getFirstName())
                    .setLastName(awakener.getHuman().getLastName())
                    .setRank(awakener.getRank())
                    .setAwakeTime(awakener.getAwakeTime())
                    .setBirthday(awakener.getHuman().getBirthday())
                    .setCountryId(awakener.getHuman().getCountry().getId_country())
                    .setCountryName(awakener.getHuman().getCountry().getName())
                    .setExperience(awakener.getExperience())
                    .setGuildId(awakener.getGuild().getId());
        } else return null;
    }


    @GetMapping(value = "/getAwakenersInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AwakenerR> getAwakenerList(@PathVariable(value = "id") Long countryId) {
        List<Awakener> awakeners = awakenerService.getAwakenersByCountry(countryId);
        List<AwakenerR> response = new ArrayList<>();
        for (Awakener awakener : awakeners) {
            response.add(new AwakenerR()
                    .setId(awakener.getId_awakener())
                    .setFirstName(awakener.getHuman().getFirstName())
                    .setLastName(awakener.getHuman().getLastName())
                    .setRank(awakener.getRank())
                    .setAwakeTime(awakener.getAwakeTime())
                    .setBirthday(awakener.getHuman().getBirthday())
                    .setCountryName(awakener.getHuman().getCountry().getName())
                    .setExperience(awakener.getExperience())
                    .setGuildId(awakener.getGuild().getId()));
        }
        return response;
    }

    @PostMapping(value = "/createGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createGroup(@RequestBody GroupD groupD) {
        try {
            responseMap = new HashMap<>();
            coordinatorService.addGroup(groupD.getName().trim());
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }

        return responseMap;
    }


    @PostMapping(value = "/addAwakenerToGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addAwakenerToGroup(@RequestBody AwakenerInGroupD awakenerInGroupD) {
        responseMap = new HashMap<>();
        try {
            coordinatorService.addAwakenerToGroup(awakenerInGroupD.getHuman_id(), awakenerInGroupD.getGroup_id(), new Timestamp(System.currentTimeMillis()));
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }
        return responseMap;
    }

    @PostMapping(value = "/removeAwakenerFromGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> removeAwakenerFromGroup(@RequestBody AwakenerInGroupD awakenerInGroupD) {
        responseMap = new HashMap<>();
        try {
            coordinatorService.removeAwakenerFromGroup(awakenerInGroupD.getHuman_id(), awakenerInGroupD.getGroup_id());
            responseMap.put("result", "true");
            return responseMap;
        } catch (Exception e) {
            responseMap.put("result", "false");
            return responseMap;
        }
    }


    @PostMapping(value = "/addArtifactOrMonsterType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addArtifactOrMonsterType(@RequestBody TypesD typesD) {
        try {
            responseMap = new HashMap<>();
            registerService.setArtifactOrMonsterType(typesD.getName().trim(), typesD.getDescription().trim(), typesD.getClassTypeName().trim());
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }

        return responseMap;
    }

    @PostMapping(value = "/addArtifact", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addArtifact(@RequestBody ArtifactD artifactD) {
        try {
            responseMap = new HashMap<>();
            registerService.addArtifact(artifactD.getId_type(), artifactD.getId_rift(), artifactD.getPrice());
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }

        return responseMap;
    }

    @PostMapping(value = "/addMonster", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addMonster(@RequestBody MonsterD monsterD) {
        responseMap = new HashMap<>();
        try {
            registerService.addMonster(monsterD.getId_type(), monsterD.getId_rift(), monsterD.getRank());
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "rank");
        }

        return responseMap;
    }

    @PostMapping(value = "/addRift", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addRift(@RequestBody RiftD riftD) {
        try {
            responseMap = new HashMap<>();
            registerService.addRift(riftD.getCoordinateId(), riftD.getCountryId(), riftD.getRank(), riftD.getAccessLevel(),
                    riftD.getReward());
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }
        return responseMap;
    }

    @PostMapping(value = "/addRc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addRc(@RequestBody RecyclingCenterD recyclingCenterD) {
        try {
            responseMap = new HashMap<>();
            rcManagerService.addRc(recyclingCenterD.getCoordinateId(), recyclingCenterD.getTypeId(), recyclingCenterD.getCountryId(),
                    recyclingCenterD.getAccess_level());
            responseMap.put("result", "true");
        } catch (Exception e) {
            responseMap.put("result", "false");
        }

        return responseMap;
    }


    @DeleteMapping(value = "/deleteRc/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> deleteRc(@PathVariable(value = "id") Long rcId) {
        responseMap = new HashMap<>();
        try {
            rcManagerService.deleteRc(rcId);
            responseMap.put("result", "true");
            return responseMap;
        } catch (Exception e) {
            responseMap.put("result", "false");
            return responseMap;
        }
    }

    @GetMapping(value = "/getRcInfo/{id_country}/{access_level}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecyclingCenterR> getAwakenerList(@PathVariable(value = "id_country") Long countryId,
                                                  @PathVariable(value = "access_level") Integer access_level) {
        return rcManagerService.getRcList(countryId, access_level);
    }

    @GetMapping(value = "/getEmployee/{id_guild}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeeByGuild(@PathVariable(value = "id_guild") Long id_guild) {
        return employeeService.getEmployees(id_guild);
    }

    @DeleteMapping(value = "/deleteEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> deleteEmployee(@PathVariable(value = "id") Long id) {
        responseMap = new HashMap<>();
        try {
            employeeService.deleteEmployee(id);
            responseMap.put("result", "true");
            return responseMap;
        } catch (Exception e) {
            responseMap.put("result", "false");
            return responseMap;
        }
    }

    @GetMapping(value = "/getListRifts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RiftR> getListRifts(@PathVariable(value = "id") Long riftId) {
        return registerService.getRiftList(riftId);
    }

    @GetMapping(value = "/getListMonsters/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonsterR> getListMonsters(@PathVariable(value = "id") Long riftId) {
        return registerService.getMonsterList(riftId);
    }

    @GetMapping(value = "/getListArtifact/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArtifactR> getListArtifacts(@PathVariable(value = "id") Long riftId) {
        return registerService.getArtifactList(riftId);
    }

    @GetMapping(value = "/getListMaterial/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MaterialR> getListMaterials(@PathVariable(value = "id") Long riftId) {
        return registerService.getMaterialList(riftId);
    }

    @GetMapping(value = "/getRiftListByEmployeeId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RiftR> getRiftsListByEmployeeId(@PathVariable(value = "id") Long id) {
        return registerService.getRiftListByEmployeeId(id);
    }

    @GetMapping(value = "/getRCsListByEmployeeId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecyclingCenterR> getRCsListByEmployeeId(@PathVariable(value = "id") Long id) {
        return rcManagerService.getRCListByEmployeeId(id);
    }

    @GetMapping(value = "/getListRiftByGroupAndCountry/{gid}/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RiftR> getListRiftByGroupAndCountry(@PathVariable(value = "gid") Long groupId, @PathVariable(value = "cid") Long countryId) {
        return registerService.getListRiftByGroupAndCountry(groupId, countryId);
    }

    @GetMapping(value = "/getListGroupsForAwakener/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupR> getListGroupsForAwakener(@PathVariable(value = "id") Long id) {
        return coordinatorService.getListGroupsForAwakener(id);
    }

    /*
        Список статусов определенного разлома
     */

    @GetMapping(value = "/getListRiftStatusesByRiftId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RiftStatusR> getListRiftStatusesByRiftId(@PathVariable(value = "id") Long id) {
        return registerService.getListRiftStatusesByRiftId(id);
    }


}
