package com.example.o2o.dao;

import com.example.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LocalAuthDao {

    /**
     * get the Local auth account by username and password for login Service
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username,@Param("password") String password);

    /**
     * Get the account by userId
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    /**
     * Insert an account to the database
     * @param localAuth
     * @return the effected ro
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * update information of particular user
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
                        @Param("password") String password, @Param("newPassword") String newPassword,
                        @Param("lastEditTime")Date lastEditTime);


}
