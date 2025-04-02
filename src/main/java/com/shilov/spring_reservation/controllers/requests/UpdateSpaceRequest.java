package com.shilov.spring_reservation.controllers.requests;

public class UpdateSpaceRequest {

    private String spaceId;
    private String updatedSpaceType;
    private String updatedHourlyRate;

    public UpdateSpaceRequest(String spaceId, String updatedSpaceType, String updatedHourlyRate) {
        this.spaceId = spaceId;
        this.updatedSpaceType = updatedSpaceType;
        this.updatedHourlyRate = updatedHourlyRate;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getUpdatedSpaceType() {
        return updatedSpaceType;
    }

    public void setUpdatedSpaceType(String updatedSpaceType) {
        this.updatedSpaceType = updatedSpaceType;
    }

    public String getUpdatedHourlyRate() {
        return updatedHourlyRate;
    }

    public void setUpdatedHourlyRate(String updatedHourlyRate) {
        this.updatedHourlyRate = updatedHourlyRate;
    }
}
