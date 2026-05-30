package com.poncheck.enums;

public enum TypeCashMovement {
    SALE(true, false),
    WITHDRAWAL(false, true),
    DEPOSIT(true, true),
    REFUND(false, false),
    EXPENSE(false, true),
    PURCHASE(false, true),
    SALE_CANCELLED(false, false);

    private final boolean addCash;
    private final boolean manualAllowed;

    private TypeCashMovement(boolean addCash, boolean manualAllowed){
        this.addCash = addCash;
        this.manualAllowed = manualAllowed;
    }

    public boolean isAddCash(){
        return addCash;
    }

    public boolean isManualAllowed(){return manualAllowed;}
}
