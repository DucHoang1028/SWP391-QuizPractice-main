package DAOs;

import Contexts.DBContext;
import Models.Slider;

import Contexts.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SliderDAO extends DBContext {
    private static final int SEARCH_ALL = 0;
    private static final int SEARCH_BY_TITLE = 1;
    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_INACTIVE = 0;

    // Method to get the active sliders (status = 1) from the database
    public List<Slider> GetActiveSliders() throws SQLException {
        List<Slider> sliders = new ArrayList<>();
        String query = "SELECT slider_id, title, image, back_link, note FROM Slider WHERE status = 1";

        // Try-with-resources to ensure the resources are closed after usage
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Loop through the result set and populate the list of sliders
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSlider_id(rs.getInt("slider_id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBack_link(rs.getString("back_link"));
                slider.setNote(rs.getString("note"));
                sliders.add(slider);
            }
        }
        return sliders;
    }
    public List<Slider> GetSliders(int search, int status, int page, int pageLength, String searchValue) {
        List<Slider> sliders = new ArrayList<>();

        String searchOption = GetSearchOption(search);
        String statusOption = GetStatusOption(status);
        int offset = GetOffset(page, pageLength);

        String query = "SELECT slider_id, title, image, back_link, note, status FROM Slider "
                     + "WHERE 1=1 "
                     + searchOption + " "
                     + statusOption + " "
                     + "ORDER BY slider_id "
                     + "OFFSET ? ROWS "
                     + "FETCH NEXT ? ROWS ONLY";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;

            if (search != SEARCH_ALL) {
                stmt.setString(paramIndex++, "%" + searchValue + "%");
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, pageLength);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSlider_id(rs.getInt("slider_id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBack_link(rs.getString("back_link"));
                slider.setNote(rs.getString("note"));
                slider.setStatus(rs.getInt("status"));

                sliders.add(slider);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sliders;
    }

    private String GetSearchOption(int search) {
        switch (search) {
            case SEARCH_BY_TITLE:
                return "AND title LIKE ?";
            case SEARCH_ALL:
            default:
                return "";
        }
    }

    private String GetStatusOption(int status) {
        if (status == STATUS_ACTIVE) {
            return "AND status = 1";
        } else if (status == STATUS_INACTIVE) {
            return "AND status = 0";
        }
        return "";
    }

    private int GetOffset(int page, int pageLength) {
        return (page - 1) * pageLength;
    }
    public int GetResultLength(int search, int status, String searchValue) {
        int resultLength=0;
        
        return resultLength;
    }

    public void updateSlider(Slider slider) throws SQLException {
        String query = "UPDATE Slider SET title = ?, image = ?, back_link = ?, status = ?, note = ? WHERE slider_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, slider.getTitle());
            stmt.setString(2, slider.getImage());
            stmt.setString(3, slider.getBack_link());
            stmt.setInt(4, slider.getStatus());
            stmt.setString(5, slider.getNote());
            stmt.setInt(6, slider.getSlider_id());

            stmt.executeUpdate();
        }
    }

     public Slider getSliderById(int sliderId) throws SQLException {
        String sql = "SELECT * FROM Sliders WHERE slider_id = ?";
        Slider slider = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, sliderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                slider = new Slider();
                slider.setSlider_id(rs.getInt("slider_id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBack_link(rs.getString("back_link"));
                slider.setStatus(rs.getInt("status"));
                slider.setNote(rs.getString("note"));
            }
        }
        return slider;
    }
    

    

   
}
