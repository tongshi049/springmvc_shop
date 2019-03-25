package com.example.o2o.dto;

import com.example.o2o.entity.LocalAuth;
import com.example.o2o.enums.LocalAuthStateEnum;

import java.util.List;

public class LocalAuthExecution {

    // result state
    private int state;
    // state info
    private String stateInfo;

    private LocalAuth localAuth;

    private List<LocalAuth> localAuthList;

    public LocalAuthExecution() {

    }

    public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public LocalAuthExecution(LocalAuthStateEnum stateEnum, LocalAuth localAuth) {
        this.localAuth = localAuth;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public LocalAuthExecution(LocalAuthStateEnum stateEnum, List<LocalAuth> localAuthList) {
        this.localAuthList = localAuthList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public List<LocalAuth> getLocalAuthList() {
        return localAuthList;
    }
}
