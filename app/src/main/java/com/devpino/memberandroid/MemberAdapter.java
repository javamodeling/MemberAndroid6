package com.devpino.memberandroid;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devpino.memberandroid.databinding.Row2LayoutBinding;

import java.util.List;

/**
 * Created by DHKOH on 2017-11-20.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> members = null;

    static class MemberViewHolder extends RecyclerView.ViewHolder {

        public Row2LayoutBinding row2LayoutBinding;

        MemberViewHolder(Row2LayoutBinding row2LayoutBinding) {
            super(row2LayoutBinding.getRoot());
            this.row2LayoutBinding = row2LayoutBinding;
        }
    }

    public MemberAdapter(List<Member> members) {
        this.members = members;
    }

    public void add(int position, Member member) {
        members.add(position, member);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        members.remove(position);
        notifyItemRemoved(position);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Row2LayoutBinding row2LayoutBinding = DataBindingUtil.inflate(inflater, R.layout.row2_layout, parent, false);

        MemberViewHolder memberViewHolder= new MemberViewHolder(row2LayoutBinding);

        return memberViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Member member = members.get(position);
        holder.row2LayoutBinding.textViewName.setText(member.getMemberName());
        holder.row2LayoutBinding.textViewEmail.setText(member.getEmail());

        if(member.getPhotoUrl() != null) {

            holder.row2LayoutBinding.imageViewPhoto.setImageURI(Uri.parse(member.getPhotoUrl()));
        }

        holder.row2LayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Member member = members.get(position);

                long no = member.getNo();

                Intent intent = new Intent(view.getContext(), MemberViewActivity.class);
                intent.putExtra("no", no);

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return members.size();
    }

}
