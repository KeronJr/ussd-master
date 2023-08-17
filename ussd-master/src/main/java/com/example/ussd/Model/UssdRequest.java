package com.example.ussd.Model;

import jakarta.persistence.*;


import java.io.Serializable;

@Entity
public class UssdRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;
    private String phoneNumber;
    private int newRequest;
    private int level;
    private String language;
    private String input; // Renamed from "text"
    private RegistrationStep currentStep;

    private String firstNameInput;
    private String lastNameInput;
    private String nationalIdInput;
    private String locationInput;

    // Constructors, getters, and setters

    public UssdRequest() {
    }

    public UssdRequest(Long id, String sessionId, String phoneNumber, int newRequest, int level, String language, String input, RegistrationStep currentStep, String firstNameInput, String lastNameInput, String nationalIdInput, String locationInput) {
        this.id = id;
        this.sessionId = sessionId;
        this.phoneNumber = phoneNumber;
        this.newRequest = newRequest;
        this.level = level;
        this.language = language;
        this.input = input;
        this.currentStep = currentStep;
        this.firstNameInput = firstNameInput;
        this.lastNameInput = lastNameInput;
        this.nationalIdInput = nationalIdInput;
        this.locationInput = locationInput;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(int newRequest) {
        this.newRequest = newRequest;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public RegistrationStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(RegistrationStep currentStep) {
        this.currentStep = currentStep;
    }

    public String getFirstNameInput() {
        return firstNameInput;
    }

    public void setFirstNameInput(String firstNameInput) {
        this.firstNameInput = firstNameInput;
    }

    public String getLastNameInput() {
        return lastNameInput;
    }

    public void setLastNameInput(String lastNameInput) {
        this.lastNameInput = lastNameInput;
    }

    public String getNationalIdInput() {
        return nationalIdInput;
    }

    public void setNationalIdInput(String nationalIdInput) {
        this.nationalIdInput = nationalIdInput;
    }

    public String getLocationInput() {
        return locationInput;
    }

    public void setLocationInput(String locationInput) {
        this.locationInput = locationInput;
    }
}
