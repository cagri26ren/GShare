package com.example.gshare.ModelClasses.NoticeModel;

import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.User.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Where i left quest system should be fixed
 */

/*
This site probably gonna be usefull
https://www.youtube.com/watch?v=XQJiiuk8Feo
This class will create a notice and do aproper things
 */
public class Notice implements Serializable {
    public static final int LEND_NOTICE = 1;
    public static final int BORROW_NOTICE = 2;

    private int noticeType;
    private int day;
    private int category;
    private int rateLend;
    private int rateBorrow;
    private boolean agreed;
    private boolean over;
    private boolean ratedBorrow;
    private boolean ratedLend;
    private String name;
    private String note;
    private long postingTime;
    private String commentBorrowing;
    private String commentLending;
    private int g;
    private int tempG;
    private int tempDay;
    private long startTime;
    private User noticeOwner;
    private User noticeTaker;
    private LocationG location;
    private String id;
    private boolean showable;
    private String imageUrl;

    public Notice(){}
    /**
    Use this constructor when creating a Borrow Notice since g amount is not a parameter here

    @param name name of the notice
    @param day how many days will be agreed on
    @param note note entered to the notice
    @param category category of the notice
    @param noticeOwner owner of the notice
    @param location the location that notice is posted
     */
    public Notice( String name, int day, String note, int category, User noticeOwner, LocationG location , String id ){
        this.name = name;
        this.day = day;
        this.note = note;
        this.category = category;
        this.noticeOwner = noticeOwner;
        noticeType = BORROW_NOTICE;
        this.location = location;
        agreed = false;
        over = false;
        ratedLend = false;
        ratedBorrow = false;
        postingTime = (new Date()).getTime();
        this.id = id;
        showable = true;
    }


    /**
    This constructor should be used when creating lend notices
    @param name name of the notice
    @param day how many days will be agreed on
    @param note note entered to the notice
    @param category category of the notice
    @param g the g amount that poster of the notice determined
    @param noticeOwner owner of the notice
    @param location the location that notice is posted
     */
    public Notice( String name, int day, String note, int category, User noticeOwner , int g, LocationG location , String id){
        this.name = name;
        this.day = day;
        this.note = note;
        this.category = category;
        this.noticeOwner = noticeOwner;
        this.g = g;
        noticeType = LEND_NOTICE;
        this.location = location;
        over = false;
        agreed = false;
        ratedLend = false;
        ratedLend = false;
        postingTime = (new Date()).getTime();
       //noticeOwner.setFirstPost(false);
        this.id = id;
        showable = true;
    }

    /**
     * gets the day
     * @return day the day.
     */
    public int getDay() {
        return day;
    }

    /**
     * sets the day to user.
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * sets the category
     * @return category the category.
     */
    public int getCategory() {
        return category;
    }

    /**
     * sets the category
     * @param category the category
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * gets the lend rate
     * @return rateLend the rate lend
     */
    public int getRateLend() {
        return rateLend;
    }

    /**
     * sets the lend rate
     * @param rateLend the rate lend.
     */
    public void setRateLend(int rateLend) {
        if( isOver() ) {
            this.rateLend = rateLend;
            ratedLend = true;
        }
    }

    /**
     * sets the database lend rate
     * @param rateLend the lend rate to set.
     */
    public void setForDatabaseRateLend( int rateLend ){
        this.rateLend = rateLend;
    }

    /**
     * gets the borrow rare
     * @return the borrow rate
     */
    public int getRateBorrow(){
        return rateBorrow;
    }

    /**
     * sets the borrow rate.
     * @param rateBorrow the borrow rate.
     */
    public void setRateBorrow(int rateBorrow) {
        if( isOver() ) {
            this.rateBorrow = rateBorrow;
            ratedBorrow = true;
        }
    }

    /**
     * sets the borrow rate for database
     * @param rateBorrow the borrow rate.
     */
    public void setForDatabaseRateBorrow( int rateBorrow ){
        this.rateBorrow = rateBorrow;
    }

    /**
     * checks if it is agreed
     * @return agreed the agreed status.
     */
    public boolean isAgreed() {
        return agreed;
    }

    /**
     * checks if the notice is over
     * @return over the over status
     */
    public boolean isOver() {
        return over;
    }

    /**
     * gets the name to user.
     * @return name the name.
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the note
     * @return note the note.
     */
    public String getNote() {
        return note;
    }

    /**
     * sets the note
     * @param note the note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * gets posting time.
     * @return postingTime the posting time.
     */
    public long getPostingTime() {
        return postingTime;
    }

    /**
     * sets the posting time.
     * @param time the time.
     */
    public void setPostingTime( long time ){
        this.postingTime = time;
    }

    /**
     * gets comment borrowing.
     * @return commentBorrowing the comment on borrowing.
     */
    public String getCommentBorrowing() {
        return commentBorrowing;
    }

    /**
     * gets the comment lending.
     * @return commentLending the comment lending.
     */
    public String getCommentLending() {
        return commentLending;
    }

    /**
     * sets the comment borrowing
     * @param comment the comment to set
     */
    public void setCommentBorrowing(String comment) {
        if( isOver() ) {
            this.commentBorrowing = comment;
        }
    }

    /**
     * sets the comment lending
     * @param comment the comment to set.
     */
    public void setCommentLending( String comment ) {
        if( isOver() ) {
            this.commentLending = comment;
        }
    }

    /**
     * gets the G to user.
     * @return g the g value.
     */
    public int getG() {
        return g;
    }

    /**
     * sets the G cdfepending on parameter.
     * @param g the g to set
     */
    public void setChatG(int g) {//TESTED AND WORKS

        if( this.g < g || isAgreed() ){
            throw new IllegalArgumentException( "It is already over or g is not approperite" );
        }
        else {
            this.g = g;
        }
    }

    /**
     * sets the g for database.
     * @param g the g value.
     */
    public void setG( int g ){
        this.g = g;
    }

    /**
     * gets the location to user.
     * @return location the location.
     */
    public LocationG getLocation() {
        return location;
    }

    /**
     * gets the notice type whether lending or borrowing.
     * @return noticeType the notice type.
     */
    public int getNoticeType() {
        return noticeType;
    }

    /**
     * For database usage only do not use this
     * @param noticeType type of the notice
     */
    public void setNoticeType( int noticeType ){
        this.noticeType = noticeType;
    }
    /**
    Please add try/catch to this and show a pop up message something like you don't have enough money so you don't have
    negative money
    Only use for borrow notices and use when users agree on the notice

    @param noticeTaker taker of the notice
    @param g g amount that agreed
     */
    public void agreeOnBorrowNotice( User noticeTaker , int g ){//TESTED AND WORKS
        this.noticeTaker = noticeTaker;
        if( noticeOwner.getG() >= g + noticeOwner.getActiveGAmount() ){
            this.g = g;
            agreed = true;
            noticeOwner.addActiveDealGAmount( g );
            startTime = new Date().getTime();
            noticeTaker.setNumberOfLends( noticeTaker.getNumberOfLends() + 1 );
            noticeOwner.setNumberOfBorrows( noticeOwner.getNumberOfBorrows() + 1 );
            showable = false;
        }
        else{
            throw new IllegalArgumentException( "Notice owner does not have enough money");
        }
    }

    public boolean isShowable() {
        return showable;
    }

    public void setShowable(boolean showable) {
        this.showable = showable;
    }

    /**
        Please add try/catch to this and show a pop up message something like you don't have enough money so you don't have
        negative money
        Only use for lending notices and use when users agree on the notice

        @param noticeTaker taker of the notice
         */
    public void agreeOnLendNotice( User noticeTaker ){//TESTED AND WORKS
        this.noticeTaker = noticeTaker;
        if( noticeTaker.getG() >= g + noticeTaker.getActiveGAmount() ){
            agreed = true;
            noticeTaker.addActiveDealGAmount( g );
            startTime = new Date().getTime();
            noticeTaker.setNumberOfBorrows( noticeTaker.getNumberOfBorrows() + 1 );
            noticeOwner.setNumberOfLends( noticeOwner.getNumberOfLends() + 1 );
            showable = false;
        }
        else{
            throw new IllegalArgumentException( "Notice owner does not have enough money");
        }
    }
    /**
    Use this method when the item is returned to its owner
    It will do transactions
     */
    public void finish(){//TESTED AND WORKS
        over = true;
        doTransaction( noticeOwner , noticeTaker );
        if( noticeType == BORROW_NOTICE ){
            noticeOwner.addActiveDealGAmount( -g );
        }
        if( noticeType == LEND_NOTICE ){
            noticeTaker.addActiveDealGAmount( -g );
        }
    }
    /**
    Used: https://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-java
    This method is used in the doTransaction method inorder to compute how many days passed since the agreement started
    you may not need to use this method

    @return long how many days left to finish
     */
    public long computeTimeLeft(){//TESTED AND WORKS
        Date current = new Date();
        long diff = current.getTime() - startTime;
        return (day - TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    /*

    /**

    For checking if the notice time is over ( did not test yet these computeTimeLeft methods definietly need testing)
    @return diffInMillies time left in milli seconds
     */
    public long computeTimeLeftForMilliSeconds(){//TESTED AND WORKS
        long finishTime = startTime + TimeUnit.MILLISECONDS.convert(day, TimeUnit.DAYS);
        long diffInMillies = finishTime - ( new Date() ).getTime();
        return diffInMillies;
    }
    public long computeTimeLeftHours(){//TESTED AND WORKS
        Date current = new Date();
        long diff = current.getTime() - startTime;
        return (day*24 - TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
    }
    /**
    This method does the G transactions between users according to the how many days of the agreement is fullfilled and
    according to the g amount of the agreement
    REMEMBER WE DO TRANSACTIONS AFTER THE AGREEMENT IS OVER SINCE IT CAN BE TERMINATED WE DO THE TRANSACTIONS ACCORDING
    TO THE HOW MANY DAYS HAVE PASSED.

    @param noticeOwner owner of the notice
    @param noticeTaker who agreed to take the notice
     */
    public void doTransaction( User noticeOwner, User noticeTaker){//TESTED AND WORKS
        double gToTransact = ( g / ( day * 1.0 ) ) * ( day - ( computeTimeLeft() * 1.0 ) );//DO NOT FORGET

        if(computeTimeLeft() <= 0 ){
            gToTransact = g;
        }

        if( noticeType == BORROW_NOTICE ) {
           noticeOwner.withdraw( (int)gToTransact );
           noticeTaker.deposit( (int)gToTransact );
        }
        if( noticeType == LEND_NOTICE ) {
            noticeTaker.withdraw( (int)gToTransact );
            noticeOwner.deposit( (int)gToTransact );
        }
    }
    /**
    This method changes users' average rates according to the given rates to the notice. This method can be called
    always since it does not compute the rates that doesnt get entered earlier BUT THIS METHOD SHOULD BE CALLED AFTER
    TRANSACTION IS OVER AND USERS ASKED TO RATE EACH OTHER AND IT DOES NOT MATTER WHETHER THEY RATED EACH OTHER !!!!MOST
    IMPORTANT THING IS TO BE ASKED TO RATE EACH OTHER.AND IT MUST ONLY BE USED ONCE.
     */
    public void evaluateRates() {//TESTED AND WORKS
        if ( isOver() ) {
            if (noticeType == BORROW_NOTICE) {
                if ( ratedBorrow ) {
                    noticeOwner.computeAverageBorrowRate(rateBorrow);
                }
                if ( ratedLend ) {
                    noticeTaker.computeAverageLendRate(rateLend);
                }
            }
            if (noticeType == LEND_NOTICE) {
                if( ratedBorrow ) {
                    noticeTaker.computeAverageBorrowRate(rateBorrow);
                }
                if( ratedLend ) {
                    noticeOwner.computeAverageLendRate(rateLend);
                }
            }
        }
    }


    /**
     * gets the notice owner
     * @return noticeOwner the notice owner.
     */
    public User getNoticeOwner(){
        return noticeOwner;
    }

    /**
     * gets the notice taker.
     * @return noticeTaker the notice taker.
     */
    public User getNoticeTaker() {
        return noticeTaker;
    }

    /**
     * For database usage only
     * @param startTime
     */
    public void setStartTime( long startTime ){
        this.startTime = startTime;
    }
    public long getStartTime(){
        return startTime;
    }

    /**
     * For database usage only
     * @param agreed
     */
    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    /**
     * For database usage only
     * @param over
     */
    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * Testing purposes only
     * @return
     */
    public String toString(){
        String output;
        output = name + " " + g;
        return output;
    }

    /**
     * the equals method to check whether notices are equal.
     * @param o the notice object to compare.
     * @return true equal
     * @return false they are not equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return noticeType == notice.noticeType &&
                day == notice.day &&
                category == notice.category &&
                agreed == notice.agreed &&
                over == notice.over &&
                postingTime == notice.postingTime &&
                g == notice.g &&
                startTime == notice.startTime &&
                Objects.equals(name, notice.name) &&
                Objects.equals(note, notice.note) &&
                Objects.equals(noticeOwner, notice.noticeOwner) &&
                Objects.equals(noticeTaker, notice.noticeTaker);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNoticeOwner(User noticeOwner) {
        this.noticeOwner = noticeOwner;
    }

    public void setNoticeTaker(User noticeTaker) {
        this.noticeTaker = noticeTaker;
    }

    public int getTempG() {
        return tempG;
    }

    public void setTempG(int tempG) {
        this.tempG = tempG;
    }

    public int getTempDay() {
        return tempDay;
    }

    public void setTempDay(int tempDay) {
        this.tempDay = tempDay;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
