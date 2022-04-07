package com.Voxloud.AndriiHubarenko.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TAGS")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false, unique = true)
    String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Picture> pictures = new HashSet<>();

    public Tag () {

    }

    public Tag (String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String tagName) {
        this.name = tagName;
    }

    public Set<Picture> getPictureList() {
        return pictures;
    }

    public void setPictureList(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(Picture todo) {
        addPicture(todo, false);
    }

    public void addPicture(Picture picture, boolean otherSideWasAffected) {
        getPictureList().add(picture);
        if (otherSideWasAffected) {
            return;
        }

        picture.addTag(this, true);
    }

    public void removePicture(Picture picture) {
        removePicture(picture, false);
    }

    public void removePicture(Picture picture, boolean otherSideWasAffected) {
        this.getPictureList().remove(picture);
        if (otherSideWasAffected) {
            return;
        }
        picture.removeTag(this, true);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return id == tag.id && name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
