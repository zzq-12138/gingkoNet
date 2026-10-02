package com.atguigu.schedule.service.impl;

import com.atguigu.schedule.service.SysFileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SysFileServiceImpl implements SysFileService {

    private static final String UPLOAD_DIR = "/home/upload"; // 修改为所需的目录

    @Override
    public List<File> searchFiles(String username, String query) {
        List<File> result = new ArrayList<>();
        File userDir = new File(UPLOAD_DIR + "/" + username);

        if (userDir.exists() && userDir.isDirectory()) {
            File[] files = userDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains(query)) {
                        result.add(file);
                    }
                }
            }
        }

        return result;
    }
}
