package controller.state;

public enum StateEnum {
    PIN_VERIFICATION(true, false, false, false),
    HAS_MONEY(false, true, false, false),
    NO_MONEY(false, false, true, false),
    DISPENSING(false, false, false, true);

    private StateEnum(boolean pin, boolean hasMoney, boolean noMoney, boolean dispensing) {
        this.pin = pin;
        this.hasMoney = hasMoney;
        this.noMoney = noMoney;
        this.dispensing = dispensing;
    }
    public boolean pin;
    public boolean hasMoney;
    public boolean noMoney;
    public boolean dispensing;

    @Override
    public String toString() {
        return "StateEnum{" + "pin=" + pin + ", hasMoney=" + hasMoney +
                ", noMoney=" + noMoney + ", dispensing=" + dispensing + '}';
    }
}