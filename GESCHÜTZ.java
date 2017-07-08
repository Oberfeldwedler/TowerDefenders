
public class GESCHÜTZ extends TURM
{
    public String Typ;
    public int xPosition;
    public int yPosition;
    public SENSOR Geschütz;

    public GESCHÜTZ(int X, int Y)
    {
        Typ = "Geschütz";
        xPosition = X;
        yPosition = Y;
        Geschütz = new SENSOR(xPosition,yPosition,"GeschützVorschau.png", 0, 1);
    }

    public void weiter()
    {
        
    }
    
    public void LeseLevel()
    {
        
    }

    public int LeseX()
    {
        return xPosition;
    }

    public int LeseY()
    {
        return yPosition;
    }
    
    public String LeseTyp()
    {
        return Typ;
    }
}
