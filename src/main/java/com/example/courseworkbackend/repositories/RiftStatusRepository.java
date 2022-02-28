package com.example.courseworkbackend.repositories;

import com.example.courseworkbackend.entities.RiftStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiftStatusRepository extends JpaRepository<RiftStatus, Long> {
}
