package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(urlPatterns = "/students")
public class StudentsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<header><link rel =stylesheet href=/All.css> </link></header>");
        out.println("<nav class = 'topnav'>");
        out.println("<li class='homebutton'><a href='index.html'><img src='https://gritacademy.se/wp-content/uploads/2021/05/Grit-Academy-logo.png' width='100' height='50' alt='GritacademyLogo'/></a></li>");
        out.println("<li><a href='http://localhost:9090/students'> Students </a></li>");
        out.println("<li><a href='http://localhost:9090/courses'> Courses </a></li>");
        out.println("<li><a href='http://localhost:9090/attendence'> Attendance </a></li>");
        out.println("<li><a href='http://localhost:9090/addstudent'> Add Student </a></li>");
        out.println("<li><a href='http://localhost:9090/addcourse'> Add Course </a></li>");
        out.println("<li><a href='http://localhost:9090/addattendence'> Add Course to student </a></li>");
        out.println("</nav>");
        out.println("<head><title>Hello Students</title></head>");
        out.println("<body>");
        out.println("<h1 class=h1> Students</h1>");
        try {
            String sql = "SELECT * FROM students";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "usergrit", "usergrit");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Starta tabellen och lägger till kolumnnamn
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Namn</th><th>Efternamn</th><th>Hobby</th><th>Stad</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("students_id");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                String hobby = rs.getString("hobby");
                String town = rs.getString("town");

                // Lägger till en rad för varje elev
                out.println("<tr><td>" + id + "</td><td>" + name + "</td><td>" + lastname + "</td><td>" + hobby + "</td><td>" + town + "</td></tr>");
            }

            out.println("</table>");
        } catch (Exception e) {
            out.println("Error: " + e);
        }
        out.println("<div class='form-container'>");
        out.println("<form action='/students' method='POST' class='input'>");
        out.println("Förnamn: <input type='text' name='fname'> ");
        out.println("Efternamn: <input type='text' name='lname'> ");
        out.println("<input type='submit' value='Sök' class='backbutton'>");
        out.println("</form>");
        out.println("</div>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");

        fname = (fname != null) ? fname.trim() : "";
        lname = (lname != null) ? lname.trim() : "";

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            String sql = String.format(
                    "SELECT courses.courses_id as id, courses.name AS CourseName, courses.description AS description, courses.YHP AS YHP " +
                            "FROM attendance " +
                            "INNER JOIN students ON attendance.`student_id` = students.students_id " +
                            "INNER JOIN courses ON attendance.course_id = courses.courses_id " +
                            "WHERE students.name = '%s' AND students.lastname = '%s'", fname, lname);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<html>");
            out.println("<header><link rel='stylesheet' href='/All.css'> </link></header>");
            out.println("<nav class = 'topnav'>");
            out.println("<li class='homebutton'><a href='index.html'><img src='https://gritacademy.se/wp-content/uploads/2021/05/Grit-Academy-logo.png' width='100' height='50' alt='GritacademyLogo'/></a></li>");
            out.println("<li><a href='http://localhost:9090/students'> Students </a></li>");
            out.println("<li><a href='http://localhost:9090/courses'> Courses </a></li>");
            out.println("<li><a href='http://localhost:9090/attendence'> Attendance </a></li>");
            out.println("<li><a href='http://localhost:9090/addstudent'> Add Student </a></li>");
            out.println("<li><a href='http://localhost:9090/addcourse'> Add Course </a></li>");
            out.println("<li><a href='http://localhost:9090/addattendence'> Add Course to student </a></li>");
            out.println("</nav>");
            out.println("<head><title>Search Results</title></head>");
            out.println("<body>");
            out.println("<h1 class='h1'>Search Results</h1>");


            if (!rs.isBeforeFirst()) {
                out.println("<p style=color:red;>No courses found for the student: " + fname + " " + lname + "</p>");
            } else {
                out.println("<table>");
                out.println("<h2 class='h2'>" + fname + " " + lname + "</h2>");
                out.println("<tr><th>Course ID</th><th>Course Name</th><th>Description</th><th>YHP</th></tr>");

                while (rs.next()) {
                    int courseId = rs.getInt("id");
                    String courseName = rs.getString("CourseName");
                    String description = rs.getString("description");
                    String yhp = rs.getString("YHP");
                    out.println("<tr><td>" + courseId + "</td><td>" + courseName + "</td><td>" + description + "</td><td>" + yhp + "</td></tr>");
                }

                out.println("</table>");
            }
            out.println("<div class='buttoncontainer'>");
            out.println("<a href='/students' class='backbutton'>Back to All Students</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace(out);
        }
        out.println("<footer><p> Gjord av Adam Barnell </p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

}

