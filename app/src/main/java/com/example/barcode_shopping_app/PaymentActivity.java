package com.example.barcode_shopping_app;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.barcode_shopping_app.models.CartItem;
import com.example.barcode_shopping_app.models.Product;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatDialog {
//    List<String> locations;
    @SuppressLint("DefaultLocale")
    public PaymentActivity(@NonNull Context context, @NonNull Double value, @NonNull String uid){
        super(context, resolveDialogTheme(context));

        setTitle("Payment");
        setContentView(R.layout.dialog_payment);

        findViewById(R.id.close).setOnClickListener(v -> dismiss());
        TextView amount = findViewById(R.id.textView9);
        amount.setText(String.format("%.2f", value));

        Button ecoButton = findViewById(R.id.button);
        Button teleButton = findViewById(R.id.button4);
        Button onemariButton = findViewById(R.id.button3);

        ecoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Paid with Ecocash $"+amount, Toast.LENGTH_LONG).show();
                processTransaction(uid);
                dismiss();
            }
        });
        teleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Paid with Telecash $"+amount, Toast.LENGTH_LONG).show();
                processTransaction(uid);
                dismiss();
            }
        });

        onemariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Paid with OneMoney $"+amount, Toast.LENGTH_LONG).show();
                processTransaction(uid);
                dismiss();
            }
        });



    }

    private static int resolveDialogTheme(@NonNull Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(androidx.appcompat.R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    private void processTransaction(String uid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cart_list").child(uid);
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("processed_transactions").child(uid);
        String stringid = databaseReference2.push().getKey();

//        databaseReference2.child(stringid).setValue(databaseReference).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                //Log.d("TK-TEST-POST", "Failed");
//            }
//        });
        databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

}
