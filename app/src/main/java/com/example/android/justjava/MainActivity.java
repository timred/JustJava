package com.example.android.justjava;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.Touch;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get the name of the user
        EditText userName = findViewById(R.id.name_input);
        String userNameText = userName.getText().toString();

        if (userNameText.length() == 0) {
            Toast userToast = Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_SHORT);
            userToast.show();
            return;
        }

        // Does the user want whipped cream
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Does the user want chocolate
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Get the price and Order Summary
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String message = createOrderSummary(userNameText, price, hasWhippedCream, hasChocolate);
        displayMessage(message);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream specifies whether the user selected whipped cream
     * @param addChocolate specifies whether the user selected chocolate
     * @return price the price of the order
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Set Price per base cup of coffee
        int price = 5;

        // Add £1 for Whipped Cream
        if (addWhippedCream) {
            price += 1;
        }

        // Add £2 for Chocolate
        if (addChocolate) {
            price += 2;
        }

        // Calculate and return the total price
        price *= quantity;
        return price;
    }

    /**
     * Creates the order summary.
     *
     * @param user the name of the user ordering coffee
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants a whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return message information
     */
    private String createOrderSummary(String user, int price, boolean addWhippedCream, boolean addChocolate) {
        String message = "Name: " + user;
        message += "\nAdd whipped cream? " + addWhippedCream;
        message += "\nAdd chocolate? " + addChocolate;
        message += "\nQuantity: " + quantity;
        message += "\nTotal: £" + price;
        message += "\nThank you!";
        return message;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }
}