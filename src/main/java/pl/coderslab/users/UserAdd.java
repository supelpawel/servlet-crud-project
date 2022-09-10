package pl.coderslab.users;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserAdd", value = "/user/add")
public class UserAdd extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User user = new User();
    UserDao userDao = new UserDao();

    user.setUserName(request.getParameter("userName"));
    user.setUserEmail(request.getParameter("userEmail"));
    user.setUserPassword(request.getParameter("userPassword"));
    userDao.create(user);
    response.sendRedirect(request.getContextPath() + "/user/list");
  }
}
