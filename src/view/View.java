package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    private Controller controller;
    private JTextArea infoArea;
    private JTextField inputField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JLabel inputLabel;
    private JLabel balanceLabel;

    public View(Controller controller, Model model) {
        this.controller = controller;
        model.addObserver(this);

        setTitle("Банкомат");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 450);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        infoArea = new JTextArea(4, 40);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.BOLD, 14));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setText(controller.getMessage());
        topPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        balanceLabel = new JLabel("Баланс карты: " + controller.getCardBalance() +
                " руб. | Наличные в банкомате: " + controller.getATMCash() + " руб.");
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        balancePanel.add(balanceLabel);
        topPanel.add(balancePanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputLabel = new JLabel("Ввод:");
        inputField = new JTextField(15);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        confirmButton = new JButton("Подтвердить");
        cancelButton = new JButton("Отмена");

        confirmButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                if (controller.isPinState()) {
                    controller.processPin(input);
                } else if (controller.isHasMoneyState()) {
                    controller.processAmount(input);
                }
                inputField.setText("");
            }
            controller.processConfirm();
        });

        cancelButton.addActionListener(e -> {
            controller.processCancel();
            inputField.setText("");
            inputField.requestFocus();
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        inputField.requestFocus();

        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        infoArea.setText(controller.getMessage());
        balanceLabel.setText("Баланс карты: " + controller.getCardBalance() +
                " руб. | Наличные в банкомате: " + controller.getATMCash() + " руб.");

        inputLabel.setText(controller.getSecondMessage());
        inputField.setEnabled(controller.isEnabled());

        if (inputField.isEnabled()) inputField.requestFocus();
    }
}