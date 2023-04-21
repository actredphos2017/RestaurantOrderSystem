package com.sakuno.restaurant;

import com.sakuno.restaurant.json.CustomerRegisterInfo;
import com.sakuno.restaurant.utils.CustomerAccountManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean registerResult = CustomerAccountManager.getInstance().register(new CustomerRegisterInfo(password, null, username));
        if (registerResult) response.sendRedirect("message.jsp?msg=注册成功！");
        else response.sendRedirect("message.jsp?msg=注册失败！");
    }

    public void destroy() {
    }
}
