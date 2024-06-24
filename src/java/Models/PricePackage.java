

package Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PricePackage extends BaseModel {
    
    private int package_id;
    private String name;
    private int duration;
    private BigDecimal list_price;
    private BigDecimal sale_price;
    private int subject_id;
    private int status;
    
    // constants for PricePackage
    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE = 1;

    public PricePackage() {
    }

    public PricePackage(int package_id, String name, int duration, 
            BigDecimal list_price, BigDecimal sale_price, int subject_id, 
            int status, LocalDateTime created_at, LocalDateTime updated_at) {
        this.package_id = package_id;
        this.name = name;
        this.duration = duration;
        this.list_price = list_price;
        this.sale_price = sale_price;
        this.subject_id = subject_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getStatusName(int status) {
        return switch (status) {
            case STATUS_ACTIVE -> "Active";
            case STATUS_INACTIVE -> "Inactive";
            default -> "Unknown";
        };
    }
    @Override
    public String toString() {
        // return a json string representation of the object, change all the " to ' to reduce the chance of breaking the string
        return "{'package_id':" + package_id + ", "
                + "'name':'" + name + "', "
                + "'duration':" + duration + ", "
                + "'list_price':" + list_price + ", "
                + "'sale_price':" + sale_price + ", "
                + "'subject_id':" + subject_id + ", "
                + "'status':" + status + ", "
                + "'created_at':'" + created_at + "', "
                + "'updated_at':'" + updated_at + "'}";

    }
    
       
}
