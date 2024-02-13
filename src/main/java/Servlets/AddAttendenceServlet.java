package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/addattendence")
public class AddAttendenceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<header><link rel='stylesheet' href='/All.css'></header>");
        out.println("<nav class = 'topnav'>");
        out.println("<li class='homebutton'><a href='index.html'><img src='https://gritacademy.se/wp-content/uploads/2021/05/Grit-Academy-logo.png' width='100' height='50' alt='GritacademyLogo'/></a></li>");
        out.println("<li><a href='http://localhost:9090/students'> Students </a></li>");
        out.println("<li><a href='http://localhost:9090/courses'> Courses </a></li>");
        out.println("<li><a href='http://localhost:9090/attendence'> Attendance </a></li>");
        out.println("<li><a href='http://localhost:9090/addstudent'> Add Student </a></li>");
        out.println("<li><a href='http://localhost:9090/addcourse'> Add Course </a></li>");
        out.println("<li><a href='http://localhost:9090/addattendence'> Add Course to student </a></li>");
        out.println("</nav>");
        out.println("<head><title>Add Student</title></head>");
        out.println("<body>");
        out.println("<h1 class='h1' style=margin-top:10px;>Students/Courses</h1>");
        out.println("<div class='tablecontainer'>");
        try {
            String sql = "SELECT * FROM students";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Starta tabellen och lägger till kolumnnamn
            out.println("<table class='atable'>");
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
        try {
            String sql = "SELECT * FROM courses";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Starta tabellen och lägger till kolumnnamn
            out.println("<table class='atable'>");
            out.println("<tr><th>ID</th><th>YHP</th><th>Name</th><th>Description</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("courses_id");
                String yhp = rs.getString("YHP");
                String name = rs.getString("name");
                String description = rs.getString("description");

                // Lägger till en rad för varje kurs
                out.println("<tr><td>" + id + "</td><td>" + yhp + "</td><td>" + name + "</td><td>" + description + "</td></tr>");
            }

            out.println("</table>");
        } catch (Exception e) {
            out.println("Error: " + e);
        }
        out.println("</div>");
        // Efter att ha skrivit ut tabeller för studenter och kurser
        out.println("<h2>Lägg till kurs till student</h2>");
        out.println("<form action='addattendence' method='POST'>");
        out.println("<label for='studentId'>Välj student:</label>");
        out.println("<select name='studentId' id='studentId'>");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT students_id, name, lastname FROM students");
            while (rs.next()) {
                out.println("<option value='" + rs.getInt("students_id") + "'>" + rs.getString("name") + " " + rs.getString("lastname") + "</option>");
            }
        } catch (Exception e) {
            e.printStackTrace(out);
            out.println("<option>Fel vid laddning av studenter</option>");
        }
        out.println("</select>");

        out.println("<label for='courseId'>Välj kurs:</label>");
        out.println("<select name='courseId' id='courseId'>");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT courses_id, name FROM courses");
            while (rs.next()) {
                out.println("<option value='" + rs.getInt("courses_id") + "'>" + rs.getString("name") + "</option>");
            }
        } catch (Exception e) {
            System.out.println(e);
            out.println("<option>Fel vid laddning av kurser</option>");
        }
        out.println("</select>");

        out.println("<div class='buttoncontainer'>");
        out.println("<input class='backbutton' type='submit' value='Lägg till kurs till student'/>");
        out.println("</div>");
        out.println("</form>");
        out.println("<footer><p>Skapad av Adam Barnell</p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta studentId och courseId från formuläret
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");

            String sql = "INSERT INTO attendance (student_id, course_id) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(studentId));
            pstmt.setInt(2, Integer.parseInt(courseId));

            pstmt.executeUpdate();

            // Omdirigera användaren eller skriv ut ett bekräftelsemeddelande
            response.sendRedirect("/students");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Det gick inte att lägga till kursen för eleven: " + e.getMessage());
        }
    }
}
