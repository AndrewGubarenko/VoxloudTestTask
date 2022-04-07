package com.Voxloud.AndriiHubarenko.repositories;

import com.Voxloud.AndriiHubarenko.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String Name);
}
