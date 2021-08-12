package intern.siva.uhelpme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  private static int time=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Animation top,bottom;
        ImageView logo;

        TextView uhelp,slogan;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom);
        logo = findViewById(R.id.circleImageView);
        uhelp=findViewById(R.id.uhelpme);
        slogan=findViewById(R.id.slogan);


        logo.animate().translationY(-100).setDuration(1000).setStartDelay(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Comun.class);
                startActivity(intent);
                finish();
            }
        },time);



    }
}