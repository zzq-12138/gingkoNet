package com.atguigu.schedule.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/deleteFile")
public class DeleteFileServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        String userPath = getServletContext().getRealPath("/upload");
        String userName = (String) req.getSession().getAttribute("username");
        File file = new File(userPath + "/" + userName + "/" + fileName);

        if (file.exists() && file.isFile()) {
            file.delete();
            resp.sendRedirect(req.getContextPath() + "/fileList");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要删除的文件不存在</h2>");
            resp.getWriter().close();
        }
    }
}
