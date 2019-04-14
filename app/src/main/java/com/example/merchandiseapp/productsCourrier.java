package com.example.merchandiseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.merchandiseapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productsCourrier extends AppCompatActivity {
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productName;
    private String productID = "";
    private String User_ID = "";
    private String OrderID = "";
    private String date = "";
    private String time = "";
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_courrier);

        Button  btn = (Button) findViewById(R.id.button1);

        productID = getIntent().getStringExtra("pid");
        date = getIntent().getStringExtra("pdate");
        time = getIntent().getStringExtra("ptime");

        User_ID = Prevalent.currentOnlineUser;
        System.out.println("\n123 "+ date);
        System.out.println("123 "+time);
        System.out.println("123 "+productID);
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);

        getProductDetails(productID);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Packed and ready to be shipped"
                , "Shipped", "On Delivery" , "Delivered"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }

    private void getProductDetails(String productID)
    {
        final DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("mayank343"
        );
         productsRef.child(date+" "+time).addValueEventListener(new ValueEventListener()
         {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@2"+date+" "+time+"@@@@@@@@@@@@@@@@@@@@");
                Order order = dataSnapshot.getValue(Order.class);
                //System.out.println("hello" + order);
                status = order.getStatus();
                OrderID = order.getOrderid();
               productName.setText(order.getPname());
               productPrice.setText(order.getPrice());



// Picasso.get().load(order.getStatus();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    public void doOnClick(View view)
    {
        System.out.println("dsaaaaaaaa");
        Spinner dropdown = findViewById(R.id.spinner1);
        String text = dropdown.getSelectedItem().toString();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("status", text);
        System.out.println("dsaaaaaaaa"+User_ID + OrderID);
        User_ID="mayank343";
        cartListRef.child(User_ID).child(OrderID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

                if(task.isSuccessful())
                {
                }
            }
        });





    }

}

