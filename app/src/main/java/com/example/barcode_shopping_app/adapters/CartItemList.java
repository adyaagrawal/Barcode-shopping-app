package com.example.barcode_shopping_app.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.barcode_shopping_app.CartActivity;
import com.example.barcode_shopping_app.R;
import com.example.barcode_shopping_app.models.CartItem;
import com.example.barcode_shopping_app.models.Product;

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
        TextView itemQty = view.findViewById(R.id.textView11);

//        decreasePress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "decrease", Toast.LENGTH_SHORT).show();
//                cartItem.setItemQty(cartItem.getItemQty()-1);
//                itemQty.setText(""+cartItem.getItemQty());
//
//            }
//        });


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
