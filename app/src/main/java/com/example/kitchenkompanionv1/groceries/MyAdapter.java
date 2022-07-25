package com.example.kitchenkompanionv1.groceries;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kitchenkompanionv1.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

   List<Grocery> groceryList;
    public MyAdapter(List<Grocery> groceryList){
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item.setText(groceryList.get(position).getName());
        holder.description.setText(String.valueOf(groceryList.get(position).getQuantity()));

    }

    public void addItem(Grocery grocery) {
        int size = groceryList.size();
        grocery.setId(size);
        groceryList.add(grocery);
        notifyItemInserted(size);
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item, description;
        CheckBox isSelected;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.food_item);
            description = itemView.findViewById(R.id.food_item_description);
            isSelected = itemView.findViewById(R.id.checked);

            itemView.findViewById(R.id.add).setOnClickListener(view -> {
                Grocery grocery = groceryList.get(getAdapterPosition());
                grocery.setQuantity(grocery.getQuantity() + 1);

                groceryList.set(getAdapterPosition(),grocery);
                MyAdapter.this.notifyItemChanged(getAdapterPosition());
            });

            itemView.findViewById(R.id.subtract).setOnClickListener(view -> {
                Grocery grocery = groceryList.get(getAdapterPosition());
                if (grocery.getQuantity() >= 1)
                   grocery.setQuantity(grocery.getQuantity() - 1);

                groceryList.set(getAdapterPosition(),grocery);
                MyAdapter.this.notifyItemChanged(getAdapterPosition());
            });

            itemView.findViewById(R.id.delete).setOnClickListener(view -> {
                groceryList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });

            isSelected.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    item.setTextColor(itemView.getContext()
                            .getResources().getColor(
                            android.R.color.darker_gray));
                } else {
                    item.setPaintFlags(0);
                    item.setTextColor(itemView.getContext()
                            .getResources().getColor(
                                    R.color.white));
                }
            });

        }
    }
}
