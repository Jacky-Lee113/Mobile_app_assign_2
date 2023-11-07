package com.example.noteme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    private List<EntryModel> entryModels;
    private Context context;



    Adapter(Context context, List<EntryModel> entryModels) {
        this.inflater = LayoutInflater.from(context);
        this.entryModels = entryModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.location_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EntryModel entryModel = entryModels.get(position);
        holder.bind(entryModel, context);
    }

    @Override
    public int getItemCount() {
        return entryModels.size();
    }
    public void setFilter(ArrayList<EntryModel> newList)   {
        entryModels = new ArrayList<>();
        entryModels.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView addressView, latitudeView, longitudeView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView); // Initialize the cardView
            addressView = itemView.findViewById(R.id.addressView);
            latitudeView = itemView.findViewById(R.id.latitudeView);
            longitudeView = itemView.findViewById(R.id.longitudeView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("ID", entryModels.get(getAdapterPosition()).getId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    public void bind(EntryModel entryModel, Context context) {
        String address = entryModel.getAddressInput();
        String latitude = "Latitude: " + entryModel.getLatitudeInput();
        String longitude = "Longitude: " + entryModel.getLongitudeInput();

        addressView.setText(address);
        latitudeView.setText(latitude);
        longitudeView.setText(longitude);
        }
    }
}