package com.buddila.tslmerchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static com.buddila.tslmerchant.MerchantTypeSelection.userType;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    //Declaring variable
    View loginLayout;
    Button loginBtn;
    EditText editTextEmail, editTextPassword;
    ProgressBar loginLoading;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageButton backBtn;
    boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //Transparent status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Initialise variable
        backBtn = findViewById(R.id.backbutton);
        loginLayout = findViewById(R.id.loginLayout);
        loginBtn = findViewById(R.id.login_btn);
        editTextEmail = findViewById(R.id.emailinput);
        editTextPassword = findViewById(R.id.passwordlinput);
        loginLoading = findViewById(R.id.progressBarLogin);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Background color
        if (userType.equals("cabServices")){
            loginLayout.setBackgroundColor(Color.parseColor("#A7DF85"));
        }else if (userType.equals("hotelServices")){
            loginLayout.setBackgroundColor(Color.parseColor("#99E5CF"));
        }

        //Back Button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(editTextEmail);
                checkField(editTextPassword);
                if(valid){
                    loginLoading.setVisibility(View.VISIBLE);
                    String email, password;
                    email = String.valueOf(editTextEmail.getText());
                    password = String.valueOf(editTextPassword.getText());

                    if (TextUtils.isEmpty(email)){
                        Toast.makeText(LoginScreen.this, "Enter email", Toast.LENGTH_SHORT).show();
                        loginLoading.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(password)){
                        Toast.makeText(LoginScreen.this, "Enter password", Toast.LENGTH_SHORT).show();
                        loginLoading.setVisibility(View.GONE);
                        return;
                    }

                    //Firebase Login account
                    fAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loginLoading.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginScreen.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loginLoading.setVisibility(View.GONE);
                            checkUserType(authResult.getUser().getUid());
                        }
                    });
                }
            }
        });
    }

    private void checkUserType(String uid) {
        DocumentReference df = fStore.collection("UserType").document(uid);
        //Extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onComplete" + documentSnapshot.getData());
                //identify the user type
                if(userType.equals("cabServices")){
                    if(Objects.equals(documentSnapshot.getString("UserType"), "CabMerchant")){
                        //Cab login
                        openCabDashboard();
                        finish();
                    }else {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Wrong User Type",
                                Toast.LENGTH_SHORT).show();
                    }                }
                else if (userType.equals("hotelServices")) {
                    if (Objects.equals(documentSnapshot.getString("UserType"), "HotelMerchant")) {
                        openHotelDashboard();
                        finish();
                    } else {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Wrong User Type",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

    public void openCabDashboard(){
        Intent intent = new Intent (this, CabDashboard.class);
        startActivity(intent);
    }
    public void openHotelDashboard(){
        Intent intent = new Intent (this, HotelDashboard.class);
        startActivity(intent);
    }
}
