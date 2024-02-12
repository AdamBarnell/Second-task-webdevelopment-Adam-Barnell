package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.System.out;

@WebServlet (name="homeservlet",urlPatterns = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        out.println("<h1> Courses</h1>");
        out.println("</body>");
        out.println("<footer><p> Gjord av Adam Barnell </p></footer>");
        out.println("</body>");
        out.println("</html>");
    }
}
