package pl.coderslab.users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserDelete", value = "/user/delete")
public class UserDelete extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserDao userDao = new UserDao();

    userDao.delete(Integer.parseInt(request.getParameter("id")));
    response.sendRedirect(request.getContextPath() + "/user/list");
  }
}
