package com.atguigu.schedule.listenter;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext context = session.getServletContext();
        Map<String, HttpSession> userSessions = (Map<String, HttpSession>) context.getAttribute("userSessions");
        if (userSessions == null) {
            userSessions = new HashMap<>();
            context.setAttribute("userSessions", userSessions);
        }
        String username = (String) session.getAttribute("username");
        if (username != null) {
            HttpSession oldSession = userSessions.get(username);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            userSessions.put(username, session);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext context = session.getServletContext();
        Map<String, HttpSession> userSessions = (Map<String, HttpSession>) context.getAttribute("userSessions");
        if (userSessions != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                userSessions.remove(username);
                // 从在线用户列表中移除
                context.setAttribute("userSessions", userSessions);
               
            }
        }
    }
}