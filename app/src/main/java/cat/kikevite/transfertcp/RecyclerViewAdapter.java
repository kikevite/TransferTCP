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
            String text = "Tu a " + m.getDesti() + ":\n" + m.getCos();
            holder.missatgeEnviat.setText(text);
            holder.horaEnviat.setText(m.getHora());
            holder.layoutEnviat.setVisibility(View.VISIBLE);
            holder.layoutRebut.setVisibility(View.INVISIBLE);
        } else {
            // Missatge rebut
            String text = m.getOrigen() + ":\n" + m.getCos();
            holder.missatgeRebut.setText(text);
            holder.horaRebut.setText(m.getHora());
            holder.layoutRebut.setVisibility(View.VISIBLE);
            holder.layoutEnviat.setVisibility(View.INVISIBLE);
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
        private final View layoutRebut;
        private final View layoutEnviat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.missatgeRebut = itemView.findViewById(R.id.missatgeRebut);
            this.missatgeEnviat = itemView.findViewById(R.id.missatgeEnviat);
            this.horaEnviat = itemView.findViewById(R.id.horaEnviat);
            this.horaRebut = itemView.findViewById(R.id.horaRebut);
            this.layoutRebut = itemView.findViewById(R.id.layoutRebut);
            this.layoutEnviat = itemView.findViewById(R.id.layoutEnviat);
        }
    }
}
