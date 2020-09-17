package com.example.gshare.ModelClasses.Quest;

/**
 * The Quest abstract class that is the parent of many other sub quest class
 *
 * @version 23.12.2019
 */
public abstract class Quest {

    private int badge;
    private int price;
    private String description;
    private int total;
    private int progress;
    private boolean completed;

    /**
     * The constructor of the quest class
     * @param badge the badge of the quest
     * @param price the price of the quest
     * @param description the short description the quest.
     * @param total the total value to finish the quest.
     * @param progress the current progress of the quest.
     */
    public Quest( int badge , int price , String description , int total , int progress ) {
        this.badge = badge;
        this.price = price;
        this.description = description;
        this.total = total;
        this.progress = progress;
        completed = isCompleted();
    }

    /**
     * get the description
     * @return description the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the price
     * @return price the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * gets the badge
     * @return badge the badge
     */
    public int getBadge() {
        return badge;
    }

    /**
     * gets the total value to finish quest
     * @return total the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * checks whether the quest is completed
     */
    private void checkCompleted() {
        if (  !(progress < total)  ) {
            completed = true;
        }
    }

    /**
     * returns completed to the user.
     * @return completed boolean of completed info.
     */
    public boolean isCompleted() {
        checkCompleted();
        return completed;
    }

    /**
     * gets the progress to the user.
     * @return
     */
    public int getProgress() {
        return progress;
    }

    /**
     * adds progress to quest
     * @param addint the int to add to progress.
     */
    public void addProgress( int addint ) {
        if (!completed) {
            if ( progress > total ) {
                progress = total;
            } else {
                progress = progress + addint;
            }
        }
        checkCompleted();
    }

    /**
     * sets the progress to quest
     * @param setint the int to add to progress.
     */
    public void setProgress( int setint ) {
        progress = setint;
        if ( progress > total ) {
            progress = total;
        }
        checkCompleted();
    }


}
