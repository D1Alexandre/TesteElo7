package br.com.daniel.testeelo7.Adapters;

import br.com.daniel.testeelo7.ActivityInformacoes;
import br.com.daniel.testeelo7.Models.Repositorio;
import br.com.daniel.testeelo7.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRepositorio extends RecyclerView.Adapter<AdapterRepositorio.ViewHolder>{
    private Context context;
    private List<Repositorio> lista_itens;
    AdapterRepositorio.ViewHolder vh;

    public AdapterRepositorio(Context c) {
        this.context = c;
        lista_itens = new ArrayList<Repositorio>();
    }

    @Override
    public AdapterRepositorio.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_repo, parent, false);
        vh = new AdapterRepositorio.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repositorio repo = lista_itens.get(position);
        holder.txtTitulo.setText( repo.getNome() );
        holder.txtDescricao.setText( repo.getDescricao() );
        holder.txtLinguagem.setText( repo.getLinguagem() );
        if( repo.isPrivado() ){
            holder.imageView.setImageDrawable( context.getDrawable(R.drawable.privado) );
        }else{
            holder.imageView.setImageDrawable( context.getDrawable(R.drawable.publico) );
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ActivityInformacoes.class);
                i.putExtra("estrelas", repo.getEstrelas());
                i.putExtra("forks", repo.getForks());
                i.putExtra("nome_completo", repo.getNomeCompleto());
                i.putExtra("nome", repo.getNome());
                i.putExtra("descricao", repo.getDescricao());
                i.putExtra("privado", repo.isPrivado());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_itens.size();
    }

    public void add(Repositorio repositorio){
        lista_itens.add(repositorio);
        notifyItemInserted(lista_itens.size()-1);
    }

    public void removeAll(){
        lista_itens.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitulo,txtDescricao,txtLinguagem;
        public View card;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            txtLinguagem = itemView.findViewById(R.id.txtLinguagem);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
