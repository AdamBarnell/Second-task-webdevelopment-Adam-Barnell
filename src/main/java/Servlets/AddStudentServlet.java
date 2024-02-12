package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.System.out;

@WebServlet("/addstudent")
public class AddStudentServlet extends HttpServlet {

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
        out.println("<h1 class='h1' style=margin-top:10px;>Add Student</h1>");
        out.println("<div class='form-container'>");
        out.println("<form action='/addstudent' method='post' class='input'>");
        out.println("Förnamn: <input type='text' name='name'>");
        out.println("Efternamn: <input type='text' name='lastname'>");
        out.println("Hobby: <input type='text' name='hobby'>");
        out.println("Stad: <input type='text' name='town'>");
        out.println("<input type='submit' value='Lägg till' class='backbutton'>");
        out.println("</form>");
        out.println("</div>");
        out.println("<footer><p>Skapad av Adam Barnell</p></footer>");
        out.println("</body>");
        out.println("</html>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String hobby = request.getParameter("hobby");
        String town = request.getParameter("town");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "Insert", "");
            String sql = "INSERT INTO students (name, lastname, hobby, town) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastname);
            statement.setString(3, hobby);
            statement.setString(4, town);

            int result = statement.executeUpdate();
            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/students"); // Använd en faktisk sida för framgångsrik insättning
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<!DOCTYPE html><html><head><title>Fel</title></head><body>");
                out.println("<p>Ett fel uppstod vid registrering av studenten.</p>");
                out.println("</body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<!DOCTYPE html><html><head><title>Databasfel</title></head><body>");
            out.println("<p>Databasfel: " + e.getMessage() + "</p>");
            out.println("</body></html>");
        }
    }

}
