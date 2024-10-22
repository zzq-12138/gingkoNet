package com.atguigu.schedule.service;

import java.io.File;
import java.util.List;

public interface SysFileService  {
    List<File> searchFiles(String username, String query);

}
