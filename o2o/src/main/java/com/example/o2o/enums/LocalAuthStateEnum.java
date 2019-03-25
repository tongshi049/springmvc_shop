package com.example.o2o.enums;

public enum LocalAuthStateEnum {

    LOGINFAIL(-1, "Password or username dismatched !"),
    SUCCESS(0, "Operation Success"),
    NULL_AUTH_INFO(-1006,"Empty account Info"),
    ONLY_ONE_ACCOUNT(-1007, "Only One Account Allowed");

    private int state;
    private String stateInfo;

    private LocalAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
    public static LocalAuthStateEnum stateOf(int index){
        for (LocalAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
