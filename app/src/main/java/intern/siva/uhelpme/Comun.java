package intern.siva.uhelpme;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;
//Toast.makeText(Verification.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

public class Comun extends AppCompatActivity {
    Button visitor;
    Button local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comun);
        visitor=findViewById(R.id.visitor);
        local=findViewById(R.id.local);
        Paper.init(this);
        visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Visitor = "Visitor";

                Paper.book().write("communi",Visitor);
                Log.d("sucess","sucuessful");
                Toast.makeText(Comun.this,"WELCOME VISITOR ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Comun.this,Login.class);
                startActivity(intent);
                finish();

            }
        });
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Local ="LOCAL";
                Paper.book().write("communi",Local);
                Toast.makeText(Comun.this,"WELCOME LOCAL ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Comun.this,Login.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
    if(Paper.book().contains("communi"))
    {   String com =Paper.book().read("communi");
        Intent intent = new Intent(this,Login.class);
        Toast.makeText(this,"WELCOME "+com,Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    }
}