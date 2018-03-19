package org.saburov.services.conference.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.saburov.services.conference.utils.MD5Util;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ConferenceUser {

    @JsonIgnore
    private long id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private Integer age;
    @JsonIgnore
    private Set<Presentation> presentations;

    public ConferenceUser(String username, String password) {
        this.username = username;
        this.password =  MD5Util.convertPasswordToHash(password);
    }

    public ConferenceUser() {
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "username", unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_presentation", joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "presentationid")})
    public Set<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(Set<Presentation> presentations) {
        this.presentations = presentations;
    }

    @Override
    public String toString() {
        return username;
    }
}
