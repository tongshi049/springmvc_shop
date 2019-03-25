package com.example.o2o.web.local;


import com.example.o2o.dto.LocalAuthExecution;
import com.example.o2o.entity.LocalAuth;
import com.example.o2o.entity.PersonInfo;
import com.example.o2o.enums.LocalAuthStateEnum;
import com.example.o2o.exceptions.LocalAuthOperationException;
import com.example.o2o.service.LocalAuthService;
import com.example.o2o.util.CodeUtil;
import com.example.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    @RequestMapping(value = "/buildlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> creatLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // verification code
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "input verification code is wrong");
            return modelMap;
        }
        //get input account
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //get password
        String password = HttpServletRequestUtil.getString(request, "password");
        //get current user info from session
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        // null check
        if (userName != null & password != null && user != null && user.getUserId() != null) {
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            try {
                LocalAuthExecution le = localAuthService.createLocalAuth(localAuth);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "username and password are not allowed to be empty.");
        }
        return modelMap;
    }

    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // verification code
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "input verification code is wrong");
            return modelMap;
        }
        //get input account
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //get password
        String password = HttpServletRequestUtil.getString(request, "password");
        //get newpassword
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        //get current user info from session
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if (userName != null && password != null && newPassword != null
                && user != null && user.getUserId() != null && !password.equals(newPassword)) {
            try {
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUsername().equals(userName)) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "The account is not the current login account");
                    return modelMap;
                }
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(),
                        userName, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Invalid input! ");
        }
        return modelMap;
    }

    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> logincheck(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        boolean needVerify = HttpServletRequestUtil.getBoolean(request,"needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "input verification code is wrong");
            return modelMap;
        }
        //get username
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //get password
        String password = HttpServletRequestUtil.getString(request, "password");

        if(userName != null && password != null){
            LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName,password);
            if(localAuth != null){
                modelMap.put("success",true);
                request.getSession().setAttribute("user",localAuth.getPersonInfo());
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","username and password is not correct! ");
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","username or password cannot be empty");
        }
        return modelMap;
    }


    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> logout(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        request.getSession().setAttribute("user",null);
        modelMap.put("success",true);
        return modelMap;
    }
}
