package Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Registrations extends BaseModel {
    private int id;
    private User user;
    private Subject subject;
    private LocalDateTime registrationTime;
    private PricePackage pricePackage;
    private BigDecimal totalCost;
    private int status;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    // Constants for status
    public static final int STATUS_SUBMITTED = 0;
    public static final int STATUS_CONFIRMED = 1;
    public static final int STATUS_CANCELLED = 2;

    public Registrations() {
    }

    public Registrations(int id, User user, Subject subject, LocalDateTime registrationTime, PricePackage pricePackage, BigDecimal totalCost, int status, LocalDateTime validFrom, LocalDateTime validTo) {
        this.id = id;
        this.user = user;
        this.subject = subject;
        this.registrationTime = registrationTime;
        this.pricePackage = pricePackage;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public PricePackage getPricePackage() {
        return pricePackage;
    }

    public void setPricePackage(PricePackage pricePackage) {
        this.pricePackage = pricePackage;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", user=" + user +
                ", subject=" + subject +
                ", registrationTime=" + registrationTime +
                ", pricePackage=" + pricePackage +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
