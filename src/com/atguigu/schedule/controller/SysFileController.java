package com.atguigu.schedule.controller;

import com.atguigu.schedule.service.impl.SysFileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;

@WebServlet("/file/*")
@MultipartConfig
public class SysFileController extends BaseController {

    private final SysFileServiceImpl fileService = new SysFileServiceImpl();
    private static final String UPLOAD_DIR = "/home/upload"; // 修改为所需的目录

    /**
     * 读取文件列表
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = (String) req.getSession().getAttribute("username");
        String workDir = UPLOAD_DIR + "/" + userName;

        System.out.println(workDir);
        File file = new File(workDir);
        File[] files = file.listFiles();

        HttpSession session = req.getSession();
        session.setAttribute("allFiles", files);

        //用户名显示
        req.setAttribute("username", userName + "的文件");

        req.setAttribute("allFiles", files);
        super.processTemplate("homepage", req, resp);
    }

    /**
     * 上传文件
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userName = (String) req.getSession().getAttribute("username");
        if (null == userName) {
            resp.getWriter().write("<script>alert(\"please load first!\")</script>");
            resp.getWriter().close();
            resp.setCharacterEncoding("UTF-8");
            return;
        }

        Part myFile = req.getPart("myFile");
        String fileName = myFile.getSubmittedFileName();
        String newFileName = req.getParameter("fileName");
        if (null != newFileName && !newFileName.isEmpty()) {
            fileName = newFileName;
        }

        String realPath = UPLOAD_DIR + "/" + userName;

        // 检查用户的文件夹是否存在，如果不存在，那么创建一个新的文件夹
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(realPath + fileName);
        if (file.exists()) {
            String overwrite = req.getParameter("overwrite");
            if ("true".equals(overwrite)) {
                // 用户选择覆盖文件，先删除原来的文件
                file.delete();
                // 保存新的文件
                myFile.write(realPath + "/" + fileName);
            } else {
                // 文件已经存在，返回一个特殊的响应
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
        }
        else {
            // 如果文件不存在，直接保存新的文件
            myFile.write(realPath + "/" + fileName);
//            System.out.println(realPath + "/" + fileName);
        }

        req.getRequestDispatcher("/file/list").forward(req, resp);
        resp.getWriter().close();
    }

    private void MakeSureFolderExists(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileName = req.getParameter("fileName");
        String userPath = getServletContext().getRealPath("/upload");
        String userName = (String) req.getSession().getAttribute("username");
        File file = new File(UPLOAD_DIR + "/" + userName + "/" + fileName);

        if (file.exists() && file.isFile()) {
            file.delete();
            resp.sendRedirect(req.getContextPath() + "/file/list");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要删除的文件不存在</h2>");
            resp.getWriter().close();
        }

    }

    protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从请求参数中获取文件名
        String fileName = req.getParameter("fileName");
        // 获取应用的根目录的真实路径
        String userPath = req.getServletContext().getRealPath("/upload");
        // 获取用户名
        String userName = (String) req.getSession().getAttribute("username");
        // 构造文件路径
        String filePath = UPLOAD_DIR + "/" + userName + "/" + fileName;

        if (!filePath.startsWith(UPLOAD_DIR + "/" + userName + "/")) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>路径有误</h2>");
            resp.getWriter().close();
            return;
        }

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            InputStream in = new FileInputStream(file);
            OutputStream out = resp.getOutputStream();

            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要下载的文件不存在</h2>");
            resp.getWriter().close();
        }
    }

}
