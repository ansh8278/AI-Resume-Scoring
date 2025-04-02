package dao;

import models.Resume;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResumeDAO {

    // Method to establish database connection
    private Connection connect() throws SQLException {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/smartresume";
        String user = "root";
        String password = "@Ansh12345678";

        // Load MySQL JDBC Driver (optional but recommended)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }

        // Establish and return the connection
        return DriverManager.getConnection(url, user, password);
    }

    // Method to add a resume to the database
    public void addResume(Resume resume) {
        String query = "INSERT INTO resumes (name, email, phone, skills, experience, score) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters for the insert statement
            stmt.setString(1, resume.getName());
            stmt.setString(2, resume.getEmail());
            stmt.setString(3, resume.getPhone());
            stmt.setString(4, resume.getSkills());
            stmt.setString(5, resume.getExperience());
            stmt.setInt(6, resume.getScore());

            // Execute the insert query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Resume added successfully.");
            } else {
                System.out.println("⚠️ Failed to insert resume.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error inserting resume:");
            e.printStackTrace();
        }
    }

    // Method to retrieve all resumes from the database
    public List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();
        String query = "SELECT * FROM resumes";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Process the result set and add each resume to the list
            while (rs.next()) {
                Resume resume = new Resume(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("skills"),
                        rs.getString("experience"),
                        rs.getInt("score")
                );
                resumes.add(resume);
            }

            System.out.println("✅ Retrieved " + resumes.size() + " resumes from the database.");
        } catch (SQLException e) {
            System.out.println("❌ Error fetching resumes:");
            e.printStackTrace();
        }

        return resumes;
    }
}
