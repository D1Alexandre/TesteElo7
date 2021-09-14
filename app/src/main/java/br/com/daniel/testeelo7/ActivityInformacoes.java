package br.com.daniel.testeelo7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.daniel.testeelo7.Models.RawRepositorio;
import br.com.daniel.testeelo7.Models.Repositorio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityInformacoes extends AppCompatActivity {
    String link, estrelas, forks, nome, descricao, nome_completo;
    TextView txtEstrelas, txtForks, txtTituloInfo, txtDescricaoInfo;
    Boolean privado;
    ImageView imageViewInfo;
    WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        imageViewInfo = (ImageView) findViewById(R.id.imageViewInfo);
        wb = (WebView) findViewById(R.id.wbInfo);
        txtEstrelas = (TextView) findViewById(R.id.txtEstrelas);
        txtForks = (TextView) findViewById(R.id.txtForks);
        txtTituloInfo = (TextView) findViewById(R.id.txtTituloInfo);
        txtDescricaoInfo = (TextView) findViewById(R.id.txtDescricaoInfo);

        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setBlockNetworkLoads(false);
        wb.getSettings().setBlockNetworkImage(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            estrelas = extras.getString("estrelas");
            forks = extras.getString("forks");
            nome = extras.getString("nome");
            descricao = extras.getString("descricao");
            nome_completo = extras.getString("nome_completo");
            privado = extras.getBoolean("privado");
        }

        txtEstrelas.setText(estrelas+" stars");
        txtForks.setText(forks+" forks");
        txtTituloInfo.setText(nome);
        txtDescricaoInfo.setText(descricao);
        if( privado ){
            imageViewInfo.setImageDrawable( this.getDrawable(R.drawable.privado) );
        }else{
            imageViewInfo.setImageDrawable( this.getDrawable(R.drawable.publico) );
        }

        Conectar();

        wb.loadUrl("https://raw.githubusercontent.com/"+nome_completo+"/master/README.md");

    }


    Call<RawRepositorio> conexaoRequest;
    private void Conectar() {

        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/repos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectionService conexao = retrofit.create(ConnectionService.class);
        conexaoRequest = conexao.getReadme(nome_completo+"/readme");
        conexaoRequest.enqueue(new Callback<RawRepositorio>() {
            @Override
            public void onResponse(Call<RawRepositorio> call, Response<RawRepositorio> response) {
                if(response.isSuccessful()){
                    Log.i("JSON", response.body().getLink());
                    wb.loadUrl(response.body().getLink());
                }
            }

            @Override
            public void onFailure(Call<RawRepositorio> call, Throwable t) {
                Log.i("RETORNO", t.getMessage());
                t.printStackTrace();
            }
        });

    }


}