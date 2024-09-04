package com.atguigu.schedule.dao.impl;

import com.atguigu.schedule.dao.BaseDao;
import com.atguigu.schedule.dao.SysUserDao;
import com.atguigu.schedule.pojo.SysUser;

import java.util.List;

public class SysUserDaoImpl extends BaseDao implements SysUserDao {

    @Override
    public int addSysUser(SysUser sysUser) {
        String sql = "insert into sys_user values(Default, ?, ?)";
        return baseUpdate(sql, sysUser.getUsername(), sysUser.getUserPwd());
    }

    @Override
    public SysUser findByUsername(String username) {
        String sql = "select uid, username, user_pwd userPwd from sys_user where username = ?";
        List<SysUser> sysUserList = baseQuery(SysUser.class, sql, username);
        return sysUserList != null && !sysUserList.isEmpty() ? sysUserList.get(0) : null;
    }

    @Override
    public SysUser findByNamePwd(String username, String userPwd) {
        // 只查询 username 字段
        String sql = "SELECT username FROM sys_user WHERE username = '" + username + "' AND user_pwd = '" + userPwd + "'";
        List<SysUser> sysUserList = baseQuery(SysUser.class, sql);
        return sysUserList != null && !sysUserList.isEmpty() ? sysUserList.get(0) : null;
    }
}