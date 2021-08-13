package intern.siva.uhelpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Comun extends AppCompatActivity {
    Button visitor,local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comun);
        visitor=findViewById(R.id.visitor);
        local=findViewById(R.id.local);
        visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Comun.this,Login.class);
                startActivity(intent);

            }
        });
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Comun.this,Login.class);
                startActivity(intent);
            }
        });
    }
}