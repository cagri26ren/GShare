package com.example.gshare.ModelClasses.User;

import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.Quest.*;
import java.util.ArrayList;
import java.util.Objects;
/**
 * This class is a blueprint for creating user object a basic unit of the program.
 *
 * @version 23.12.2019
 */
public class User {
    //Variables
    private String id;
    private String userName;
    private String password;
    private String email;
    private LocationG location;
    private long creditCardNumber;
    private int g;
    private double lenderRating;
    private double borrowerRating;
    private int numberOfLendRates;
    private int numberOfBorrowRates;
    private int numberOfLends;
    private int numberOfBorrows;
    private int activeGAmount;//TESTED AND WORKS
    private ArrayList<Quest> questsToDisplay;
    private ArrayList<User> bookmarkList;
    private QuestCollection allQuests;
    //Only for quests
    private int fiveStarLendingRates;
    private int fiveStarBorrowinRates;
    private boolean firstPost; //Only for lending


    /**
     * This constructor constructs a user object.
     * @param userName username of the user
     * @param password password of the user
     * @param email email of the user
     * @param g money amount of the user
     */
    public User( String userName, String password, String email, int g , String id ) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.location = getLocation();
        this.g = g;
        activeGAmount = 0;
        firstPost = true;
        bookmarkList = new ArrayList<User>();
        questsToDisplay = new ArrayList<Quest>();
        allQuests = QuestCollection.getQuestCollection();
        this.id = id;
    }
    public User(){}


    /**
     * This method deposits money to the user
     * @param g money amount
     */
    public void deposit( int g ){
        this.g = this.g + g;
    }

    /**
     * This method withdraws money from the user
     * @param g money amount
     */
    public void withdraw( int g ){
        if( g > this.g ){
            throw new IllegalArgumentException( "G is too high!" );
        }
        else{
            this.g = this.g - g;
        }
    }

    /**
     * Gets lender rating
     * @return lenderRating
     */
    public double getLenderRating() {
        return lenderRating;
    }

    /*
    PLEASE DONT USE THIS ONLY FOR DATABASE USING
      */

    /**
     * Sets lender rating database usage only.
     * @param lenderRating
     */
    public void setLenderRating(double lenderRating) {
        this.lenderRating = lenderRating;
    }

    /**
     * Gets borrower rating
     * @return borrower rating
     */
    public double getBorrowerRating() {
        return borrowerRating;
    }

    /*
    PLEASE DONT USE THIS ONLY FOR DATABASE USING
     */

    /**
     * Sets borrower rating
     * @param borrowerRating rating of the borrower
     */
    public void setBorrowerRating(double borrowerRating) {
        this.borrowerRating = borrowerRating;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setG(int g) {
        this.g = g;
    }

    /**
     * Gets number of lending rates that this user have
     * @return numberOfLendRates number of lendings rates
     */
    public int getNumberOfLendRates(){
        return numberOfLendRates;
    }

    /*
    PLEASE DONT USE THIS ONLY FOR DATABASE USING
     */
    /**
     * Sets number of lending rates this user have database usage only
     * @param numberOfLendRates number of lend rates user have
     */
    public void setNumberOfLendRates(int numberOfLendRates ) {
        this.numberOfLendRates = numberOfLendRates ;
    }

    /**
     * Gets number of borrowing rates that this user have
     * @return numberOfBorrowRates amount this user have
     */
    public int getNumberOfBorrowRates() {
        return numberOfBorrowRates;
    }
    /*
    PLEASE DONT USE THIS ONLY FOR DATABASE USING
     */

    /**
     * Sets number of borrowing rates database usage only
     * @param numberOfBorrowRates number of borrow rates
     */
    public void setNumberOfBorrowRates(int numberOfBorrowRates ) {
        this.numberOfBorrowRates = numberOfBorrowRates;
    }

    /**
     * This method adds g to active deal g amount which is the amount of debt that user currently have, method used for
     * decision of the system which lets users agree on another notice
     * @param g amount of g that will be add
     */
    public void addActiveDealGAmount(int g ){
        this.activeGAmount += g;
    }

    /**
     * Gets active deal g amount
     * @return activeGAmount active g amount
     */
    public int getActiveGAmount(){
        return activeGAmount;
    }

    /**
     * This methods gets the g of the user
     * @return g current g of the user
     */
    public int getG(){
        return g;
    }

    /**
     * This methods computes average lend rate of the user
     * @param lendRate lend rate that this method will compute average by adding it
     */
    public void computeAverageLendRate( int lendRate ){//TESTED AND WORKS
        lenderRating = ( ( lenderRating * numberOfLendRates ) + lendRate ) / ( numberOfLendRates + 1 );
        numberOfLendRates++;
    }

    /**
     * This methods computes average borrow rate of the user
     * @param borrowRate borrow rate that this method will compute average by adding it
     */
    public void computeAverageBorrowRate( int borrowRate ){//TESTED AND WORKS
        borrowerRating = ( ( borrowerRating * numberOfBorrowRates ) + borrowRate ) / ( numberOfBorrowRates + 1 );
        numberOfBorrowRates++;
    }

    /**
     * This method updates the location of the user
     */
    public void updateLocation(){
        location = new LocationG();
    }

    /**
     * This method returns a location object
     * @return location location object
     */
    public LocationG getLocation(){
        updateLocation();
        return location;
    }

    /**
     * This method gets the password of the user
     * @return password password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method gets the email of the user
     * @return email of the user
     */
    public String getEmail(){
        return email;
    }

    /**
     * This method gets the user name of the user
     * @return userName username of the user
     */
    public String getUserName(){
        return userName;
    }

    /**
     * This method gets the name and surname of the user
     * @return name and surname of the user
     */

    /**
     * This method gets the credit card number of the user
     * @return credit card number of the user
     */
    public long getCreditCardNumber(){
        return creditCardNumber;
    }

    /**
     * This method sets the credit card number of the user
     * @param creditCardNumber credit card number of the user
     */
    public void setCreditCardNumber( long creditCardNumber ){
        this.creditCardNumber = creditCardNumber;
    }

    /*
    PLEASE DONT USE THIS DATABASE USE ONLY
     */
    /**
     * This method sets the active g amount of the user ( debt )
     * Database usage only please dont use
     * @param g g amount
     */
    public void setActiveGAmount( int g ){
        this.activeGAmount = g;
    }

    /**
     * This method gets the number of five stars that user got from lendings
     * @return five star lending rate amount
     */
    public int getFiveStarLendingRates() {
        return fiveStarLendingRates;
    }

    /**
     * This method sets the five star amount that user got from lendings
     * @param fiveStarLendingRates five star amount
     */
    public void setFiveStarLendingRates(int fiveStarLendingRates) {
        this.fiveStarLendingRates = fiveStarLendingRates;
    }

    /**
     * This method gets the number of five stars that user got from borrowings
     * @return five star borrowing rate amount
     */
    public int getFiveStarBorrowinRates() {
        return fiveStarBorrowinRates;
    }

    /**
     * This method sets the five star amount that user got from borrowings
     * @param fiveStarBorrowinRates five star amount
     */
    public void setFiveStarBorrowinRates(int fiveStarBorrowinRates) {
        this.fiveStarBorrowinRates = fiveStarBorrowinRates;
    }

    /**
     * This method gets the amount of lends user made
     * @return amount of lends
     */
    public int getNumberOfLends() {
        return numberOfLends;
    }

    /**
     * This method sets the amount of lends user made
     * Database usage only
     * @param numberOfLends amount of lends
     */
    public void setNumberOfLends(int numberOfLends) {
        this.numberOfLends = numberOfLends;
    }

    /**
     * This method gets the number of borrows that user made
     * @return amount of borrows
     */
    public int getNumberOfBorrows() {
        return numberOfBorrows;
    }

    /**
     * This method sets the amount of borrows user made
     * Database usage only
     * @param numberOfBorrows amount of borrows
     */
    public void setNumberOfBorrows(int numberOfBorrows) {
        this.numberOfBorrows = numberOfBorrows;
    }

    /**
     * This method gets the bookmark list of the user
     * @return bookmark list of the user
     */
    public ArrayList<User> getBookmarkList(){
        return bookmarkList;
    }

    /*
    ONLY FOR DATABASE USAGE
     */
    /**
     * This method sets the bookmark list of the user
     * Database usage only
     * @param list bookmark list of the user
     */
    public void setBookmarkList( ArrayList<User> list ){
        bookmarkList = list;
    }

    /**
     * This method sets which quests to displa
     * @param q1 quest to display
     * @param q2 quest to display
     * @param q3 quest to display
     * @param q4 quest to display
     * @param q5 quest to display
     */
    public void setQuestsToDisplay(Quest q1, Quest q2, Quest q3, Quest q4, Quest q5 ){
        ArrayList<Quest> list = new ArrayList<Quest>();
        list.add(q1);
        list.add(q2);
        list.add(q3);
        list.add(q4);
        list.add(q5);
        questsToDisplay = list;
    }

    /**
     * This method get quests that displayed
     * @return quests that displayed
     */
    public ArrayList<Quest> getQuestsToDisplay(){
        return questsToDisplay;
    }

    /**
     * This method returns all quests currently added to program
     * @return all quests
     */
    public QuestCollection getAllQuests() {
        return allQuests;
    }

    /**
     * This method returns whether it's user's first post or not
     * @return whether it's user have ever posted or not
     */
    public boolean isFirstPost() {
        return firstPost;
    }

    /**
     * Sets whether user's first post or not
     * @param firstPost whether its user's first post or not
     */
    public void setFirstPost(boolean firstPost) {
        this.firstPost = firstPost;
    }

    /**
     * Only for arrayList usage not really important for now
     * Equals if they have the same username
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user.getEmail().equals( this.getEmail() );
    }

    /**Sets the userName
     *
     * @param str user name
     */
    public void setUserName(String str){
        userName = str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
