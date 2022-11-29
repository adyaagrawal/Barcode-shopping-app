package com.example.barcode_shopping_app.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.barcode_shopping_app.CartActivity;
import com.example.barcode_shopping_app.R;
import com.example.barcode_shopping_app.models.CartItem;

import java.util.List;


public class CartItemList extends ArrayAdapter<CartItem>  {
    private Activity context;
    private List<CartItem> cartItemtList;

    public CartItemList(Activity context, List<CartItem> cartItemtList){
        super(context, R.layout.list_item_1, cartItemtList);
        this.context = context;
        this.cartItemtList = cartItemtList;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item_1, null, true);

        CartItem cartItem = cartItemtList.get(position);

        Log.d("TK_cartitem", cartItem.getProductId());

        TextView itemName = view.findViewById(R.id.itemname);
        TextView itemDescription = view.findViewById(R.id.itemdesc);
        TextView itemQty = view.findViewById(R.id.itemquantity);

        Button increaseQuantity = view.findViewById(R.id.buttonPlus);
        Button decreaseQuantity = view.findViewById(R.id.buttonMinus);

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "increase quantity of item: "+cartItem.getItemName(), Toast.LENGTH_SHORT).show();
                cartItem.setItemQty(cartItem.getItemQty() + 1);
                itemQty.setText("Quantity: "+cartItem.getItemQty());

                CartActivity.calculatePrice();

                if(cartItem.getItemQty() > 1){
                    decreaseQuantity.setEnabled(true);
                }
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartItem.getItemQty() > 1){
                    Toast.makeText(getContext(), "decrease quantity of item: "+cartItem.getItemName(), Toast.LENGTH_SHORT).show();
                    cartItem.setItemQty(cartItem.getItemQty() - 1);
                    itemQty.setText("Quantity: "+cartItem.getItemQty());

                    CartActivity.calculatePrice();
                }
                if(cartItem.getItemQty() == 1){
                    decreaseQuantity.setEnabled(false);
                }
            }
        });

        itemName.setText(cartItem.getItemName());
        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.deleteCartItem(cartItem);
            }
        });
        String description = String.format("Rs. %.2f", cartItem.getItemPrice());
        itemDescription.setText(description);
        itemQty.setText(String.format("Quantity: %d", cartItem.getItemQty()));

        
        return view;
    }

}
