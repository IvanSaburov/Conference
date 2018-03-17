package org.saburov.services.conference.utils;


public class ButtonAccessConfigurator {
    boolean isPresentationListAvailable = false;
    boolean isConferenceUsersListAvailable = false;

    public boolean isPresentationListAvailable() {
        return isPresentationListAvailable;
    }

    public void setIsPresentationListAvailable(boolean isPresentationListAvailable) {
        this.isPresentationListAvailable = isPresentationListAvailable;
    }

    public boolean isConferenceUsersListAvailable() {
        return isConferenceUsersListAvailable;
    }

    public void setIsConferenceUsersListAvailable(boolean isConferenceUsersListAvailable) {
        this.isConferenceUsersListAvailable = isConferenceUsersListAvailable;
    }
}
