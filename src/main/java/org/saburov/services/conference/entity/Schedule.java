package org.saburov.services.conference.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "presentationTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private java.util.Date presentationTime;
    @OneToOne
    @JoinColumn(name = "presentationid")
    private Presentation presentation;

    @OneToOne
    @OrderBy
    @JoinColumn(name = "presentation_room")
    @JsonIgnore
    private Room presentationRoom;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPresentationTime() {
        return presentationTime;
    }

    public void setPresentationTime(Date presentationTime) {
        this.presentationTime = presentationTime;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Room getPresentationRoom() {
        return presentationRoom;
    }

    public void setPresentationRoom(Room presentationRoom) {
        this.presentationRoom = presentationRoom;
    }
}
