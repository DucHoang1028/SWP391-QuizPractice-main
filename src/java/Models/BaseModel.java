

package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseModel {

    LocalDateTime created_at;
    LocalDateTime updated_at;

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
    
    public String getFormattedDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
        return date.format(formatter);
    }
}
