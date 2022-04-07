package com.Voxloud.AndriiHubarenko.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String nickName;

    @Column (nullable = false)
    private String password;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Set<Picture> pictures = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    protected void addPicture(Picture picture, boolean otherSideHasBeenSet) {
        this.getPictures().add(picture);
        if(otherSideHasBeenSet) {
            return;
        }
        picture.addUser(this, true);
    }

    protected void removePicture(Picture picture, boolean otherSideHasBeenSet) {
        this.getPictures().remove(picture);
        if(otherSideHasBeenSet) {
            return;
        }
        picture.removeUser(this, true);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", pictures=" + "[" + pictures.stream().map(p -> p.getPictureName() + " ").collect(Collectors.joining()) + "]" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == id  && email.equals(user.email) && nickName.equals(user.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickName);
    }
}
