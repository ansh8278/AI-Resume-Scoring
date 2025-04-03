package dao;

import models.Resume;
import utils.EmailUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResumeDAO {

    // Method to establish database connection
    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/smartresume";
        String user = "root";
        String password = "@Ansh12345678";

        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, password);
    }

    // Method to add a resume and send email notifications
    public boolean addResume(Resume resume) {
        String query = "INSERT INTO resumes (name, email, phone, skills, experience, score) VALUES (?, ?, ?, ?, ?, ?)";
        boolean isInserted = false;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, resume.getName());
            stmt.setString(2, resume.getEmail());
            stmt.setString(3, resume.getPhone());
            stmt.setString(4, resume.getSkills());
            stmt.setString(5, resume.getExperience());
            stmt.setInt(6, resume.getScore());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Resume added successfully.");
                isInserted = true;

                // Send email to the candidate
                String candidateSubject = "Resume Submitted Successfully!";
                String candidateMessage = "Hello " + resume.getName() + ",\n\n"
                        + "Thank you for submitting your resume.\n"
                        + "Our team will review it soon.\n\n"
                        + "Best Regards,\nSmart Resume System";
                EmailUtil.sendEmail(resume.getEmail(), candidateSubject, candidateMessage);

                // Send email notification to HR
                String hrEmail = "hr@company.com";  // Replace with actual HR email
                String hrSubject = "New Resume Submission: " + resume.getName();
                String hrMessage = "A new resume has been submitted by " + resume.getName() + " (" + resume.getEmail() + ").\n\n"
                        + "Check the admin dashboard for details.";
                EmailUtil.sendEmail(hrEmail, hrSubject, hrMessage);
            } else {
                System.out.println("⚠️ Failed to insert resume.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error inserting resume:");
            e.printStackTrace();
        }
        return isInserted;
    }

    // Method to retrieve all resumes from the database
    public List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();
        String query = "SELECT * FROM resumes";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
