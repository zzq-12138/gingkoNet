package com.atguigu.schedule.controller;

import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.service.impl.SysUserServiceImpl;
import com.atguigu.schedule.util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class SysUserController extends BaseController {

    private SysUserService userService = new SysUserServiceImpl();

    /**
     * 接受用户登录请求的业务处理方法（业务接口 不是Java中的interface）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.接收客户端提交的参数
        String username = req.getParameter("username");
        String userPwd = req.getParameter("userPwd");

        // 2.调用服务层方法，根据用户名和密码查询用户信息
        SysUser loginUser = userService.findByUsername(username);
        if (null == loginUser) {
            // 跳转到用户名错误页面
            resp.sendRedirect("/loginUsernameError.html");
        } else if (!MD5Util.encrypt(userPwd).equals(loginUser.getUserPwd())){
            // 跳转到密码错误页面
            resp.sendRedirect("/loginUserPwdError.html");
        } else {
            // 登录成功跳转到首页
            resp.sendRedirect("/showSchedule.html");
        }

        // 3.判断密码是否一致

        // 4.根据登录结果做页面跳转
    }

    /**
     * 接受用户注册请求的业务处理方法（接口 不是Java中的interface）
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
            // 注册失败
            // 3.根据注册结果做页面跳转
            resp.sendRedirect("/registFail.html");
        }
        // 3.根据注册结果做页面跳转
    }
}
