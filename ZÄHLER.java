import java.util.ArrayList;

public class ZÄHLER
{
    public int MomentaneZahl;
    public int Einer;
    public int Zehner;
    public int Hunderter;
    public int Tausender;
    public int Ziffernabstand;
    public int VorZeichenVerschiebung;
    public double Skalierung;
    
    public int XPosition;
    public int YPosition;
    public SENSOR VorZeichen;
    SENSOR[] Ziffern;

    public ZÄHLER(int neuZahl, int neuXPosition, int neuYPosition, int Abstand, String Ordner, String Vorzeichen, int VorzeichenVerschiebung)
    {
        Ziffernabstand = Abstand;
        Skalierung = 1.0;
        MomentaneZahl = neuZahl;
        Ziffern = new SENSOR[4];
        for(int i = 0; i < 4; i++)
        {
            Ziffern[i] = new SENSOR(0, 0, "Texturen/"+Ordner+"/"+0+".png", 0, 1);
            for(int j = 1; j < 10;j++)
            {
                Ziffern[i].BildHinzufügen(0, 0, "Texturen/"+Ordner+"/"+j+".png", 0, 1);
            }
        }
        VorZeichen = new SENSOR(0,0,"Texturen/"+Vorzeichen+".png",0,1);
        VorZeichenVerschiebung = VorzeichenVerschiebung;
        XPosition = neuXPosition;
        YPosition = neuYPosition;
        BerechneZiffern();
    }

    public void update(int NeuZahl)
    {
        MomentaneZahl = NeuZahl;
        BerechneZiffern();
    }

    private void BerechneZiffern()
    {
        int Zahl = MomentaneZahl;
        if(Zahl>9999)
        {
            Zahl = 9999;
        }
        if(Zahl>999)
        {
            Tausender = Zahl/1000;
            Zahl-=Tausender*1000;
        }
        else{Tausender=0;}
        if(Zahl>99)
        {
            Hunderter = Zahl/100;
            Zahl-=Hunderter*100;
        }
        else{Hunderter=0;}
        if(Zahl>9)
        {
            Zehner = Zahl/10;
            Zahl-=Zehner*10;
        }
        else{Zehner=0;}
        Einer = Zahl;
        //System.out.println("Tausender: "+Tausender+", Hunderter: "+Hunderter+", Zehner: "+Zehner+", Einer: "+ Einer);
        int Offset = 0;
        if(Tausender>0) {
            Offset=3;
        }
        else
        {
            if(Hunderter>0) {
                Offset=2;
            }
            else
            {
                if(Zehner>0) {
                    Offset=1;
                }
            }
        }

        Ziffern[0].SetzePosition(XPosition-(int)(Ziffernabstand*0.5)+(int)(Offset*Ziffernabstand*0.5), YPosition-Ziffern[0].LeseHoehe(Einer));
        Ziffern[0].SetzeAktivesBild(Einer);
        if(Offset>0)
        {
            Ziffern[1].SetzePosition(XPosition-(int)(Ziffernabstand*1.5)+(int)(Offset*Ziffernabstand*0.5), YPosition-Ziffern[1].LeseHoehe(Zehner));
            Ziffern[1].SetzeAktivesBild(Zehner);
        }
        else {Ziffern[1].SetzeUnsichtbar();}
        if(Offset>1)
        {
            Ziffern[2].SetzePosition(XPosition-(int)(Ziffernabstand*2.5)+(int)(Offset*Ziffernabstand*0.5), YPosition-Ziffern[2].LeseHoehe(Hunderter));
            Ziffern[2].SetzeAktivesBild(Hunderter);
        }
        else {Ziffern[2].SetzeUnsichtbar();}
        if(Offset>2)
        {
            Ziffern[3].SetzePosition(XPosition-(int)(Ziffernabstand*3.5)+(int)(Offset*Ziffernabstand*0.5), YPosition-Ziffern[3].LeseHoehe(Tausender));
            Ziffern[3].SetzeAktivesBild(Tausender);
        }
        else {
            Ziffern[3].SetzeUnsichtbar();
        }
        VorZeichen.SetzePosition(XPosition-(int)(Ziffernabstand*1.5)-(int)(Offset*Ziffernabstand*0.5)+VorZeichenVerschiebung, YPosition-VorZeichen.LeseHoehe(0)); //28 anstatt 24 weil's besser aussieht
        //Skalieren(Skalierung);
    }
    /* FUNZT NED...
    public void Skalieren(double Größe)
    {
        Skalierung = Größe;
        SensorSkalierung(VorZeichen, Größe);
        SensorSkalierung(Ziffern[0], Größe);
        SensorSkalierung(Ziffern[1], Größe);
        SensorSkalierung(Ziffern[2], Größe);
        SensorSkalierung(Ziffern[3], Größe);
    }
    
    private void SensorSkalierung(SENSOR Sensor, double Faktor)
    {
        int XDifferenz = XPosition-(Sensor.LeseX(0)+Sensor.LeseBreite(0)/2);
        int YDifferenz = YPosition-(Sensor.LeseY(0)+Sensor.LeseHoehe(0)/2);
        XDifferenz=(int)(XDifferenz*Faktor);
        YDifferenz=(int)(YDifferenz*Faktor);
        Sensor.SetzePosition(XPosition-XDifferenz, YPosition-YDifferenz);
        Sensor.NormalSkalieren(Faktor);
    }
    */
    public void setzeUnsichtbar(boolean Unsichtbar)
    {
        if(Unsichtbar)
        {
            Ziffern[0].SetzeUnsichtbar();
            Ziffern[1].SetzeUnsichtbar();
            Ziffern[2].SetzeUnsichtbar();
            Ziffern[3].SetzeUnsichtbar();
        }
        else
        {
            BerechneZiffern();
        }
    }
}
