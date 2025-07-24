import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class CurrencyConverterGUI {

    private static final String[] currencies = {
        "INR", "USD", "EUR", "GBP", "JPY", "CNY", "CAD", "AUD", "NZD", "SGD"
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame("Currency Converter - Welcome Rishank 💙");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Currency Converter", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JComboBox<String> fromCurrency = new JComboBox<>(currencies);
        JComboBox<String> toCurrency = new JComboBox<>(currencies);

        JTextField amountField = new JTextField();
        JLabel resultLabel = new JLabel("Converted amount will appear here", JLabel.CENTER);

        JButton convertButton = new JButton("Convert");

        convertButton.addActionListener(e -> {
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();
            String amountText = amountField.getText();

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an amount!");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                double rate = getExchangeRate(from, to);
                double converted = rate * amount;

                resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, from, converted, to));
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });

        frame.add(titleLabel);
        frame.add(fromCurrency);
        frame.add(toCurrency);
        frame.add(amountField);
        frame.add(convertButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private static double getExchangeRate(String base, String target) throws Exception {
        String urlStr = "https://api.exchangerate-api.com/v4/latest/" + base;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject obj = new JSONObject(response.body());
        return obj.getJSONObject("rates").getDouble(target);
    }
}
