package com.login.pd.loginPD.service;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Service
@Transactional
public class LoggingService {

    //creates user creation log
    public String createUserCreationLog() throws IOException {
        File userCreationLog = new File("User Creation Log.txt");
        try {
            userCreationLog.createNewFile();
            return "File created successfully";
        } catch (IOException e){
            System.out.println(e);
        }
        return null;
    }

    //creates user activity log
    public String createUserActivityLog() throws IOException {

        File userActivityLog = new File("User Activity Log.txt");
        try{
            userActivityLog.createNewFile();
            return "File created successfully";
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    //logs user created action
    public void logUserCreated(String username) throws IOException {
        createUserCreationLog();
        FileWriter logUserCreated = new FileWriter("User Creation Log.txt", true);
        try{
            logUserCreated.write("|| CREATE || User: "  + username + " has been created at: " + new Date().toString() + "\n");
            logUserCreated.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //logs user deleted action
    public void logUserDeleted(String username) throws IOException {
        createUserCreationLog();
        FileWriter logUserDeleted = new FileWriter("User Creation Log.txt", true);
        try{
            logUserDeleted.write("|| DELETE || User: " + username + " has been deleted at: " + new Date().toString() + "\n" );
            logUserDeleted.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //logs user logged in action
    public void logUserLoggedIn(String username) throws IOException {
        createUserActivityLog();
        FileWriter logUserLoggedIn = new FileWriter("User Activity Log.txt", true);
        try{
            logUserLoggedIn.write("|| LOGIN || User: " + username + " has logged in at: " + new Date().toString() + "\n" );
            logUserLoggedIn.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //logs user logged out action
    public void logUserLoggedOut(String username) throws IOException {
        createUserActivityLog();
        FileWriter logUserLoggedIn = new FileWriter("User Activity Log.txt", true);
        try{
            logUserLoggedIn.write("|| LOGOUT|| User: " + username + " has logged out at: " + new Date().toString() + "\n" );
            logUserLoggedIn.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
