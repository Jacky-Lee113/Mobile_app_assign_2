package com.example.noteme;

public class EntryModel {
    int id;
    String addressInput;
    String latitudeInput;
    String longitudeInput;
    EntryModel() {

    }

    public EntryModel(String addressInput, String latitudeInput, String longitudeInput) {
        this.addressInput = addressInput;
        this.latitudeInput = latitudeInput;
        this.longitudeInput = longitudeInput;
    }
    public EntryModel(int id, String addressInput, String latitudeInput, String longitudeInput) {
        this.id = id;
        this.addressInput = addressInput;
        this.latitudeInput = latitudeInput;
        this.longitudeInput = longitudeInput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressInput() {
        return addressInput;
    }

    public void setAddressInput(String addressInput) {
        this.addressInput = addressInput;
    }

    public String getLatitudeInput() {
        return latitudeInput;
    }

    public void setLatitudeInput(String latitudeInput) {
        this.latitudeInput = latitudeInput;
    }

    public String getLongitudeInput() {
        return longitudeInput;
    }

    public void setLongitudeInput(String longitudeInput) {
        this.longitudeInput = longitudeInput;
    }
}
