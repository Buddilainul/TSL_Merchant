package com.buddila.tslmerchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HotelRegisterScreen extends AppCompatActivity {

    //Declaring variable
    Button nextBtn;
    EditText editTextOwnerName, editTextHotelName, editTextEmail, editTextPassword,
            editTextPhoneNumber, editTextHotelLongitude, editTextHotelLatitude,
            editTextCity, editTextTown, editTextHotelAddress;
    ProgressBar registerLoading;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageButton backBtn;
    boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_register_screen);


        //Transparent status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //initialize variable
        backBtn = findViewById(R.id.backbutton);
        nextBtn = findViewById(R.id.nextbtn_register);
        editTextOwnerName = findViewById(R.id.editText1);
        editTextHotelName = findViewById(R.id.hotelname);
        editTextHotelLongitude = findViewById(R.id.editText8);
        editTextHotelLatitude = findViewById(R.id.editText7);
        editTextHotelAddress = findViewById(R.id.hotelAddress);
        editTextCity = findViewById(R.id.editText11);
        editTextTown = findViewById(R.id.editText10);
        editTextEmail = findViewById(R.id.editText4);
        editTextPhoneNumber = findViewById(R.id.editText5);
        editTextPassword = findViewById(R.id.editText6);
        registerLoading = findViewById(R.id.progressBarRegister);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        //Back Button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(editTextOwnerName);
                checkField(editTextHotelName);
                checkField(editTextHotelLongitude);
                checkField(editTextHotelLatitude);
                checkField(editTextCity);
                checkField(editTextTown);
                checkField(editTextHotelAddress);
                checkField(editTextEmail);
                checkField(editTextPhoneNumber);
                checkField(editTextPassword);


                registerLoading.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(HotelRegisterScreen.this, "Enter email", Toast.LENGTH_SHORT).show();
                    registerLoading.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(HotelRegisterScreen.this, "Enter password", Toast.LENGTH_SHORT).show();
                    registerLoading.setVisibility(View.GONE);
                    return;
                }

                if (valid){


                    //Firebase create account
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            registerLoading.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                FirebaseUser user = fAuth.getCurrentUser();
                                Toast.makeText(HotelRegisterScreen.this, "Account Created.",
                                        Toast.LENGTH_SHORT).show();

                                DocumentReference df = fStore.collection("UserType").document(user.getUid());
                                Map<String,Object> userInfo = new HashMap<>();
                                userInfo.put("OwnerName",editTextOwnerName.getText().toString());
                                userInfo.put("HotelName",editTextHotelName.getText().toString());
                                userInfo.put("HotelLongitude",editTextHotelLongitude.getText().toString());
                                userInfo.put("HotelLatitude",editTextHotelLatitude.getText().toString());
                                userInfo.put("City",editTextCity.getText().toString());
                                userInfo.put("Town",editTextTown.getText().toString());
                                userInfo.put("HotelAddress",editTextHotelAddress.getText().toString());
                                userInfo.put("Email",editTextEmail.getText().toString());
                                userInfo.put("PhoneNumber",editTextPhoneNumber.getText().toString());

                                //specify user type
                                userInfo.put("UserType", "HotelMerchant");
                                df.set(userInfo);

                                openNextScreen();
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(HotelRegisterScreen.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void openNextScreen(){
        Intent intent = new Intent (this, MerchantTypeSelection.class);
        startActivity(intent);
    }

    private boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()){
            textField.setError("Please enter data");
            valid = false;
        }else {
            valid = true;
        }
        return valid;
    }

}