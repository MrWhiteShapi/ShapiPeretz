package com.example.shapiperetz;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CreatMyViewHolder>{

    private Context context;
    private List<Constructor> food;

    public RecyclerViewAdapter(Context context, List<Constructor> list) {
        this.context = context;
        this.food = list;
    }

    @NonNull
    @Override

    public CreatMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.item, parent, false);
        return new CreatMyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatMyViewHolder holder, int position) {
        holder.inputer(food.get(position));
    }


    @Override
    public int getItemCount() {
        return food.size();
    }

    public static class CreatMyViewHolder extends RecyclerView.ViewHolder{

        ImageView food_image;
        TextView name;
        TextView description;
        TextView price;
        TextView count_of_food;
        ImageView plus_button;
        ImageView minus_button;
        int count;
        SharedPreferences sPref;


        public CreatMyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.imgFood);
            name = itemView.findViewById(R.id.txtTitle);
            description = itemView.findViewById(R.id.txtDescription);
            price = itemView.findViewById(R.id.txtPrice);
            count_of_food = itemView.findViewById(R.id.txtCount);;
            plus_button = itemView.findViewById(R.id.plus_butt);
            minus_button = itemView.findViewById(R.id.minus_butt);

            sPref = itemView.getContext().getSharedPreferences ("MyPref", Context.MODE_PRIVATE);

        }
        public void inputer(Constructor model){
            name.setText(String.valueOf(model.getSectionTitle()));
            description.setText(String.valueOf(model.getDesk()));
            price.setText(String.valueOf(model.getPrice()));
            count_of_food.setText(String.valueOf(count));
            Picasso.get()
                    .load(model.getSectionImage())
                    .into(food_image);


            count_of_food.setVisibility(View.INVISIBLE);
            minus_button.setVisibility(View.INVISIBLE);
            if(count > 0){
                count_of_food.setVisibility(View.VISIBLE);
                plus_button.setVisibility(View.VISIBLE);
            }
            plus_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count+=1;
                    if (count > 0){
                        minus_button.setVisibility(View.VISIBLE);
                        count_of_food.setVisibility(View.VISIBLE);
                        count_of_food.setText(String.valueOf(count));
                    }else{
                        count_of_food.setText(Integer.toString(count));
                    }
                    saveData(model.getSectionId(), count);
                }
            });


            minus_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count-=1;
                    if(count == 0){
                        count = 0;
                        minus_button.setVisibility(View.INVISIBLE);
                        count_of_food.setVisibility(View.INVISIBLE);
                    }
                    count_of_food.setText(Integer.toString(count));
                    saveData(model.getSectionId(), count);
                }
            });
        }
        public void saveData(String id, int count) {
            SharedPreferences.Editor ed = sPref.edit();
            ed.putInt(id, count);
            ed.apply();
        }
    }
}
