package com.atguigu.schedule.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UploadService {
    void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;
}
