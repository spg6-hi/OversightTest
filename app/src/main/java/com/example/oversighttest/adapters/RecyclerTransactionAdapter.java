package com.example.oversighttest.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oversighttest.R;
import com.example.oversighttest.entities.Transaction;
import com.example.oversighttest.pages.TransactionsPage;

import java.util.ArrayList;

public class RecyclerTransactionAdapter extends RecyclerView.Adapter<RecyclerTransactionAdapter.ViewHolder> {
    private static final String TAG = null;
    private final Context context;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;
        private final TextView tvAmount;
        private final TextView tvCategory;
        private final TextView tvDate;
        private View parentView;


        public ViewHolder(@NonNull View view) {
            super(view);
            this.parentView = view;
            // Define click listener for the ViewHolder's View

            //TextView textView = (TextView) view.findViewById(R.id.textView);
            this.tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            this.tvCategory = (TextView) view.findViewById(R.id.tvCategory);
            this.tvDate = (TextView) view.findViewById(R.id.tvDate);
        }

        public TextView getTvAmount() {
            return tvAmount;
        }

        public TextView getTvCategory() {
            return tvCategory;
        }

        public TextView getTvDate() {
            return tvDate;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param //dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     *
     */
    public RecyclerTransactionAdapter(Context contx, ArrayList<Transaction> transactions) {
        //localDataSet = dataSet;
        this.context = contx;
        this.transactions = transactions;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_transaction, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setIsRecyclable(false);
        final Transaction transaction = transactions.get(position);

        if(viewHolder != null) {
            viewHolder.getTvAmount().setTextColor((Color.WHITE));
            viewHolder.getTvCategory().setTextColor((Color.WHITE));
            viewHolder.getTvDate().setTextColor((Color.WHITE));
            viewHolder.getTvAmount().setText(Integer.toString(transaction.getAmount()));
            viewHolder.getTvCategory().setText(transaction.getCategory().getDisplayName());
            viewHolder.getTvDate().setText((transaction.getDate().toString()));
        }else{
            notifyItemChanged( position );
        }
        viewHolder.parentView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, TransactionsPage.class);
                return false;
            }


        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.transactions.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
