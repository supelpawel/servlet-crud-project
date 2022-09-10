package pl.coderslab.users;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserShow", value = "/user/show")
public class UserShow extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String id = request.getParameter("id");
    UserDao userDao = new UserDao();

    User userToShow = userDao.read(Integer.parseInt(id));
    request.setAttribute("user", userToShow);
    getServletContext().getRequestDispatcher("/users/show.jsp").forward(request, response);
  }
}
