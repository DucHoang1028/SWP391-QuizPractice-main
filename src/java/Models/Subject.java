package Models;

import DAOs.SubjectCategoryDAO;
import DAOs.SubjectDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Subject extends BaseModel {

    private int subject_id;
    private String thumbnail;
    private String title;
    private String tag_line;
    private int status;
    private String description;
    private boolean is_featured;
    private int category_id;
    private BigDecimal list_price;
    private BigDecimal sale_price;
    private BigDecimal total_cost;
    private String valid_from;
    private String valid_to;
    

    // constants for Subject
    public static final int STATUS_PUBLISHED = 1;
    public static final int STATUS_UNPUBLISHED = 0;

    public Subject() {
    }

    public Subject(int subject_id, String thumbnail, String title, String tag_line,
            int status, LocalDateTime created_at, LocalDateTime updated_at,
            String description, boolean is_featured, int category_id) {
        this.subject_id = subject_id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.tag_line = tag_line;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.description = description;
        this.is_featured = is_featured;
        this.category_id = category_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag_line() {
        return tag_line;
    }

    public void setTag_line(String tag_line) {
        this.tag_line = tag_line;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_featured() {
        return is_featured;
    }

    public void setIs_featured(boolean is_featured) {
        this.is_featured = is_featured;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getStatusName(int status) {
        return switch (status) {
            case STATUS_PUBLISHED ->
                "Published";
            case STATUS_UNPUBLISHED ->
                "Unpublished";
            default ->
                "Unknown";
        };
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

    public String getCategory() {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        return dao.GetCategoryById(this.category_id).getCategory_name();
    }

    public List<PricePackage> getPricePackage() throws SQLException {
        SubjectDAO dao = new SubjectDAO();
        return dao.getPricePackagesBySubjectId(subject_id);
    }
    public void setTotal_cost(BigDecimal total_cost) {
        this.total_cost = total_cost;

    }

    public BigDecimal getTotal_cost() {
        return total_cost;

    }
    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
        

    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public String getValid_from() {
        return valid_from;
    }

    public String getValid_to() {
        return valid_to;
    }
     public String getStatusRegister(int status) {
        return switch (status) {
            case STATUS_PUBLISHED ->
                "Submitted";
            case STATUS_UNPUBLISHED ->
                "Submitted";
            default ->
                "Unknown";
        };
    }
     
     public String getSubjectStatus(int status){
         return switch(status){
             case STATUS_PUBLISHED -> "Published";
             case STATUS_UNPUBLISHED -> "Unpublished";
             default -> "Unknown";
         };
     }
}
