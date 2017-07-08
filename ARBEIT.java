import gamewindow.*;

public class ARBEIT
{
    public ZÄHLER SpeedZähler;
    public ZÄHLER CoinZähler;
    public ZÄHLER MünzZähler;
    public ZÄHLER LebenZähler;
    public ZEITGEBER Zeitgeber;
    
    public ARBEIT()
    {
        SENSOR Userinterface = new SENSOR(0,0,"Texturen/MauserkennungOriginal.png",0,1);
        SpeedZähler=new ZÄHLER(345,100,100,20,"Hell","Geschwindigkeit",2);
        CoinZähler=new ZÄHLER(728,100,150,16,"Hell","$",-4);
        MünzZähler=new ZÄHLER(345,100,200,20,"Hell","Münze",-16);
        LebenZähler=new ZÄHLER(345,100,250,20,"Hell","Leben",-16);
        Zeitgeber = new ZEITGEBER(false, 150);
        Zeitgeber.AusgelösteAktionHinzufügen("pimmle", this);
        Zeitgeber.Starten();
    }
    
    public void pimmle()
    {
        SpeedZähler.update(10+(int)(Math.pow(Math.random(),1)*90));
        CoinZähler.update((int)(Math.pow(Math.random(),2)*10000));
        MünzZähler.update((int)(Math.pow(Math.random(),2)*10000));
        LebenZähler.update((int)(Math.pow(Math.random(),2)*101));
    }
}
