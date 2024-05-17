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

@WebServlet("/user/*")
public class SysUserController extends BaseController {

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

        // 3.判断密码是否一致

        // 4.根据登录结果做页面跳转
        SysUser loginUser = userService.findByUsername(username);
        if (null == loginUser || !MD5Util.encrypt(userPwd).equals(loginUser.getUserPwd())) {
            req.setAttribute("errorMsg", "登录失败，请检查邮箱及密码是否正确！");
            super.processTemplate("login", req, resp);
        } else {
            // 登录成功，将用户信息存入session
            req.getSession().setAttribute("username", username);
            // 登录成功则重定向到fileList
            resp.sendRedirect("/fileList");
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
            resp.sendRedirect("/registSuccess.html");
        } else {
            req.setAttribute("errorMsg", "注册失败，该用户名已被抢注，或两次密码输入不一致");
            super.processTemplate("regist", req, resp);
        }
        // 3.根据注册结果做页面跳转
    }
}

