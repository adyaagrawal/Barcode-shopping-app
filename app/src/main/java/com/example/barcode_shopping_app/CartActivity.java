package com.example.barcode_shopping_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.barcode_shopping_app.models.CartItem;
import com.example.barcode_shopping_app.adapters.CartItemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static List<CartItem> cartItems;
    private String uid;
    private ListView lv;
    private Button btnCheckout;
    private static TextView total;
    private static Double value=0.00;

    public static void deleteCartItem(CartItem cartItem){
        cartItems.remove(cartItem);
        Log.d("Updated CartItems", Arrays.toString(cartItems.toArray()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("My cart");
        cartItems = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            uid = user.getUid();
        }else {
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        btnCheckout = findViewById(R.id.btncheckout);
        lv = findViewById(R.id.listview);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CartItem item = cartItems.get(position);
                //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
                DeleteActivity deleteActivity = new DeleteActivity(CartActivity.this, item);
                deleteActivity.show();
            }
        });
        total = findViewById(R.id.textView8);
        fetchCartItems();
        calculatePrice();
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePrice();

                PaymentActivity dialog = new PaymentActivity(CartActivity.this, value, uid);
                dialog.show();
            }
        });


    }

    void tempItems(){
        for(int i=0; i<5; i++){
            CartItem item = new CartItem(""+i, "name:"+i,1.00, i,"");
            cartItems.add(item);
        }

        CartItemList adapter = new CartItemList(CartActivity.this, cartItems);
        lv.setAdapter(adapter);
    }


    @SuppressLint("DefaultLocale")
    public static void calculatePrice(){
        value = 0.00;
        for(CartItem item: cartItems){
            value += item.getItemPrice()*item.getItemQty();
        }
        Log.d("price",value.toString());
        total.setText(String.format("%.2f", value));

    }

    void fetchCartItems(){
        Toast.makeText(getApplicationContext(),"Loading Cart",Toast.LENGTH_SHORT).show();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("cart_list").child(uid);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItems.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    CartItem item = dataSnapshot1.getValue(CartItem.class);
                    cartItems.add(item);
                }

                CartItemList adapter = new CartItemList(CartActivity.this, cartItems);
                lv.setAdapter(adapter);
                calculatePrice();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
