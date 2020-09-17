package com.example.gshare.ModelClasses.ChatModel;
import android.os.Handler;

import java.util.ArrayList;
/**
 * The ChatCollection class that have a collection of chats to alter.
 *
 * @date 15.12.2019
 */
public class ChatCollection {

    private ArrayList<Chat> chatList;


    /**
     * The constructor of the ChatCollection class.
     */
    public ChatCollection( ArrayList<Chat> chats) {
        chatList = chats;
    }

    /**
     * This method adds a new chat to the chat list.
     *
     * @param chat a new chat to add.
     */
    public void addChat( Chat chat ) {
        chatList.add( chat );
    }

    /**
     * This method gets all chats as an arraylist.
     *
     * @return chatList the list of chats as an arrayList.
     */
    public ArrayList<Chat> getAllChat() {
        return chatList;
    }
    public void setChatList( ArrayList<Chat> list ){
        this.chatList = list;
    }

    /**
     * This method gets a specified chat.
     *
     * @param index the specified index.
     * @return chatList.get( index )
     */
    public Chat getChat( int index ) {
        return chatList.get( index );
    }

    /**
     * This method deletes a specified chat.
     *
     * @param index the specified index.
     * @return chat the chat that have deleted.
     */
    public Chat deleteChat( int index ) {
        Chat chat = chatList.get( index );
        chatList.remove( index );
        return chat;
    }

    /**
     * This method sorts the chats based on the latest converstaion at the top of the list.
     */
    public static ArrayList<Chat> sortChat( final ArrayList<Chat> chatList ) {//TESTED AND WORKS
        for( int i = 0; i < chatList.size(); i++ ){
            int smallestIndex = i;

            for( int j = i; j < chatList.size(); j++ ){
                if( chatList.get( j ).compareTo( chatList.get( smallestIndex ) ) < 0 ){
                    smallestIndex = j;
                }
            }
            Chat temp = chatList.get( i );
            chatList.set( i , chatList.get( smallestIndex ) );
            chatList.set( smallestIndex, temp );
        }
        return chatList;
    }

}
