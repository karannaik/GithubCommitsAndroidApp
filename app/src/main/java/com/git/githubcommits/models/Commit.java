package com.git.githubcommits.models;

public class Commit {
    private String mCommitId;
    private String mCommiter;
    private String mDate;

    public String getmCommitId() {
        return mCommitId;
    }

    public void setmCommitId(String mCommitId) {
        this.mCommitId = mCommitId;
    }

    public String getmCommiter() {
        return mCommiter;
    }

    public void setmCommiter(String mCommiter) {
        this.mCommiter = mCommiter;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
