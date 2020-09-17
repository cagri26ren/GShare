package com.example.gshare.ModelClasses;

import java.util.ArrayList;
import com.example.gshare.ModelClasses.User.*;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.ChatModel.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Test {
    public static void main( String [] Args){
        System.out.println("hello");

        User user1 = new User( "Bora Cun", "afafa", "cuncun24@gmail.com", 100,"" );
        User user2 = new User( "Cagri Eren",  "dfasfd", "ejderado99@gmail.com", 100 ,"");

        Notice user1Notice = new Notice( "Math132 Book", 5, "dfsdf", Sort.BOOKS, user1, new LocationG() , "");//Borrowing
        Notice user1Notice2 = new Notice( "Math132 Book 102", 6, "dfsdf", Sort.BOOKS, user1, 30,new LocationG(),"" );//Lending
        Notice user2Notice = new Notice( "Math102 Book 102 ", 4, "dfsdf", Sort.ALL, user2, 20,new LocationG() ,"");//Lending
        Notice user1Notice3 = new Notice( "Math132 Book", 7, "dfsdf", Sort.BOOKS, user1, 70, new LocationG(),"" );//Lending
        Notice user2Notice2 = new Notice( "Math102 Book", 8, "dfsdf", Sort.BOOKS, user2, 40,new LocationG() ,"");//Lending
        Notice user1Notice4 = new Notice( "Math132 Book", 7, "dfsdf", Sort.STATIONARY, user1, new LocationG(),"" );//Borrowing

        ArrayList<Notice> noticeList = new ArrayList<Notice>();
        ArrayList<Notice> testList;
        noticeList.add(user1Notice);
        noticeList.add(user1Notice2);
        noticeList.add(user1Notice3);
        noticeList.add(user1Notice4);
        noticeList.add(user2Notice);
        noticeList.add(user2Notice2);

        System.out.println("SORT BY KEY WORD");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        testList = Sort.sortByKeyWord( testList , "102", Sort.LENDING );
        System.out.println( testList.toString() );

        System.out.println("SORT BY CATEGORY");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        //testList = Sort.sortByCategory( testList , Sort.ALL, Sort.LENDING );
        System.out.println( testList.toString() );

        System.out.println("SORT BY G");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        testList = Sort.sortByGInterval( testList , 50 , 70 );
        System.out.println( testList.toString() );

        System.out.println("SORT BY DISTANCE");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        testList = Sort.sortByDistance( testList , user1, 100000000 , Sort.LENDING );
        System.out.println( testList.toString() );

        System.out.println("SORT BY BOOKMARK");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        user1.getBookmarkList().add(user2);
        testList = Sort.sortByBookmark( testList , user1, Sort.LENDING );
        System.out.println( testList.toString() );

        System.out.println("SORT BY ALPHABETICAL");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        //testList = Sort.sortByLexiography( testList, Sort.LENDING );
        System.out.println( testList.toString() );

        System.out.println("SORT BY TIME");
        testList = ( ArrayList<Notice> ) noticeList.clone();
        //testList = Sort.sortByPostTime( testList, Sort.LENDING );
        System.out.println( testList.toString() );



        int g = 30;
        try {
            user2Notice.setG(30);
        }
        catch( IllegalArgumentException e ){
            user2Notice.setG(20);
        }
        //Agree
        System.out.println(user2.getNumberOfLends());
        System.out.println(user1.getNumberOfBorrows());
        user2Notice.agreeOnLendNotice(user1);//Agree
        System.out.println("TEST1 NOTICE1 AGREE ");
        System.out.println(user2.getNumberOfLends());
        System.out.println(user1.getNumberOfBorrows());
        System.out.println( user2Notice.isAgreed() );
        System.out.println( user2Notice.computeTimeLeftForMilliSeconds());
        //System.out.println( user1.getActiveDealGAmount() + " * " );
        for( int i = 0; i < 20000000; i++){

        }
        System.out.println( user2Notice.computeTimeLeftForMilliSeconds());
        System.out.println( user2Notice.getNoticeType() );
        System.out.println( user2Notice.computeTimeLeft());
        //setting day to 8
        //user2Notice.setDay(8);

        System.out.println();
        System.out.println( user2Notice.computeTimeLeft());
        System.out.println( user2Notice.computeTimeLeftForMilliSeconds());
        System.out.println(user2Notice.isOver());

        //Finish
        user2Notice.finish();
        System.out.println("TEST2 NOTICE1 RATE AND G");
        System.out.println(user1.getG());
        System.out.println(user2.getG());
        System.out.println(user2Notice.isOver());

        //Rating for lend notice
        user2Notice.setRateLend( 3 );
        user2Notice.setRateBorrow( 4 );
        user2Notice.evaluateRates();

        //After rating and evaluating
        System.out.println();
        System.out.println( user2.getLenderRating() );
        System.out.println( user2.getBorrowerRating() );
        System.out.println( user1.getLenderRating() );
        System.out.println( user1.getBorrowerRating() );

        // Agree and finish borrow notice
        System.out.println("TEST3 NOTICE2 AGREE ");
        user1Notice.agreeOnBorrowNotice( user2 , 50 );//Agree
        //System.out.println( user1.getActiveDealGAmount() + " * " );
        user1Notice.finish();

        //Rating borrow notice
        user1Notice.setRateLend( 4 );
        user1Notice.setRateBorrow( 3 );
        user1Notice.evaluateRates();

        //Printing Results
        System.out.println();
        System.out.println("TEST4 NOTICE2 RATE AND G");
        System.out.println( user2.getLenderRating() );
        System.out.println( user2.getBorrowerRating() );
        System.out.println( user1.getLenderRating() );
        System.out.println( user1.getBorrowerRating() );
        System.out.println(user1.getG());
        System.out.println(user2.getG());

        //Adding one more notice
        System.out.println("TEST5 NOTICE3 AGREE");
        System.out.println(user2.getNumberOfLends());
        user1Notice2.agreeOnLendNotice(user2);//Agree
        System.out.println(user2.getNumberOfBorrows());
        System.out.println(user1Notice.getStartTime() );
        //System.out.println( user2.getActiveDealGAmount() + " * " );
        //user1Notice3.agreeOnLendNotice(user2);
        //System.out.println( user2.getActiveDealGAmount() + " * " );
        user1Notice2.finish();
        user1Notice2.setRateLend(4);
        user1Notice2.setRateBorrow(3);
        user1Notice2.evaluateRates();

        //Printing results
        System.out.println();
        System.out.println("TEST6 NOTICE3 RATE AND G");
        System.out.println( user2.getLenderRating() );
        System.out.println( user2.getBorrowerRating() );
        System.out.println( user1.getLenderRating() );
        System.out.println( user1.getBorrowerRating() );
        System.out.println(user1.getG());
        System.out.println(user2.getG());

        //Testing these 2
        System.out.println(user2Notice.computeTimeLeft());
        System.out.println(user2Notice.computeTimeLeftForMilliSeconds());

        //LOCATION TESTING
        System.out.println("LOCATION TESTING");
        LocationG l1 = new LocationG();
        LocationG l2 = new LocationG();
        l1.setLatitude(50);
        System.out.println( l1.distanceFrom(l2) );

        //USER TESTING
        System.out.println( "USER TESTING");
        System.out.println( user1.getLocation().toString() );
        System.out.println( user1.getBookmarkList().toString() );

        //CHAT TESTING
        Chat chat1 = new Chat(user2Notice2, user2 , user1 ,"");
        Chat chat2 = new Chat(user1Notice4, user1, user2 ,"");
        Chat chat3 = new Chat(user1Notice3, user1, user2 ,"");
        ChatCollection collection = new ChatCollection( new ArrayList<Chat>());
        Message msg1 = new Message( "Hello I wanna buy", user2 , user1 );
        Message msg2 = new Message( "Ok I am Listening ", user1 , user2);
        Message msg3 = new Message( "Let's talk ", user1 , user2);
        chat1.addMessage(msg1);
        chat1.addMessage(msg2);
        chat1.addMessage(msg3);

        System.out.println( "TEST1 PRINTING CHAT" );
        System.out.println();
        System.out.println();
        User you = user1;
        System.out.printf("%1s%34s",user2.getUserName(), user1.getUserName());
        printChat(chat1 , you);
        chat1.addMessage( new Message( "tamamdır", user2 , user1 ));
        printChat(chat1 , you);
        System.out.println( "\nTEST2 TESTING CHAT COLLECTION");

        collection.addChat(chat1);
        collection.addChat(chat2);
        collection.addChat(chat3);

        collection.getChat(1).addMessage( new Message("hey", user2 , you ) );
        collection.getChat(2).addMessage( new Message("asdfasfd", user2 , you ) );
        collection.getChat(1).addMessage( new Message("heyddd", you , user2 ) );
        //collection.sortChat();

        for( int i = 0; i < collection.getAllChat().size(); i++){
            System.out.println("\n-----------------------------");
            printChat( collection.getAllChat().get(i) , you );
        }




    }

    public static int a(){
        return 2;
    }

    public static void printChat( Chat chat1 , User user ){
        for(int i = 0; i < chat1.getAllMessage().size(); i++ ){
            System.out.println();
            if(chat1.getAllMessage().get(i).getSender().equals(user)) {
                System.out.printf("%40s", chat1.getAllMessage().get(i) );
                System.out.println();
                System.out.printf("%40s", chat1.getAllMessage().get(i).getTime() );
            }
            else{
                System.out.println( chat1.getAllMessage().get(i) );
                System.out.println( chat1.getAllMessage().get(i).getTime() );
            }
        }
    }
}
