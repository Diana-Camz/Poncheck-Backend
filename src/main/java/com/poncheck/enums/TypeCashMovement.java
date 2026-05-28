package com.poncheck.enums;

public enum TypeCashMovement {
    SALE(true),
    WITHDRAWAL(false),
    DEPOSIT(true),
    REFUND(false),
    EXPENSE(false);

    private final boolean addCash;

    private TypeCashMovement(boolean addCash){
        this.addCash = addCash;
    }

    public boolean isAddCash(){
        return addCash;
    }
}
