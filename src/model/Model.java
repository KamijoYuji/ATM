package model;

import controller.state.*;

import java.util.Observable;

public class Model extends Observable {
    private static Model instance;

    public static final String CORRECT_PIN = "1234";
    public static final int INITIAL_CARD_BALANCE = 1000;
    public static final int INITIAL_ATM_CASH = 5000;

    private ATMState currentState;
    private ATMState pinState;
    private ATMState hasMoneyState;
    private ATMState noMoneyState;
    private ATMState dispensingState;

    private String enteredPin = "";
    private int enteredAmount = 0;
    private String message = "Введите PIN (тест: 1234)";
    private boolean waitingForNewAmount = false;

    private int cardBalance = INITIAL_CARD_BALANCE;
    private int atmCash = INITIAL_ATM_CASH;

    public static Model getInstance(){
        if(instance == null)
            return instance = new Model();
        return instance;
    }

    private Model() {
        pinState = new PinState(this);
        hasMoneyState = new HasMoneyState(this);
        noMoneyState = new NoMoneyState(this);
        dispensingState = new DispensingState(this);

        currentState = pinState;
    }

    public void enterPin(String pin) {
        enteredPin = pin;
        currentState.handlePinEntered(pin);
        notifyObservers();
    }

    public void enterAmount(int amount) {
        enteredAmount = amount;
        currentState.handleAmountEntered(amount);
        notifyObservers();
    }

    public void confirm() {
        currentState.handleConfirm();
        notifyObservers();
    }

    public void cancel() {
        currentState.handleCancel();
        notifyObservers();
    }

    public void setState(ATMState state) {
        currentState = state;
        setChanged();
        notifyObservers();
    }

    public String getMessage() { return message; }
    public void setMessage(String msg) {
        message = msg;
        setChanged();
        notifyObservers();
    }
    public String getEnteredPin() { return enteredPin; }
    public int getEnteredAmount() { return enteredAmount; }
    public int getCardBalance() { return cardBalance; }
    public int getATMCash() { return atmCash; }
    public boolean isWaitingForNewAmount() { return waitingForNewAmount; }
    public void setWaitingForNewAmount(boolean waiting) {
        waitingForNewAmount = waiting;
        setChanged();
        notifyObservers();
    }

    public boolean isPinCorrect() {
        return enteredPin.equals(CORRECT_PIN);
    }

    public boolean hasEnoughMoney() {
        return enteredAmount <= cardBalance && enteredAmount <= atmCash;
    }

    public void withdrawMoney() {
        if (enteredAmount > 0 && enteredAmount <= cardBalance && enteredAmount <= atmCash) {
            cardBalance -= enteredAmount;
            atmCash -= enteredAmount;
            setChanged();
            notifyObservers();
        }
    }

    public void resetPin() {
        enteredPin = "";
    }

    public void resetAmount() {
        enteredAmount = 0;
    }

    public void resetSession() {
        enteredPin = "";
        enteredAmount = 0;
        waitingForNewAmount = false;
    }

    public ATMState getPinState() { return pinState; }
    public ATMState getHasMoneyState() { return hasMoneyState; }
    public ATMState getNoMoneyState() { return noMoneyState; }
    public ATMState getDispensingState() { return dispensingState; }
    public StateEnum getCurrentState() { return currentState.getStateEnum(); }

    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}