package com.atguigu.schedule.controller;

import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.service.impl.SysUserServiceImpl;
import com.atguigu.schedule.util.MD5Util;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/user/*")
public class SysUserController extends BaseController {

    // 通过接口调用实现类
    private SysUserService userService = new SysUserServiceImpl();

    /**
     * 接受用户登录请求的业务处理方法（业务接口 不是Java中的interface）
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        // 1.接收客户端提交的参数
        String username = req.getParameter("username");
        String userPwd = req.getParameter("userPwd");

        // 2.调用服务层方法，根据用户名和密码查询用户信息
        SysUser loginUser = userService.findByUsername(username);
        if (null == loginUser || !MD5Util.encrypt(userPwd).equals(loginUser.getUserPwd())) {
            req.setAttribute("errorMsg", "登录失败，请检查邮箱及密码是否正确！");
            super.processTemplate("login", req, resp);
        } else {
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                // 如果存在旧的会话，使其失效
                oldSession.invalidate();

            }
            // 创建新的会话
            HttpSession newSession = req.getSession(true);
            // 在新的会话中设置用户名
            newSession.setAttribute("username", username);

            // 登录成功则重定向到List
            resp.sendRedirect("/schedule_system/file/list");
        }
    }
    /**
     * 接受用户注册请求的业务处理方法（接口 不是Java中的interface）
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.接收客户端提交的参数
        String username = req.getParameter("username");
        String userPwd = req.getParameter("userPwd");

        // 2.调用服务层方法，完成注册功能
        // 将参数放入一个SysUser对象中，传递给service层
        SysUser SysUser = new SysUser(null, username, userPwd);
        int rows = userService.regist(SysUser);
        if (rows > 0) {
            // 注册成功
            // 3.根据注册结果做页面跳转
            resp.sendRedirect("/schedule_system/registSuccess.html");
        } else {
            // 注册失败，设置错误信息并转发回注册页面
            String errorMsg = "注册失败，该用户名已被抢注，或两次密码输入不一致";
            req.setAttribute("errorMsg", errorMsg);
            super.processTemplate("regist", req, resp);
        }
    }

    /**
     * 退出登录的业务处理方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 使 所有的与该用户有关的session 失效
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();

        }

        // 重定向到登录页面
        resp.sendRedirect("/schedule_system/html/login.html");
    }

    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("username") != null);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"loggedIn\": " + loggedIn + "}");
    }

    /**
     * 重写doGet和doPost方法，根据请求路径的不同，调用不同的方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/logout".equals(action)) {
            logout(req, resp);
        } else if ("/checkLogin".equals(action)) {
            checkLogin(req, resp);
        } else {
            // ... 其他代码 ...
            super.doGet(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/login".equals(action)) {
            login(req, resp);
        } else if ("/regist".equals(action)) {
            regist(req, resp);
        } else {
            // ... 其他代码 ...
            super.doPost(req, resp);
        }
    }


}

