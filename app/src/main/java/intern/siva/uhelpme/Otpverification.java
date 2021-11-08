package intern.siva.uhelpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import intern.siva.uhelpme.Pojo.SignupPojo;
import io.paperdb.Paper;

public class Otpverification extends AppCompatActivity {


    Button verify_btn;
    EditText otpentered;
    ProgressBar progressbar;
    String verificationcodebysystem;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseDatabase rootnode;
    String fullname,username,phoneNo,password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
         progressbar=findViewById(R.id.progressBar);
         otpentered=findViewById(R.id.OtpNumber);
         verify_btn=findViewById(R.id.go);
         fullname = getIntent().getStringExtra("fullname");
         username = getIntent().getStringExtra("username");
         phoneNo = getIntent().getStringExtra("phoneNo");
         password = getIntent().getStringExtra("password");
         email = getIntent().getStringExtra("email");
         sendVerificationCodeToUser(phoneNo);
         firebaseAuth = FirebaseAuth.getInstance();
         verify_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String otp=otpentered.getText().toString();
                 verifyCode(otp);
             }
         });
    }


    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder()
                        .setPhoneNumber("+91" + phoneNo)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)  // Timeout and unit
                        .setActivity(this)                        // Activity (for callback binding)
                        .setCallbacks(mCallbacks)                // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcodebysystem = s;
        }
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressbar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Otpverification.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcodebysystem, codeByUser);
        signInTheUserByCredentials(credential);
    }


    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Otpverification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(Otpverification.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();
                            //Perform Your required action here to either let the user sign In or do something required
                            Intent intent = new Intent(getApplicationContext(), Home1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("Fullname",fullname);
                            intent.putExtra("username",username);
                            intent.putExtra("phoneNo",phoneNo);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            Paper.book().write("Login","True");
                            Paper.book().write("PhoneNo",phoneNo);
                            Paper.book().write("UserName",username);
                            Paper.book().write("Email",email);
                            Paper.book().write("Fullname",fullname);
                            startActivity(intent);
                            storeNewUsersData();
                            finish();

                        } else {
                            Log.d("failed",task.getException().getMessage());
                            Toast.makeText(Otpverification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void storeNewUsersData() {

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");
        String user=Paper.book().read("communi");
        SignupPojo signupPojo = new SignupPojo(fullname,username,email,phoneNo,password,user);
        reference.child(phoneNo).setValue(signupPojo);

    }

}