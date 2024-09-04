package com.atguigu.schedule.dao;

/*
* Data Access Object for SysUser
* 用于定义针对SysUser的数据访问操作
* */

import com.atguigu.schedule.pojo.SysUser;

public interface SysUserDao {

    /**
     * 该方法用于将用户信息保存到数据库（增加一条用户记录）
     * @param sysUser 封装了用户信息的SysUser对象（username 和 userPwd）
     * @return 返回受影响的行数
     */
    int addSysUser(SysUser sysUser);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回查询到的用户信息，如果没有查询到则返回null
     */
    SysUser findByUsername(String username);
}
