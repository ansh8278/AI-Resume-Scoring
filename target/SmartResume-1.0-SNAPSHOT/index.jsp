<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Smart Resume Submission</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        /* Soft and modern UI styling */
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(to right, #e0f7fa, #f1f8e9);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 100%;
            max-width: 500px;
            text-align: center;
        }
        h2 {
            color: #333;
            font-weight: 600;
            margin-bottom: 20px;
        }
        .form-group input,
        .form-group textarea {
            border-radius: 8px;
            border: 1px solid #ddd;
            padding: 12px;
            width: 100%;
            font-size: 16px;
            transition: 0.3s ease;
        }
        .form-group input:focus,
        .form-group textarea:focus {
            border-color: #009688;
            box-shadow: 0 0 8px rgba(0, 150, 136, 0.2);
            outline: none;
        }
        .form-group textarea {
            resize: none;
            height: 120px;
        }
        .btn-submit {
            background: linear-gradient(to right, #26c6da, #009688);
            color: white;
            border-radius: 8px;
            padding: 12px;
            font-size: 18px;
            width: 100%;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease-in-out;
        }
        .btn-submit:hover {
            background: linear-gradient(to right, #009688, #00796b);
            transform: scale(1.05);
        }
        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Submit Your Resume</h2>
        <form action="ResumeServlet" method="post">
            <div class="form-group">
                <input type="text" name="name" class="form-control" placeholder="Full Name" required>
            </div>
            <div class="form-group">
                <input type="email" name="email" class="form-control" placeholder="Email" required>
            </div>
            <div class="form-group">
                <input type="text" name="phone" class="form-control" placeholder="Phone" required>
            </div>
            <div class="form-group">
                <textarea name="skills" class="form-control" placeholder="Enter skills (comma-separated)" required></textarea>
            </div>
            <div class="form-group">
                <textarea name="experience" class="form-control" placeholder="Describe your experience" required></textarea>
            </div>
            <button type="submit" class="btn btn-submit">Submit Resume</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
