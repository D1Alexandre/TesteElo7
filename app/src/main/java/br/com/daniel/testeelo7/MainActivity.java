package br.com.daniel.testeelo7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String clientId = "#";
    private String clientSecret = "#";
    private String callback = "elo7://callback";
    private Button BtnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.app_name);
        }
        BtnLogin = (Button) findViewById(R.id.btnlogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                intent.putExtra("id", clientId);
                intent.putExtra("sec",clientSecret);
                intent.putExtra("url", "https://github.com/login/oauth/authorize" + "?client_id="+clientId +
                        "&scope=repo&redirect_uri=" + callback);
                startActivity(intent);
            }
        });



    }
}
