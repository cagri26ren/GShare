package com.example.gshare.ModelClasses.Quest;
import com.example.gshare.ModelClasses.User.User;

/**
 * Bookmark quest class that extends the quest class
 *
 * @version .12.2019
 */
public class BookmarkQuest extends Quest {

    /**
     * The constructor of the bookmark class
     * @param badge the badge of the quest
     * @param price the price of the quest
     * @param description the short description the quest.
     * @param total the total value to finish the quest.
     * @param progress the current progress of the quest.
     */
    public BookmarkQuest( int badge , int price , String description , int total , int progress ) {
        super( badge , price ,description , total , progress );
    }

    /**
     * checks the progress of the bookmark quest
     * @param user the user to check
     */
    public void checkProgress( User user ) {
        int size = user.getBookmarkList().size();
        setProgress( size );
    }

}
