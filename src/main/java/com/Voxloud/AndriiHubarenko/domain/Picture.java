package com.Voxloud.AndriiHubarenko.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "PICTURES")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false, unique = true)
    private String pictureName;

    @Column (nullable = false)
    private ContentType contentType;

    @Column (nullable = false)
    private float size;

    @Column (nullable = false)
    private String reference;

    @Column
    private String creationTimeStamp;

    @Column
    private String modificationTimeStamp;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(name = "PICTURE_TAG", joinColumns = @JoinColumn(name = "PICTURE_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "name")
    private Set<Tag> tags = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public User getUser() {
        return user;
    }

    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getModificationTimeStamp() {
        return modificationTimeStamp;
    }

    public void setModificationTimeStamp(String modificationTimeStamp) {
        this.modificationTimeStamp = modificationTimeStamp;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addUser(User user) {
        this.addUser(user, false);
    }

    protected void addUser(User user, boolean otherSideHasBeenSet) {
        this.user = user;
        if(otherSideHasBeenSet) {
            return;
        }
        user.addPicture(this, true);
    }

    public void removeUser() {
        removeUser(this.user, false);
    }

    protected void removeUser(User user, boolean otherSideHasBeenSet) {
        this.user = null;
        if(otherSideHasBeenSet) {
            return;
        }
        user.removePicture(this, true);
    }

    public void addTag(Tag tag) {
        addTag(tag, false);
    }

    public void addTag(Tag tag, boolean otherSideWasAffected) {
        getTags().add(tag);
        if (otherSideWasAffected) {
            return;
        }

        tag.addPicture(this, true);
    }

    public void setAllTags(Collection<Tag> tags) {
        this.removeAllTags();
        tags.forEach(this::addTag);
    }

    public void removeAllTags() {
        getTags().stream().collect(Collectors.toList()).forEach(this::removeTag);
    }

    public void removeTag(Tag tag) {
        removeTag(tag, false);
    }

    public void removeTag(Tag tag, boolean otherSideWasAffected) {
        this.getTags().remove(tag);
        if (otherSideWasAffected) {
            return;
        }
        tag.removePicture(this, true);
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", pictureName='" + pictureName + '\'' +
                ", contentType=" + contentType +
                ", size=" + size +
                ", reference='" + reference + '\'' +
                ", creationTimeStamp=" + creationTimeStamp +
                ", modificationTimeStamp=" + modificationTimeStamp +
                ", user=" + user.getNickName() +
                ", tags=" + "[" + tags.stream().map(t -> t.getName() + " ").collect(Collectors.joining()) + "]" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return Float.compare(picture.size, size) == 0 && creationTimeStamp == picture.creationTimeStamp && modificationTimeStamp == picture.modificationTimeStamp && id == id && pictureName.equals(picture.pictureName) && contentType == picture.contentType && reference.equals(picture.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pictureName, contentType, size, reference, creationTimeStamp, modificationTimeStamp);
    }
}
