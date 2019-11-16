import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

class DisplayDriver extends JPanel{
    //Textfields
    private static JTextField tfVolume;
    private static JTextField tfSource;
    private static JTextField tfPower;
    private static Font fDisplay;
    
    //format for setting volume
    DecimalFormat df = new DecimalFormat("##");
    private static int iVol;
    NumberFormat nf = new DecimalFormat("00.0"); 
    private static double dVol;
    
    //array
    private static String[] arrData = new String[2];
    
    /**
     * Konstruktor
     */
    public DisplayDriver(){
        //Container
        setSize(235,50);
        setLayout(null);
        setBackground(Color.BLACK); //setBackground(Color.LIGHT_GRAY);
        setLocation(5,5);
        
        //Set font of display
        fDisplay = new Font("Calibri", Font.BOLD, 20);
        
        //Power part
        tfPower = new JTextField();
        tfPower.setEditable(false);
        tfPower.setHighlighter(null);
        tfPower.setBackground(Color.BLACK);
        tfPower.setForeground(Color.GREEN);
        tfPower.setHorizontalAlignment(JTextField.CENTER);
        tfPower.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tfPower.setBounds(0,0,115,25);
        tfPower.setFont(fDisplay);
        add(tfPower);
        
        //Volume part
        tfVolume = new JTextField();
        tfVolume.setEditable(false);
        tfVolume.setHighlighter(null);
        tfVolume.setBackground(Color.BLACK);
        tfVolume.setForeground(Color.GREEN);
        tfVolume.setHorizontalAlignment(JTextField.CENTER);
        tfVolume.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tfVolume.setBounds(117,0,115,25);
        tfVolume.setFont(fDisplay);
        add(tfVolume);
        
        //Source part
        tfSource = new JTextField();
        tfSource.setEditable(false);  
        tfSource.setHighlighter(null);
        tfSource.setBackground(Color.BLACK);
        tfSource.setForeground(Color.GREEN);
        tfSource.setHorizontalAlignment(JTextField.CENTER);
        tfSource.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tfSource.setBounds(0,25,235,25);
        tfSource.setFont(fDisplay);
        add(tfSource);
        
        setVisible(true);
    }
    
    public void setDisplayPower(String strPower){
        tfPower.setText(strPower);
    }
    
    public void setDisplayVolume(int iVolumeMethod){
        iVol = iVolumeMethod;
        dVol = iVolumeMethod;
        dVol = dVol / 10;
        if(iVolumeMethod > 0){
            tfVolume.setText("VOL: "+nf.format(dVol));
        }else{
            tfVolume.setText("VOL: - ");
        }
    }
    
    public void setDisplaySource(String strSource){
        switch(strSource){
            case "SISAT/CBL": strSource = "CBL/SAT";
            break;
            case "SIBD": strSource = "Blu-ray";
            break;
            case "SIGAME": strSource = "GAME";
            break;
            case "SIMPLAY": strSource = "MEDIA PLAYER";
            break;
            case "SIDVD": strSource = "DVD";
            break;
            case "SIAUX1": strSource = "AUX1";
            break;
            case "SIAUX2": strSource = "AUX2";
            break;
            case "SICD": strSource = "CD";
            break;
            case "SITUNER": strSource = "TUNER";
            break;
            case "SIPHONO": strSource = "PHONO";
            break;
            case "SINET": strSource = "ONLINE MUSIC";
            break;
            case "SIBT": strSource = "Bluetooth";
            break;
            case "SIUSB/IPOD": strSource = "iPod/USB";
            break;
            case "SIIRADIO": strSource = "INTERNET RADIO";
            break;
            default: strSource = "-";
            break;
        }
        tfSource.setText(strSource);
    }
    
    public void setDisplayStatus(String arrData[]){
        tfSource.setText(arrData[0]);
        tfVolume.setText(arrData[1]);
    }    
    
    public double getDisplayVolume(){
        return iVol;
    }
}