package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/attendence")
public class AttendenceServlet extends HttpServlet {


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
        out.println("<head><title>Hello courses</title></head>");
        out.println("<body>");
        out.println("<h1 class='h1' style=margin-top:10px;> Attendence</h1>");
        Map<String, List<String>> studentCourses = new HashMap<>();
        try {
            String sql = "SELECT students.name AS StudentFirstName, students.lastname AS StudentLastName, courses.name AS CourseName FROM attendance INNER JOIN students ON attendance.`student_id` = students.students_id INNER JOIN courses ON attendance.course_id = courses.courses_id";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String studentFirstName = rs.getString("StudentFirstName");
                String studentLastName = rs.getString("StudentLastName");
                String courseName = rs.getString("CourseName");
                String fullName = studentFirstName + " " + studentLastName;

                studentCourses.putIfAbsent(fullName, new ArrayList<>());
                studentCourses.get(fullName).add(courseName);
            }

            // Startar tabellen och lägger till rubriker
            out.println("<table border='1'>");
            out.println("<tr><th>Student</th><th>Kurser</th></tr>");

            // Loopar igenom varje student och dess kurser för att skapa en tabellrad
            for (Map.Entry<String, List<String>> entry : studentCourses.entrySet()) {
                out.println("<tr><td>" + entry.getKey() + "</td><td>" + String.join(", ", entry.getValue()) + "</td></tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            out.println("Error: " + e);
        }
        out.println("<footer><p> Gjord av Adam Barnell </p></footer>");
        out.println("</body>");
        out.println("</html>");
        System.out.println("GET Request");
    }
}


