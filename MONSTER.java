import gamewindow.*;

public class MONSTER  
{
    private BILD Mon;
    private double xGeschwindigkeit, yGeschwindigkeit;
    private double Multiplikator=1;
    private int xPos, yPos;
    private int HP;
    
    public MONSTER(KACHEL Kachel,int HP_u)
    {
        Mon=new BILD("Texturen/Ziel.png");
        Mon.SetzePosition(Kachel.LeseX()+16,Kachel.LeseY()+16);
        xGeschwindigkeit=0;
        yGeschwindigkeit=0;
        HP= HP_u;
    }
    
    public int getHP()
    {
        return HP;
    }
    
    public int LeseX()
    {
        return Mon.LeseX();
    }

    public int LeseY()
    {
        return Mon.LeseY();
    }

    public void SetzeGeschwindigkeitsMultiplikator(int Multiplikator)
    {
        Multiplikator=Multiplikator;
    }

    public void Bewegen()
    {
        int KPX=Mon.LeseX()-272;
        int KPY=Mon.LeseY()-16;
        xPos=Mon.LeseX();
        yPos=Mon.LeseY();
        SPIELFELD.KachelGeben(0,0).besucht=true;
        if(KPX%64==0&&KPY%64==0){
            System.out.println("Bin in der IF");
            int xK=(KPX)/64;
            int yK=(KPY)/64;
            try
            {if(!SPIELFELD.KachelGeben(xK,yK+1).LeseZustand().contains("Frei")&&!SPIELFELD.KachelGeben(xK,yK+1).besucht){
                    yGeschwindigkeit=1*Multiplikator;
                    xGeschwindigkeit=0;
                    SPIELFELD.KachelGeben(xK,yK+1).besucht=true;
                }
            }
            catch(ArrayIndexOutOfBoundsException e)
            {  
            }
            try{
                if(!SPIELFELD.KachelGeben(xK,yK-1).LeseZustand().contains("Frei")&&!SPIELFELD.KachelGeben(xK,yK-1).besucht){
                    yGeschwindigkeit=-1*Multiplikator;
                    xGeschwindigkeit=0;
                    SPIELFELD.KachelGeben(xK,yK-1).besucht=true;
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
            try{
                if(!SPIELFELD.KachelGeben(xK+1,yK).LeseZustand().contains("Frei")&&!SPIELFELD.KachelGeben(xK+1,yK).besucht){
                    yGeschwindigkeit=0;
                    xGeschwindigkeit=1*Multiplikator;
                    SPIELFELD.KachelGeben(xK+1,yK).besucht=true;
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
            try{
                if(!SPIELFELD.KachelGeben(xK-1,yK).LeseZustand().contains("Frei")&&!SPIELFELD.KachelGeben(xK-1,yK).besucht){
                    yGeschwindigkeit=0;
                    xGeschwindigkeit=-1*Multiplikator;
                    SPIELFELD.KachelGeben(xK-1,yK).besucht=true;
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
            System.out.println(Mon.LeseX()+" "+Mon.LeseY());
        }
        Mon.SichtbarSetzen(true);
        Mon.SetzePosition((int)(xPos+xGeschwindigkeit), (int)(yPos+yGeschwindigkeit));
    }

    public void Tod(){
        Mon.SichtbarSetzen(false);
    }

    public void SetzePosition(int x, int y){
        Mon.SetzePosition(x,y);
    }

    public int LeseHP(){
        return HP;
    }

    public void SetzeHP(int newHP){
        HP=newHP;
    }
}