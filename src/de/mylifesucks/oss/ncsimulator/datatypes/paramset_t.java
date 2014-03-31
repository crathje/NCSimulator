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
 * Parameter Set Struct adapted from:
 * http://svn.mikrokopter.de/filedetails.php?repname=FlightCtrl&path=/tags/V0.82a/eeprom.h
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class paramset_t extends c_int {

    int EEPARAM_REVISION = 100;
    //GlobalConfig3
    int CFG3_NO_SDCARD_NO_START = 0x01;
    int CFG3_DPH_MAX_RADIUS = 0x02;
    int CFG3_VARIO_FAILSAFE = 0x04;
    int CFG3_MOTOR_SWITCH_MODE = 0x08;
    int CFG3_NO_GPSFIX_NO_START = 0x10;
    int CFG3_USE_NC_FOR_OUT1 = 0x20;
    int CFG3_SPEAK_ALL = 0x40;
    int CFG3_SERVO_NICK_COMP_OFF = 0x80;

    //GlobalConfig
    int CFG_HOEHENREGELUNG = 0x01;
    int CFG_HOEHEN_SCHALTER = 0x02;
    int CFG_HEADING_HOLD = 0x04;
    int CFG_KOMPASS_AKTIV = 0x08;
    int CFG_KOMPASS_FIX = 0x10;
    int CFG_GPS_AKTIV = 0x20;
    int CFG_ACHSENKOPPLUNG_AKTIV = 0x40;
    int CFG_DREHRATEN_BEGRENZER = 0x80;
//BitConfig
    int CFG_LOOP_OBEN = 0x01;
    int CFG_LOOP_UNTEN = 0x02;
    int CFG_LOOP_LINKS = 0x04;
    int CFG_LOOP_RECHTS = 0x08;
    int CFG_MOTOR_BLINK1 = 0x10;
    int CFG_MOTOR_OFF_LED1 = 0x20;
    int CFG_MOTOR_OFF_LED2 = 0x40;
    int CFG_MOTOR_BLINK2 = 0x80;
// ExtraConfig
    int CFG2_HEIGHT_LIMIT = 0x01;
    int CFG2_VARIO_BEEP = 0x02;
    int CFG_SENSITIVE_RC = 0x04;
    int CFG_3_3V_REFERENCE = 0x08;
    int CFG_NO_RCOFF_BEEPING = 0x10;
    int CFG_GPS_AID = 0x20;
    int CFG_LEARNABLE_CAREFREE = 0x40;
    int CFG_IGNORE_MAG_ERR_AT_STARTUP = 0x80;
// bit mask for ParamSet.Config0
    int CFG0_AIRPRESS_SENSOR = 0x01;
    int CFG0_HEIGHT_SWITCH = 0x02;
    int CFG0_HEADING_HOLD = 0x04;
    int CFG0_COMPASS_ACTIVE = 0x08;
    int CFG0_COMPASS_FIX = 0x10;
    int CFG0_GPS_ACTIVE = 0x20;
    int CFG0_AXIS_COUPLING_ACTIVE = 0x40;
    int CFG0_ROTARY_RATE_LIMITER = 0x80;
// defines for the receiver selection
    int RECEIVER_PPM = 0;
    int RECEIVER_SPEKTRUM = 1;
    int RECEIVER_SPEKTRUM_HI_RES = 2;
    int RECEIVER_SPEKTRUM_LOW_RES = 3;
    int RECEIVER_JETI = 4;
    int RECEIVER_ACT_DSL = 5;
    int RECEIVER_HOTT = 6;
    int RECEIVER_SBUS = 7;
    int RECEIVER_USER = 8;
    int RECEIVER_UNKNOWN = 0xFF;
    u8 Revision;
    u8 Kanalbelegung[];// GAS[0], GIER[1],NICK[2], ROLL[3], POTI1, POTI2, POTI3
    u8Flags GlobalConfig;// 0x01;
    u8 Hoehe_MinGas;// Wert : 0-100
    u8 Luftdruck_D;// Wert : 0-250
    u8 HoeheChannel;// Wert : 0-32
    u8 Hoehe_P;// Wert : 0-32
    u8 Hoehe_Verstaerkung;// Wert : 0-50
    u8 Hoehe_ACC_Wirkung;// Wert : 0-250
    u8 Hoehe_HoverBand;// Wert : 0-250
    u8 Hoehe_GPS_Z;// Wert : 0-250
    u8 Hoehe_StickNeutralPoint;// Wert : 0-250
    u8 Stick_P;// Wert : 1-6
    u8 Stick_D;// Wert : 0-64
    u8 StickGier_P;// Wert : 1-20
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
    u8 NaviOut1Parameter;// Parameters for the Naviboard
    u8 NaviGpsModeChannel;// Parameters for the Naviboard
    u8 NaviGpsGain;
    u8 NaviGpsP;
    u8 NaviGpsI;
    u8 NaviGpsD;
    u8 NaviGpsPLimit;
    u8 NaviGpsILimit;
    u8 NaviGpsDLimit;
    u8 NaviGpsA;
    u8 NaviGpsMinSat;
    u8 NaviStickThreshold;
    u8 NaviWindCorrection;
    u8 NaviAccCompensation;
    u8 NaviOperatingRadius;
    u8 NaviAngleLimitation;
    u8 NaviPH_LoginTime;
    //---Ext.Ctrl---------------------------------------------
    u8 ExternalControl;// for serial Control
    //---CareFree---------------------------------------------
    u8 OrientationAngle;// Where is the front-direction?
    u8 CareFreeChannel;// switch for CareFree
    u8 MotorSafetySwitch;
    u8 MotorSmooth;
    u8 ComingHomeAltitude;
    u8 FailSafeTime;
    u8 MaxAltitude;
    u8 FailsafeChannel;
    u8 ServoFilterNick;
    u8 ServoFilterRoll;
    u8 StartLandChannel;
    u8 LandingSpeed;

    u8 CompassOffset;
    u8 AutoLandingVoltage;    // in 0,1V  0 -> disabled
    u8 ComingHomeVoltage;    // in 0,1V  0 -> disabled
    u8 AutoPhotoAtitudes;
    u8 SingleWpSpeed;
    //------------------------------------------------
    u8Flags BitConfig;// (war Loop-Cfg) Bitcodiert: 0x01;/ wird getrennt behandelt
    u8Flags ServoCompInvert;// //  0x01 ;// WICHTIG!!! am Ende lassen
    u8 ExtraConfig;// bitcodiert
    u8Flags GlobalConfig3;
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

    void ParamSet_DefaultStickMapping() {

        Kanalbelegung[0].setValue(1, false);
        Kanalbelegung[1].setValue(2, false);
        Kanalbelegung[2].setValue(3, false);
        Kanalbelegung[4].setValue(4, false);
        Kanalbelegung[5].setValue(5, false);
        Kanalbelegung[6].setValue(6, false);
        Kanalbelegung[7].setValue(7, false);
        Kanalbelegung[8].setValue(8, false);
        Kanalbelegung[9].setValue(9, false);
        Kanalbelegung[10].setValue(10, false);
        Kanalbelegung[11].setValue(11, false);
        Kanalbelegung[11].setValue(12, false);
    }

    /**
     * ************************************************
     */
    /*    Default Values for parameter set 1           */
    /**
     * ************************************************
     */
    void CommonDefaults() {
        Revision.setValue(EEPARAM_REVISION, false);

        //if (PlatinenVersion >= 20) {
        Gyro_D.setValue(10, false);
        Driftkomp.setValue(0, false);
        GyroAccFaktor.setValue(27, false);
        WinkelUmschlagNick.setValue(78, false);
        WinkelUmschlagRoll.setValue(78, false);
//        } else {
//            Gyro_D = 3;
//            Driftkomp = 32;
//            GyroAccFaktor = 30;
//            WinkelUmschlagNick = 85;
//            WinkelUmschlagRoll = 85;
//        }
        GlobalConfig.setValue(CFG_ACHSENKOPPLUNG_AKTIV | CFG_KOMPASS_AKTIV | CFG_GPS_AKTIV | CFG_HOEHEN_SCHALTER, false);
        ExtraConfig.setValue(CFG_GPS_AID | CFG2_VARIO_BEEP, false);
        Receiver.setValue(RECEIVER_JETI, false);
        MotorSafetySwitch.setValue(0, false);
        ExternalControl.setValue(0, false);

        Gas_Min.setValue(8, false);             // Wert : 0-32
        Gas_Max.setValue(230, false);           // Wert : 33-247
        KompassWirkung.setValue(64, false);    // Wert : 0-247

        Hoehe_MinGas.setValue(30, false);
        HoeheChannel.setValue(255, false);         // Wert : 0-247   255 -> Poti1
        Hoehe_P.setValue(15, false);          // Wert : 0-32
        Luftdruck_D.setValue(30, false);          // Wert : 0-247
        Hoehe_ACC_Wirkung.setValue(0, false);     // Wert : 0-247
        Hoehe_HoverBand.setValue(8, false);     	  // Wert : 0-247
        Hoehe_GPS_Z.setValue(64, false);           // Wert : 0-247
        Hoehe_StickNeutralPoint.setValue(0, false);// Wert : 0-247 (0.value = Hover-Estimation)
        Hoehe_Verstaerkung.setValue(15, false);    // Wert : 0-50 (15 -> ca. +/- 5m/sek bei Stick-Voll-Ausschlag)

        UserParam1.setValue(0, false);           // zur freien Verwendung
        UserParam2.setValue(0, false);           // zur freien Verwendung
        UserParam3.setValue(0, false);           // zur freien Verwendung
        UserParam4.setValue(0, false);           // zur freien Verwendung
        UserParam5.setValue(0, false);           // zur freien Verwendung
        UserParam6.setValue(0, false);           // zur freien Verwendung
        UserParam7.setValue(0, false);             // zur freien Verwendung
        UserParam8.setValue(0, false);             // zur freien Verwendung

        ServoNickControl.setValue(128, false);     // Wert : 0-247     // Stellung des Servos
        ServoNickComp.setValue(50, false);         // Wert : 0-247     // Einfluss Gyro/Servo
        ServoCompInvert.setValue(2, false);        // Wert : 0-247     // Richtung Einfluss Gyro/Servo
        ServoNickMin.setValue(15, false);          // Wert : 0-247     // Anschlag
        ServoNickMax.setValue(230, false);         // Wert : 0-247     // Anschlag
        ServoNickRefresh.setValue(4, false);
        Servo3.setValue(125, false);
        Servo4.setValue(125, false);
        Servo5.setValue(125, false);
        ServoRollControl.setValue(128, false);     // Wert : 0-247     // Stellung des Servos
        ServoRollComp.setValue(85, false);         // Wert : 0-247     // Einfluss Gyro/Servo
        ServoRollMin.setValue(70, false);          // Wert : 0-247     // Anschlag
        ServoRollMax.setValue(220, false);         // Wert : 0-247     // Anschlag
        ServoManualControlSpeed.setValue(60, false);
        CamOrientation.setValue(0, false);         // Wert : 0-24 -> 0-360 -> 15∞ steps

        J16Bitmask.setValue(95, false);
        J17Bitmask.setValue(243, false);
        WARN_J16_Bitmask.setValue(0xAA, false);
        WARN_J17_Bitmask.setValue(0xAA, false);
        J16Timing.setValue(20, false);
        J17Timing.setValue(20, false);

        LoopGasLimit.setValue(50, false);
        LoopThreshold.setValue(90, false);         // Wert: 0-247  Schwelle f¸r Stickausschlag
        LoopHysterese.setValue(50, false);
        BitConfig.setValue(0, false);              // Bitcodiert: 0x01=oben, 0x02=unten, 0x04=links, 0x08=rechts / wird getrennt behandelt

        NaviOut1Parameter.setValue(0, false); // Photo release in meter
        NaviGpsModeChannel.setValue(254, false); // 254 -> Poti 2
        NaviGpsGain.setValue(100, false);
        NaviGpsP.setValue(90, false);
        NaviGpsI.setValue(90, false);
        NaviGpsD.setValue(90, false);
        NaviGpsPLimit.setValue(75, false);
        NaviGpsILimit.setValue(85, false);
        NaviGpsDLimit.setValue(75, false);
        NaviGpsA.setValue(0, false);
        NaviGpsMinSat.setValue(6, false);
        NaviStickThreshold.setValue(8, false);
        NaviWindCorrection.setValue(90, false);
        NaviAccCompensation.setValue(42, false);
        NaviOperatingRadius.setValue(245, false);
        NaviAngleLimitation.setValue(140, false);
        NaviPH_LoginTime.setValue(5, false);
        OrientationAngle.setValue(0, false);
        CareFreeChannel.setValue(0, false);
        UnterspannungsWarnung.setValue(33, false); // Wert : 0-247 ( Automatische Zellenerkennung bei < 50)
        NotGas.setValue(45, false);                // Wert : 0-247     // Gaswert bei Empangsverlust
        NotGasZeit.setValue(90, false);            // Wert : 0-247     // Zeit bis auf NotGas geschaltet wird, wg. Rx-Problemen
        MotorSmooth.setValue(0, false);
        ComingHomeAltitude.setValue(0, false); 	  // 0.value = don't change 
        FailSafeTime.setValue(0, false); 	          // 0.value = off
        MaxAltitude.setValue(150, false);           // 0.value = off
        AchsKopplung1.setValue(90, false);
        AchsKopplung2.setValue(55, false);
        StartLandChannel.setValue(0, false);
        LandingSpeed.setValue(12, false);

        CompassOffset.setValue(0, false);
        UnterspannungsWarnung.setValue(33, false);
        ComingHomeVoltage.setValue(32, false);
        AutoLandingVoltage.setValue(31, false);
        
        AutoPhotoAtitudes.setValue(0, false);
        SingleWpSpeed.setValue(50, false);
        
    }
    /*
     void ParamSet_DefaultSet1(void) // sport
     {
     CommonDefaults();
     Stick_P = 14;            // Wert : 1-20
     Stick_D = 16;            // Wert : 0-20
     StickGier_P = 12;             // Wert : 1-20
     Gyro_P = 80;             // Wert : 0-247
     Gyro_I = 150;            // Wert : 0-247
     Gyro_Gier_P = 80;        // Wert : 0-247
     Gyro_Gier_I = 150;       // Wert : 0-247
     Gyro_Stability = 6; 	  // Wert : 1-8
     I_Faktor = 32;
     CouplingYawCorrection = 1;
     GyroAccAbgleich = 16;        // 1/k;
     DynamicStability = 100;
     memcpy(Name, "Sport\0", 12);
     crc = RAM_Checksum((uint8_t*)(&EE_Parameter), sizeof(EE_Parameter)-1);
     }
     */

    /**
     * ************************************************
     */
    /*    Default Values for parameter set 1           */
    /**
     * ************************************************
     */
    private void ParamSet_DefaultSet1() // normal
    {
        CommonDefaults();
        Stick_P.setValue(10, false);               // Wert : 1-20
        Stick_D.setValue(16, false);               // Wert : 0-20
        StickGier_P.setValue(6, false);                 // Wert : 1-20
        Gyro_P.setValue(90, false);                // Wert : 0-247
        Gyro_I.setValue(120, false);               // Wert : 0-247
        Gyro_Gier_P.setValue(90, false);           // Wert : 0-247
        Gyro_Gier_I.setValue(120, false);          // Wert : 0-247
        Gyro_Stability.setValue(6, false); 	  	  // Wert : 1-8
        I_Faktor.setValue(32, false);
        CouplingYawCorrection.setValue(60, false);
        GyroAccAbgleich.setValue(32, false);        // 1/k
        DynamicStability.setValue(75, false);
        Name.setValue("Fast");
    }

    /**
     * ************************************************
     */
    /*    Default Values for parameter set 2           */
    /**
     * ************************************************
     */
    private void ParamSet_DefaultSet2() // beginner
    {
        CommonDefaults();
        Stick_P.setValue(8, false);                // Wert : 1-20
        Stick_D.setValue(16, false);               // Wert : 0-20
        StickGier_P.setValue(6, false);                // Wert : 1-20
        Gyro_P.setValue(100, false);               // Wert : 0-247
        Gyro_I.setValue(120, false);               // Wert : 0-247
        Gyro_Gier_P.setValue(100, false);          // Wert : 0-247
        Gyro_Gier_I.setValue(120, false);          // Wert : 0-247
        Gyro_Stability.setValue(6, false); 	  	  // Wert : 1-8
        I_Faktor.setValue(16, false);
        CouplingYawCorrection.setValue(70, false);
        GyroAccAbgleich.setValue(32, false);        // 1/k
        DynamicStability.setValue(70, false);
        Name.setValue("Normal");
    }

    /**
     * ************************************************
     */
    /*    Default Values for parameter set 3           */
    /**
     * ************************************************
     */
    private void ParamSet_DefaultSet3() // beginner
    {
        CommonDefaults();
        Stick_P.setValue(6, false);                // Wert : 1-20
        Stick_D.setValue(10, false);               // Wert : 0-20
        StickGier_P.setValue(4, false);           // Wert : 1-20
        Gyro_P.setValue(100, false);               // Wert : 0-247
        Gyro_I.setValue(120, false);               // Wert : 0-247
        Gyro_Gier_P.setValue(100, false);          // Wert : 0-247
        Gyro_Gier_I.setValue(120, false);          // Wert : 0-247
        Gyro_Stability.setValue(6, false); 	      // Wert : 1-8
        I_Faktor.setValue(16, false);
        CouplingYawCorrection.setValue(70, false);
        GyroAccAbgleich.setValue(32, false);        // 1/k
        DynamicStability.setValue(70, false);
        Name.setValue("Easy");
    }

    /**
     * Create new Parameter Set Struct
     *
     * @param index which Set is this? typically 1-5
     */
    public paramset_t(int index) {

        this.index = index;

        Revision = new u8(index + " Revision");
        Kanalbelegung = new u8[12];       // GAS[0], GIER[1],NICK[2], ROLL[3], POTI1, POTI2, POTI3
        GlobalConfig = new u8Flags(index + " GlobalConfig", new String[]{"HOEHENREGELUNG", "HOEHEN_SCHALTER ", "HEADING_HOLD ", "KOMPASS_AKTIV ", "KOMPASS_FIX;", "GPS_AKTIV", "ACHSENKOPPLUNG_AKTIV", "DREHRATEN_BEGRENZER"});           // 0x01=Höhenregler aktiv,0x02=Kompass aktiv, 0x04=GPS aktiv, 0x08=Heading Hold aktiv
        Hoehe_MinGas = new u8(index + " Hoehe_MinGas");           // Wert : 0-100
        Luftdruck_D = new u8(index + " Luftdruck_D");            // Wert : 0-250
        HoeheChannel = new u8(index + " HoeheChannel");               // Wert : 0-32
        Hoehe_P = new u8(index + " Hoehe_P");                // Wert : 0-32
        Hoehe_Verstaerkung = new u8(index + " Hoehe_Verstaerkung");     // Wert : 0-50
        Hoehe_ACC_Wirkung = new u8(index + " Hoehe_ACC_Wirkung");      // Wert : 0-250
        Hoehe_HoverBand = new u8(index + " Hoehe_HoverBand");        // Wert : 0-250
        Hoehe_GPS_Z = new u8(index + " Hoehe_GPS_Z");            // Wert : 0-250
        Hoehe_StickNeutralPoint = new u8(index + " Hoehe_StickNeutralPoint");// Wert : 0-250
        Stick_P = new u8(index + " Stick_P");                // Wert : 1-6
        Stick_D = new u8(index + " Stick_D");                // Wert : 0-64
        StickGier_P = new u8(index + " Gier_P");                 // Wert : 1-20
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
        NaviOut1Parameter = new u8(index + " NaviOut1Parameter");     // Parameters for the Naviboard
        NaviGpsModeChannel = new u8(index + " NaviGpsModeChannel");     // Parameters for the Naviboard
        NaviGpsGain = new u8(index + " NaviGpsGain");
        NaviGpsP = new u8(index + " NaviGpsP");
        NaviGpsI = new u8(index + " NaviGpsI");
        NaviGpsD = new u8(index + " NaviGpsD");
        NaviGpsPLimit = new u8(index + " NaviGpsPLimit");
        NaviGpsILimit = new u8(index + " NaviGpsILimit");
        NaviGpsDLimit = new u8(index + " NaviGpsDLimit");
        NaviGpsA = new u8(index + " NaviGpsA");
        NaviGpsMinSat = new u8(index + " NaviGpsMinSat");
        NaviStickThreshold = new u8(index + " NaviStickThreshold");
        NaviWindCorrection = new u8(index + " NaviWindCorrection");
        NaviAccCompensation = new u8(index + " NaviSpeedCompensation");
        NaviOperatingRadius = new u8(index + " NaviOperatingRadius");
        NaviAngleLimitation = new u8(index + " NaviAngleLimitation");
        NaviPH_LoginTime = new u8(index + " NaviPH_LoginTime");
        //---Ext.Ctrl---------------------------------------------
        ExternalControl = new u8(index + " ExternalControl");         // for serial Control
        //---CareFree---------------------------------------------
        OrientationAngle = new u8(index + " OrientationAngle");        // Where is the front-direction?
        CareFreeChannel = new u8(index + " CareFreeChannel");  // switch for CareFree
        MotorSafetySwitch = new u8(index + "MotorSafetySwitch");
        MotorSmooth = new u8(index + "MotorSmooth");
        ComingHomeAltitude = new u8(index + "ComingHomeAltitude");
        FailSafeTime = new u8(index + "FailSafeTime");
        MaxAltitude = new u8(index + "MaxAltitude");
        FailsafeChannel = new u8(index + "FailsafeChannel");
        ServoFilterNick = new u8(index + "ServoFilterNick");
        ServoFilterRoll = new u8(index + "ServoFilterRoll");
        StartLandChannel = new u8(index + "StartLandChannel");
        LandingSpeed = new u8(index + "LandingSpeed");

        CompassOffset = new u8(index + "CompassOffset");
        AutoLandingVoltage = new u8(index + "AutoLandingVoltage");
        ComingHomeVoltage = new u8(index + "ComingHomeVoltage");
        AutoPhotoAtitudes = new u8(index + "AutoPhotoAtitudes");
        SingleWpSpeed = new u8(index + "SingleWpSpeed");

        //------------------------------------------------
        BitConfig = new u8Flags(index + " BitConfig", new String[]{"UP", "DOWN", "LEFT", "RIGHT", "MOTOR_BLINK1", "MOTOR_OFF_LED1", "MOTOR_OFF_LED2", "MOTOR_BLINK1"});          // (war Loop-Cfg) Bitcodiert: 0x01=oben, 0x02=unten, 0x04=links, 0x08=rechts / wird getrennt behandelt
        ServoCompInvert = new u8Flags(index + " ServoCompInvert", new String[]{"SERVO_NICK_INV", "SERVO_ROLL_INV", "SERVO_RELATIVE", "", "", "", "", ""});

        ExtraConfig = new u8Flags(index + " ExtraConfig", new String[]{"HEIGHT_LIMIT", "VARIO_BEEP", "SENSITIVE_RC", "3_3V_REFERENCE", "NO_RCOFF_BEEPING", "GPS_AID", "LEARNABLE_CAREFREE", "IGNORE_MAG_ERR_AT_STARTUP"});        // bitcodiert
        GlobalConfig3 = new u8Flags(index + " GlobalConfig3", new String[]{"NO_SDCARD_NO_START", "DPH_MAX_RADIUS", "VARIO_FAILSAFE", "MOTOR_SWITCH_MODE", "NO_GPSFIX_NO_START", "USE_NC_FOR_OUT1", "SPEAK_ALL", "SERVO_NICK_COMP_OFF"});        // bitcodiert

        Name = new c_string(index + " Name", 12, "Setting " + index);
        crc = new u8(index + " crc");

        switch (index) {
            case 1:
                ParamSet_DefaultSet1(); // Fill ParamSet Structure to default parameter set 1 (Sport)
                break;
            case 2:
                ParamSet_DefaultSet2(); // Kamera
                break;
            case 3:
                ParamSet_DefaultSet3(); // Beginner
                break;
            default:
                ParamSet_DefaultSet3(); // Beginner
                break;
        }

        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Revision);
        for (int i = 0; i < Kanalbelegung.length; i++) {
            u8 c = new u8(index + " Kanalbelegung " + i);
            Kanalbelegung[i] = c;
            if (c.value == 0) {
                c.setValue(i, false);
            }
            allAttribs.add(c);
        }
        allAttribs.add(GlobalConfig);
        allAttribs.add(Hoehe_MinGas);
        allAttribs.add(Luftdruck_D);
        allAttribs.add(HoeheChannel);
        allAttribs.add(Hoehe_P);
        allAttribs.add(Hoehe_Verstaerkung);
        allAttribs.add(Hoehe_ACC_Wirkung);
        allAttribs.add(Hoehe_HoverBand);
        allAttribs.add(Hoehe_GPS_Z);
        allAttribs.add(Hoehe_StickNeutralPoint);//
        allAttribs.add(Stick_P);
        allAttribs.add(Stick_D);
        allAttribs.add(StickGier_P);
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
        allAttribs.add(NaviOut1Parameter);
        allAttribs.add(NaviGpsModeChannel);
        allAttribs.add(NaviGpsGain);
        allAttribs.add(NaviGpsP);
        allAttribs.add(NaviGpsI);
        allAttribs.add(NaviGpsD);
        allAttribs.add(NaviGpsPLimit);
        allAttribs.add(NaviGpsILimit);
        allAttribs.add(NaviGpsDLimit);
        allAttribs.add(NaviGpsA);
        allAttribs.add(NaviGpsMinSat);
        allAttribs.add(NaviStickThreshold);
        allAttribs.add(NaviWindCorrection);
        allAttribs.add(NaviAccCompensation);
        allAttribs.add(NaviOperatingRadius);
        allAttribs.add(NaviAngleLimitation);
        allAttribs.add(NaviPH_LoginTime);

        allAttribs.add(ExternalControl);

        allAttribs.add(OrientationAngle);
        allAttribs.add(CareFreeChannel);
        allAttribs.add(MotorSafetySwitch);
        allAttribs.add(MotorSmooth);
        allAttribs.add(ComingHomeAltitude);
        allAttribs.add(FailSafeTime);
        allAttribs.add(MaxAltitude);
        allAttribs.add(FailsafeChannel);
        allAttribs.add(ServoFilterNick);
        allAttribs.add(ServoFilterRoll);
        allAttribs.add(StartLandChannel);
        allAttribs.add(LandingSpeed);

        allAttribs.add(CompassOffset);
        allAttribs.add(AutoLandingVoltage);
        allAttribs.add(ComingHomeVoltage);
        allAttribs.add(AutoPhotoAtitudes);
        allAttribs.add(SingleWpSpeed);

        allAttribs.add(BitConfig);
        allAttribs.add(ServoCompInvert);
        allAttribs.add(ExtraConfig);
        allAttribs.add(GlobalConfig3);
        /*for (int i = 0; i < Name.length; i++) {
         u8 c = new u8("Name " + i);
         Name[i] = c;
         allAttribs.add(c);
         }*/
        allAttribs.add(Name);
        allAttribs.add(crc);

    }
}
