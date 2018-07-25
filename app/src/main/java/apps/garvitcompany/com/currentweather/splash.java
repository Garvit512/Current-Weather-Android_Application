package apps.garvitcompany.com.currentweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    splash.this.startActivity(new Intent(splash.this.getApplicationContext(), MainActivity.class));
                    splash.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
