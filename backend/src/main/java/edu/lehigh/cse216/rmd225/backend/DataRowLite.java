package edu.lehigh.cse216.rmd225.backend;

public class DataRowLite {
     /**
     * The id for this row; see DataRow.mId
     */
    public int mId;

    /**
     * The title string for this row of data; see DataRow.mTitle
     */
    public String mTitle;

    /**
     * Create a DataRowLite by copying fields from a DataRow
     */
    public DataRowLite(DataRow data) {
        this.mId = data.postID;
        this.mTitle = data.mSubject;
    }
    
}
