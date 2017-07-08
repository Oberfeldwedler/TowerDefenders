import java.util.ArrayList;

public class WELLENGENERIERUNG
{
    private ArrayList<MONSTER> PW;//PreviousWave
    private ArrayList<MONSTER> NW;//NextWave

    public WELLENGENERIERUNG()
    {
        PW=new ArrayList<MONSTER>();
        NW=new ArrayList<MONSTER>();

    }

    public void GenWave()
    {

        NW.add(new MONSTER(SPIELFELD.Kacheln[0][0],100));
        for (int i = 1;i<PW.size();i++)
        {
            if(PW.size()<2)
            {
                NW.add(new MONSTER(SPIELFELD.Kacheln[0][0],100));
                //NW.add(new MONSTER(SPIELFELD.Kacheln[0][0],100));
            }
            else
            {
                if(PW.get(i-1)==PW.get(i))
                {
                    NW.add(new MONSTER(SPIELFELD.Kacheln[0][0], PW.get(i-1).getHP()+100));
                }
            }
        }
    }

}
