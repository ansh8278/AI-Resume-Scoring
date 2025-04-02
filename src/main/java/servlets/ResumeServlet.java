package servlets;

import models.Resume;
import dao.ResumeDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    // Handle POST request to submit the resume
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Fetch parameters from the form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String skills = request.getParameter("skills");
        String experience = request.getParameter("experience");

        // Validate the input fields (basic checks)
        if (name == null || name.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            phone == null || phone.trim().isEmpty() || 
            skills == null || skills.trim().isEmpty() || 
            experience == null || experience.trim().isEmpty()) {
                
            // Set an error message if validation fails
            request.setAttribute("errorMessage", "All fields are required!");
            // Forward to the index page to show the error message
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Calculate resume score based on skills and experience
        int score = calculateResumeScore(skills, experience);

        // Create a new Resume object with the data
        Resume resume = new Resume(name, email, phone, skills, experience, score);

        // Add the resume to the database via ResumeDAO
        ResumeDAO resumeDAO = new ResumeDAO();
        resumeDAO.addResume(resume);

        // Redirect to the dashboard after submission
        response.sendRedirect("dashboard.jsp");
    }

    // Method to calculate the resume score based on skills and experience
    private int calculateResumeScore(String skills, String experience) {
        // Count the number of skills by splitting the string by commas
        int skillCount = skills.split(",").length;

        // Estimate years of experience by calculating the number of sentences or characters in experience
        // Here, we assume 50 characters roughly correspond to 1 year of experience
        int experienceYears = experience.length() / 50;

        // Calculate the score: 10 points per skill and 20 points per year of experience
        int scoreFromSkills = skillCount * 10;
        int scoreFromExperience = experienceYears * 20;

        // Return the total score
        return scoreFromSkills + scoreFromExperience;
    }

    // Optionally, you can override the doGet method if you want to handle GET requests (like form pre-filling or other features)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For now, we don't need to do anything on GET request
        response.sendRedirect("index.jsp");
    }
}
