package org.example.vistas.alerts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationAlert extends JFrame{
    private JButton okButton;
    private JLabel tittle;
    private JLabel desc;
    private JPanel pane1;

    public ApplicationAlert() {
        setContentPane(pane1);
        okButton.addActionListener(e -> {
            setVisible(false);
        });
    }

    public void setTittle(String tittle) {
        this.tittle.setText(tittle);
    }

    public void setDesc(String desc) {
        this.desc.setText(desc);
    }
}
