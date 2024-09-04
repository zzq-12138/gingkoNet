package com.atguigu.schedule.pojo;

import lombok.*;

@AllArgsConstructor//生成全参构造器
@NoArgsConstructor//生成无参构造器
@Data//生成get set方法


public class SysUser {
    private Integer uid;
    private String username;
    private String userPwd;
}
