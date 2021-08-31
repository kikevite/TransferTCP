package cat.kikevite.transfertcp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Missatge> conversa;

    RecyclerViewAdapter(List<Missatge> conversa) {
        this.conversa = conversa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Missatge m = conversa.get(position);
        if (m.isEnviat()) {
            // Missatge enviat
            String text = "Tu a " + m.getDesti() + ": " + m.getCos();
            holder.missatgeEnviat.setText(text);
            holder.missatgeEnviat.setVisibility(View.VISIBLE);
            holder.globoEnviat.setVisibility(View.VISIBLE);
            holder.horaEnviat.setText(m.getHora());
            holder.horaEnviat.setVisibility(View.VISIBLE);

            holder.missatgeRebut.setVisibility(View.INVISIBLE);
            holder.globoRebut.setVisibility(View.INVISIBLE);
            holder.horaRebut.setVisibility(View.INVISIBLE);
        } else {
            // Missatge rebut
            String text = m.getOrigen() + ": " + m.getCos();
            holder.missatgeRebut.setText(text);
            holder.globoRebut.setVisibility(View.VISIBLE);
            holder.missatgeRebut.setVisibility(View.VISIBLE);
            holder.horaRebut.setText(m.getHora());
            holder.horaRebut.setVisibility(View.VISIBLE);

            holder.missatgeEnviat.setVisibility(View.INVISIBLE);
            holder.globoEnviat.setVisibility(View.INVISIBLE);
            holder.horaEnviat.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return conversa.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView horaRebut;
        private final TextView horaEnviat;
        private final TextView missatgeRebut;
        private final TextView missatgeEnviat;
        private final View globoRebut;
        private final View globoEnviat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.missatgeRebut = itemView.findViewById(R.id.missatgeRebut);
            this.globoRebut = itemView.findViewById(R.id.globoRebut);
            this.missatgeEnviat = itemView.findViewById(R.id.missatgeEnviat);
            this.globoEnviat = itemView.findViewById(R.id.globoEnviat);
            this.horaEnviat = itemView.findViewById(R.id.horaEnviat);
            this.horaRebut = itemView.findViewById(R.id.horaRebut);
        }
    }
}
