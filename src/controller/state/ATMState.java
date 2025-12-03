package controller.state;

import model.Model;

public abstract class ATMState {
    protected Model context;
    protected StateEnum stateEnum;

    public ATMState(Model context) {
        this.context = context;
    }

    public abstract void handlePinEntered(String pin);
    public abstract void handleAmountEntered(int amount);
    public abstract void handleConfirm();
    public abstract void handleCancel();

    public StateEnum getStateEnum() {
        return stateEnum;
    }
}