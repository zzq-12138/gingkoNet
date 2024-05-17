package com.atguigu.schedule.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 下载文件的Servlet
 */
@WebServlet("/download")
public class DownloadServlert extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fileName = req.getParameter("fileName");
        String userPath = getServletContext().getRealPath("/upload");
        String userName=(String)req.getSession().getAttribute("username");
        File file = new File(userPath + "/" + userName + "/" + fileName);
        System.out.println(userPath + "/" +userName + "/" + fileName);

        if (file.exists() && file.isFile()) {
            resp.setContentType("application/x-msdownload");
            resp.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            FileInputStream is = new FileInputStream(file);
            ServletOutputStream os = resp.getOutputStream();
            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = is.read(temp)) != -1) {
                os.write(temp, 0, len);
            }
            os.close();
            is.close();
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要下载的文件不存在</h2>");
            resp.getWriter().close();
        }
    }


}
