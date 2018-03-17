package org.saburov.services.conference.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Presentation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long presentatiionid;
    @Column(name="tittle")
    private String tittle;
    @Column(name="description")
    private String description;

//    @OneToOne(mappedBy = "presentationOb", cascade = CascadeType.ALL)
//    @OneToOne(optional = false, mappedBy="presentationOb")
//    private Schedule schedule;

    @ManyToMany(mappedBy = "presentations")
    private Set<ConferenceUser> conferenceUsers;
    public Presentation() {
    }

//    public Schedule getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(Schedule schedule) {
//        this.schedule = schedule;
//    }

    public long getPresentatiionid() {
        return presentatiionid;
    }

    public void setPresentatiionid(long presentatiionid) {
        this.presentatiionid = presentatiionid;
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
