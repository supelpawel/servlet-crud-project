package pl.coderslab.users;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "UserList", value = "/user/list")
public class UserList extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserDao userDao = new UserDao();

    request.setAttribute("users", userDao.findAll());
    getServletContext().getRequestDispatcher("/users/list.jsp")
        .forward(request, response);
  }
}
