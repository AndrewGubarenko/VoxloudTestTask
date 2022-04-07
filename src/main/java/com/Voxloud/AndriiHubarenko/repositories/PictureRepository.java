package com.Voxloud.AndriiHubarenko.repositories;

import com.Voxloud.AndriiHubarenko.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PictureRepository  extends JpaRepository<Picture, Long> {

}
