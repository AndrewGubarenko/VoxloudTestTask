package com.Voxloud.AndriiHubarenko.controllers;

import com.Voxloud.AndriiHubarenko.domain.Picture;
import com.Voxloud.AndriiHubarenko.services.PictureService;
import com.Voxloud.AndriiHubarenko.services.interfaces.PictureServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class PictureController {

    private final PictureServiceInterface service;

    @Autowired
    public PictureController (PictureService service) {
        this.service = service;
    }

    @PostMapping(path = "/user/{id}/picture")
    public ResponseEntity<Picture> createPicture(@RequestBody Picture picture, @PathVariable long id) {
        Picture response = service.createPicture(id, picture);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/user/{id}/pictures")
    public ResponseEntity<Set<Picture>> createAllPictures(@RequestBody Set<Picture> pictures, @PathVariable long id) {
        Set<Picture> response = service.createAllPictures(id, pictures);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/user/{userId}/picture/{id}")
    public ResponseEntity<Picture> getPicture(@PathVariable long userId, @PathVariable long id) {
        Picture response = service.getPicture(userId, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/user/{userId}/pictures")
    public ResponseEntity<Set<Picture>> getAllPictures( @PathVariable long userId) {
        Set<Picture> response = service.getAllPictures(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/user/{userId}/picture")
    public ResponseEntity<Picture> updatePicture(@RequestBody Picture picture, @PathVariable long userId) {
        Picture response = service.updatePicture(userId, picture);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/user/{userId}/pictures")
    public ResponseEntity<Set<Picture>> updateAllPictures(@RequestBody Set<Picture> pictures, @PathVariable long userId) {
        Set<Picture> response = service.updateAllPictures(userId, pictures);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/user/{userId}/picture/{id}")
    public ResponseEntity<String> deletePicture(@PathVariable long id, @PathVariable long userId) {
        String response = service.deletePicture(userId, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/user/{userId}/pictures")
    public ResponseEntity<String> deleteAllPictures(@PathVariable long userId) {
        String response = service.deleteAllPictures(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
