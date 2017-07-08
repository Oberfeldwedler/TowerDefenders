import gamewindow.*;
import java.util.ArrayList;

public class SENSOR
{
    private ArrayList<BILD> Bilder;
    private int AktivesBild;
    private int Höhe;
    private int Breite;
    protected int UrX;
    protected int UrY;

    public SENSOR()
    {
        Bilder = new ArrayList<BILD>();
    }

    public SENSOR(int PositionX, int PositionY, String Name, double Rotation, double Skalierung)
    {
        UrX = PositionX;
        UrY = PositionY;
        Bilder = new ArrayList<BILD>();
        Bilder.add(new BILD(Name));
        Höhe = Bilder.get(0).LeseHoehe();
        Breite = Bilder.get(0).LeseBreite();
        Bilder.get(0).SetzeRotation(Rotation);
        Bilder.get(0).Skalieren(Skalierung);
        Bilder.get(0).SetzePosition(PositionX-(int)((Skalierung-1)*(Bilder.get(0).LeseBreite()/2)), PositionY-(int)((Skalierung-1)*(Bilder.get(0).LeseHoehe()/2)));
        AktivesBild = 0;
    }

    public void BildHinzufügen(int PositionX, int PositionY, String Name, double Rotation, double Skalierung)
    {
        Bilder.add(new BILD(Name));
        Bilder.get(Bilder.size()-1).SetzeRotation(Rotation);
        Bilder.get(Bilder.size()-1).Skalieren(Skalierung);
        Bilder.get(Bilder.size()-1).SetzePosition(PositionX-(int)((Skalierung-1)*(Bilder.get(Bilder.size()-1).LeseBreite()/2)), PositionY-(int)((Skalierung-1)*(Bilder.get(Bilder.size()-1).LeseHoehe()/2)));
        SetzeAktivesBild(AktivesBild);
    }

    public void BildHinzufügen(String Name, int Index)
    {
        Bilder.add(new BILD(Name));
        SetzeEinzelnRotation(Bilder.get(Index).LeseRotation(), Bilder.size()-1);
        EinzelnSkalieren(Bilder.get(Index).LeseSkalierung(), Bilder.size()-1);
        SetzeAktivesBild(AktivesBild);
    }

    public void SetzeUrsprünglichePosition()
    {
        SetzePosition(UrX, UrY);
    }

    public void SetzePosition(int PositionX, int PositionY)
    {
        for(int i = 0; i<Bilder.size(); i++)
        {
            Bilder.get(i).SetzePosition(PositionX-(int)((Bilder.get(i).LeseSkalierung()-1)*(Bilder.get(i).LeseBreite()/2)), PositionY-(int)((Bilder.get(i).LeseSkalierung()-1)*(Bilder.get(i).LeseHoehe()/2)));
        }
    }

    public void SetzeRotation(double Rotation)
    {
        for(int i = 0; i<Bilder.size(); i++)
        {
            Bilder.get(i).SetzeRotation(Rotation);
        }
    }
    
    public void SetzeEinzelnRotation(double Rotation, int Index)
    {
        Bilder.get(Index).SetzeRotation(Rotation);
    }

    public void Skalieren(double Skalierung)
    {
        for(int i = 0; i<Bilder.size(); i++)
        {
            double VorherigeSkalierung = Bilder.get(i).LeseSkalierung();
            int XVerschiebung = (int)((VorherigeSkalierung-1)*(Breite/2)-(Skalierung-1)*(Breite/2));
            int YVerschiebung = (int)((VorherigeSkalierung-1)*(Höhe/2)-(Skalierung-1)*(Höhe/2));
            Bilder.get(i).Skalieren(Skalierung);
            Bilder.get(i).SetzePosition(Bilder.get(i).LeseX()+XVerschiebung, Bilder.get(i).LeseY()+YVerschiebung);
        }
    }

    public void EinzelnSkalieren(double Skalierung, int Index)
    {
        double VorherigeSkalierung = Bilder.get(Index).LeseSkalierung();
        int XVerschiebung = (int)((VorherigeSkalierung-1)*(Breite/2)-(Skalierung-1)*(Breite/2));
        int YVerschiebung = (int)((VorherigeSkalierung-1)*(Höhe/2)-(Skalierung-1)*(Höhe/2));
        Bilder.get(Index).Skalieren(Skalierung);
        Bilder.get(Index).SetzePosition(Bilder.get(Index).LeseX()+XVerschiebung, Bilder.get(Index).LeseY()+YVerschiebung);
    }

    public void NormalSkalieren(double Skalierung)
    {
        for(int i = 0; i<Bilder.size(); i++)
        {
            Bilder.get(i).Skalieren(Skalierung);
        }
    }

    public int LeseX(int Index)
    {
        return Bilder.get(Index).LeseX();
    }

    public int LeseY(int Index)
    {
        return Bilder.get(Index).LeseX();
    }

    public int LeseBreite(int Index)
    {
        return Bilder.get(Index).LeseBreite();
    }

    public int LeseHoehe(int Index)
    {
        return Bilder.get(Index).LeseHoehe();
    }
    
    public int LeseUrsprünglicheBreite() //
    {
        return Breite;
    }

    public int LeseUrsprünglicheHoehe() //
    {
        return Höhe;
    }

    public GAMEWINDOW GamewindowGeben(int Index) //
    {
        return Bilder.get(Index).GamewindowGeben();
    }

    public boolean Kollision(int x, int y, int Index)
    {
        int xDifferenz = x-Bilder.get(Index).LeseX();
        int yDifferenz = y-Bilder.get(Index).LeseY();
        if(xDifferenz>=0 && xDifferenz<LeseBreite(Index)&& yDifferenz>=0 && yDifferenz<LeseHoehe(Index))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int[] KollisionsKoordinaten(int x, int y, int Index)
    {
        return new int[] {x-Bilder.get(Index).LeseX(), y-Bilder.get(Index).LeseY()};
    }

    public void SetzeAktivesBild(int Index)
    {
        Bilder.get(Index).SichtbarSetzen(true);
        AktivesBild = Index;
        for(int i = 0; i<Bilder.size(); i++)
        {
            if(i != Index)
            {
                Bilder.get(i).SichtbarSetzen(false);
            }
        }
    }

    public void SetzeUnsichtbar()
    {
        Bilder.get(AktivesBild).SichtbarSetzen(false);
    }
}
