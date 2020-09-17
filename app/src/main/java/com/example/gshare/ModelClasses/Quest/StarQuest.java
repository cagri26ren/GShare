package com.example.gshare.ModelClasses.Quest;
import com.example.gshare.ModelClasses.User.User;

/**
 * The starquest class that extends the quest class.
 *
 * @version 23.12.2019
 */
public class StarQuest extends Quest {

    public static final int lenderTotalRatingQuest = 0;
    public static final int borrowerTotalRatingQuest = 1;
    public static final int bothTotalRatingQuest = 2;
    public static final int lender5StarRatingQuest = 3;
    public static final int borrower5StarRatingQuest = 4;
    public static final int both5StarRatingQuest = 5;

    /**
     * The constructor fo the class
     * @param badge the badge of the quest
     * @param price the price of the quest
     * @param description the short description the quest.
     * @param total the total value to finish the quest.
     * @param progress the current progress of the quest.
     */
    public StarQuest( int badge , int price , String description , int total , int progress ) {
        super( badge , price ,description , total , progress );
    }

    /**
     * checks the progress of a user.
     * @param user the user to check
     * @param questType the quest type of the quest.
     */
    public void checkProgress( User user , int questType ) {
        int lenderTotal = user.getNumberOfLendRates();
        int borrowerTotal = user.getNumberOfBorrowRates();

        if( questType == lenderTotalRatingQuest ) {
            setProgress( lenderTotal );
        }
        if ( questType == borrowerTotalRatingQuest ) {
            setProgress( borrowerTotal );
        }
        if ( questType == bothTotalRatingQuest ) {
            setProgress( borrowerTotal + lenderTotal );
        }

        int lender5 = user.getFiveStarLendingRates();
        int borrower5 = user.getFiveStarBorrowinRates();

        if( questType == lender5StarRatingQuest ) {
            setProgress( lender5 );
        }
        if ( questType == borrower5StarRatingQuest ) {
            setProgress( borrower5 );
        }
        if ( questType == both5StarRatingQuest ) {
            setProgress( borrower5 + lender5 );
        }

    }

}