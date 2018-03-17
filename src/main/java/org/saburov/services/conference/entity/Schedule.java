package org.saburov.services.conference.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="presentationTime")
    private Date presentationTime;
//    @JoinColumn(name = "presentationid")
@OneToOne
@JoinColumn(name="presentation")
    private Presentation presentation;

    @OneToOne
    @OrderBy
    @JoinColumn(name="presentation_room")
    private Room presentationRoom;
//    @Column(name="presentation")
//    private long presentation;
//    @Column(name="presentationRoom")
//    private long presentationRoom;
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
