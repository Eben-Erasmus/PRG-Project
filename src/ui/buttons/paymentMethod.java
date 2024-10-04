package ui.buttons;

import javax.swing.*;
import java.awt.*;

public class paymentMethod {
    JFrame frame = new JFrame("My GUI");

    public paymentMethod(){
        JRadioButton();

        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout(10,10));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        frame.pack();// is used to automatically adjust the size of a JFrame to fit its contents
    }

    private void JRadioButton() {

        JRadioButton FNB = new JRadioButton();
        JRadioButton standardBank = new JRadioButton();
        JRadioButton bankWindhoek = new JRadioButton();
        JRadioButton nedbank = new JRadioButton();


        JButton select = new JButton("OK");
        JLabel L1 = new JLabel("Payment System: ");

        FNB.setText("First National Bank of Namibia (FNB)");
        standardBank.setText("Standard Bank");
        bankWindhoek.setText("Bank Windhoek");
        nedbank.setText("Nedbank");


        FNB.setBounds(20, 80, 240, 40);
        standardBank.setBounds(20, 160, 200, 40);
        bankWindhoek.setBounds(20, 240, 200, 40);
        nedbank.setBounds(20, 320, 200, 40);

        select.setBounds(20, 380, 200, 40);
        L1.setBounds(20, 40, 200, 40);

        frame.add(FNB);
        frame.add(standardBank);
        frame.add(bankWindhoek);
        frame.add(nedbank);
        frame.add(L1);
        frame.add(select);

        //grouping the buttons as part of one unit
        ButtonGroup G1 = new ButtonGroup();
        G1.add(FNB);
        G1.add(standardBank);
        G1.add(bankWindhoek);
        G1.add(nedbank);

        frame.setSize(800, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
