package Models;

import java.time.LocalDateTime;

public class Post extends BaseModel {

    private int post_id;
    private String thumbnail;
    private String title;
    private int author_id;
    private String content;
    private String summary;
    private boolean is_featured;
    private int status;
    private int category_id;
    private LocalDateTime post_date;

    // constants for Post
    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_DRAFT = 2;

    public Post() {
    }

    public Post(int post_id, String thumbnail, String title, int author_id,
            String content, String summary, LocalDateTime created_at,
            LocalDateTime updated_at, boolean is_featured, int status, int category_id) {
        this.post_id = post_id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.author_id = author_id;
        this.content = content;
        this.summary = summary;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_featured = is_featured;
        this.status = status;
        this.category_id = category_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isIs_featured() {
        return is_featured;
    }

    public void setIs_featured(boolean is_featured) {
        this.is_featured = is_featured;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public LocalDateTime getPost_date() {
        return post_date;
    }

    public void setPost_date(LocalDateTime post_date) {
        this.post_date = post_date;
    }

    public String getStatusName(int status) {
        return switch (status) {
            case STATUS_ACTIVE ->
                "Active";
            case STATUS_INACTIVE ->
                "Inactive";
            case STATUS_DRAFT ->
                "Draft";
            default ->
                "Unknown";
        };
    }

}
