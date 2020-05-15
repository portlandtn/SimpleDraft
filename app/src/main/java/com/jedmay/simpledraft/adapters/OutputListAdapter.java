package com.jedmay.simpledraft.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jedmay.simpledraft.R;
import com.jedmay.simpledraft.model.OutputState;

import java.util.List;

public class OutputListAdapter extends RecyclerView.Adapter<OutputListAdapter.OutputListHolder> {

    class OutputListHolder extends RecyclerView.ViewHolder {
        private final TextView valueItemView;

        OutputListHolder(View itemView) {
            super(itemView);
            if (outputViewNumber == 1) {
                valueItemView = itemView.findViewById(R.id.textView);
            } else {
                valueItemView = itemView.findViewById(R.id.textView2);
            }
        }
    }

    private final LayoutInflater mInflater;
    private List<OutputState> mOutputStates;
    private int outputViewNumber;

    public OutputListAdapter(Context context, int outputViewNumber) {
        mInflater = LayoutInflater.from(context);
        this.outputViewNumber = outputViewNumber;
    }

    @NonNull
    @Override
    public OutputListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (outputViewNumber == 1) {
            View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new OutputListHolder(itemView);
        } else {
            View itemView = mInflater.inflate(R.layout.recyclerview_item2, parent, false);
            return new OutputListHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OutputListHolder holder, int position) {
        if (mOutputStates != null) {
            OutputState current = mOutputStates.get(position);
            holder.valueItemView.setText(String.valueOf(current.getValues()));
        } else {
            holder.valueItemView.setText(R.string.no_saved_data);
        }
    }

    @Override
    public int getItemCount() {
        if (mOutputStates != null) {
            return mOutputStates.size();
        } else return 0;
    }

    public void setOutputStates(List<OutputState> outputStates) {
        mOutputStates = outputStates;
        notifyDataSetChanged();
    }





}
