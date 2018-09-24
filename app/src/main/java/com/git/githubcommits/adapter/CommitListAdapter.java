package com.git.githubcommits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.git.githubcommits.R;
import com.git.githubcommits.models.Commit;

import java.util.List;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.MyViewHolder> {

    List<Commit> mCommits;
    private Context mContext;
    private CommitListAdapter.OnItemClickListener mItemClickListener;
    private int mLayout;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView commitId;
        public TextView committerName;
        public TextView commitDate;

        public MyViewHolder(View view) {
            super(view);
            commitId =  view.findViewById(R.id.commit_id);
            committerName =  view.findViewById(R.id.committer_name);
            commitDate =  view.findViewById(R.id.date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public CommitListAdapter(Context context, List<Commit> commits, int layout) {
        this.mCommits = commits;
        this.mContext = context;
        mLayout = layout;
        LayoutInflater mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommitListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);

        return new CommitListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommitListAdapter.MyViewHolder holder, final int position) {

        holder.commitId.setText(mCommits.get(position).getmCommitId());
        holder.committerName.setText(mCommits.get(position).getmCommiter());
        holder.commitDate.setText(mCommits.get(position).getmDate());

    }

    @Override
    public int getItemCount() {
        return mCommits.size();
    }

    //interface for recyclerView item click
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /*
    method to set listener to the adapter ViewHolder item
     */
    public void setOnItemClickListener(final CommitListAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
