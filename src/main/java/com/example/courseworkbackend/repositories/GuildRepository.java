package com.example.courseworkbackend.repositories;

import com.example.courseworkbackend.entities.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
}
