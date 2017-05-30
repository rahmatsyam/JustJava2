package application.rahmatsyam.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    //jumlah cofee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:udacity@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }


/**
 * @return price
 */
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int basePrice=5;
        if(addWhippedCream){
        basePrice=basePrice+1;
        }
        if(addChocolate){
        basePrice=basePrice+2;
        }
        return quantity*basePrice;
        }

    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage="Name :" + name;
        priceMessage+="\nAdd whipped cream? "+addWhippedCream;
        priceMessage+="\nAdd chocolate? "+addChocolate;
        priceMessage+="\nQuantity: "+quantity;
        priceMessage+="\nTotal: $"+price;
        priceMessage+="\nThank You!";
        return priceMessage;
        }

    public void increment(View view){
        if(quantity==100){
        Toast.makeText(this,"You can not hava more than 100 coffees",Toast.LENGTH_SHORT).show();
        return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
        }

    public void decrement(View view){
        if(quantity==1){
        Toast.makeText(this,"You can not hava more than 1 coffees",Toast.LENGTH_SHORT).show();
        return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
        }

    private void displayQuantity(int number){
        TextView quantityTextView=(TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
        }

    private void displayMessage(String message){
        TextView orderSummaryTextView=(TextView)findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
        }

}
