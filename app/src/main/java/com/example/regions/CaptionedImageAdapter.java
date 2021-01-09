package com.example.regions;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder> {
    private String[] regionsName;
    private int[] imageIds;
    private String[] lang;
    private String[] lat;
    private String[] pop;
    public CaptionedImageAdapter(String [] regionsName, int [] imageIds,String [] lang,String [] lat,String [] pop){
        this.regionsName = regionsName;
        this.imageIds = imageIds;
        this.lang = lang;
        this.lat = lat;
        this.pop = pop;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image, parent, false);
        return  new ViewHolder(v);
    }
    public void onBindViewHolder(ViewHolder holder, int position){
        CardView cardView=holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(),imageIds[position]);
        imageView.setImageDrawable(dr);
        TextView txt=(TextView)cardView.findViewById(R.id.txtName);
        txt.setText(regionsName[position]);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),
                        MainActivity2.class);


                intent.putExtra("region_name", regionsName[position]);
                intent.putExtra("region_lang", lang[position]);
                intent.putExtra("region_lat", lat[position]);
                intent.putExtra("region_pop", pop[position]);
                v.getContext().startActivity(intent);



            }
        });
    }
    public int getItemCount(){
        return regionsName.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }
}
