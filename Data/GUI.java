import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.prefs.Preferences;
import java.util.Timer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.*;

public class GUI extends JFrame implements ActionListener, ItemListener, WindowListener{
    //Deklarationen
    
    //Einstellungen
    private static Preferences prefsAVR;
    
    //Strings
    //private static String strAdrSafe = "adr";
    private static String strAdr = null;
    private static String arrAdr[] = new String[100];
    private static String strSource = null;
    private static String strVolumeFromAVRCommand = null;
    private static String strVolumeFromAVRCommandOnlyNumbers = null;
    private static String strARP;
    private static String arrARP[];
    private static String arrIPMAC[] = new String[100];
    private static String arrDevice[] = new String[100];
    
    //Integer
    private static int iVolAVR;
    private static int iVolRemote;
    private static int iVolRemoteActual;
    private static int i;
    private static int j;
    private static int k;
    private static int itemNumber;
    
    //integer format for setting volume
    NumberFormat nf = new DecimalFormat("##.0"); 
    
    //Buttons
    private static JButton btnPowerToggle;
    private static JButton btnVolUp;
    private static JButton btnVolDown;
    
    //Textfields
    //private static JTextField tfAdr = null; 
    
    //Labels
    private static JLabel lblAdr = null;
    private static JLabel lblVol = null;
    
    //Booleans
    private static boolean bolStateVolumeChanged = false;
    private static boolean bolStateSourceChanged = false;
    private static boolean bolStatePowerChanged = false;
    private static boolean bolStateStartupVolume = false;
    private static boolean bolJcDevice = true;
    
    //ComboBoxes
    private static JComboBox jcSource;
    private static String arrSource[] = {"Source","CBL/SAT","Blu-ray","GAME","MEDIA PLAYER","DVD","AUX1","AUX2","CD","TUNER","PHONO","ONLINE MUSIC","Bluetooth","iPod/USB","INTERNET RADIO"};
    private static JComboBox jcDevice;
    private static DefaultComboBoxModel model;
    private static String arrAVR[] = {"receiver detected"};
    
    //Receiver
    private static AVRFunctions avrx7200w;
    
    //Timer for upgrading AVR status
    private static Timer timerUpdateTick;
    private static Boolean bolTimer;
    //private static Boolean bolProcessing;
    
    //Display for AVR informations
    private static DisplayDriver ddAVR;
    
    //Scanner for searching the local area network
    private static Scanner sLAN;
    
    
    /**
     * Konstruktor
     */
    public GUI(){
        //Layout
        setTitle("Denon Rem \u00a9 Nico");
        setSize(250,260);
        setLayout(null);
        getContentPane().setBackground(new Color(255,251,242)); //setBackground(Color.LIGHT_GRAY);
        setLocation(100,100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Einstellungen laden
        //getPreference();
        
        //status display
        ddAVR = new DisplayDriver();
        add(ddAVR);
        
        //Power Toggle Button
        btnPowerToggle = new JButton("on");
        btnPowerToggle.setBounds(5,70,235,25);
        btnPowerToggle.addActionListener(this);
        add(btnPowerToggle);
        
        //Volume label
        lblVol = new JLabel();
        lblVol.setForeground(Color.gray);
        lblVol.setBounds(78,100,100,25);
        add(lblVol);
        
        //Volume UP Button
        btnVolUp = new JButton("+");
        btnVolUp.setBounds(190,100,50,25);
        btnVolUp.addActionListener(this);
        add(btnVolUp);
        
        //Volume Down Button
        btnVolDown = new JButton("-");
        btnVolDown.setBounds(5,100,50,25);
        btnVolDown.addActionListener(this);
        add(btnVolDown);
        
        //Source
        jcSource = new JComboBox(arrSource);
        jcSource.setBounds(5,130,235,25);
        jcSource.addItemListener(this);
        add(jcSource);
            
        //IP Adresse label
        lblAdr = new JLabel("choose receiver from local network");
        lblAdr.setBounds(20,175,235,25);
        add(lblAdr);
        
        //AVR
        jcDevice = new JComboBox(arrAVR);
        jcDevice.setBounds(5,200,235,25);
        jcDevice.addItemListener(this);
        add(jcDevice);
        
        //search local area network for devices
        arrARP = getNetworkAVRList();
        if(jcDevice.getItemCount() == 1){
            arrAVR[0] = "no receiver detected";
            model = new DefaultComboBoxModel(arrAVR);
            jcDevice.setModel(model);
        }

        //start in initial state
        stateInit();
        
        addWindowListener(this);
        setVisible(true);
    }
    
    private void stateInit(){
        //Display Settings
        ddAVR.setDisplayPower("AVR: -");
        ddAVR.setDisplayVolume(-1);
        //Button and Combobox settings
        btnPowerToggle.setEnabled(false);
        lblVol.setText("Set Volume: -");  //set volume on GUI
        btnVolUp.setEnabled(false);
        btnVolDown.setEnabled(false);
        jcSource.setEnabled(false);
    }
    
    private void stateVolumeChanged(boolean bolStateVolumeChangedMethod){
        if(bolStateVolumeChanged){
            bolStateVolumeChanged = false; //Volume has beend changed, set bolStateVolumeChanged on false to avoid repeating
            avrx7200w.setVol(iVolRemote);         //send command for changing the volume
            lblVol.setForeground(Color.gray);
        }
    }
    
    private void stateSourceChanged(boolean bolStateSourceChangedMethod){
        if(bolStateSourceChanged){
            bolStateSourceChanged = false; //Volume has beend changed, set bolStateVolumeChanged on false to avoid repeating
            avrx7200w.setSource(strSource);         //send command for changing the volume
        }
    }
    
    private void statePowerChanged(boolean bolStatePowerChangedMethod){
        if(bolStatePowerChanged){
            if(btnPowerToggle.getText().equals("on")){
                bolStateStartupVolume = true;
                avrx7200w.setAVRON();
                btnPowerToggle.setText("off");
            }else{
                avrx7200w.setAVROFF();
                btnPowerToggle.setText("on");
            }
            bolStatePowerChanged = false;
        }
    }
    
    private void stateStartupVolume(boolean bolStateStartupVolume){
        if(bolStateStartupVolume){
            bolStateStartupVolume = false;
            iVolRemote = avrx7200w.getVolume();
            lblVol.setText("Set Volume: "+nf.format((double)iVolRemote/10));  //set volume on GUI
        }
    }
    
    
    /**
     * Safe Preferences
     */
    public void setPreference(){
        prefsAVR = Preferences.userRoot().node(this.getClass().getName());
        //prefsAVR.put(strAdrSafe, tfAdr.getText());
    }
    
    /**
     * Load Preferences
     */
    public void getPreference(){
        prefsAVR = Preferences.userRoot().node(this.getClass().getName());
        //strAdr = prefsAVR.get(strAdrSafe, "0.0.0.0");
    }
    
    /**
     * Start Timer
     */
    public void startStatusUpdater(){
        timerUpdateTick = new Timer();
        timerUpdateTick.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if(bolTimer){
                    if(avrx7200w.checkAVRPowerSTBY() == false){
                        //Send commands to AVR if states has been changed
                        waitProcessing(25);
                        stateVolumeChanged(bolStateVolumeChanged);
                        waitProcessing(25);
                        statePowerChanged(bolStatePowerChanged);
                        waitProcessing(25);
                        stateSourceChanged(bolStateSourceChanged);
                        waitProcessing(25);
                        stateStartupVolume(bolStateStartupVolume);
                        waitProcessing(25);
                        //Display Settings
                        ddAVR.setDisplayPower("AVR: ON");
                        ddAVR.setDisplaySource(avrx7200w.getSource());
                        waitProcessing(25);
                        iVolAVR = avrx7200w.getVolume();
                        ddAVR.setDisplayVolume(iVolAVR);
                        waitProcessing(25);
                        //Button and Combobox settings
                        btnPowerToggle.setText("off");
                        btnPowerToggle.setEnabled(true);
                        btnVolUp.setEnabled(true);
                        btnVolDown.setEnabled(true);
                        jcSource.setEnabled(true);
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
                    }else{
                        //Display Settings
                        ddAVR.setDisplayPower("AVR: OFF");
                        ddAVR.setDisplayVolume(-1);
                        ddAVR.setDisplaySource("");
                        //Button and Combobox settings
                        btnPowerToggle.setText("on");
                        btnPowerToggle.setEnabled(true);
                        lblVol.setText("Set Volume: -");  //set volume on GUI
                        btnVolUp.setEnabled(false);
                        btnVolDown.setEnabled(false);
                        jcSource.setEnabled(false);
                        waitProcessing(25);
                        statePowerChanged(bolStatePowerChanged);
                        waitProcessing(25);
                    }
                    System.out.println("iVol: "+iVolRemote+"   display: "+ddAVR.getDisplayVolume());
                    repaint();
                }
            }
        }, 100,1000);
    }
    
    //Algorithm for the receiver search in the lan
    public String[] getNetworkAVRList(){
        try{
           sLAN = new Scanner(Runtime.getRuntime().exec("arp -a").getInputStream()).useDelimiter("\\A");
        }catch(IOException d){
        }
        strARP = sLAN.hasNext() ? sLAN.next() : "";
        arrARP = strARP.split("\\s+");
        k = 0;
        for(i = 9; i < arrARP.length-1; i += 3){
            arrIPMAC[k] = arrARP[i];
            k += 1;
            arrIPMAC[k] = arrARP[i+1];
            k += 1;
        } 
        j = 0;
        for(i = 1; i < k; i += 2){
            if(arrIPMAC[i].substring(0,8).equals("00-05-cd")){
                 addjcDeviceItem(j, "Denon Device: "+arrIPMAC[i], arrIPMAC[i-1]);
                 j += 1;
            }
            System.out.println(arrIPMAC[i].substring(0,7));
        }
        return arrARP;
    }
    
    private void addjcDeviceItem(int iItemNumberMethod, String strDeviceMethod, String strAdrMethod ){
        jcDevice.addItem(strDeviceMethod);
        arrDevice[iItemNumberMethod] = strDeviceMethod;
        arrAdr[iItemNumberMethod] = strAdrMethod;
        System.out.println(arrAdr[iItemNumberMethod]+"   number:"+iItemNumberMethod);
    }
    
    private static void waitProcessing(int iWait){
        try {
            Thread.sleep(iWait);
        }catch(InterruptedException ie) {
        }
    }
    
    /**
     * ActionListener
     */
    public void actionPerformed(ActionEvent e){
        // Power Toggle of the AVR
        if(e.getSource() == btnPowerToggle){
            bolStatePowerChanged = true;
        }
        // Volume up
        if(e.getSource() == btnVolUp){
            if(iVolRemote < 970){
                iVolRemote += 5;                                                  //increment volume
                bolStateVolumeChanged = true;                                     //give permission to send a command in the timer method
                lblVol.setText("Set Volume: "+nf.format((double)iVolRemote/10));  //set volume on GUI
                lblVol.setForeground(Color.black);
            }
        }
        // Volume down
        if(e.getSource() == btnVolDown){
            if(iVolRemote > 5){
                iVolRemote -= 5;                                                  //decrement volume
                bolStateVolumeChanged = true;                                     //give permission to send a command in the timer method
                lblVol.setText("Set Volume: "+nf.format((double)iVolRemote/10));  //set volume on GUI
                lblVol.setForeground(Color.black);
            }
        }
        repaint();
    }
    
     /**
     * ItemListener
     */
    public void itemStateChanged(ItemEvent f){
        //Listener for choosing a source
        if(f.getSource() == jcSource){
            strSource = jcSource.getSelectedItem().toString();          //get selected source from combolist
            bolStateSourceChanged = true;                               //give permission to send a command in the timer method
        }
        //Listener for choosing avr from combobox
        if(f.getSource() == jcDevice && jcDevice.getSelectedIndex() > 0 && bolJcDevice){
            jcDevice.setEnabled(false);
            itemNumber = jcDevice.getSelectedIndex();                   //get index from combobox list
            avrx7200w = new AVRFunctions(arrAdr[itemNumber-1]);         //receiver initialisation
            waitProcessing(50);
            bolStateStartupVolume = true;
            startStatusUpdater();                                       //start status updater
            bolTimer = true;                                            //start status updater
            bolJcDevice = false;
        }
    }
    
    /**
     * WindowListener Methoden
     */
    public void windowClosing(WindowEvent event){
        //setPreference();    //safe properties
        dispose();          //vanish window
        System.exit(0);     //end application
    }
    public void windowIconified(WindowEvent event){ 
    }
    public void windowOpened(WindowEvent event){
    }
    public void windowClosed(WindowEvent event){
    }
    public void windowActivated(WindowEvent event){
    }
    public void windowDeiconified(WindowEvent event){
    }
    public void windowDeactivated(WindowEvent event){
    }
    private void exitForm(WindowEvent event){
    }
}
