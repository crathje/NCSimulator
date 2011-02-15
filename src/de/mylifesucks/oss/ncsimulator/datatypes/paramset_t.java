/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.LinkedList;

/**
 * Parameter Set Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=FlightCtrl&path=/tags/V0.82a/eeprom.h
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class paramset_t extends c_int {

    u8 Revision;
    u8 Kanalbelegung[];// GAS[0], GIER[1],NICK[2], ROLL[3], POTI1, POTI2, POTI3
    u8Flags GlobalConfig;// 0x01;
    u8 Hoehe_MinGas;// Wert : 0-100
    u8 Luftdruck_D;// Wert : 0-250
    u8 MaxHoehe;// Wert : 0-32
    u8 Hoehe_P;// Wert : 0-32
    u8 Hoehe_Verstaerkung;// Wert : 0-50
    u8 Hoehe_ACC_Wirkung;// Wert : 0-250
    u8 Hoehe_HoverBand;// Wert : 0-250
    u8 Hoehe_GPS_Z;// Wert : 0-250
    u8 Hoehe_StickNeutralPoint;// Wert : 0-250
    u8 Stick_P;// Wert : 1-6
    u8 Stick_D;// Wert : 0-64
    u8 Gier_P;// Wert : 1-20
    u8 Gas_Min;// Wert : 0-32
    u8 Gas_Max;// Wert : 33-250
    u8 GyroAccFaktor;// Wert : 1-64
    u8 KompassWirkung;// Wert : 0-32
    u8 Gyro_P;// Wert : 10-250
    u8 Gyro_I;// Wert : 0-250
    u8 Gyro_D;// Wert : 0-250
    u8 Gyro_Gier_P;// Wert : 10-250
    u8 Gyro_Gier_I;// Wert : 0-250
    u8 Gyro_Stability;// Wert : 0-16
    u8 UnterspannungsWarnung;// Wert : 0-250
    u8 NotGas;// Wert : 0-250     //Gaswert bei Empängsverlust
    u8 NotGasZeit;// Wert : 0-250     // Zeitbis auf NotGas geschaltet wird, wg. Rx-Problemen
    u8Flags Receiver;// 0;
    u8 I_Faktor;// Wert : 0-250
    u8 UserParam1;// Wert : 0-250
    u8 UserParam2;// Wert : 0-250
    u8 UserParam3;// Wert : 0-250
    u8 UserParam4;// Wert : 0-250
    u8 ServoNickControl;// Wert : 0-250     // Stellung des Servos
    u8 ServoNickComp;// Wert : 0-250     // Einfluss Gyro/Servo
    u8 ServoNickMin;// Wert : 0-250     // Anschlag
    u8 ServoNickMax;// Wert : 0-250     // Anschlag
    //--- Seit V0.75
    u8 ServoRollControl;// Wert : 0-250     // Stellung des Servos
    u8 ServoRollComp;// Wert : 0-250
    u8 ServoRollMin;// Wert : 0-250
    u8 ServoRollMax;// Wert : 0-250
    //---
    u8 ServoNickRefresh;// Speed of the Servo
    u8 ServoManualControlSpeed;//
    u8 CamOrientation;         //
    u8 Servo3;// Value or mapping of the Servo Output
    u8 Servo4;// Value or mapping of the Servo Output
    u8 Servo5;// Value or mapping of the Servo Output
    u8 LoopGasLimit;// Wert: 0-250  max. Gas während Looping
    u8 LoopThreshold;// Wert: 0-250  Schwelle für Stickausschlag
    u8 LoopHysterese;// Wert: 0-250  Hysterese für Stickausschlag
    u8 AchsKopplung1;// Wert: 0-250  Faktor, mit dem Gier die Achsen Roll und Nick koppelt (NickRollMitkopplung)
    u8 AchsKopplung2;// Wert: 0-250  Faktor, mit dem Nick und Roll verkoppelt werden
    u8 CouplingYawCorrection;// Wert: 0-250  Faktor, mit dem Nick und Roll verkoppelt werden
    u8 WinkelUmschlagNick;// Wert: 0-250  180°-Punkt
    u8 WinkelUmschlagRoll;// Wert: 0-250  180°-Punkt
    u8 GyroAccAbgleich;// 1/k  (Koppel_ACC_Wirkung)
    u8 Driftkomp;
    u8 DynamicStability;
    u8 UserParam5;// Wert : 0-250
    u8 UserParam6;// Wert : 0-250
    u8 UserParam7;// Wert : 0-250
    u8 UserParam8;// Wert : 0-250
    //---Output ---------------------------------------------
    u8 J16Bitmask;// for the J16 Output
    u8 J16Timing;// for the J16 Output
    u8 J17Bitmask;// for the J17 Output
    u8 J17Timing;// for the J17 Output
    // seit version V0.75c
    u8 WARN_J16_Bitmask;// for the J16 Output
    u8 WARN_J17_Bitmask;// for the J17 Output
    //---NaviCtrl---------------------------------------------
    u8 NaviGpsModeControl;// Parameters for the Naviboard
    u8 NaviGpsGain;
    u8 NaviGpsP;
    u8 NaviGpsI;
    u8 NaviGpsD;
    u8 NaviGpsPLimit;
    u8 NaviGpsILimit;
    u8 NaviGpsDLimit;
    u8 NaviGpsACC;
    u8 NaviGpsMinSat;
    u8 NaviStickThreshold;
    u8 NaviWindCorrection;
    u8 NaviSpeedCompensation;
    u8 NaviOperatingRadius;
    u8 NaviAngleLimitation;
    u8 NaviPH_LoginTime;
    //---Ext.Ctrl---------------------------------------------
    u8 ExternalControl;// for serial Control
    //---CareFree---------------------------------------------
    u8 OrientationAngle;// Where is the front-direction?
    u8 OrientationModeControl;// switch for CareFree
    u8 MotorSafetySwitch;
    //------------------------------------------------
    u8Flags BitConfig;// (war Loop-Cfg) Bitcodiert: 0x01;/ wird getrennt behandelt
    u8 ServoCompInvert;// //  0x01 ;// WICHTIG!!! am Ende lassen
    u8 ExtraConfig;// bitcodiert
    //u8 Name[] ;
    c_string Name;
    u8 crc;			  // must be the last byte!
    public int index = 1;

    @Override
    public int[] getAsInt() {
        int[] ret = super.getAsInt();
        ret = c_int.concatArray(new int[]{index}, ret);
        return ret;

    }

    /**
     * Create new Parameter Set Struct
     * @param index which Set is this? typically 1-5
     */
    public paramset_t(int index) {

        this.index = index;

        Revision = new u8(index + " Revision");
        Kanalbelegung = new u8[12];       // GAS[0], GIER[1],NICK[2], ROLL[3], POTI1, POTI2, POTI3
        GlobalConfig = new u8Flags(index + " GlobalConfig", new String[]{"HOEHENREGELUNG", "HOEHEN_SCHALTER ", "HEADING_HOLD ", "KOMPASS_AKTIV ", "KOMPASS_FIX;", "GPS_AKTIV", "ACHSENKOPPLUNG_AKTIV", "DREHRATEN_BEGRENZER"});           // 0x01=Höhenregler aktiv,0x02=Kompass aktiv, 0x04=GPS aktiv, 0x08=Heading Hold aktiv
        Hoehe_MinGas = new u8(index + " Hoehe_MinGas");           // Wert : 0-100
        Luftdruck_D = new u8(index + " Luftdruck_D");            // Wert : 0-250
        MaxHoehe = new u8(index + " MaxHoehe");               // Wert : 0-32
        Hoehe_P = new u8(index + " Hoehe_P");                // Wert : 0-32
        Hoehe_Verstaerkung = new u8(index + " Hoehe_Verstaerkung");     // Wert : 0-50
        Hoehe_ACC_Wirkung = new u8(index + " Hoehe_ACC_Wirkung");      // Wert : 0-250
        Hoehe_HoverBand = new u8(index + " Hoehe_HoverBand");        // Wert : 0-250
        Hoehe_GPS_Z = new u8(index + " Hoehe_GPS_Z");            // Wert : 0-250
        Hoehe_StickNeutralPoint = new u8(index + " Hoehe_StickNeutralPoint");// Wert : 0-250
        Stick_P = new u8(index + " Stick_P");                // Wert : 1-6
        Stick_D = new u8(index + " Stick_D");                // Wert : 0-64
        Gier_P = new u8(index + " Gier_P");                 // Wert : 1-20
        Gas_Min = new u8(index + " Gas_Min");                // Wert : 0-32
        Gas_Max = new u8(index + " Gas_Max");                // Wert : 33-250
        GyroAccFaktor = new u8(index + " GyroAccFaktor");          // Wert : 1-64
        KompassWirkung = new u8(index + " KompassWirkung");         // Wert : 0-32
        Gyro_P = new u8(index + " Gyro_P");                 // Wert : 10-250
        Gyro_I = new u8(index + " Gyro_I");                 // Wert : 0-250
        Gyro_D = new u8(index + " Gyro_D");                 // Wert : 0-250
        Gyro_Gier_P = new u8(index + " Gyro_Gier_P");            // Wert : 10-250
        Gyro_Gier_I = new u8(index + " Gyro_Gier_I");            // Wert : 0-250
        Gyro_Stability = new u8(index + " Gyro_Stability");         // Wert : 0-16
        UnterspannungsWarnung = new u8(index + " UnterspannungsWarnung");  // Wert : 0-250
        NotGas = new u8(index + " NotGas");                 // Wert : 0-250     //Gaswert bei Empängsverlust
        NotGasZeit = new u8(index + " NotGasZeit");             // Wert : 0-250     // Zeitbis auf NotGas geschaltet wird, wg. Rx-Problemen
        Receiver = new u8Flags(index + " Receiver", new String[]{"PPM", "SPEKTRUM", "SPEKTRUM_HI_RES", "SPEKTRUM_LOW_RES", "JETI", "ACT_DSL", "", ""});         	  // 0= Summensignal, 1= Spektrum, 2 =Jeti, 3=ACT DSL, 4=ACT S3D
        I_Faktor = new u8(index + " I_Faktor");               // Wert : 0-250
        UserParam1 = new u8(index + " UserParam1");             // Wert : 0-250
        UserParam2 = new u8(index + " UserParam2");             // Wert : 0-250
        UserParam3 = new u8(index + " UserParam3");             // Wert : 0-250
        UserParam4 = new u8(index + " UserParam4");             // Wert : 0-250
        ServoNickControl = new u8(index + " ServoNickControl");       // Wert : 0-250     // Stellung des Servos
        ServoNickComp = new u8(index + " ServoNickComp");          // Wert : 0-250     // Einfluss Gyro/Servo
        ServoNickMin = new u8(index + " ServoNickMin");           // Wert : 0-250     // Anschlag
        ServoNickMax = new u8(index + " ServoNickMax");           // Wert : 0-250     // Anschlag
        //--- Seit V0.75
        ServoRollControl = new u8(index + " ServoRollControl");       // Wert : 0-250     // Stellung des Servos
        ServoRollComp = new u8(index + " ServoRollComp");          // Wert : 0-250
        ServoRollMin = new u8(index + " ServoRollMin");           // Wert : 0-250
        ServoRollMax = new u8(index + " ServoRollMax");           // Wert : 0-250
        //---
        ServoNickRefresh = new u8(index + " ServoNickRefresh");       // Speed of the Servo
        ServoManualControlSpeed = new u8(index + " ServoManualControlSpeed");//
        CamOrientation = new u8(index + " CamOrientation");         //
        Servo3 = new u8(index + " Servo3");        		 // Value or mapping of the Servo Output
        Servo4 = new u8(index + " Servo4");       			 // Value or mapping of the Servo Output
        Servo5 = new u8(index + " Servo5");       			 // Value or mapping of the Servo Output
        LoopGasLimit = new u8(index + " LoopGasLimit");           // Wert: 0-250  max. Gas während Looping
        LoopThreshold = new u8(index + " LoopThreshold");          // Wert: 0-250  Schwelle für Stickausschlag
        LoopHysterese = new u8(index + " LoopHysterese");          // Wert: 0-250  Hysterese für Stickausschlag
        AchsKopplung1 = new u8(index + " AchsKopplung1");          // Wert: 0-250  Faktor, mit dem Gier die Achsen Roll und Nick koppelt (NickRollMitkopplung)
        AchsKopplung2 = new u8(index + " AchsKopplung2");          // Wert: 0-250  Faktor, mit dem Nick und Roll verkoppelt werden
        CouplingYawCorrection = new u8(index + " CouplingYawCorrection");  // Wert: 0-250  Faktor, mit dem Nick und Roll verkoppelt werden
        WinkelUmschlagNick = new u8(index + " WinkelUmschlagNick");     // Wert: 0-250  180°-Punkt
        WinkelUmschlagRoll = new u8(index + " WinkelUmschlagRoll");     // Wert: 0-250  180°-Punkt
        GyroAccAbgleich = new u8(index + " GyroAccAbgleich");        // 1/k  (Koppel_ACC_Wirkung)
        Driftkomp = new u8(index + " Driftkomp");
        DynamicStability = new u8(index + " DynamicStability");
        UserParam5 = new u8(index + " UserParam5");             // Wert : 0-250
        UserParam6 = new u8(index + " UserParam6");             // Wert : 0-250
        UserParam7 = new u8(index + " UserParam7");             // Wert : 0-250
        UserParam8 = new u8(index + " UserParam8");             // Wert : 0-250
        //---Output ---------------------------------------------
        J16Bitmask = new u8(index + " J16Bitmask");             // for the J16 Output
        J16Timing = new u8(index + " J16Timing");              // for the J16 Output
        J17Bitmask = new u8(index + " J17Bitmask");             // for the J17 Output
        J17Timing = new u8(index + " J17Timing");              // for the J17 Output
        // seit version V0.75c
        WARN_J16_Bitmask = new u8(index + " WARN_J16_Bitmask");       // for the J16 Output
        WARN_J17_Bitmask = new u8(index + " WARN_J17_Bitmask");       // for the J17 Output
        //---NaviCtrl---------------------------------------------
        NaviGpsModeControl = new u8(index + " NaviGpsModeControl");     // Parameters for the Naviboard
        NaviGpsGain = new u8(index + " NaviGpsGain");
        NaviGpsP = new u8(index + " NaviGpsP");
        NaviGpsI = new u8(index + " NaviGpsI");
        NaviGpsD = new u8(index + " NaviGpsD");
        NaviGpsPLimit = new u8(index + " NaviGpsPLimit");
        NaviGpsILimit = new u8(index + " NaviGpsILimit");
        NaviGpsDLimit = new u8(index + " NaviGpsDLimit");
        NaviGpsACC = new u8(index + " NaviGpsACC");
        NaviGpsMinSat = new u8(index + " NaviGpsMinSat");
        NaviStickThreshold = new u8(index + " NaviStickThreshold");
        NaviWindCorrection = new u8(index + " NaviWindCorrection");
        NaviSpeedCompensation = new u8(index + " NaviSpeedCompensation");
        NaviOperatingRadius = new u8(index + " NaviOperatingRadius");
        NaviAngleLimitation = new u8(index + " NaviAngleLimitation");
        NaviPH_LoginTime = new u8(index + " NaviPH_LoginTime");
        //---Ext.Ctrl---------------------------------------------
        ExternalControl = new u8(index + " ExternalControl");         // for serial Control
        //---CareFree---------------------------------------------
        OrientationAngle = new u8(index + " OrientationAngle");        // Where is the front-direction?
        OrientationModeControl = new u8(index + " OrientationModeControl");  // switch for CareFree
        MotorSafetySwitch = new u8(index + "MotorSafetySwitch");
        //------------------------------------------------
        BitConfig = new u8Flags(index + " BitConfig", new String[]{"UP", "DOWN", "LEFT", "RIGHT", "", "", "", ""});          // (war Loop-Cfg) Bitcodiert: 0x01=oben, 0x02=unten, 0x04=links, 0x08=rechts / wird getrennt behandelt
        ServoCompInvert = new u8(index + " ServoCompInvert");    // //  0x01 = Nick, 0x02 = Roll   0 oder 1  // WICHTIG!!! am Ende lassen
        ExtraConfig = new u8(index + " ExtraConfig");        // bitcodiert

        Name = new c_string(index + " Name", 12, "Setting " + index);
        crc = new u8(index + " crc");


        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Revision);
        for (int i = 0; i < Kanalbelegung.length; i++) {
            u8 c = new u8(index + " Kanalbelegung " + i);
            Kanalbelegung[i] = c;
            allAttribs.add(c);
        }
        allAttribs.add(GlobalConfig);
        allAttribs.add(Hoehe_MinGas);
        allAttribs.add(Luftdruck_D);
        allAttribs.add(MaxHoehe);
        allAttribs.add(Hoehe_P);
        allAttribs.add(Hoehe_Verstaerkung);
        allAttribs.add(Hoehe_ACC_Wirkung);
        allAttribs.add(Hoehe_HoverBand);
        allAttribs.add(Hoehe_GPS_Z);
        allAttribs.add(Hoehe_StickNeutralPoint);//
        allAttribs.add(Stick_P);
        allAttribs.add(Stick_D);
        allAttribs.add(Gier_P);
        allAttribs.add(Gas_Min);
        allAttribs.add(Gas_Max);
        allAttribs.add(GyroAccFaktor);
        allAttribs.add(KompassWirkung);
        allAttribs.add(Gyro_P);
        allAttribs.add(Gyro_I);
        allAttribs.add(Gyro_D);
        allAttribs.add(Gyro_Gier_P);
        allAttribs.add(Gyro_Gier_I);
        allAttribs.add(Gyro_Stability);
        allAttribs.add(UnterspannungsWarnung);
        allAttribs.add(NotGas);
        allAttribs.add(NotGasZeit);
        allAttribs.add(Receiver);
        allAttribs.add(I_Faktor);
        allAttribs.add(UserParam1);
        allAttribs.add(UserParam2);
        allAttribs.add(UserParam3);
        allAttribs.add(UserParam4);
        allAttribs.add(ServoNickControl);
        allAttribs.add(ServoNickComp);
        allAttribs.add(ServoNickMin);
        allAttribs.add(ServoNickMax);

        allAttribs.add(ServoRollControl);
        allAttribs.add(ServoRollComp);
        allAttribs.add(ServoRollMin);
        allAttribs.add(ServoRollMax);
        allAttribs.add(ServoNickRefresh);
        allAttribs.add(ServoManualControlSpeed);
        allAttribs.add(CamOrientation);
        allAttribs.add(Servo3);
        allAttribs.add(Servo4);
        allAttribs.add(Servo5);
        allAttribs.add(LoopGasLimit);
        allAttribs.add(LoopThreshold);
        allAttribs.add(LoopHysterese);
        allAttribs.add(AchsKopplung1);
        allAttribs.add(AchsKopplung2);
        allAttribs.add(CouplingYawCorrection);
        allAttribs.add(WinkelUmschlagNick);
        allAttribs.add(WinkelUmschlagRoll);
        allAttribs.add(GyroAccAbgleich);
        allAttribs.add(Driftkomp);
        allAttribs.add(DynamicStability);
        allAttribs.add(UserParam5);
        allAttribs.add(UserParam6);
        allAttribs.add(UserParam7);
        allAttribs.add(UserParam8);
        allAttribs.add(J16Bitmask);
        allAttribs.add(J16Timing);
        allAttribs.add(J17Bitmask);
        allAttribs.add(J17Timing);
        allAttribs.add(WARN_J16_Bitmask);
        allAttribs.add(WARN_J17_Bitmask);
        allAttribs.add(NaviGpsModeControl);
        allAttribs.add(NaviGpsGain);
        allAttribs.add(NaviGpsP);
        allAttribs.add(NaviGpsI);
        allAttribs.add(NaviGpsD);
        allAttribs.add(NaviGpsPLimit);
        allAttribs.add(NaviGpsILimit);
        allAttribs.add(NaviGpsDLimit);
        allAttribs.add(NaviGpsACC);
        allAttribs.add(NaviGpsMinSat);
        allAttribs.add(NaviStickThreshold);
        allAttribs.add(NaviWindCorrection);
        allAttribs.add(NaviSpeedCompensation);
        allAttribs.add(NaviOperatingRadius);
        allAttribs.add(NaviAngleLimitation);
        allAttribs.add(NaviPH_LoginTime);

        allAttribs.add(ExternalControl);

        allAttribs.add(OrientationAngle);
        allAttribs.add(OrientationModeControl);
        allAttribs.add(MotorSafetySwitch);

        allAttribs.add(BitConfig);
        allAttribs.add(ServoCompInvert);
        allAttribs.add(ExtraConfig);
        /*for (int i = 0; i < Name.length; i++) {
        u8 c = new u8("Name " + i);
        Name[i] = c;
        allAttribs.add(c);
        }*/
        allAttribs.add(Name);
        allAttribs.add(crc);

    }
}

