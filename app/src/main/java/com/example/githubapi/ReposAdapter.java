package com.example.githubapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.githubapi.R;
import com.example.githubapi.GitHubRepo;
import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<GitHubRepo> repos;
    private int rowLayout;
    private Context context;

    public ReposAdapter(List<GitHubRepo> repos, int rowLayout, Context context)
    {
        this.setContext(context);
        this.setRowLayout(rowLayout);
        this.setRepos(repos);
    }


    public List<GitHubRepo> getRepos() {
        return repos;
    }

    public void setRepos(List<GitHubRepo> repos) {
        this.repos = repos;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout,viewGroup,false);

        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder reposViewHolder, int position) {

        reposViewHolder.repoName.setText(repos.get(position).getName());
        reposViewHolder.repoDescription.setText(repos.get(position).getDescription());
        reposViewHolder.repoLanguage.setText(repos.get(position).getLanguage());

    }

    @Override
    public int getItemCount() {
        return repos.size();
    }


    public static class ReposViewHolder extends RecyclerView.ViewHolder {

        LinearLayout reposLayout;
        TextView repoName;
        TextView repoDescription;
        TextView repoLanguage;

        public ReposViewHolder(@NonNull View itemView) {
            super(itemView);

            reposLayout = (LinearLayout) itemView.findViewById(R.id.repo_item_layout);
            repoName = (TextView) itemView.findViewById(R.id.repoName);
            repoDescription = (TextView) itemView.findViewById(R.id.repoDescription);
            repoLanguage = (TextView) itemView.findViewById(R.id.repoLanguage);
        }
    }

}
