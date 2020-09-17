package com.example.gshare.ModelClasses.Sort;

import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import java.util.ArrayList;

/**
 * The full static sort class that sorts the notices in the main home page.
 *
 * @version 23.12.2019
 */
public class Sort {

    public static final char LENDING = 'L';
    public static final char BORROWING = 'B';


    public static final int ALL = 0;
    public static final int EDUCATION = 1;
    public static final int ELECTRONIC = 2;
    public static final int TRANSPORT = 3;
    public static final int STATIONARY = 4;
    public static final int PET = 5;
    public static final int LECTURE_NOTES = 6;
    public static final int BOOKS = 7;
    public static final int OTHER = 8;

    /**
     * sorts all notices by keyword.
     * @param noticeList the notice list to sort.
     * @param keyWord the keyword to sort.
     * @param noticeType the noticetype.
     * @return sortedList the sorted list.
     */
    public static ArrayList<Notice> sortByKeyWord ( ArrayList<Notice> noticeList , String keyWord , char noticeType ) {//TESTED AND WORKS
        ArrayList<Notice> sortedList = new ArrayList<Notice>();

        if( noticeType == BORROWING){
            noticeList = getBorrowings( noticeList );
        }
        if( noticeType == LENDING){
            noticeList = getLendings( noticeList );
        }
        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            if ( noticeList.get(i).getName().indexOf(keyWord) != -1 ) {
                sortedList.add(noticeList.get(i));
            }
        }

        return sortedList;
    }

    /**
     * sorts all notices by category.
     * @param noticeList the notice list to sort.
     * @param categoryType the category type to sort.
     * @return sortedList the sorted list.
     */
    public static ArrayList<Notice> sortByCategory ( ArrayList<Notice> noticeList , int categoryType) {//TESTED AND WORKS
        ArrayList<Notice> sortedList = new ArrayList<Notice>();

        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            if ( noticeList.get(i).getCategory() == categoryType ) {
                sortedList.add(noticeList.get(i));
            }
        }

        return sortedList;
    }

    /**
     * sorts all notices by g interval
     * @param noticeList the notice list to sort.
     * @param startG the start g.
     * @param endG the end g.
     * @return sortedList the sorted list.
     */
    public static ArrayList<Notice> sortByGInterval ( ArrayList<Notice> noticeList , int startG , int endG  ) {//TESTED AND WORK
        ArrayList<Notice> sortedList = new ArrayList<Notice>();

        noticeList = getLendings( noticeList );

        for ( int i = 0 ; i < noticeList.size(); i++ ) {
            if ( noticeList.get(i).getG() <= endG && startG <= noticeList.get(i).getG() ) {
                sortedList.add(noticeList.get(i));
            }
        }

        return sortedList;
    }

    /**
     * sorts all notices by distance.
     * @param noticeList the notice list to sort.
     * @param user the user that is sorting.
     * @param interval the distance interval of sort.
     * @param noticeType the notice type.
     * @return sortedlist the sorted list.
     */
    public static ArrayList<Notice> sortByDistance ( ArrayList<Notice> noticeList , User user , int interval, char noticeType ) {//TESTED AND PROBABLY IT WORKS HARD TO SEE
        ArrayList<Notice> sortedList = new ArrayList<Notice>();

        if( noticeType == BORROWING){
            noticeList = getBorrowings( noticeList );
        }
        if( noticeType == LENDING){
            noticeList = getLendings( noticeList );
        }
        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            if ( user.getLocation().distanceFrom(noticeList.get(i).getLocation()) <= interval ){
                sortedList.add(noticeList.get(i));
            }
        }

        for( int j = 0; j < sortedList.size(); j++ ) {
            int smallestPos = j;

            for (int i = j; i < sortedList.size(); i++) {
                if (user.getLocation().distanceFrom(sortedList.get(i).getLocation()) < user.getLocation().distanceFrom(sortedList.get(smallestPos).getLocation()))
                    smallestPos = i;
            }

            Notice temp = sortedList.get(smallestPos);
            sortedList.set( smallestPos, sortedList.get(j) );
            sortedList.set( j , temp );

        }


        return sortedList;
    }

    /**
     * sorts all notices by bookmark
     * @param noticeList the notice list to sort.
     * @param user the user to get the bookmark.
     * @param noticeType the notice type
     * @return
     */
    public static ArrayList<Notice> sortByBookmark ( ArrayList<Notice> noticeList , User user, char noticeType ) {//TESTED AND WORKS
        ArrayList<Notice> sortedList = new ArrayList<Notice>();

        if( noticeType == BORROWING){
            noticeList = getBorrowings( noticeList );
        }
        if( noticeType == LENDING){
            noticeList = getLendings( noticeList );
        }
        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            for (int j = 0; j < user.getBookmarkList().size(); j++) {
                if (noticeList.get( i ).getNoticeOwner().equals(user.getBookmarkList().get( j ) ) ) {
                    sortedList.add( noticeList.get( i ) );
                }
            }
        }

        return sortedList;
    }

    /**
     * sorts all notices by lexiographically.
     * Reason not working is big and small letter!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @param noticeList the notice list to sort.
     * @return noticeList the sorted list.
     */
    public static ArrayList<Notice> sortByLexiography ( ArrayList<Notice> noticeList ) {//TESTED AND WORKS

        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            int smallestIndex = i;

            for ( int j = i ; j < noticeList.size() ; j++ ) {
                if ( noticeList.get(smallestIndex).getName().compareTo(noticeList.get(j).getName()) > 0 ) {
                    smallestIndex = j;
                }
            }

            Notice temp = noticeList.get(smallestIndex);
            noticeList.set( smallestIndex , noticeList.get(i) );
            noticeList.set(i , temp);
        }
        return noticeList;
    }

    /**
     * sorts all notices by post time.
     * @param noticeList the notice list to sort.
     * @return noticeList the sorted list.
     */
    public static ArrayList<Notice> sortByPostTime ( ArrayList<Notice> noticeList ){//TESTED AND WORKS

        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            int smallestIndex = i;

            for (int j = i; j < noticeList.size(); j++) {
                if (noticeList.get(smallestIndex).getPostingTime() < noticeList.get(j).getPostingTime()) {
                    smallestIndex = j;
                }
            }

            Notice temp = noticeList.get( smallestIndex );
            noticeList.set(smallestIndex, noticeList.get(i));
            noticeList.set(i, temp);
        }
        return noticeList;
    }

    /**
     * gets all lending notices to the user.
     * @param noticeList the notice list to get lending notices
     * @return sortedList the sorted list.
     */
    public static ArrayList< Notice > getLendings( ArrayList<Notice> noticeList ){//TESTED AND WORKS
        ArrayList< Notice > sortedList = new ArrayList<Notice>();

        for( int i = 0; i < noticeList.size(); i++ ){
            if( noticeList.get(i).getNoticeType() == Notice.LEND_NOTICE){
                sortedList.add( noticeList.get(i) );
            }
        }
        return sortedList;

    }

    /**
     * gets all borrowing notices to the user.
     * @param noticeList the notice list the get borrowing notices.
     * @return sortedList returns sorted list.
     */
    public static ArrayList< Notice > getBorrowings( ArrayList<Notice> noticeList ){//TESTED AND WORKS
        ArrayList< Notice > sortedList = new ArrayList<Notice>();

        for( int i = 0; i < noticeList.size(); i++ ){
            if( noticeList.get(i).getNoticeType() == Notice.BORROW_NOTICE){
                sortedList.add( noticeList.get(i) );
            }
        }
        return sortedList;

    }
    public static ArrayList< Notice > getShowables( ArrayList<Notice> noticeList ){//TESTED AND WORKS
        ArrayList< Notice > sortedList = new ArrayList<Notice>();

        for( int i = 0; i < noticeList.size(); i++ ){
            if( noticeList.get(i).isShowable()){
                sortedList.add( noticeList.get(i) );
            }
        }
        return sortedList;

    }
    public static ArrayList< Notice > removeOvers( ArrayList<Notice> noticeList ){//TESTED AND WORKS
        ArrayList< Notice > sortedList = new ArrayList<Notice>();

        for( int i = 0; i < noticeList.size(); i++ ){
            if( !noticeList.get(i).isOver()){
                sortedList.add( noticeList.get(i) );
            }
        }
        return sortedList;

    }

    // Not tested added recently
    /**
     * randomizes all notices.
     * @param noticeList the noticeList to randomize noticeList.
     * @return noticeList the randomized notice list.
     */
    public static ArrayList< Notice > randomize( ArrayList<Notice> noticeList ){
        for( int i = 0; i < noticeList.size(); i++ ) {
            int random = (int) (Math.random() * noticeList.size());
            Notice temp = noticeList.get(random);
            noticeList.set( random, noticeList.get(i) );
            noticeList.set( i , temp );
        }
        return noticeList;
        
    }




}