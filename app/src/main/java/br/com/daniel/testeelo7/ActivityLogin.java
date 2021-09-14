package br.com.daniel.testeelo7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.daniel.testeelo7.Models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityLogin extends AppCompatActivity {

    private WebView webview;
    String codigo, ClientId, ClientSecret, link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().permitDiskReads().build();
        StrictMode.setThreadPolicy(policy);
        codigo = "";
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if(view.getTitle().startsWith("Sign in to")){
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(view.getTitle());
                    }
                }
                Log.i("LINK ATUAL", url);
                if(url.startsWith("elo7://")){
                    codigo = view.getUrl().split("=")[1];
                    Gson gson = new GsonBuilder().setLenient().create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://github.com")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    ConnectionService conexao = retrofit.create(ConnectionService.class);
                    Call<Token> request = conexao.getToken( ClientId, ClientSecret, codigo);
                    request.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(ActivityLogin.this,"Por favor, tente novamente!",Toast.LENGTH_SHORT);
                            }else{
                                Log.i("TOKEN", response.body().getAccessToken());
                                Intent intent = new Intent(ActivityLogin.this, ActivityRepositorios.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("token", response.body().getAccessToken());
                                intent.putExtra("tipo",response.body().getTokenType());
                                intent.putExtra("cod",codigo);
                                startActivity(intent);//webview.loadUrl(link);
                            }

                        }
                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(ActivityLogin.this,"Por favor, tente novamente!",Toast.LENGTH_SHORT);
                        }
                    });

                }
            }
        });

        webview.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            };

        });

        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBlockNetworkLoads(false);
        webview.getSettings().setBlockNetworkImage(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("url");
            ClientId = extras.getString("id");
            ClientSecret = extras.getString("sec");
            webview.loadUrl(link);
        }
    }
}