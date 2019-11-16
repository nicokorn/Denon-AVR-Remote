import java.text.DecimalFormat;

public class AVRFunctions{
    //Connection data
    private static ConnectionDriver connectionAVR;
    private static String strData = null;
    private static String strVol = null;
    private static String[] arrData = new String[2];
    
    //integers
    private static int iVol;
    
    //integer format for setting volume
    DecimalFormat df = new DecimalFormat("##");
    
    public AVRFunctions(String adr){
        connectionAVR = new ConnectionDriver(adr);
        connectionAVR.openConnection();
    }
    
    public static void setAVRON(){
        connectionAVR.sendCommand("ZMON\r");
        waitAfterPowerOn();
    }
    
    public static void setAVROFF(){
        connectionAVR.sendCommand("ZMOFF\r");
        connectionAVR.closeConnection();
    }
    
    public static void setVol(int iVol){
        switch(iVol){
            case 5: strVol = ("005");
            break;
            case 10: strVol = ("01");
            break;
            case 15: strVol = ("015");
            break;
            case 20: strVol = ("02");
            break;
            case 25: strVol = ("025");
            break;
            case 30: strVol = ("03");
            break;
            case 35: strVol = ("035");
            break;
            case 40: strVol = ("04");
            break;
            case 45: strVol = ("045");
            break;
            case 50: strVol = ("05");
            break;
            case 55: strVol = ("055");
            break;
            case 60: strVol = ("06");
            break;
            case 65: strVol = ("065");
            break;
            case 70: strVol = ("07");
            break;
            case 75: strVol = ("075");
            break;
            case 80: strVol = ("08");
            break;
            case 85: strVol = ("085");
            break;
            case 90: strVol = ("09");
            break;
            case 95: strVol = ("095");
            break;
            case 100: strVol = ("10");
            break;
            case 105: strVol = ("105");
            break;
            case 110: strVol = ("11");
            break;
            case 115: strVol = ("115");
            break;
            case 120: strVol = ("12");
            break;
            case 125: strVol = ("125");
            break;
            case 130: strVol = ("13");
            break;
            case 135: strVol = ("135");
            break;
            case 140: strVol = ("14");
            break;
            case 145: strVol = ("145");
            break;
            case 150: strVol = ("15");
            break;
            case 155: strVol = ("155");
            break;
            case 160: strVol = ("16");
            break;
            case 165: strVol = ("165");
            break;
            case 170: strVol = ("17");
            break;
            case 175: strVol = ("175");
            break;
            case 180: strVol = ("18");
            break;
            case 185: strVol = ("185");
            break;
            case 190: strVol = ("19");
            break;
            case 195: strVol = ("195");
            break;
            case 200: strVol = ("20");
            break;
            case 205: strVol = ("205");
            break;
            case 210: strVol = ("21");
            break;
            case 215: strVol = ("215");
            break;
            case 220: strVol = ("22");
            break;
            case 225: strVol = ("225");
            break;
            case 230: strVol = ("23");
            break;
            case 235: strVol = ("235");
            break;
            case 240: strVol = ("24");
            break;
            case 245: strVol = ("245");
            break;
            case 250: strVol = ("25");
            break;
            case 255: strVol = ("255");
            break;
            case 260: strVol = ("26");
            break;
            case 265: strVol = ("265");
            break;
            case 270: strVol = ("27");
            break;
            case 275: strVol = ("275");
            break;
            case 280: strVol = ("28");
            break;
            case 285: strVol = ("285");
            break;
            case 290: strVol = ("29");
            break;
            case 295: strVol = ("295");
            break;
            case 300: strVol = ("30");
            break;
            case 305: strVol = ("305");
            break;
            case 310: strVol = ("31");
            break;
            case 315: strVol = ("315");
            break;
            case 320: strVol = ("32");
            break;
            case 325: strVol = ("325");
            break;
            case 330: strVol = ("33");
            break;
            case 335: strVol = ("335");
            break;
            case 340: strVol = ("34");
            break;
            case 345: strVol = ("345");
            break;
            case 350: strVol = ("350");
            break;
            case 355: strVol = ("355");
            break;
            case 360: strVol = ("36");
            break;
            case 365: strVol = ("365");
            break;
            case 370: strVol = ("37");
            break;
            case 375: strVol = ("375");
            break;
            case 380: strVol = ("38");
            break;
            case 385: strVol = ("385");
            break;
            case 390: strVol = ("39");
            break;
            case 395: strVol = ("395");
            break;
            case 400: strVol = ("40");
            break;
            case 405: strVol = ("405");
            break;
            case 410: strVol = ("41");
            break;
            case 415: strVol = ("415");
            break;
            case 420: strVol = ("42");
            break;
            case 425: strVol = ("425");
            break;
            case 430: strVol = ("43");
            break;
            case 435: strVol = ("435");
            break;
            case 440: strVol = ("44");
            break;
            case 445: strVol = ("445");
            break;
            case 450: strVol = ("45");
            break;
            case 455: strVol = ("455");
            break;
            case 460: strVol = ("46");
            break;
            case 465: strVol = ("465");
            break;
            case 470: strVol = ("47");
            break;
            case 475: strVol = ("475");
            break;
            case 480: strVol = ("48");
            break;
            case 485: strVol = ("485");
            break;
            case 490: strVol = ("49");
            break;
            case 495: strVol = ("495");
            break;
            case 500: strVol = ("50");
            break;
            case 505: strVol = ("505");
            break;
            case 510: strVol = ("51");
            break;
            case 515: strVol = ("515");
            break;
            case 520: strVol = ("52");
            break;
            case 525: strVol = ("525");
            break;
            case 530: strVol = ("53");
            break;
            case 535: strVol = ("535");
            break;
            case 540: strVol = ("54");
            break;
            case 545: strVol = ("545");
            break;
            case 550: strVol = ("55");
            break;
            case 555: strVol = ("555");
            break;
            case 560: strVol = ("56");
            break;
            case 565: strVol = ("565");
            break;
            case 570: strVol = ("57");
            break;
            case 575: strVol = ("575");
            break;
            case 580: strVol = ("58");
            break;
            case 585: strVol = ("585");
            break;
            case 590: strVol = ("59");
            break;
            case 595: strVol = ("595");
            break;
            case 600: strVol = ("60");
            break;
            case 605: strVol = ("605");
            break;
            case 610: strVol = ("61");
            break;
            case 615: strVol = ("615");
            break;
            case 620: strVol = ("62");
            break;
            case 625: strVol = ("625");
            break;
            case 630: strVol = ("63");
            break;
            case 635: strVol = ("635");
            break;
            case 640: strVol = ("64");
            break;
            case 645: strVol = ("645");
            break;
            case 650: strVol = ("65");
            break;
            case 655: strVol = ("655");
            break;
            case 660: strVol = ("66");
            break;
            case 665: strVol = ("665");
            break;
            case 670: strVol = ("67");
            break;
            case 675: strVol = ("675");
            break;
            case 680: strVol = ("68");
            break;
            case 685: strVol = ("685");
            break;
            case 690: strVol = ("69");
            break;
            case 695: strVol = ("695");
            break;
            case 700: strVol = ("70");
            break;
            case 705: strVol = ("705");
            break;
            case 710: strVol = ("71");
            break;
            case 715: strVol = ("715");
            break;
            case 720: strVol = ("72");
            break;
            case 725: strVol = ("725");
            break;
            case 730: strVol = ("73");
            break;
            case 735: strVol = ("735");
            break;
            case 740: strVol = ("74");
            break;
            case 745: strVol = ("745");
            break;
            case 750: strVol = ("75");
            break;
            case 755: strVol = ("755");
            break;
            case 760: strVol = ("76");
            break;
            case 765: strVol = ("765");
            break;
            case 770: strVol = ("77");
            break;
            case 775: strVol = ("775");
            break;
            case 780: strVol = ("78");
            break;
            case 785: strVol = ("785");
            break;
            case 790: strVol = ("79");
            break;
            case 795: strVol = ("795");
            break;
            case 800: strVol = ("80");
            break;
            case 805: strVol = ("805");
            break;
            case 810: strVol = ("81");
            break;
            case 815: strVol = ("815");
            break;
            case 820: strVol = ("82");
            break;
            case 825: strVol = ("825");
            break;
            case 830: strVol = ("83");
            break;
            case 835: strVol = ("835");
            break;
            case 840: strVol = ("84");
            break;
            case 845: strVol = ("845");
            break;
            case 850: strVol = ("85");
            break;
            case 855: strVol = ("855");
            break;
            case 860: strVol = ("86");
            break;
            case 865: strVol = ("865");
            break;
            case 870: strVol = ("87");
            break;
            case 875: strVol = ("875");
            break;
            case 880: strVol = ("88");
            break;
            case 885: strVol = ("885");
            break;
            case 890: strVol = ("89");
            break;
            case 895: strVol = ("895");
            break;
            case 900: strVol = ("90");
            break;
            case 905: strVol = ("905");
            break;
            case 910: strVol = ("91");
            break;
            case 915: strVol = ("915");
            break;
            case 920: strVol = ("92");
            break;
            case 925: strVol = ("925");
            break;
            case 930: strVol = ("93");
            break;
            case 935: strVol = ("935");
            break;
            case 940: strVol = ("94");
            break;
            case 945: strVol = ("945");
            break;
            case 950: strVol = ("95");
            break;
            case 955: strVol = ("955");
            break;
            case 960: strVol = ("96");
            break;
            case 965: strVol = ("965");
            break;
            case 970: strVol = ("97");
            break;
            case 975: strVol = ("975");
            break;
            case 980: strVol = ("98");
            break;
            default: strVol = strVol;
            break;
        }
        connectionAVR.sendCommand("MV"+strVol+"\r");
    }
    
    public static void setSource(String strSource){
        System.out.println(strSource);
        switch(strSource){
            case "CBL/SAT": strSource = "SISAT/CBL\r";
            break;
            case "Blu-ray": strSource = "SIBD\r";
            break;
            case "GAME": strSource = "SIGAME\r";
            break;
            case "MEDIA PLAYER": strSource = "SIMPLAY\r";
            break;
            case "DVD": strSource = "SIDVD\r";
            break;
            case "AUX1": strSource = "SIAUX1\r";
            break;
            case "AUX2": strSource = "SIAUX2\r";
            break;
            case "CD": strSource = "SICD\r";
            break;
            case "TUNER": strSource = "SITUNER\r";
            break;
            case "PHONO": strSource = "SIPHONO\r";
            break;
            case "ONLINE MUSIC": strSource = "SINET\r";
            break;
            case "Bluetooth": strSource = "SIBT\r";
            break;
            case "iPod/USB": strSource = "SIUSB/IPOD\r";
            break;
            case "INTERNET RADIO": strSource = "SIIRADIO\r";
            break;
            default: ;
            break;
        }
        connectionAVR.sendCommand(strSource);
    }
   
    public static String getSource(){
        strData = null;
        while(strData == null){
            strData = connectionAVR.getData("SI?\r");
        }
        return strData;
    }
    
    public static int getVolume(){
        strData = null;
        while(strData == null){
            strData = connectionAVR.getData("MV?\r");
        }
        strData = strData.replaceAll("[^0-9]", "");
        switch(strData){
            case "005": iVol = 5;
            break;
            case "01": iVol = 10;
            break;
            case "015": iVol = 15;
            break;
            case "02": iVol = 20;
            break;
            case "025": iVol = 25;
            break;
            case "03": iVol = 30;
            break;
            case "035": iVol = 35;
            break;
            case "04": iVol = 40;
            break;
            case "045": iVol = 45;
            break;
            case "05": iVol = 50;
            break;
            case "055": iVol = 55;
            break;
            case "06": iVol = 60;
            break;
            case "065": iVol = 65;
            break;
            case "07": iVol = 70;
            break;
            case "075": iVol = 75;
            break;
            case "08": iVol = 80;
            break;
            case "085": iVol = 85;
            break;
            case "09": iVol = 90;
            break;
            case "095": iVol = 95;
            break;
            case "10": iVol = 100;
            break;
            case "105": iVol = 105;
            break;
            case "11": iVol = 110;
            break;
            case "115": iVol = 115;
            break;
            case "12": iVol = 120;
            break;
            case "125": iVol = 125;
            break;
            case "13": iVol = 130;
            break;
            case "135": iVol = 135;
            break;
            case "14": iVol = 140;
            break;
            case "145": iVol = 145;
            break;
            case "15": iVol = 150;
            break;
            case "155": iVol = 155;
            break;
            case "16": iVol = 160;
            break;
            case "165": iVol = 165;
            break;
            case "17": iVol = 170;
            break;
            case "175": iVol = 175;
            break;
            case "18": iVol = 180;
            break;
            case "185": iVol = 185;
            break;
            case "19": iVol = 190;
            break;
            case "195": iVol = 195;
            break;
            case "20": iVol = 200;
            break;
            case "205": iVol = 205;
            break;
            case "21": iVol = 210;
            break;
            case "215": iVol = 215;
            break;
            case "22": iVol = 220;
            break;
            case "225": iVol = 225;
            break;
            case "23": iVol = 230;
            break;
            case "235": iVol = 235;
            break;
            case "24": iVol = 240;
            break;
            case "245": iVol = 245;
            break;
            case "25": iVol = 250;
            break;
            case "255": iVol = 255;
            break;
            case "26": iVol = 260;
            break;
            case "265": iVol = 265;
            break;
            case "27": iVol = 270;
            break;
            case "275": iVol = 275;
            break;
            case "28": iVol = 280;
            break;
            case "285": iVol = 285;
            break;
            case "29": iVol = 290;
            break;
            case "295": iVol = 295;
            break;
            case "30": iVol = 300;
            break;
            case "305": iVol = 305;
            break;
            case "31": iVol = 310;
            break;
            case "315": iVol = 315;
            break;
            case "32": iVol = 320;
            break;
            case "325": iVol = 325;
            break;
            case "33": iVol = 330;
            break;
            case "335": iVol = 335;
            break;
            case "34": iVol = 340;
            break;
            case "345": iVol = 345;
            break;
            case "35": iVol = 350;
            break;
            case "355": iVol = 355;
            break;
            case "36": iVol = 360;
            break;
            case "365": iVol = 365;
            break;
            case "37": iVol = 370;
            break;
            case "375": iVol = 375;
            break;
            case "38": iVol = 380;
            break;
            case "385": iVol = 385;
            break;
            case "39": iVol = 390;
            break;
            case "395": iVol = 395;
            break;
            case "40": iVol = 400;
            break;
            case "405": iVol = 405;
            break;
            case "41": iVol = 410;
            break;
            case "415": iVol = 415;
            break;
            case "42": iVol = 420;
            break;
            case "425": iVol = 425;
            break;
            case "43": iVol = 430;
            break;
            case "435": iVol = 435;
            break;
            case "44": iVol = 440;
            break;
            case "445": iVol = 445;
            break;
            case "45": iVol = 450;
            break;
            case "455": iVol = 455;
            break;
            case "46": iVol = 460;
            break;
            case "465": iVol = 465;
            break;
            case "47": iVol = 470;
            break;
            case "475": iVol = 475;
            break;
            case "48": iVol = 480;
            break;
            case "485": iVol = 485;
            break;
            case "49": iVol = 490;
            break;
            case "495": iVol = 495;
            break;
            case "50": iVol = 500;
            break;
            case "505": iVol = 505;
            break;
            case "51": iVol = 510;
            break;
            case "515": iVol = 515;
            break;
            case "52": iVol = 520;
            break;
            case "525": iVol = 525;
            break;
            case "53": iVol = 530;
            break;
            case "535": iVol = 535;
            break;
            case "54": iVol = 540;
            break;
            case "545": iVol = 545;
            break;
            case "55": iVol = 550;
            break;
            case "555": iVol = 555;
            break;
            case "56": iVol = 560;
            break;
            case "565": iVol = 565;
            break;
            case "57": iVol = 570;
            break;
            case "575": iVol = 575;
            break;
            case "58": iVol = 580;
            break;
            case "585": iVol = 585;
            break;
            case "59": iVol = 590;
            break;
            case "595": iVol = 595;
            break;
            case "60": iVol = 600;
            break;
            case "605": iVol = 605;
            break;
            case "61": iVol = 610;
            break;
            case "615": iVol = 615;
            break;
            case "62": iVol = 620;
            break;
            case "625": iVol = 625;
            break;
            case "63": iVol = 630;
            break;
            case "635": iVol = 635;
            break;
            case "64": iVol = 640;
            break;
            case "645": iVol = 645;
            break;
            case "65": iVol = 650;
            break;
            case "655": iVol = 655;
            break;
            case "66": iVol = 660;
            break;
            case "665": iVol = 665;
            break;
            case "67": iVol = 670;
            break;
            case "675": iVol = 675;
            break;
            case "68": iVol = 680;
            break;
            case "685": iVol = 685;
            break;
            case "69": iVol = 690;
            break;
            case "695": iVol = 695;
            break;
            case "70": iVol = 700;
            break;
            case "705": iVol = 705;
            break;
            case "71": iVol = 710;
            break;
            case "715": iVol = 715;
            break;
            case "72": iVol = 720;
            break;
            case "725": iVol = 725;
            break;
            case "73": iVol = 730;
            break;
            case "735": iVol = 735;
            break;
            case "74": iVol = 740;
            break;
            case "745": iVol = 745;
            break;
            case "75": iVol = 750;
            break;
            case "755": iVol = 755;
            break;
            case "76": iVol = 760;
            break;
            case "765": iVol = 765;
            break;
            case "77": iVol = 770;
            break;
            case "775": iVol = 775;
            break;
            case "78": iVol = 780;
            break;
            case "785": iVol = 785;
            break;
            case "79": iVol = 790;
            break;
            case "795": iVol = 795;
            break;
            case "80": iVol = 800;
            break;
            case "805": iVol = 805;
            break;
            case "81": iVol = 810;
            break;
            case "815": iVol = 815;
            break;
            case "82": iVol = 820;
            break;
            case "825": iVol = 825;
            break;
            case "83": iVol = 830;
            break;
            case "835": iVol = 835;
            break;
            case "84": iVol = 840;
            break;
            case "845": iVol = 845;
            break;
            case "85": iVol = 850;
            break;
            case "855": iVol = 855;
            break;
            case "86": iVol = 860;
            break;
            case "865": iVol = 865;
            break;
            case "87": iVol = 870;
            break;
            case "875": iVol = 875;
            break;
            case "88": iVol = 880;
            break;
            case "885": iVol = 885;
            break;
            case "89": iVol = 890;
            break;
            case "895": iVol = 895;
            break;
            case "90": iVol = 900;
            break;
            case "905": iVol = 905;
            break;
            case "91": iVol = 910;
            break;
            case "915": iVol = 915;
            break;
            case "92": iVol = 920;
            break;
            case "925": iVol = 925;
            break;
            case "93": iVol = 930;
            break;
            case "935": iVol = 935;
            break;
            case "94": iVol = 940;
            break;
            case "945": iVol = 945;
            break;
            case "95": iVol = 950;
            break;
            case "955": iVol = 955;
            break;
            case "96": iVol = 960;
            break;
            case "965": iVol = 965;
            break;
            case "97": iVol = 970;
            break;
            case "975": iVol = 975;
            break;
            case "98": iVol = 980;
            break;
            default: iVol = iVol;
            break;
        }
        return iVol;
    }
    
    public static Boolean checkAVRPowerOn(){
        strData = null;
        while(strData == null){
            strData = connectionAVR.getData("PW?\r");
        }
        if(strData.equals("PWON")){
            return true;
        }else{
            return false;
        }       
    }
    
    public static Boolean checkAVRPowerSTBY(){
        strData = null;
        while(strData == null){
            strData = connectionAVR.getData("PW?\r");
        }
        if(strData.equals("PWSTANDBY")){
            return true;
        }else{
            return false;
        }       
    }
    
    private static void waitAfterPowerOn(){
        try {
            Thread.sleep(1000);
        }catch(InterruptedException ie) {
        }
    }
    
    public static void connect(){
        connectionAVR.openConnection();
    }    
    
}