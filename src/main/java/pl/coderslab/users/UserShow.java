package pl.coderslab.users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UserShow", value = "/user/show")
public class UserShow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        UserDao userDao = new UserDao();
        User userToShow = userDao.read(Integer.parseInt(id));

        request.setAttribute("user", userToShow);
        getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
    }
}
