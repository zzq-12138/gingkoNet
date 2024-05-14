package com.atguigu.schedule.dao;

import com.atguigu.schedule.pojo.SysSchedule;

import java.util.List;

/**
 * Data Access Object for SysSchedule
 * 作者：周正奇
 * 日期：2024/05/13
 */
public interface SysScheduleDao {

    /**
     * Add a schedule
     * 添加一个日程
     * @param schedule 日程数据以SystemSchedule对象的形式传入
     * @return 返回值为int类型，表示添加成功的记录数，0表示添加失败，大于0表示添加成功
     */
    int addSchedule(SysSchedule schedule);

    /**
     * 查询所有日程
     * @return 返回值为List<SysSchedule>类型，表示查询到的所有日程数据
     */
    List<SysSchedule> findAll();

}
