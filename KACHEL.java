import gamewindow.*;

public class KACHEL
{ 
    private int xKoordinate;
    private int yKoordinate;
    private SENSOR Kachel;
    private BILD ausgewählteKachel;
    private String Zustand;
    private boolean ausgewählt;
    public boolean besucht=false;
    public KACHEL(int x, int y,String Typ, int Rotation)
    {
        xKoordinate=x;
        yKoordinate=y;
        Zustand=Typ;

        Kachel=new SENSOR(x,y,"Texturen/"+Zustand+".png",Rotation,1.0);
        Kachel.BildHinzufügen(x,y,"Texturen/"+Zustand+" ausgewählt.png",Rotation,1.0);
    }

    public KACHEL()
    {
        
    }
    
    public String LeseZustand()
    {
        return Zustand;
    }
    
    public int LeseX()
    {
        return xKoordinate;
    }
    
    public int LeseY()
    {
        return yKoordinate;
    }
    
    public void auswählen(boolean ausgewählt_)
    {
        if(ausgewählt_)
        {
            if(!ausgewählt)
            {
                Kachel.SetzeAktivesBild(1);
                ausgewählt = true;
            }
        }
        else
        {
            if(ausgewählt)
            {
                Kachel.SetzeAktivesBild(0);
                ausgewählt = false;
            }
        }
    }
}
