package pl.coderslab.users;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserEdit", value = "/user/edit")
public class UserEdit extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String id = request.getParameter("id");
    UserDao userDao = new UserDao();

    User read = userDao.read(Integer.parseInt(id));
    request.setAttribute("user", read);
    getServletContext().getRequestDispatcher("/users/edit.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User user = new User();
    UserDao userDao = new UserDao();

    user.setId(Integer.parseInt(request.getParameter("id")));
    user.setUserName(request.getParameter("userName"));
    user.setUserEmail(request.getParameter("userEmail"));
    user.setUserPassword(request.getParameter("userPassword"));
    userDao.update(user);
    response.sendRedirect(request.getContextPath() + "/user/list");
  }
}
