import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.*;
import javax.swing.*;

public class ConnectionDriver{
    //Ethernet
    private static Socket sock = null;
    private static PrintWriter sockOut = null;
    private static BufferedReader sockIn = null;
    private static String idn = null, strData = null, opcComp = null, strAdr = null, strCmd = null;
    
    //Error Panel
    private static JPanel pError;
    
    public ConnectionDriver(String adr){
        this.strAdr = adr;
        pError = new JPanel();
    }
    
    public static void openConnection(){
        try{
            sock = new Socket(strAdr, 23);   //Setup ethernet connection
            sockOut = new PrintWriter(sock.getOutputStream(), true);
            sockIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        catch (UnknownHostException e){
            JOptionPane.showMessageDialog(pError,
            "Verbindung fehlgeschlagen. Starten sie das Program neu.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (IOException e){
            //System.out.println("Cannot connect to Socket. openconnection");
            //System.out.println("Try to reconnect with openConnection");
            //waitProcessing(250);
            //openConnection();
            //System.out.println("open in openconnection");
            //System.exit(1);
            JOptionPane.showMessageDialog(pError,
            "Verbindung fehlgeschlagen. Starten sie das Program neu.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        System.out.println("succsefully connected");
    }
    
    public static void sendCommand(String cmd){
        closeConnection();
        openConnection();
        sockOut.println(cmd);             //Switching to Master Zone
        waitProcessing(10);               //Wait a little to let the AVR process the commands
    }
    
    public static String getData(String cmd){
        strCmd = cmd;
        strData = null;
        try{
            closeConnection();
            openConnection();
            sockOut.println(cmd);          //Get status information of the AVR
            System.out.println("1");
            strData=sockIn.readLine();      //Copy data from buffer into string
            System.out.println(strData);
            System.out.println("2");
            waitProcessing(10);               //Wait a little to let the AVR process the commands
        }catch (IOException e){
            System.out.println("Cannot connect to Socket. getdata");
            JOptionPane.showMessageDialog(pError,
            "Verbindung fehlgeschlagen. Starten sie das Program neu.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
            return strData = null;
        }
        //closeConnection();
        System.out.println("done");
        return strData;
    }
    
    public static void closeConnection(){
        try{
            sock.close();   //Closing ethernet connection
        }catch (IOException e){

        }
    }
    
    public static boolean checkConnection(){
        return false;
    }
    
    private static void waitProcessing(int iWait){
        try {
            Thread.sleep(iWait);
        }catch(InterruptedException ie) {
        }
    }

}