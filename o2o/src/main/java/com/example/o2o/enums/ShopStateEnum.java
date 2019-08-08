package com.example.o2o.enums;

public enum ShopStateEnum {
    CHECK(0, "under review"), OFFLINE(-1, "illegal shop"),
    SUCCESS(1, "operation successfully"), PASS(2, "approved"),
    INNER_ERROR(-1001, "inner error"), NULL_SHOPID(-1002, "ShopId is empty"),
    NULL_SHOP(-1003,"shop is empty");

    private int state;
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * return stateEnum given state code.
     * if state code does not exist, return null.
     */
    public static ShopStateEnum stateOf(int state) {
        for (ShopStateEnum stateEnum: values()) {
            if (stateEnum.getState() == state)
                return stateEnum;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
