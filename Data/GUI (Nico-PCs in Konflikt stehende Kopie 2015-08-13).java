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
    private static String strAdrSafe = "adr";
    private static String strAdr = null;
    private static String strSource = null;
    private static String strVolumeFromAVRCommand = null;
    private static String strVolumeFromAVRCommandOnlyNumbers = null;
    private static String strARP;
    private static String arrARP[];
    
    //Integer
    private static int iVol;
    
    //integer format for setting volume
    NumberFormat nf = new DecimalFormat("##.0"); 
    
    //Buttons
    private static JButton btnPowerToggle;
    private static JButton btnVolUp;
    private static JButton btnVolDown;
    
    //Textfields
    private static JTextField tfAdr = null; 
    
    //Labels
    private static JLabel lblAdr = null;
    private static JLabel lblVol = null;
    
    //Booleans
    private static boolean bolStateVolumeChanged = false;
    private static boolean bolStateSourceChanged = false;
    
    //ComboBoxes
    private static JComboBox jcSource;
    private static String arrSource[] = {"Source","CBL/SAT","Blu-ray","GAME","MEDIA PLAYER","DVD","AUX1","AUX2","CD","TUNER","PHONO","ONLINE MUSIC","Bluetooth","iPod/USB","INTERNET RADIO"};
    private static JComboBox jcAVR;
    private static String arrAVR[] ={"Choose AVR","","",""};
    
    //Receiver
    private static AVRFunctions avrx7200w;
    
    //Timer for upgrading AVR status
    private static Timer timerUpdateTick;
    private static Boolean bolTimer;
    private static Boolean bolProcessing;
    
    //Display for AVR informations
    private static DisplayDriver ddAVR;
    
    //Scanner for searching the local area network
    private static Scanner sLAN;
    
    
    /**
     * Konstruktor
     */
    public GUI(){
        //Layout
        setTitle("Denon Remote");
        setSize(250,260);
        setLayout(null);
        getContentPane().setBackground(new Color(255,251,242)); //setBackground(Color.LIGHT_GRAY);
        setLocation(100,100);
        setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Einstellungen laden
        getPreference();
        
        //status display
        ddAVR = new DisplayDriver();
        add(ddAVR);
        
        //Power Toggle Button
        btnPowerToggle = new JButton("on");
        btnPowerToggle.setBounds(5,60,235,25);
        btnPowerToggle.addActionListener(this);
        add(btnPowerToggle);
        
        //Volume label
        lblVol = new JLabel();
        lblVol.setForeground(Color.gray);
        lblVol.setBounds(78,90,100,25);
        add(lblVol);
        
        //Volume UP Button
        btnVolUp = new JButton("+");
        btnVolUp.setBounds(190,90,50,25);
        btnVolUp.addActionListener(this);
        add(btnVolUp);
        
        //Volume Down Button
        btnVolDown = new JButton("-");
        btnVolDown.setBounds(5,90,50,25);
        btnVolDown.addActionListener(this);
        add(btnVolDown);
        
        //Source
        jcSource = new JComboBox(arrSource);
        jcSource.setBounds(5,120,235,25);
        jcSource.addItemListener(this);
        add(jcSource);
            
        //IP Adresse label
        lblAdr = new JLabel("IP Adresse des AVR's eingeben");
        lblAdr.setBounds(5,150,235,25);
        add(lblAdr);
        //IP Adresse textfield
        tfAdr = new JTextField(strAdr);
        tfAdr.setBounds(5,175,235,25);
        //tfAdr.setText(strAdr);
        tfAdr.setHorizontalAlignment(JTextField.CENTER);
        tfAdr.setEditable(true);
        add(tfAdr);
        
        //AVR
        jcAVR = new JComboBox(arrAVR);
        jcAVR.setBounds(5,200,235,25);
        jcAVR.addItemListener(this);
        add(jcAVR);
        
        //search local area network for devices
        strARP = getARPTable();
        
        //Receiver deklaration
        avrx7200w = new AVRFunctions("172.16.0.22");
        
        //get AVR Volume
        iVol = avrx7200w.getVolume();
        lblVol.setText("Set Volume: "+nf.format((double)iVol/10)); //set volume on GUI
       
        //start status updater
        bolTimer = true;
        bolProcessing = false;
        startStatusUpdater();
        
        addWindowListener(this);
        setVisible(true);
    }
    
    public void stateOn(){
        bolTimer = false;
        avrx7200w.setAVRON();
        bolTimer = true;
    }
    
    public void stateOff(){
        bolTimer = false;
        avrx7200w.setAVROFF();
        bolTimer = true;
    }
    
    public void stateVolumeChanged(boolean bolStateVolumeChangedMethod){
        if(bolStateVolumeChanged || (iVol != ddAVR.getDisplayVolume())){
            avrx7200w.setVol(iVol);         //send command for changing the volume
            lblVol.setForeground(Color.gray);
            bolStateVolumeChanged = false; //Volume has beend changed, set bolStateVolumeChanged on false to avoid repeating
        }
    }
    
    public void stateSourceChanged(boolean bolStateSourceChangedMethod){
        if(bolStateSourceChanged){
            avrx7200w.setSource(strSource);         //send command for changing the volume
            bolStateSourceChanged = false; //Volume has beend changed, set bolStateVolumeChanged on false to avoid repeating
        }
    }
    
    /**
     * Safe Preferences
     */
    public void setPreference(){
        prefsAVR = Preferences.userRoot().node(this.getClass().getName());
        prefsAVR.put(strAdrSafe, tfAdr.getText());
    }
    
    /**
     * Load Preferences
     */
    public void getPreference(){
        prefsAVR = Preferences.userRoot().node(this.getClass().getName());
        strAdr = prefsAVR.get(strAdrSafe, "0.0.0.0");
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
                    System.out.println(bolTimer);
                    if(avrx7200w.checkAVRPowerSTBY() == false){
                        //Set and get Status
                        btnPowerToggle.setText("off");
                        //System.out.println("setTextOk");
                        ddAVR.setDisplayPower("AVR: ON");
                        //System.out.println("set AVR ON");
                        ddAVR.setDisplayVolume(avrx7200w.getVolume());
                        //System.out.println("set Volume on Display");
                        ddAVR.setDisplaySource(avrx7200w.getSource());
                        //System.out.println("Power On");
                        
                        //Send commands to AVR if states has been changed
                        stateVolumeChanged(bolStateVolumeChanged);
                        stateSourceChanged(bolStateSourceChanged);
                        //System.out.println(strSource);
                    }else{
                        btnPowerToggle.setText("on");
                        ddAVR.setDisplayPower("AVR: OFF");
                        ddAVR.setDisplayVolume(-1);
                        ddAVR.setDisplaySource("");
                        //System.out.println("Power Off");
                    }
                    System.out.println("iVol: "+iVol+"   display: "+ddAVR.getDisplayVolume());
                    repaint();
                }
            }
        }, 100,1000);
    }
    
    private static void waitProcessing(){
        try {
            Thread.sleep(500);
        }catch(InterruptedException ie) {
        }
    }
    
    public String getARPTable(){
        try{
           sLAN = new Scanner(Runtime.getRuntime().exec("arp -a").getInputStream()).useDelimiter("\\A");
        }catch(IOException d){
        }
        arrARP = strARP.split(" ");
        return sLAN.hasNext() ? sLAN.next() : "";
    }
    
    /**
     * ActionListener
     */
    public void actionPerformed(ActionEvent e){
        // Power Toggle of the AVR
        if(e.getSource() == btnPowerToggle){
            switch(btnPowerToggle.getText()){
                case "on": stateOn();   //command for switching on
                break;
                case "off": stateOff(); //comnmand for switching off
                break;
                default: stateOff();    //command for switching off
                break;
            }
        }
        // Volume up
        if(e.getSource() == btnVolUp){
            if(iVol < 970){
                iVol += 5;                          //increment volume
                lblVol.setText("Set Volume: "+nf.format((double)iVol/10)); //set volume on GUI
                lblVol.setForeground(Color.black);
                bolStateVolumeChanged = true;       //give permission to send a command in the timer method
            }
        }
        // Volume down
        if(e.getSource() == btnVolDown){
            if(iVol > 5){
                iVol -= 5;                          //decrement volume
                lblVol.setText("Set Volume: "+nf.format((double)iVol/10)); //set volume on GUI
                lblVol.setForeground(Color.black);
                bolStateVolumeChanged = true;       //give permission to send a command in the timer method
            }
        }
        repaint();
    }
    
     /**
     * ItemListener
     */
    public void itemStateChanged(ItemEvent f){
        if(f.getSource() == jcSource){
            strSource = jcSource.getSelectedItem().toString();  //get selected source from combolist
            bolStateSourceChanged = true;                       //give permission to send a command in the timer method
        }
        
        if(f.getSource() == jcAVR){
            strARP = getARPTable();
            System.out.println(strARP);
        }
    }
    
    /**
     * WindowListener Methoden
     */
    public void windowClosing(WindowEvent event){
        setPreference();    //safe properties
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
