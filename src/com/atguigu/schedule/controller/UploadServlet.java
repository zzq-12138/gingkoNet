package com.atguigu.schedule.controller;
//

import com.atguigu.schedule.service.UploadService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends ViewBaseServlet implements UploadService {
    private void MakeSureFolderExists(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userName = (String) req.getSession().getAttribute("username");
        if (null == userName) {
            resp.getWriter().write("<script>alert(\"please load first!\")</script>");
            resp.getWriter().close();
            resp.setCharacterEncoding("UTF-8");
            return;
        }

        Part myfile = req.getPart("myfile");
        String fileName = req.getParameter("fileName");
        if (fileName.isEmpty()) {
            fileName = myfile.getSubmittedFileName();
        }
        String realPath = req.getServletContext().getRealPath("/upload/");
        MakeSureFolderExists(realPath + userName);
        try {
            myfile.write(realPath + userName + "/" + fileName);
//        resp.sendRedirect("/allFiles.html");
//        resp.getWriter().write("<script>alert(\"success!\")</script>");
//        System.out.println(realPath);
            req.getRequestDispatcher("/fileList").forward(req, resp);
        } catch (Exception e) {
//        resp.sendRedirect("/allFiles.html");
//        resp.getWriter().write("<script>alert(\"fail!\")</script>");
            req.getRequestDispatcher("/fileList").forward(req, resp);
        }
        resp.getWriter().close();
    }
}
