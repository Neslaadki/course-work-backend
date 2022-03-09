package com.example.courseworkbackend.services;

import com.example.courseworkbackend.entities.Employee;
import com.example.courseworkbackend.entities.Guild;
import com.example.courseworkbackend.entities.Human;
import com.example.courseworkbackend.entities.User;
import com.example.courseworkbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HumanRepository humanRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuildRepository guildRepository;


    @SneakyThrows
    @Transactional
    public boolean addNewEmployee(
                String firstName,
                String lastName,
                Timestamp birthday,
                Long id_country,
                Long id_position,
                Integer experience,
                Integer accessLevel,
                Timestamp startTime,
                Timestamp endTime,
                String login,
                String password){

        Human human = createHuman(firstName, lastName, birthday, id_country);
        Employee employee = createEmployee(human, id_position, experience, accessLevel, startTime, endTime);

        if (userRepository.findUserByLogin(login) == null){
            userRepository.save(
                    new User()
                            .setLogin(login)
                            .setPassword(password)
                            .setEmployee(employee));
            return true;
        }
        else return false;

    }


    @SneakyThrows
    @Transactional
    public boolean addExistEmployee(
            Long id_human,
            Long id_position,
            Integer experience,
            Integer accessLevel,
            Timestamp startTime,
            Timestamp endTime,
            String login,
            String password){

        Human human = humanRepository.getById(id_human);
        Employee employee = createEmployee(human, id_position, experience, accessLevel, startTime, endTime);

        if (userRepository.findUserByLogin(login) == null){
            userRepository.save(
                    new User()
                            .setLogin(login)
                            .setPassword(password)
                            .setEmployee(employee));
            return true;
        }
        else return false;

    }

    @Transactional
    Human createHuman(
            String firstName,
            String lastName,
            Timestamp birthday,
            Long id_country
    ){
        Human human = new Human()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBirthday(birthday)
                .setCountry(countryRepository.getById(id_country));

        human = humanRepository.save(human);
            return human;

    }

    @Transactional
    Employee createEmployee(
            Human human,
            Long id_position,
            Integer experience,
            Integer accessLevel,
            Timestamp startTime,
            Timestamp endTime
    ){
        Employee employee = new Employee()
                .setHuman(human)
                .setPosition(
                        positionRepository.getById(id_position)
                )
                .setExperience(experience)
                .setAccessLevel(accessLevel)
                .setStartTime(startTime)
                .setEndTime(endTime);
        return  employeeRepository.save(employee);
    }

    public List<Employee> getEmployees(Long guild_id){
        return employeeRepository.getEmployeeByGuild(
                guildRepository.getById(guild_id));
        )
    }


    public String getPositionNameById(Long id){
        return  positionRepository.getById(id).getPosition_name();
    }

    public boolean deleteEmployee(Long id){
        Employee employee = employeeRepository.getById(id);
        if (employee.getAccessLevel() < 10){
            employeeRepository.deleteById(id);
            return true;
        }else
            return false;
    }

    String enctyptPass(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] b = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b1 : b) {
            stringBuilder.append(b1);
        }
        return stringBuilder.toString();

    }

}
