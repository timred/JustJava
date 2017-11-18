package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
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

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
            return;
        }
        Toast tooHigh = Toast.makeText(getApplicationContext(), getString(R.string.tooHigh), Toast.LENGTH_SHORT);
        tooHigh.show();
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
            return;
        }
        Toast tooLow = Toast.makeText(getApplicationContext(), getString(R.string.tooLow), Toast.LENGTH_SHORT);
        tooLow.show();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get the name of the user
        EditText userName = findViewById(R.id.name_input);
        String userNameText = userName.getText().toString();

        if (userNameText.length() == 0) {
            Toast userToast = Toast.makeText(getApplicationContext(), getString(R.string.noName), Toast.LENGTH_SHORT);
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

        // Email the Order
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, userNameText));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(message);
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
        String message = getString(R.string.name, user);
        message += "\n" + getString(R.string.add_whipped_cream, addWhippedCream);
        message += "\n" + getString(R.string.add_chocolate, addChocolate);
        message += "\n" + getString(R.string.order_quantity, quantity);
        message += "\n" + getString(R.string.order_total,
                NumberFormat.getCurrencyInstance().format(price));
        message += "\n" + getString(R.string.thank_you);
        return message;
    }

    /**
     * This method displays the given text on the screen.
     */  /*
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }
}