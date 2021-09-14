package br.com.daniel.testeelo7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.List;

import br.com.daniel.testeelo7.Adapters.AdapterRepositorio;
import br.com.daniel.testeelo7.Models.Repositorio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityRepositorios extends AppCompatActivity {
    String codigo, token, tipo;
    AdapterRepositorio adapter, adapter1;

    private RecyclerView recyclerViewPublico, recyclerViewPrivado;
    private GridLayoutManager layoutManager, layoutManager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorios);
        //
        TabHost tabhost = (TabHost) findViewById(R.id.tabhost);
        tabhost.setup();
        TabHost.TabSpec spec = tabhost.newTabSpec("Públicos");
        spec.setContent(R.id.recycler_view_publico);
        spec.setIndicator("Públicos");
        tabhost.addTab(spec);
        //
        spec = tabhost.newTabSpec("Privados");
        spec.setContent(R.id.recycler_view_privado);
        spec.setIndicator("Privados");
        tabhost.addTab(spec);
        tabhost.setCurrentTab(0);
        //
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
            tipo = extras.getString("tipo");
            codigo = extras.getString("cod");
        }

        recyclerViewPublico = (RecyclerView) findViewById(R.id.recycler_view_publico);
        recyclerViewPublico.setHasFixedSize(true);

        recyclerViewPrivado = (RecyclerView) findViewById(R.id.recycler_view_privado);
        recyclerViewPrivado.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(ActivityRepositorios.this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager1 = new GridLayoutManager(ActivityRepositorios.this, 2);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewPublico.setLayoutManager(layoutManager);
        recyclerViewPrivado.setLayoutManager(layoutManager1);

        adapter = new AdapterRepositorio(ActivityRepositorios.this);
        adapter1 = new AdapterRepositorio(ActivityRepositorios.this);

        recyclerViewPublico.setAdapter(adapter);
        recyclerViewPrivado.setAdapter(adapter1);

        conectar();
    }

    Call<List<Repositorio>> conexaoRequest;
    private void conectar() {
        adapter.removeAll();
        adapter1.removeAll();

        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectionService conexao = retrofit.create(ConnectionService.class);
        conexaoRequest = conexao.getRepos(tipo+" "+token , "all");
        conexaoRequest.enqueue(new Callback<List<Repositorio>>() {
            @Override
            public void onResponse(Call<List<Repositorio>> call, Response<List<Repositorio>> response) {
                if(response.isSuccessful()){
                    List<Repositorio> repositorios = response.body();
                    for (Repositorio r : repositorios) {
                        if(r.isPrivado()){
                            adapter1.add(r);
                        }else{
                            adapter.add(r);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Repositorio>> call, Throwable t) {
                Log.i("RETORNO", t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityRepositorios.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}