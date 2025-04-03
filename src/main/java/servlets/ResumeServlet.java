package servlets;

import models.Resume;
import dao.ResumeDAO;
import utils.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Fetch form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String skills = request.getParameter("skills");
        String experience = request.getParameter("experience");

        // Validate input fields
        if (name == null || name.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            phone == null || phone.trim().isEmpty() || 
            skills == null || skills.trim().isEmpty() || 
            experience == null || experience.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Calculate resume score
        int score = calculateResumeScore(skills, experience);

        // Create a Resume object
        Resume resume = new Resume(name, email, phone, skills, experience, score);

        // Add resume to the database
        ResumeDAO resumeDAO = new ResumeDAO();
        boolean inserted = resumeDAO.addResume(resume);

        if (inserted) {
            // Send confirmation email to the candidate
            String candidateSubject = "Resume Submitted Successfully!";
            String candidateMessage = "Hello " + name + ",\n\nThank you for submitting your resume.\n"
                    + "Our team will review it soon.\n\nBest Regards,\nSmart Resume System";
            EmailUtil.sendEmail(email, candidateSubject, candidateMessage);

            // Notify HR about the new resume
            String hrEmail = "hr@company.com";  // Replace with actual HR email
            String hrSubject = "New Resume Submitted: " + name;
            String hrMessage = "A new resume has been submitted by " + name + " (" + email + ").\n\n"
                    + "Check the admin dashboard for details.";
            EmailUtil.sendEmail(hrEmail, hrSubject, hrMessage);

            // Redirect to dashboard
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("error.jsp");  // Redirect to an error page
        }
    }

    // Method to calculate the resume score based on skills and experience
    private int calculateResumeScore(String skills, String experience) {
        int skillCount = skills.split(",").length;
        int experienceYears = experience.length() / 50;
        int scoreFromSkills = skillCount * 10;
        int scoreFromExperience = experienceYears * 20;
        return scoreFromSkills + scoreFromExperience;
    }
}
