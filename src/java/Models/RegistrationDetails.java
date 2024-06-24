package Models;

import java.time.LocalDateTime;

public class RegistrationDetails {

    private int registrationId;
    private LocalDateTime registrationTime;
    private String userEmaill;
    private String subjectName;
    private String packageName;
    private LocalDateTime ValidFrom;
    private LocalDateTime validTo;
    private String lastUpdated;
    private float totalCost;
    private int status;
    private float listPrice;
    private String note;

    public RegistrationDetails(int registrationId, LocalDateTime registrationTime, String userEmaill, String subjectName, String packageName, LocalDateTime ValidFrom, LocalDateTime validTo, String lastUpdated, float totalCost, int status) {
        this.registrationId = registrationId;
        this.registrationTime = registrationTime;
        this.userEmaill = userEmaill;
        this.subjectName = subjectName;
        this.packageName = packageName;
        this.ValidFrom = ValidFrom;
        this.validTo = validTo;
        this.lastUpdated = lastUpdated;
        this.totalCost = totalCost;
        this.status = status;
    }

    public RegistrationDetails(int registrationId, LocalDateTime registrationTime,
            String userEmaill, String subjectName, String packageName, LocalDateTime ValidFrom,
            LocalDateTime validTo, String lastUpdated, float totalCost, int status, float listPrice,
            String note) {
        this.registrationId = registrationId;
        this.registrationTime = registrationTime;
        this.userEmaill = userEmaill;
        this.subjectName = subjectName;
        this.packageName = packageName;
        this.ValidFrom = ValidFrom;
        this.validTo = validTo;
        this.lastUpdated = lastUpdated;
        this.totalCost = totalCost;
        this.status = status;
        this.listPrice = listPrice;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public String getUserEmaill() {
        return userEmaill;
    }

    public void setUserEmaill(String userEmaill) {
        this.userEmaill = userEmaill;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public LocalDateTime getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(LocalDateTime ValidFrom) {
        this.ValidFrom = ValidFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
