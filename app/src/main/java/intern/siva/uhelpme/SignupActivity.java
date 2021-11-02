package intern.siva.uhelpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {

    TextView haveAccount;
    TextInputLayout Fname,username,phoneno,email,password;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        haveAccount=findViewById(R.id.haveaccount);
        Fname=findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        phoneno=findViewById(R.id.phoneno);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        next=findViewById(R.id.next);




    next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        validate();

        }
    });

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void validate()
    {
        if(!validateName() |!validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername())
        {
            return;
        }

        String fullnameText=Fname.getEditText().getText().toString();
        String usernameText=username.getEditText().getText().toString();
        String phoneNoText=phoneno.getEditText().getText().toString();
        String passwordText=password.getEditText().getText().toString();
        String emailText=email.getEditText().getText().toString();

        Intent i=new Intent(SignupActivity.this,Otpverification.class);
        i.putExtra("fullname",fullnameText);
        i.putExtra("username",usernameText);
        i.putExtra("phoneNo",phoneNoText);
        i.putExtra("password",passwordText);
        i.putExtra("email",emailText);
        startActivity(i);
    }

    private Boolean validateName() {
        String val = Fname.getEditText().getText().toString();
        if (val.isEmpty()) {
            Fname.setError("Field cannot be empty");
            return false;
        }
        else {
            Fname.setError(null);
            Fname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            username.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            username.setError("White Spaces are not allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = phoneno.getEditText().getText().toString();

        if (val.isEmpty()) {
            phoneno.setError("Field cannot be empty");
            return false;
        }
        else if (val.length()!=10)
        {
            phoneno.setError("invalid no");
            return false;
        }
        else {
            phoneno.setError(null);
            phoneno.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("feild cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}