// src/main/java/com/example/apiplayground/ui/AppFrame.java
package com.example.apiplayground.ui;

import com.example.apiplayground.model.CurrencyItem;
import com.example.apiplayground.service.CurrencyConverterService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class AppFrame extends JFrame {
    private final CurrencyConverterService service;

    private final JTextField amountField = new JTextField("100");

    private final JComboBox<CurrencyItem> fromCombo = new JComboBox<>();
    private final JComboBox<CurrencyItem> toCombo = new JComboBox<>();

    private final JButton refreshButton = new JButton("Actualizar monedas");
    private final JButton convertButton = new JButton("Convertir");
    private final JLabel resultLabel = new JLabel("Resultado: -");

    public AppFrame(CurrencyConverterService service) {
        super("API Playground - Currency Converter");
        this.service = service;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 280);
        setLocationRelativeTo(null);

        setContentPane(buildContent());
        wireEvents();
        loadCurrencies();
    }

    private JComponent buildContent() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Monto:"), c);
        c.gridx = 1; c.gridy = row; c.weightx = 1;
        panel.add(amountField, c);

        row++;
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Moneda origen:"), c);
        c.gridx = 1; c.gridy = row; c.weightx = 1;
        panel.add(fromCombo, c);

        row++;
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(new JLabel("Moneda destino:"), c);
        c.gridx = 1; c.gridy = row; c.weightx = 1;
        panel.add(toCombo, c);

        row++;
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        panel.add(refreshButton, c);

        c.gridx = 1; c.gridy = row; c.weightx = 0;
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.add(convertButton);
        actions.add(resultLabel);
        panel.add(actions, c);

        return panel;
    }

    private void wireEvents() {
        refreshButton.addActionListener(e -> loadCurrencies());
        convertButton.addActionListener(e -> runConvert());
        amountField.addActionListener(e -> runConvert());
    }

    private void loadCurrencies() {
        refreshButton.setEnabled(false);
        convertButton.setEnabled(false);
        resultLabel.setText("Resultado: cargando monedas...");

        SwingWorker<List<CurrencyItem>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<CurrencyItem> doInBackground() throws Exception {
                return service.getSupportedCurrencies();
            }

            @Override
            protected void done() {
                try {
                    List<CurrencyItem> items = get();

                    fromCombo.removeAllItems();
                    toCombo.removeAllItems();

                    for (CurrencyItem item : items) {
                        fromCombo.addItem(item);
                        toCombo.addItem(item);
                    }

                    selectDefaults();
                    resultLabel.setText("Resultado: -");
                } catch (Exception ex) {
                    resultLabel.setText("Resultado: -");
                    showError("Error cargando monedas: " + ex.getMessage());
                } finally {
                    refreshButton.setEnabled(true);
                    convertButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void selectDefaults() {
        selectComboItem(fromCombo, "USD");
        selectComboItem(toCombo, "MXN");
    }

    private void selectComboItem(JComboBox<CurrencyItem> combo, String code) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            CurrencyItem item = combo.getItemAt(i);
            if (item != null && item.code().equalsIgnoreCase(code)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void runConvert() {
        convertButton.setEnabled(false);
        resultLabel.setText("Resultado: consultando...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                BigDecimal amount = new BigDecimal(amountField.getText().trim());

                CurrencyItem from = (CurrencyItem) fromCombo.getSelectedItem();
                CurrencyItem to = (CurrencyItem) toCombo.getSelectedItem();

                if (from == null || to == null) throw new IllegalStateException("Selecciona monedas.");

                BigDecimal result = service.convert(amount, from.code(), to.code());
                return amount + " " + from.code() + " = " + result + " " + to.code();
            }

            @Override
            protected void done() {
                try {
                    resultLabel.setText("Resultado: " + get());
                } catch (Exception ex) {
                    resultLabel.setText("Resultado: -");
                    showError(ex.getMessage());
                } finally {
                    convertButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
