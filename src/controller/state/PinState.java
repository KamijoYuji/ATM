package controller.state;

import model.Model;

public class PinState extends ATMState {
    public PinState(Model context) {
        super(context);
        stateEnum = StateEnum.PIN_VERIFICATION;
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
        if (context.isPinCorrect()) {
            if (context.getCardBalance() > 0 && context.getATMCash() > 0) {
                context.setMessage("PIN верный! Баланс карты: " + context.getCardBalance() +
                        " руб. Наличные в банкомате: " + context.getATMCash() +
                        " руб. Введите сумму:");
                context.setState(new HasMoneyState(context));
                context.setSecondMessage(context.isWaitingForNewAmount()?"Новая сумма (макс "+context.getCardBalance()+"):":
                        "Сумма (макс "+context.getCardBalance()+"):");
                context.setEnabled(true);
            } else {
                context.setEnabled(false);
                context.setMessage("PIN верный! Но на карте или в банкомате нет денег.");
                context.setSecondMessage("Ввод");
                context.setState(new NoMoneyState(context));

            }
        } else {
            context.setMessage("Неверный PIN! Попробуйте снова");
            context.resetPin();
        }
    }

    @Override
    public void handleCancel() {
        context.setMessage("Введите PIN (тест: 1234)");
        context.resetPin();
    }
}