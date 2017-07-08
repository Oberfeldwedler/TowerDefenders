import gamewindow.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import java.awt.MouseInfo;
import javax.swing.JFrame;
import javax.swing.Timer;

public class BENUTZEROBERFLÄCHE
{
    private SPIELFELD Spielfeld;
    private int VerschiebungX; //Das Spielfeld ist nicht direkt in der linken Oberen Ecke

    private ZEITGEBER Zeitgeber;

    private MONSTER M;
    
    private SENSOR Mauserkennung; //Das graphische, das nicht das Spielfeld ist
    private boolean MausKlick; //wenn die Maustaste von gedrückt auf nicht gedrückt gewechselt hat
    private boolean LinkeMaustaste; //wenn die Maustaste gedrückt ist
    private int MausPositionX; //die Position der Maustaste
    private int MausPositionY;
    private SENSOR MausZeiger; //die Grafik, die die Maus repräsentiert
    private SENSOR SpielPause;
    private SENSOR AnzeigeFreiesFeld;
    private SENSOR AnzeigeBebautesFeld;
    private SENSOR AnzeigeBasen;
    private SENSOR GeschützVorschau;
    private SENSOR SniperVorschau;
    private SENSOR MörserVorschau;
    private SENSOR RaketenwerferVorschau;
    private SENSOR TurmSchrift;
    private String ZuKaufenderTurm;
    private ZÄHLER Guthaben;
    private ZÄHLER Leben;
    private SENSOR KaufenSensor;
    //private SENSOR KaufenAnzeige;
    private ZÄHLER KaufenZähler;

    private KACHEL Kachel; //Kachel als Hilfe für die graphische Darstellung der Auswahl der Kacheln
    private boolean ausgewählt; //SOLLTE NOCH VERBESSERT(GELÖSCHT) WERDEN

    public BENUTZEROBERFLÄCHE()
    {
        VerschiebungX = 256; //das Spielfeld ist um 256 nach rechts verschoben
        Spielfeld = new SPIELFELD(VerschiebungX, 0, "Kacheln.txt");
        Kachel = new KACHEL();
        M=new MONSTER(SPIELFELD.Kacheln[0][0],100);
        Mauserkennung = new SENSOR(0,0,"Texturen/MauserkennungOriginal.png",0,1);
        Mauserkennung.GamewindowGeben(0).addMouseListener(new MouseListener ()
            {
                @Override public void mouseClicked (MouseEvent e) {MausKlick = true;}

                @Override public void mouseEntered (MouseEvent e) { }

                @Override public void mouseExited (MouseEvent e) { }

                @Override public void mousePressed (MouseEvent e) {LinkeMaustaste = true;}

                @Override public void mouseReleased (MouseEvent e) {LinkeMaustaste = false;}
            });
        JFrame frame = Mauserkennung.GamewindowGeben(0).JFrameGeben();

        GeschützVorschau = new SENSOR(20, 200, "Texturen/GeschützVorschau.png", 0, 1);
        SniperVorschau = new SENSOR(130, 200, "Texturen/SniperVorschau.png", 0, 1);
        MörserVorschau = new SENSOR(20, 310, "Texturen/MörserVorschau.png", 0, 1);
        RaketenwerferVorschau = new SENSOR(130, 310, "Texturen/RaketenwerferVorschau.png", 0, 1);
        SpielPause = new SENSOR(25, 440, "Texturen/Spiel.png", 0, 1);
        SpielPause.BildHinzufügen("Texturen/Pause.png", 0);
        int Money = 0;
        int Live = 8100;
        Guthaben = new ZÄHLER(Money,100,50,16,"Hell","Münze",-19);
        Leben = new ZÄHLER(Live,200,50,16,"Hell","Leben",-16);
        TurmSchrift = new SENSOR(65,65,"Texturen/GeschützSchrift.png",0,1);
        TurmSchrift.BildHinzufügen("Texturen/SniperSchrift.png", 0);
        TurmSchrift.BildHinzufügen("Texturen/MörserSchrift.png", 0);
        TurmSchrift.BildHinzufügen("Texturen/RaketenwerferSchrift.png", 0);
        TurmSchrift.SetzeUnsichtbar();
        ZuKaufenderTurm = "null";
        KaufenSensor = new SENSOR(65,125,"Texturen/Kaufen.png",0,1);
        //KaufenAnzeige = 
        KaufenZähler = new ZÄHLER(0,128,157,16,"Hell","$",-4);
        MausZeiger = new SENSOR(0,0,"Texturen/Mauszeiger.png",0,0);
        ausgewählt = false;

        Zeitgeber = new ZEITGEBER(false, 16);
        Zeitgeber.AusgelösteAktionHinzufügen("Mainloop", this);
        Zeitgeber.Starten();
    }

    public void Mainloop()
    {
         M.Bewegen();
        Point a = MouseInfo.getPointerInfo().getLocation();
        Point b = Mauserkennung.GamewindowGeben(0).getLocationOnScreen();
        MausPositionX = (int) (a.getX() - b.getX());
        MausPositionY = (int) (a.getY() - b.getY());
        if(MausPositionX<0){MausPositionX=0;}
        if(MausPositionY<0){MausPositionY=0;}
        if(MausPositionX>767){MausPositionX=767;}
        if(MausPositionY>511){MausPositionY=511;}

        if(MausPositionX>=VerschiebungX)
        {
            MausPositionX/=64; //man müsste die Verschiebung noch abziehen und danach hinzufügen, wenn sie nicht ein Vielfaches von 64 beträge
            MausPositionY/=64;
            MausPositionX*=64;
            MausPositionY*=64;
            MausZeiger.SetzeAktivesBild(0);
            if(LinkeMaustaste)
            {
                MausZeiger.Skalieren(1.0);
                MausZeiger.SetzePosition(MausPositionX, MausPositionY);
            }
            else
            {
                double Skalierungsfaktor = 1.1;
                MausZeiger.Skalieren(1.1);
                MausZeiger.SetzePosition(MausPositionX, MausPositionY);
            }
            if(MausKlick)
            {
                Kachel.auswählen(false);
                KACHEL hilfsKachel = Kachel;
                Kachel = Spielfeld.KachelnGeben()[(MausPositionX-VerschiebungX)/64][MausPositionY/64];
                if(Kachel!=hilfsKachel)
                {
                    Kachel.auswählen(true);
                    ausgewählt = true;
                }
                else
                {
                    Kachel = new KACHEL();
                    ausgewählt = false;
                    System.out.println("Entwählt");
                }
            }
        }
        else
        {
            AlleSensorenSkalieren();  //ACHTUNG!!! NIE DARF EIN SENSOR ÜBER DEN KACHELN LIEGEN!!!
            MausZeiger.SetzeUnsichtbar();
            MausZeiger.SetzePosition(MausPositionX - MausZeiger.LeseBreite(0)/2, MausPositionY - MausZeiger.LeseHoehe(0)/2);

        }
        if(ausgewählt)
        {
            if(Kachel.LeseZustand().equals("Kachel Frei"))
            {
                String Turm = Spielfeld.LeseTurm(Kachel.LeseX(), Kachel.LeseY());
                if(Turm.equals("null"))
                {
                    AusgewähltFreiesFeld();
                }
                else
                {
                    AusgewähltBebautesFeld(Turm);
                }
            }
            else if(Kachel.LeseZustand().equals("Kachel Straße Längs")||Kachel.LeseZustand().equals("Kachel Straße Ecke"))
            {
            }
            else if(Kachel.LeseZustand().equals("Basis Gegner Oben")||Kachel.LeseZustand().equals("Basis Gegner Links")||Kachel.LeseZustand().equals("Basis Gegner Unten")||Kachel.LeseZustand().equals("Basis Gegner Rechts"))
            {
            }
            else if(Kachel.LeseZustand().equals("Basis Spieler Oben")||Kachel.LeseZustand().equals("Basis Spieler Links")||Kachel.LeseZustand().equals("Basis Spieler Unten")||Kachel.LeseZustand().equals("Basis Spieler Rechts"))
            {
            }
        }
        else
        {
            System.out.println("Nix Ausgewählt");
        }
        MausKlick = false; //Muss sein, da es sonst true bleiben würde
    }

    public void AusgewähltStraße()
    {
        //-alles unsichtbar setzen-
        //-alle Buttons durchgehen-
    }

    public void AusgewähltFreiesFeld()
    {
        if(ZuKaufenderTurm == "null")
        {
            KaufenSensor.SetzeUnsichtbar();
        }
        else
        {
            KaufenSensor.SetzeAktivesBild(0);
        }
        if(MausKlick)
        {
            if(KaufenSensor.Kollision(MausPositionX, MausPositionY, 0))
            {
                if(ZuKaufenderTurm.equals("Geschütz")){new GESCHÜTZ(Kachel.LeseX(),Kachel.LeseY());}
                
                KaufenSensor.Skalieren(0.9);
                System.out.println("Turm "+ ZuKaufenderTurm + " wurde gebaut.");
            }
            if(GeschützVorschau.Kollision(MausPositionX, MausPositionY, 0))
            {
                ZuKaufenderTurm = "Geschütz";
            }
            else if(SniperVorschau.Kollision(MausPositionX, MausPositionY, 0))
            {
                ZuKaufenderTurm = "Sniper";
            }
            else if(MörserVorschau.Kollision(MausPositionX, MausPositionY, 0))
            {
                ZuKaufenderTurm = "Mörser";
            }
            else if(RaketenwerferVorschau.Kollision(MausPositionX, MausPositionY, 0))
            {
                ZuKaufenderTurm = "Raketenwerfer";
            }
            else
            {
                ZuKaufenderTurm = "null";
            }
        }
    }

    public void AusgewähltBebautesFeld(String Typ)
    {

        if(Typ.equals("Geschütz"))
        {
            System.out.println("Die ausgewählte Kachel ist Geschützt. HaHa.");
        }
        else if(Typ.equals("Sniper"))
        {

        }
        else if(Typ.equals("Mörser"))
        {

        }
        else if(Typ.equals("Raketenwerfer"))
        {

        }
        else
        {
            System.out.println("Das ausgewählte Geschütz gibt's nicht...");
        }
    }

    public void AusgewähltBasisSpieler()
    {

    }

    public void AusgewähltBasisGegner()
    {

    }

    public void SetzeAllesInaktivAußer(String UITyp)
    {
        if(!UITyp.equals("Straße"))
        {

        }
        if(!UITyp.equals("Freies Feld"))
        {

        }
        if(!UITyp.equals("Bebautes Feld"))
        {

        }
        if(!UITyp.equals("Basis"))
        {

        }
    }

    public void AlleSensorenSkalieren()
    {
        double Scale = 1.05; //bei 100px
        if(GeschützVorschau.Kollision(MausPositionX, MausPositionY, 0)){GeschützVorschau.Skalieren(Scale);}
        else{GeschützVorschau.SetzeUrsprünglichePosition();
            GeschützVorschau.Skalieren(1.0);}
        if(SniperVorschau.Kollision(MausPositionX, MausPositionY, 0)){SniperVorschau.Skalieren(Scale);}
        else{SniperVorschau.SetzeUrsprünglichePosition();
            SniperVorschau.Skalieren(1.0);}
        if(MörserVorschau.Kollision(MausPositionX, MausPositionY, 0)){MörserVorschau.Skalieren(Scale);}
        else{MörserVorschau.SetzeUrsprünglichePosition();
            MörserVorschau.Skalieren(1.0);}
        if(RaketenwerferVorschau.Kollision(MausPositionX, MausPositionY, 0)){RaketenwerferVorschau.Skalieren(Scale);}
        else{RaketenwerferVorschau.SetzeUrsprünglichePosition();
            RaketenwerferVorschau.Skalieren(1.0);}
        if(SpielPause.Kollision(MausPositionX, MausPositionY, 0)){
            SpielPause.Skalieren(1.1);}
        else{SpielPause.SetzeUrsprünglichePosition();
            SpielPause.Skalieren(1.0);}
        if(KaufenSensor.Kollision(MausPositionX, MausPositionY, 0)){KaufenSensor.Skalieren(Scale);}
        else{KaufenSensor.SetzeUrsprünglichePosition();
            System.out.println("X: "+KaufenSensor.LeseX(0)+", Y: "+KaufenSensor.LeseY(0));
            KaufenSensor.Skalieren(1.0);}
    }
}
