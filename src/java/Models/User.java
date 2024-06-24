

package Models;

import java.time.LocalDateTime;

public class User extends BaseModel {
    
    private int user_id;
    private String full_name;
    private int gender;
    private String email;
    private String mobile;
    private String password_hash;
    private int role;
    private String avatar;
    private int status;
    private LocalDateTime locked_until;
    
    // constants for User
    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_UNREGISTERED = 2;
    public static final int STATUS_LOCKED = 3;
    
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 0;
    
    
    

    public User() {
    }

    public User(int user_id, String full_name, int gender, String email, 
            String mobile, String password_hash, int role, String avatar, 
            LocalDateTime created_at, LocalDateTime updated_at, int status, 
            LocalDateTime locked_until) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.password_hash = password_hash;
        this.role = role;
        this.avatar = avatar;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.locked_until = locked_until;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public int getRole() {
        return role;
    }
    public String getRoleName()
    {
        return Role.getRoleName(this.role);
    }
    public void setRole(int role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getLocked_until() {
        return locked_until;
    }

    public void setLocked_until(LocalDateTime locked_until) {
        this.locked_until = locked_until;
    }
    
    public String getStatusName(int status) {
        return switch (status) {
            case STATUS_INACTIVE -> "Inactive";
            case STATUS_ACTIVE -> "Active";
            case STATUS_UNREGISTERED -> "Unregistered";
            case STATUS_LOCKED -> "Locked";
            default -> "Unknown";
        };
    }
    
    public String getGenderName() {
        return switch (this.gender) {
            case GENDER_MALE -> "Male";
            case GENDER_FEMALE -> "Female";
            default -> "Unknown";
        };
    }
    public boolean isLocked()
    {
        return this.status == STATUS_LOCKED;
    }
    
}
