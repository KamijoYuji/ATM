package controller.state;

import model.Model;

public class HasMoneyState extends ATMState {
    public HasMoneyState(Model context) {
        super(context);
        stateEnum = StateEnum.HAS_MONEY;
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
        if (context.getEnteredAmount() <= 0) {
            context.setMessage("Введите сумму больше 0");
            return;
        }

        if (context.hasEnoughMoney()) {
            context.setMessage("Одобрено! Выдаём " + context.getEnteredAmount() + " руб.");
            context.setState(context.getDispensingState());
        } else {
            if (context.getEnteredAmount() > context.getCardBalance()) {
                context.setMessage("Недостаточно средств на карте. Ваш баланс: " +
                        context.getCardBalance() + " руб. Введите другую сумму:");
            } else if (context.getEnteredAmount() > context.getATMCash()) {
                context.setMessage("В банкомате недостаточно наличных. Доступно: " +
                        context.getATMCash() + " руб. Введите меньшую сумму:");
            }
            context.setWaitingForNewAmount(true);
            context.resetAmount();
        }
    }

    @Override
    public void handleCancel() {
        context.setMessage("Операция отменена. Введите PIN:");
        context.setState(context.getPinState());
        context.resetSession();
    }
}