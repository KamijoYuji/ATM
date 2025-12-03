package controller;

import controller.state.*;
import model.Model;
import view.View;

import javax.swing.*;

public class Controller {
    private static Controller instance;
    private Model model;
    private View view;

    public static Controller getInstance(){
        if(instance == null)
            return instance = new Controller();
        return instance;
    }

    private Controller() {
        this.model = Model.getInstance();
        view = new View(this, model);
    }

    public void processPin(String pin) {
        model.enterPin(pin);
    }

    public void processAmount(String amountStr) {
        try {
            int amount = Integer.parseInt(amountStr);
            if (amount <= 0) {
                model.setMessage("Сумма должна быть больше 0");
                return;
            }
            model.enterAmount(amount);
        } catch (NumberFormatException e) {
            model.setMessage("Введите число!");
        }
    }

    public void processConfirm() {
        model.confirm();
    }

    public void processCancel() {
        model.cancel();
    }

    public String getMessage() {
        return model.getMessage();
    }

    public boolean isPinState() {
        return model.getCurrentState() == StateEnum.PIN_VERIFICATION;
    }

    public boolean isHasMoneyState() {
        return model.getCurrentState() == StateEnum.HAS_MONEY;
    }

    public boolean isWaitingForNewAmount() {
        return model.isWaitingForNewAmount();
    }

    public int getCardBalance() {
        return model.getCardBalance();
    }

    public int getATMCash() {
        return model.getATMCash();
    }
}