package controller.state;

import model.Model;

public class DispensingState extends ATMState {
    public DispensingState(Model context) {
        super(context);
        stateEnum = StateEnum.DISPENSING;
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
        context.withdrawMoney();

        context.setMessage("Выдано " + context.getEnteredAmount() + " руб. Заберите деньги.");

        if (context.getCardBalance() > 0 && context.getATMCash() > 0) {
            context.setMessage("Деньги выданы! Остаток на карте: " + context.getCardBalance() +
                    " руб. Наличные в банкомате: " + context.getATMCash() +
                    " руб. Введите сумму для следующего снятия или нажмите Отмена.");
            context.setState(new HasMoneyState(context));
            context.setSecondMessage(context.isWaitingForNewAmount()?"Новая сумма (макс "+context.getCardBalance()+"):":
                    "Сумма (макс "+context.getCardBalance()+"):");
            context.setEnabled(true);
            context.resetAmount();
            context.setWaitingForNewAmount(false);
        } else {
            context.setMessage("Деньги выданы! На карте или в банкомате не осталось средств.");
            context.setState(new NoMoneyState(context));
            context.setSecondMessage("Ввод");
            context.setEnabled(false);
        }
    }

    @Override
    public void handleCancel() {
    }
}