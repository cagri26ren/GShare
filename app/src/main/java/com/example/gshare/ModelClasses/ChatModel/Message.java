package com.example.gshare.ModelClasses.ChatModel;

import com.example.gshare.ModelClasses.User.User;
import java.util.Calendar;

/**
 * The basic unit of a message.
 *
 * @date 12.12.2019
 */
public class Message {

    private String message;
    private String time;
    private long millisecond;
    private User reciever;
    private User sender;

    /**
     * The constructor of the message class.
     *
     * @param msg the string message
     */
    public Message( String msg, User reciever, User sender ) {
        this.message = msg;
        time = getMessageTime();
        millisecond = createMillisecond();
        this.reciever = reciever;
        this.sender = sender;
    }

    /**
     * Use this for database
     * @param msg
     * @param time
     */
    public Message( String msg , String time, long msecond , User reciever , User sender ) {
        this.message = msg;
        this.time = time;
        this.millisecond = msecond;
        this.reciever = reciever;
        this.sender = sender;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setMillisecond(long millisecond) {
        this.millisecond = millisecond;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Message(){}

    /**
     * Gets the message string.
     *
     * @return msg message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the current time which shows the full date hour and minute.
     *
     * @return str the Time string.
     */
    private String getMessageTime() {//TESTED AND WORKS
        String newMinute = Calendar.getInstance().get( Calendar.MINUTE ) + "";
        if(Calendar.getInstance().MINUTE / 10 == 0 ){
            newMinute = 0 + "" + Calendar.getInstance().get( Calendar.MINUTE );
        }
        String str = "" + Calendar.getInstance().get( Calendar.DATE ) + "." + (Calendar.getInstance().get( Calendar.MONTH ) + 1) + "." +
                Calendar.getInstance().get( Calendar.YEAR ) + " " + fixTime( Calendar.getInstance().get( Calendar.HOUR_OF_DAY ) + "" ) + ":" + fixTime( newMinute );
        return str;
    }


    /**
     * gets the current time to user.
     * @return time the current time.
     */
    public String getTime() {
        return time;
    }
/**
    public void setMsg(String msg) {
        this.msg = msg;
    }*/

    /**
     *
     * @param msg
     * @return -1 if this object's message created earlier
     * @return 1 if this object's message created later
     */
    public int compareTo( Message msg ){
        if( this.getMillisecond() < msg.getMillisecond() ){
            return -1;
        }
        if( this.getMillisecond() > msg.getMillisecond() ) {
            return 1;
        }
        return 0;
    }

    /**
     * creates the millisecond as long data type.
     * @return calendar.getTimeInMillis() the time in milliseconds.
     */
     private long createMillisecond() {
       return Calendar.getInstance().getTimeInMillis();
   }

    /**
     * gets the millisecond time.
     * @return millisecond the millisecond time.
     */
     public long getMillisecond() {
        return millisecond;
    }

    /**
     * gets the reciever.
     * @return reciever the reciever.
     */
     public User getReciever(){
        return reciever;
    }

    /**
     * gets the sender.
     * @return sender the sender.
     */
     public User getSender(){
        return sender;
    }

    /**
     * Testing purposes only
     * @return
     */
    public String toString(){
        return message ;//+ "\n" + time;
    }

    /**
     * Only for fixing time showing
     * @param time
     * @return
     */
    private static String fixTime( String time ){
        if(time.length() == 1 ){
            return 0 + time;
        }
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}