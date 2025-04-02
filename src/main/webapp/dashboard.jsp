<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resume Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <style>
        body {
            background-color: #f4f6f9;
        }
        .container {
            margin-top: 50px;
        }
        h2 {
            text-align: center;
            color: #007bff;
            font-weight: bold;
            margin-bottom: 30px;
        }
        .table-container {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
        }
        .table-hover tbody tr:hover {
            background-color: #e9f5ff;
            transition: 0.3s;
        }
        .resume-score {
            font-weight: bold;
            color: #28a745;
            font-size: 1.1em;
            animation: fadeIn 1s;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        .search-bar {
            width: 100%;
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            outline: none;
        }
        .dark-mode {
            background-color: #1e1e2d;
            color: white;
        }
        .dark-mode .table-container {
            background: #292b3a;
            color: white;
        }
        .dark-mode .table thead {
            background: #44475a !important;
        }
        .toggle-btn {
            position: fixed;
            top: 20px;
            right: 20px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <button class="btn btn-dark toggle-btn" onclick="toggleDarkMode()">Dark Mode</button>
    <div class="container">
        <h2><i class="fas fa-user-tie"></i> Submitted Resumes</h2>
        <input type="text" id="search" class="search-bar" placeholder="Search resumes...">
        <div class="table-container">
            <table class="table table-hover table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Skills</th>
                        <th>Experience</th>
                        <th>AI Resume Score</th>
                    </tr>
                </thead>
                <tbody id="resumeTable">
                    <%
                        dao.ResumeDAO resumeDAO = new dao.ResumeDAO();
                        java.util.List<models.Resume> resumes = resumeDAO.getResumes();
                        for (models.Resume resume : resumes) {
                    %>
                    <tr>
                        <td><%= resume.getName() %></td>
                        <td><%= resume.getEmail() %></td>
                        <td><%= resume.getPhone() %></td>
                        <td><%= resume.getSkills() %></td>
                        <td><%= resume.getExperience() %></td>
                        <td class="resume-score"><%= resume.getScore() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#search').on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#resumeTable tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });

        function toggleDarkMode() {
            document.body.classList.toggle("dark-mode");
        }
    </script>
</body>
</html>
