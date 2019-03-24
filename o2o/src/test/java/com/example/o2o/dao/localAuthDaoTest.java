package com.example.o2o.dao;

import com.example.o2o.BaseTest;
import com.example.o2o.entity.LocalAuth;
import com.example.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class localAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    private static final String username = "testusername";
    private static final String password = "testpassword";

    @Test
    public void testAInsertLocalAuth() throws Exception{
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testBQueryLocalByUserNameAndPwd() throws Exception{
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username,password);
        long userId = localAuth.getPersonInfo().getUserId();
        assertEquals(1L,userId);
        assertEquals("测试",localAuth.getPersonInfo().getName());
    }

    @Test
    public void testCQueryLocalByUserId() throws Exception{
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("测试",localAuth.getPersonInfo().getName());
    }

    @Test
    public void testDUpdatelocalAuth() throws Exception{
        Date now = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(1L,username,password,"testNewPassword",new Date());
        assertEquals(1,effectedNum);
    }
}
