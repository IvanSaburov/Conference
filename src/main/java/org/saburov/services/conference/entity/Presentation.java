package org.saburov.services.conference.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Presentation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long presentationid;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "presentations")
    @JsonProperty("presenters")
    private Set<ConferenceUser> conferenceUsers;

    public Presentation() {
    }

    public long getPresentationid() {
        return presentationid;
    }

    public void setPresentationid(long presentationid) {
        this.presentationid = presentationid;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ConferenceUser> getConferenceUsers() {
        return conferenceUsers;
    }

    public void setConferenceUsers(Set<ConferenceUser> conferenceUsers) {
        this.conferenceUsers = conferenceUsers;
    }
}
