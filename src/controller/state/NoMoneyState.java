package controller.state;

import model.Model;

public class NoMoneyState extends ATMState {
    public NoMoneyState(Model context) {
        super(context);
        stateEnum = StateEnum.NO_MONEY;
    }

    @Override
    public StateEnum getStateEnum() {
        return super.getStateEnum();
    }

    @Override
    public void handlePinEntered(String pin) {
    }

    @Override
    public void handleAmountEntered(int amount) {
    }

    @Override
    public void handleConfirm() {
        if (context.getCardBalance() > 0 && context.getATMCash() > 0) {
            context.setMessage("Введите сумму (доступно: " + context.getCardBalance() + " руб.):");
            context.setState(new HasMoneyState(context));
        } else {
            if (context.getCardBalance() <= 0) {
                context.setMessage("На карте нет денег. Баланс: 0 руб.");
            } else {
                context.setMessage("В банкомате нет наличных.");
            }
        }
    }

    @Override
    public void handleCancel() {
        context.setMessage("Операция отменена. Введите PIN:");
        context.setState(new PinState(context));
        context.setEnabled(true);
        context.resetSession();
        context.setSecondMessage("PIN-код (1234):");
    }
}