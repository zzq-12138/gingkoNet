package com.atguigu.schedule.controller;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet("/fileList")
public class ListFileServlet extends ViewBaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        String userName=(String) req.getSession().getAttribute("username");
        String workDir=getServletContext().getRealPath("/upload/")+userName;

        System.out.println(workDir);
        File file=new File(workDir);
        File[] files=file.listFiles();

        HttpSession session=req.getSession();
        session.setAttribute("allFiles",files);

        //用户名显示
        req.setAttribute("username", userName);

        req.setAttribute("allFiles",files);
        super.processTemplate("homepage",req,resp);



    }
}
