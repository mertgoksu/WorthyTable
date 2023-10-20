package com.mertg.worthytable;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mertg.worthytable.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {

    ArrayList<Company> companyArrayList;

    public CompanyAdapter(ArrayList<Company> companyArrayList){
        this.companyArrayList = companyArrayList;
    }

    @NonNull
    @Override
    public CompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CompanyHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyHolder holder, int position) {
        holder.binding.recyclerViewTextView.setText(companyArrayList.get(position).hisseadi);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Verileri showresulta gönderiyorum
                    Intent intent = new Intent(holder.itemView.getContext(),ShowResultActivity.class);
                    intent.putExtra("companyId",companyArrayList.get(position).id);
                    holder.itemView.getContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }

    public class CompanyHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;

        public CompanyHolder(RecyclerRowBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
