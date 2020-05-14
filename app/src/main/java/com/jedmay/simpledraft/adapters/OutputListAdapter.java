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


    private final LayoutInflater mInflator;
    private List<OutputState> mOutputStates;
    private int outputViewId;


    public OutputListAdapter(Context context, int outputViewId) {
        mInflator = LayoutInflater.from(context);
        this.outputViewId = outputViewId;
    }

    @NonNull
    @Override
    public OutputListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_item, parent, false);
        return new OutputListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutputListHolder holder, int position) {
        if (mOutputStates != null) {
            OutputState current = mOutputStates.get(position);
            holder.valueItemView.setText(String.valueOf(current.getValues()));
        } else {
            holder.valueItemView.setText("No Saved Data");
        }
    }

    @Override
    public int getItemCount() {
        if (mOutputStates != null) {
            return mOutputStates.size();
        } else return 0;
    }

    void setOutputStates(List<OutputState> outputStates) {
        mOutputStates = outputStates;
        notifyDataSetChanged();
    }

    class OutputListHolder extends RecyclerView.ViewHolder {
        private final TextView valueItemView;

        private OutputListHolder(View itemView) {
            super(itemView);
            valueItemView = itemView.findViewById(outputViewId);
        }
    }



}
