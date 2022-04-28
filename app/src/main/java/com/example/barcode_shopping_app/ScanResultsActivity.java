package com.example.barcode_shopping_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import com.example.barcode_shopping_app.models.CartItem;
import com.example.barcode_shopping_app.models.Product;

import java.util.List;
import java.util.Objects;

public class ScanResultsActivity extends AppCompatDialog {
    private DatabaseReference databaseReference;
    private String uid;
    
    public ScanResultsActivity(@NonNull Context context, Product product, @NonNull Result result, Boolean condition){
        super(context, resolveDialogTheme(context));

        setTitle(R.string.scan_result);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        if(!condition){
            setContentView(R.layout.dialog_scan_result_error);
            ((TextView) findViewById(R.id.result)).setText("Item not available in the database");

        }else {
            setContentView(R.layout.dialog_scan_result_normal);
            ((TextView) findViewById(R.id.result)).setText("You scanned "+product.getName()+" successfully");

            CartItem cartItem = new CartItem(product.getId(), product.getName(), product.getUnitPrice(),1,"");

            Button increase = (Button) findViewById(R.id.button7);
            Button decrease = (Button) findViewById(R.id.button6);
            TextView display = (TextView) findViewById(R.id.textView12);

            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(cartItem.getItemQty()<= product.getAvailable()){
                        cartItem.setItemQty(cartItem.getItemQty()+1);
                        display.setText(""+cartItem.getItemQty());
                    }

                }
            });

            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    display.setText("-");
                    if(cartItem.getItemQty()>0){
                        cartItem.setItemQty(cartItem.getItemQty()-1);
                        display.setText(""+cartItem.getItemQty());
                    }

                }
            });

            Button button = (Button)findViewById(R.id.button8);
            Objects.requireNonNull(button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Save pressed", Toast.LENGTH_LONG).show();
                    postToCart(cartItem);
                }
            });


        }


        findViewById(R.id.close).setOnClickListener(v -> dismiss());
    }

    private static int resolveDialogTheme(@NonNull Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(androidx.appcompat.R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    private void postToCart(CartItem cartItem) {
        databaseReference = FirebaseDatabase.getInstance().getReference("cart_list").child(uid);
        //String stringid = databaseReference.push().getKey();

        databaseReference.child(cartItem.getProductId()).setValue(cartItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d("TK-TEST-POST", "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.d("TK-TEST-POST", "Failed");
            }
        });

    }

}
