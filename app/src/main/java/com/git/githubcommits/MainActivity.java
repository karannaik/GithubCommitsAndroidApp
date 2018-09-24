package com.git.githubcommits;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.git.githubcommits.adapter.CommitListAdapter;
import com.git.githubcommits.models.Commit;
import com.git.githubcommits.network.AsyncTaskInterface;
import com.git.githubcommits.network.NetworkCall;
import com.git.githubcommits.network.NetworkTags;
import com.git.githubcommits.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskInterface {

    private static final String TAG = "MainActivity";
    private ProgressDialog mProgressDialog;
        private List<Commit> mCommitList;
    private CommitListAdapter mCommitListAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

    }

    private void initializeViews() {
        findViewById(R.id.btn_fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchCommits();
            }
        });

        // Initialize Adapter.
        mCommitList = new ArrayList<>();
        mCommitListAdapter = new CommitListAdapter(this, mCommitList, R.layout.list_item);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCommitListAdapter);
    }

    private void fetchCommits() {

        mProgressDialog = Utility.showProgressDialog(this, getString(R.string.fetch_commits));
        new NetworkCall(null, this, NetworkTags.URL_GET_COMMITS).execute();
    }

    @Override
    public void onTaskCompleted(String response, String urlIdentifier) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();
        }

        Log.d(TAG, "Resposnes : "+response);

        try {
            JSONArray jsonArray = new JSONArray(response);
            // Logic to fetch 10 commits only latest.
            for (int i=10;i>0;i--) {
                Commit newCommit = new Commit();
                newCommit.setmCommitId(jsonArray.getJSONObject(i).getString("node_id"));
                newCommit.setmCommiter(jsonArray.getJSONObject(i).getJSONObject("commit").getJSONObject("committer").getString("name"));
                newCommit.setmDate(jsonArray.getJSONObject(i).getJSONObject("commit").getJSONObject("committer").getString("date"));
                mCommitList.add(newCommit);
            }
            mCommitListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
