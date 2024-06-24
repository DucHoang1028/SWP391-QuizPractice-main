package Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Registration extends BaseModel {

    private int registration_id;
    private BigDecimal list_price;
    private BigDecimal sale_price;
    private int user_id;
    private int status;
    private LocalDateTime valid_from;
    private LocalDateTime valid_to;
    private String notes;
    
    // constants for registration
    public static final int STATUS_SUBMITTED = 0;
    public static final int STATUS_PAID = 1;
    public static final int STATUS_EXPIRED = 2;
    public static final int STATUS_CANCELLED = 3;

    public Registration() {
    }

    public Registration(int registration_id, BigDecimal list_price,
            BigDecimal sale_price, int user_id, int status, LocalDateTime valid_from,
            LocalDateTime valid_to, String notes) {
        this.registration_id = registration_id;
        this.list_price = list_price;
        this.sale_price = sale_price;
        this.user_id = user_id;
        this.status = status;
        this.valid_from = valid_from;
        this.valid_to = valid_to;
        this.notes = notes;
    }

    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public BigDecimal getList_price() {
        return list_price;
    }

    public void setList_price(BigDecimal list_price) {
        this.list_price = list_price;
    }

    public BigDecimal getSale_price() {
        return sale_price;
    }

    public void setSale_price(BigDecimal sale_price) {
        this.sale_price = sale_price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getValid_from() {
        return valid_from;
    }

    public void setValid_from(LocalDateTime valid_from) {
        this.valid_from = valid_from;
    }

    public LocalDateTime getValid_to() {
        return valid_to;
    }

    public void setValid_to(LocalDateTime valid_to) {
        this.valid_to = valid_to;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatusName(int status) {
        return switch (status) {
            case STATUS_SUBMITTED ->
                "Submitted";
            case STATUS_PAID ->
                "Paid";
            case STATUS_EXPIRED ->
                "Expired";
            case STATUS_CANCELLED ->
                "Cancelled";
            default ->
                "Unknown";
        };
    }

}
