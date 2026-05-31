package com.poncheck.enums;

public enum TypeInventoryMovement {

    PURCHASE(true, true),
    PRODUCTION(true, true),
    SALE_CANCELLED(true, false),
    ADJUSTMENT_IN(true, true),
    SALE(false, false),
    GIFT(false, true),
    COURTESY(false, true),
    SAMPLING(false, true),
    WASTE(false, true),
    ADJUSTMENT_OUT(true, true);

    private final boolean addsStock;
    private final boolean manualAllowed;

    TypeInventoryMovement(boolean addsStock, boolean manualAllowed) {
        this.addsStock = addsStock;
        this.manualAllowed = manualAllowed;
    }

    public boolean isAddsStock(){
        return addsStock;
    }
    public boolean isManualAllowed(){return manualAllowed;}
}
