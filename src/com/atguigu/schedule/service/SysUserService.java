package com.atguigu.schedule.service;


import com.atguigu.schedule.pojo.SysUser;

/**
 * 该接口定义了以sys_user表为对象的相关操作
 */
public interface SysUserService {
    /**
     * 该方法用于处理用户注册请求
     * @param sysUser 封装了用户注册信息的SysUser对象
     * @return 返回注册结果，成功返回1，失败返回0
     */
    int regist(SysUser sysUser);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回查询到的用户信息，如果没有查询到则返回null
     */
    SysUser findByUsername(String username);
}
