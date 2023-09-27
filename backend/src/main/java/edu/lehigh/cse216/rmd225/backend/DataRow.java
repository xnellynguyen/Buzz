package edu.lehigh.cse216.rmd225.backend;
import java.util.Date;


public class DataRow {
    /**
     * All table: postID for this row of data
     */
    public int postID;

    /**
     * All tables: UserID for this row of data
     */
    public int userID;

    /**
     * tblData: The subject for this row of data
     */
    public String mSubject;

    /**
     * tblData: The message for this row of data
     */
    public String mMessage;

    /**
     * tblData: The attachment for this row of data
     */
    public String mAttachment;

    /**
     * tblData: The link for this row of data
     */
    public String mLink;

    /**
     * tblData: The upvote for this row of data
     */

    public int mLikes;

    /**
     * tblData: The downvote for this row of data
     */
    public int mDislikes;

    /**
     * tblData: The final vote value for this row of data
     */
    public int mTotalLike;

    /**
     * tblUser: userName for this row of data
     */
    public String uName;

    /**
     * tblUser: userEmail for this row of data
     */
    public String uEmail;

    /**
     * tblUser: genderID for this row of data
     */
    public String uGenderID;

    /**
     * tblUser: sexOri for this row of data
     */
    public String uSexOrientation;

    /**
     * tblUser: bio for this row of data
     */
    public String uBio;

    /**
     * tblComment: commentID (serial primary key of this table) for this row of data
     */
    public int cID;

    /**
     * tblComment: commentMessage for this row of data
     */
    public String cMessage;

    /**
     * tblComment: comment's attachment for this row of data
     */
    public String cAttachment;

    /**
     * tblComment: comment's link for this row of data
     */
    public String cLink;

    /**
     * tblVote: likeID for this row of data
     */
    public int vLikeID;

    /**
     * tblVote: likeState for this row of data
     */
    public int vLikeState;

    /**
     * The creation date for this row of data.  Once it is set, it cannot be 
     * changed
     */
   // public final Date mCreated;

   DataRow(){}

    /**
     * Create a new DataRow with the provided id and title/content, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param title The title string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     * @param attachment The attachment string for this row of data (image in txt form)
     * 
     * @param link The link string for this row of data (link in txt form)
     */
    DataRow(int id, String title, String content, int likeCount, int dislikes, int total, int usrid, String attachment, String link) {
        postID = id;
        mSubject = title;
        mMessage = content;
        mLikes = likeCount;
        mDislikes = dislikes;
        mTotalLike = likeCount - dislikes;
        userID = usrid;
        mAttachment = attachment;
        mLink = link;
    }

    /**
     * Datarow for tblUser
     * @param int userID
     * @param String username
     * @param String useremail
     * @param String genderID
     * @param String sexualOrientation
     * 
     * @return datarow
     */
    DataRow(int useriden, String usrname, String useremail, String genderIdentity, String sexOri, String bio) {
        userID = useriden;
        uName = usrname;
        uEmail = useremail;
        uGenderID = genderIdentity;
        uSexOrientation = sexOri;
        uBio = bio;
    }

    /**
     * Datarow for tblComment
     * @param int commentID
     * @param String commentMessage
     * @param int id (postID)
     * @param int userID
     * @param String commentAttachment
     * @param String commentLink
     * 
     * @return datarow
     */
    DataRow(int commentID, String commentMessage, int id, int uid, String commentAttachment, String commentLink) {
        cID = commentID;
        cMessage = commentMessage;
        postID = id;
        userID = uid;
        cAttachment = commentAttachment;
        cLink = commentLink;
    }

    /**
     * Datarow for tblVote
     * @param int likeiD
     * @param int userID
     * @param int id (postID)
     * @param int likeState
     * 
     * @return datarow
     */
    DataRow(int likeID, int uID, int pID, int likeState) {
        vLikeID = likeID;
        userID = uID;
        postID = pID;
        vLikeState = likeState;
    }

    /*
     * Copy constructor to create one datarow from another
    */
    DataRow(DataRow data) {
        userID = data.userID;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        mSubject = data.mSubject;
        mMessage = data.mMessage;
        mLikes = data.mLikes ;
        mDislikes = data.mDislikes;
        mTotalLike = data.mTotalLike;
        postID = data.postID;
    //   mCreated = data.mCreated;
    }
    
}
