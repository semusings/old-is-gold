package com.aldieemaulana.president.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aldieemaulana.president.R;
import com.aldieemaulana.president.activity.DetailActivity;
import com.aldieemaulana.president.model.Price;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private Context context;
    private List<Price> priceList;

    public GridAdapter(Context context, List<Price> priceList) {
        this.context = context;
        this.priceList = priceList;
    }

    private void loadIntet(int position) {
        Intent intent;
        intent = new Intent(context, DetailActivity.class);

        Price price = priceList.get(position);
        intent.putExtra("id", price.getId());
        intent.putExtra("title", "Detail of " + price.getName());
        intent.putExtra("name", price.getName());
        intent.putExtra("sp", price.getSp());
        intent.putExtra("cp", price.getCp());

        context.startActivity(intent);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_style, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Price price = priceList.get(position);
        holder.itemName.setText(price.getName());
        holder.sp.setText("CP = Rs. " + price.getSp());
        holder.cp.setText("SP = Rs. " + price.getCp());
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public void clear() {
        priceList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Price> list) {
        priceList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sp, itemName, cp;
        public RelativeLayout layout;

        public ViewHolder(View view) {
            super(view);

            layout = (RelativeLayout) view.findViewById(R.id.layout);
            sp = (TextView) view.findViewById(R.id.sp);
            itemName = (TextView) view.findViewById(R.id.item_name);
            cp = (TextView) view.findViewById(R.id.cp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadIntet(getAdapterPosition());
                }
            });

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadIntet(getAdapterPosition());
                }
            });
        }

    }


}
