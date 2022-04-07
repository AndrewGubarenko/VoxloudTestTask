package com.Voxloud.AndriiHubarenko.services;

import com.Voxloud.AndriiHubarenko.domain.Picture;
import com.Voxloud.AndriiHubarenko.domain.Tag;
import com.Voxloud.AndriiHubarenko.domain.User;
import com.Voxloud.AndriiHubarenko.repositories.PictureRepository;
import com.Voxloud.AndriiHubarenko.repositories.UserRepository;
import com.Voxloud.AndriiHubarenko.services.interfaces.PictureServiceInterface;
import com.Voxloud.AndriiHubarenko.services.interfaces.TagServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

@Service
public class PictureService implements PictureServiceInterface {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final TagServiceInterface tagService;

    @Autowired
    public PictureService(UserRepository userRepository,
                          PictureRepository pictureRepository,
                          TagServiceInterface tagService) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.tagService = tagService;
    }


    @Override
    @Transactional
    public Picture createPicture(long userId, Picture picture) {

        Set<Tag> tags = tagService.findOrCreate(picture.getTags());
        picture.getTags().clear();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        picture.setCreationTimeStamp(formatter.format(calendar.getTime()));
        picture.addUser(userRepository.findById(userId).get());
        Picture result = pictureRepository.save(picture);

        tags.forEach(picture::addTag);

        return result;
    }

    @Override
    @Transactional
    public Set<Picture> createAllPictures(long userId, Set<Picture> pictures) {

        pictures.forEach(picture -> {
            picture.getTags().forEach(tag -> {
                Set<Tag> tags = tagService.findOrCreate(picture.getTags());
                picture.getTags().clear();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                picture.setCreationTimeStamp(formatter.format(calendar.getTime()));
                picture.addUser(userRepository.findById(userId).get());
                pictureRepository.save(picture);

                tags.forEach(picture::addTag);
            });
        });
        return pictures;
    }

    @Override
    @Transactional
    public Picture getPicture(long userId, long id) {
        Optional<Picture> resultOpt = pictureRepository.findById(id);
        if (!resultOpt.isPresent())
            throw new RuntimeException("no such pictures were found");
        Picture result = resultOpt.get();
        if (result.getUser().getId() != userId)
            throw new RuntimeException("Illegal access!");

        return result;
    }

    @Override
    @Transactional
    public Set<Picture> getAllPictures(long userId) {
        User user = userRepository.findById(userId).get();
        return user.getPictures();
    }

    @Override
    @Transactional
    public Picture updatePicture(long userId, Picture updatedPicture) {
        Picture pictureForUpdate = pictureRepository.findById(updatedPicture.getId()).get();
        pictureForUpdate.setPictureName(updatedPicture.getPictureName());
        pictureForUpdate.setContentType(updatedPicture.getContentType());
        pictureForUpdate.setSize(updatedPicture.getSize());
        pictureForUpdate.setReference(updatedPicture.getReference());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        pictureForUpdate.setModificationTimeStamp(formatter.format(calendar.getTime()));

        Set<Tag> tags = tagService.findOrCreate(updatedPicture.getTags());

        Picture result = pictureRepository.save(pictureForUpdate);

        tags.forEach(pictureForUpdate::addTag);

        return result;
    }

    @Override
    @Transactional
    public Set<Picture> updateAllPictures(long userId, Set<Picture> updatedPictures) {
        User user = userRepository.findById(userId).get();
        Set<Picture> picturesForUpdate = user.getPictures();
        updatedPictures.forEach(p -> {
            picturesForUpdate.forEach(pic -> {
                if(p.getId() == pic.getId()) {
                    pic.setPictureName(p.getPictureName());
                    pic.setContentType(p.getContentType());
                    pic.setSize(p.getSize());
                    pic.setReference(p.getReference());

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    pic.setModificationTimeStamp(formatter.format(calendar.getTime()));

                    Set<Tag> tags = tagService.findOrCreate(p.getTags());

                    pictureRepository.save(pic);

                    tags.forEach(pic::addTag);
                }
            });
        });
        return picturesForUpdate;
    }

    @Override
    @Transactional
    public String deletePicture(long userId, long id) {
        User user = userRepository.findById(userId).get();
        Picture picture = pictureRepository.findById(id).get();
        picture.removeAllTags();
        picture.removeUser();
        user.getPictures().remove(picture);
        return "Picture removed";
    }

    @Override
    @Transactional
    public String deleteAllPictures(long userId) {
        User user = userRepository.findById(userId).get();
        user.getPictures().forEach(p -> {
            p.removeAllTags();
            p.removeUser();
        });
        return "All pictures were removed";
    }

}
