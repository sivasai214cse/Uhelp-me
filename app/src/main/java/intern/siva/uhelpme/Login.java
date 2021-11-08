package intern.siva.uhelpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import intern.siva.uhelpme.Fragments.HomeFragment;
import intern.siva.uhelpme.Fragments.ProfileFragment;
import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    TextInputLayout LoginphonenoEditText ,LoginPasswordEditText;
    String phoneno="siv",password="sai";
    Button Login;
    TextView newuserSignup;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth Auth;
    ProgressBar progressBar;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Paper.init(getApplicationContext());
        Auth=FirebaseAuth.getInstance();

        LoginphonenoEditText=findViewById(R.id.phoneno);
        LoginPasswordEditText=findViewById(R.id.password);
        Login=findViewById(R.id.Login);
        newuserSignup=findViewById(R.id.newuser);
        progressBar=findViewById(R.id.progressbar1);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno = LoginphonenoEditText.getEditText().getText().toString();
                password=LoginPasswordEditText.getEditText().getText().toString();
                loginUser(v);
            }
        });

        newuserSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
            }
        });






    }
    public void loginUser(View view) {
        //Validate Login Info
        if (!validatePhoneNo() | !validatePassword()) {
            return;
        }else
            progressBar.setVisibility(View.VISIBLE);
            isUser();

    }

    private void isUser() {
        final String userEnteredPhone = LoginphonenoEditText.getEditText().getText().toString().trim();
        final String userEnteredPassword = LoginPasswordEditText.getEditText().getText().toString().trim();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        Query checkUser = reference.orderByChild("phoneNo").equalTo(userEnteredPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    LoginphonenoEditText.setError(null);
                    LoginphonenoEditText.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(userEnteredPhone).child("password").getValue(String.class);   //realtime data base do not take email special characters so use phone no insted.
                    if(passwordFromDB.equals(userEnteredPassword)) {
                        LoginphonenoEditText.setError(null);
                        LoginphonenoEditText.setErrorEnabled(false);
                        String userDB = snapshot.child(userEnteredPhone).child("name").getValue(String.class);
                        String phoneDB = snapshot.child(userEnteredPhone).child("phoneNo").getValue(String.class);
                        String emailDB = snapshot.child(userEnteredPhone).child("email").getValue(String.class);
                        String usernameDB = snapshot.child(userEnteredPhone).child("username").getValue(String.class);

                        Paper.book().write("PhoneNo",phoneDB);
                        Paper.book().write("UserName",usernameDB);
                        Paper.book().write("Email",emailDB);
                        Paper.book().write("Fullname",userDB);
                        Paper.book().write("Login","True");


//                        id=Auth.getUid();
//                        Paper.book().write("id",id);
                        Intent intent = new Intent(getApplicationContext(),Home1.class);
                        intent.putExtra("name", userDB);
                        intent.putExtra("phoneNo", phoneDB);
                        intent.putExtra("email", emailDB);
                        intent.putExtra("username", usernameDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        LoginPasswordEditText.setError("wrong password");
                        LoginPasswordEditText.requestFocus();

                    }

                }
                else
                {
                    LoginPasswordEditText.setError("invalid id");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }

    private boolean validatePassword() {
        String val = LoginPasswordEditText.getEditText().getText().toString();
        if (val.isEmpty()) {
            LoginPasswordEditText.setError("feild cannot be empty");
            return false;
        } else {
            LoginPasswordEditText.setError(null);
            LoginPasswordEditText.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNo() {
        String val = LoginphonenoEditText.getEditText().getText().toString();

        if (val.isEmpty()) {
            LoginphonenoEditText.setError("Field cannot be empty");
            return false;
        }
        else {
            LoginphonenoEditText.setError(null);
            LoginphonenoEditText.setErrorEnabled(false);
            return true;
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(Paper.book().contains("Login"))
        {   String com =Paper.book().read("Login");
            Intent intent = new Intent(this,Home1.class);
            startActivity(intent);
            finish();
        }

    }

}