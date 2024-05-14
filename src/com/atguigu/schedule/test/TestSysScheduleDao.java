package com.atguigu.schedule.test;

import com.atguigu.schedule.dao.SysScheduleDao;
import com.atguigu.schedule.dao.impl.SysScheduleDaoImpl;
import com.atguigu.schedule.pojo.SysSchedule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class TestSysScheduleDao {

    private static SysScheduleDao scheduleDao;
    @BeforeClass
    public static void initScheduleDao(){
        scheduleDao = new SysScheduleDaoImpl();
    }
    @Test
    public void testAddSchedule() {
        int rows = scheduleDao.addSchedule(new SysSchedule(null,2,"学习数据库",1));
        System.out.println(rows);
    }

    @Test
    public void testFindAll() {
        List<SysSchedule> scheduleList = scheduleDao.findAll();
        scheduleDao.findAll().forEach(System.out::println);
    }
}
