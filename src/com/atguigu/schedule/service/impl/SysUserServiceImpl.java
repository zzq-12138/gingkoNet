package com.atguigu.schedule.service.impl;

import com.atguigu.schedule.dao.SysUserDao;
import com.atguigu.schedule.dao.impl.SysUserDaoImpl;
import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.util.MD5Util;

public class SysUserServiceImpl implements SysUserService {

    private SysUserDao userDao = new SysUserDaoImpl();

    @Override
    public int regist(SysUser sysUser) {

        // 将用户的明文密码加密为密文密码
        sysUser.setUserPwd(MD5Util.encrypt(sysUser.getUserPwd()));

        // 调用dao层方法，将用户信息保存到数据库
        // 返回值为受影响的行数

        return userDao.addSysUser(sysUser);
    }

    @Override
    public SysUser findByUsername(String username) {

        return userDao.findByUsername(username);
    }
}
