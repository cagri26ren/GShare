package com.example.gshare.ModelClasses.Quest;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.User.User;

/**
 * The notice quest class that extends the quest class.
 *
 * @version  23.12.2019
 */
public class NoticeQuest extends Quest {

    public final static int lendQuest = 0;
    public final static int borrowQuest = 1;
    public static final int bothQuest = 2;
    public static final int willWaitDayTime = 3;

    public NoticeQuest( int badge , int price , String description , int total , int progress ) {
        super( badge , price ,description , total , progress );
    }

    /**
     * Only for constructing the first quest
     */
    public NoticeQuest( int badge , int progress ) {
        super( badge , 50 ,"Create a lending notice and do not remove it for " + willWaitDayTime + " days" , willWaitDayTime, progress );
    }

    /**
     * Chekcs Progress of the quest
     * @param user user that will checked
     * @param questType quest type
     */
    public void checkProgress( User user, int questType ) {
        int lend = user.getNumberOfLends();
        int borrow = user.getNumberOfBorrows();
        if( questType == lendQuest ) {
            setProgress( lend );
        }
        if ( questType == borrowQuest ) {
            setProgress( borrow );
        }
        if ( questType == bothQuest ) {
            setProgress( borrow + lend );
        }
    }

    /**
     * checks the progress from the starting.
     * @param user the user to check
     * @param notice the notice to check
     */
    public void checkProressForStarting( User user, Notice notice ){
        int day = (int) notice.computeTimeLeft();

        if( user.isFirstPost() ) {
            setProgress(day);
        }
    }


}