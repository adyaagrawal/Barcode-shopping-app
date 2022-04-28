package com.example.barcode_shopping_app;

import android.content.Context;
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

import java.util.Objects;

public class DeleteActivity extends AppCompatDialog {
    private DatabaseReference databaseReference;
    private String uid;

    public DeleteActivity(@NonNull Context context, CartItem cartItem){
        super(context, resolveDialogTheme(context));

        setTitle("Confirm action");

        setContentView(R.layout.dialog_delete);

        Button deleteBtn = (Button) findViewById(R.id.button9);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), cartItem.getItemName(), Toast.LENGTH_SHORT).show();
                deleteFromCart(cartItem);

                dismiss();
            }
        });

        findViewById(R.id.close).setOnClickListener(v -> dismiss());
    }

    private static int resolveDialogTheme(@NonNull Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(androidx.appcompat.R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    private void deleteFromCart(CartItem cartItem) {

        CartActivity.deleteCartItem(cartItem);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            uid = user.getUid();
        }


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cart_list").child(uid);
        //String stringid = databaseReference.push().getKey();

        databaseReference.child(cartItem.getProductId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
