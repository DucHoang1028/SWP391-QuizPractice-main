package DAOs;

import Contexts.DBContext;
import Models.Subject;
import Models.PricePackage;
import Models.Registrations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationsDAO extends DBContext {
    public List<Registrations> getUserRegistrations(int userId) {
        List<Registrations> registrations = new ArrayList<>();
        String sql = "SELECT * FROM Registrations WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Registrations reg = new Registrations();
                reg.setId(rs.getInt("id"));
                reg.setRegistrationTime(rs.getTimestamp("registration_time").toLocalDateTime());
                reg.setTotalCost(rs.getBigDecimal("total_cost"));
                reg.setStatus(rs.getInt("status"));
                reg.setValidFrom(rs.getTimestamp("valid_from").toLocalDateTime());
                reg.setValidTo(rs.getTimestamp("valid_to").toLocalDateTime());
                
                SubjectDAO subjectDAO = new SubjectDAO();
                Subject subject = subjectDAO.getSubjectById(rs.getInt("subject_id"));
                reg.setSubject(subject);
                
                // Fetch price package
                List<PricePackage> pricePackages = subjectDAO.getPricePackagesBySubjectId(subject.getSubject_id());
                if (!pricePackages.isEmpty()) {
                    reg.setPricePackage(pricePackages.get(0));  // Assuming there's at least one price package
                }
                
                registrations.add(reg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public void cancelRegistration(int registrationId) {
        String sql = "UPDATE Registrations SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, Registrations.STATUS_CANCELLED);
            ps.setInt(2, registrationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addRegistration(Registrations registrations) throws SQLException {
    String sql = "INSERT INTO Registrations (user_id, subject_id, price_package_id, registration_time, total_cost, status, valid_from, valid_to) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, registrations.getUser().getUser_id());
        ps.setInt(2, registrations.getSubject().getSubject_id());
        ps.setInt(3, registrations.getPricePackage().getPackage_id());
        ps.setTimestamp(4, Timestamp.valueOf(registrations.getRegistrationTime()));
        ps.setBigDecimal(5, registrations.getTotalCost());
        ps.setInt(6, registrations.getStatus());
        ps.setTimestamp(7, Timestamp.valueOf(registrations.getValidFrom()));
        ps.setTimestamp(8, Timestamp.valueOf(registrations.getValidTo()));
        ps.executeUpdate();
    }
}
      public void createRegistration(Registrations registrations) throws SQLException {
        String sql = "INSERT INTO Registrations (user_id, subject_id, registration_time, package_id, total_cost, status, valid_from, valid_to) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, registrations.getUser().getUser_id());
            ps.setInt(2, registrations.getSubject().getSubject_id());
            ps.setTimestamp(3, Timestamp.valueOf(registrations.getRegistrationTime()));
            ps.setInt(4, registrations.getPricePackage().getPackage_id());
            ps.setBigDecimal(5, registrations.getTotalCost());
            ps.setInt(6, registrations.getStatus());
            ps.setTimestamp(7, Timestamp.valueOf(registrations.getValidFrom()));
            ps.setTimestamp(8, Timestamp.valueOf(registrations.getValidTo()));
            
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

}

