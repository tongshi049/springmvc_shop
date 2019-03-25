package com.example.o2o.service;

import com.example.o2o.dto.LocalAuthExecution;
import com.example.o2o.entity.LocalAuth;
import com.example.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {
    /**
     *  get the acount by username and password
     * @param username
     * @param password
     * @return
     */
    LocalAuth getLocalAuthByUsernameAndPwd(String username, String password);

    LocalAuth getLocalAuthByUserId(long userId);

    LocalAuthExecution createLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

    /**
     *  Modify the local account password
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword)
            throws LocalAuthOperationException;
}
