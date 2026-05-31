package com.poncheck.enums;

public enum TypeMovement {

    PURCHASE(true),
    PRODUCTION(true),
    SALE_CANCELLED(true),
    ADJUSTMENT_IN(true),
    SALE(false),
    GIFT(false),
    COURTESY(false),
    SAMPLING(false),
    WASTE(false),
    ADJUSTMENT_OUT(true);

    private final boolean addsStock;

    TypeMovement(boolean addsStock) {
        this.addsStock = addsStock;
    }

    public boolean isAddsStock(){
        return addsStock;
    }
}
