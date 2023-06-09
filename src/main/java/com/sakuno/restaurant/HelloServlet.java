package com.sakuno.restaurant;

import java.io.*;

import com.sakuno.restaurant.json.CustomerRegisterInfo;
import com.sakuno.restaurant.utils.CustomerAccountManager;
import com.sakuno.restaurant.utils.DatabaseEntrance;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("hello.html");
        dispatcher.forward(request, response);
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