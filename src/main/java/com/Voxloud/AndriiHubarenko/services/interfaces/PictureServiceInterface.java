package com.Voxloud.AndriiHubarenko.services.interfaces;

import com.Voxloud.AndriiHubarenko.domain.Picture;

import java.util.Set;

/**
 * @author Andrii Hubarenko
 * Interface for PictureService service
 */
public interface PictureServiceInterface {

    /**
     * Method for adding single picture
     * @param userId
     * @param picture
     * @return
     */
    Picture createPicture(long userId, Picture picture);

    /**
     * Method for adding group of pictures
     * @param userId
     * @param pictures
     * @return
     */
    Set<Picture> createAllPictures(long userId, Set<Picture> pictures);

    /**
     *Method for retrieving single picture
     * @param userId
     * @param id
     * @return
     */
    Picture getPicture(long userId, long id);

    /**
     * Method for retrieving all of pictures
     * @param userId
     * @return
     */
    Set<Picture> getAllPictures(long userId);

    /**
     * Method for updating single picture
     * @param userId
     * @param updatedPicture
     * @return
     */
    Picture updatePicture(long userId, Picture updatedPicture);

    /**
     * Method for updating group of pictures
     * @param userId
     * @param updatedPictures
     * @return
     */
    Set<Picture> updateAllPictures(long userId, Set<Picture> updatedPictures);

    /**
     * Method for removing single image
     * @param userId
     * @param id
     * @return
     */
    String deletePicture(long userId, long id);

    /**
     * Method for removing all of images
     * @param userId
     * @return
     */
    String deleteAllPictures(long userId);


}
