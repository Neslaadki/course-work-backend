package com.example.courseworkbackend.repositories;

import com.example.courseworkbackend.entities.Monster;
import com.example.courseworkbackend.entities.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
    @Modifying
    @Query("update Monster m set m.types = :type where m.id_monster = :id")
    void updateMonsterType(@Param(value = "id") long id, @Param(value = "type")Types type);


    @Query(value = "select * from list_monsters(cast(? as bigint))", nativeQuery = true)
    List<Monster> getListMonsters(Long riftId);
}
