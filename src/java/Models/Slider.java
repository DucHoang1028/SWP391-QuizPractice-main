

package Models;

public class Slider extends BaseModel {
    
    private int slider_id;
    private String title;
    private String image;
    private String back_link;
    private int status;
    private String note;

    // constants for slider
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;
    
    public Slider() {
    }

    public Slider(int slider_id, String title, String image, String back_link, 
            int status, String note) {
        this.slider_id = slider_id;
        this.title = title;
        this.image = image;
        this.back_link = back_link;
        this.status = status;
        this.note = note;
    }

    public int getSlider_id() {
        return slider_id;
    }

    public void setSlider_id(int slider_id) {
        this.slider_id = slider_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBack_link() {
        return back_link;
    }

    public void setBack_link(String back_link) {
        this.back_link = back_link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public String getStatusName(int status) {
        return switch (status) {
            case STATUS_ACTIVE -> "Show";
            case STATUS_INACTIVE -> "Hide";
            default -> "Unknown";
        };
    }
    
    
}
