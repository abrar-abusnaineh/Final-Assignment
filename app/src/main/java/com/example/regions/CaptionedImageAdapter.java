package com.example.regions;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder> {
    private String[] captions;
    private int[] imageIds;

    public CaptionedImageAdapter(String [] captions, int [] imageIds){
        this.captions = captions;
        this.imageIds = imageIds;
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
        txt.setText(captions[position]);
        cardView.setOnClickListener(v -> {

        });
    }
    public int getItemCount(){
        return captions.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }
}
