import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class SPIELFELD
{
    public static KACHEL[][] Kacheln;
    private int VerschiebungX;
    private int VerschiebungY;
    private String SpielfeldName;
    private ArrayList<TURM> Türme;

    public SPIELFELD(int XVerschiebung, int YVerschiebung, String NameSpielfeld)
    {
        Kacheln = new KACHEL[8][8];
        VerschiebungX = XVerschiebung;
        VerschiebungY = YVerschiebung;
        SpielfeldName = NameSpielfeld;
        Laden();
        Türme = new ArrayList<TURM>();
    }

    public KACHEL[][] KachelnGeben()
    {
        return Kacheln;
    }

    public static KACHEL KachelGeben(int x, int y){
        return Kacheln[x][y];
    } 
    
    public void TurmHinzufügen(TURM neuerTurm)
    {
        Türme.add(neuerTurm);
    }

    public String LeseTurm(int X, int Y)
    {
        String Ergebnis = "null";
        TURM HilfsTurm = null;
        for(int i = 0; i<Türme.size(); i++)
        {
            HilfsTurm = Türme.get(i);
            if(HilfsTurm.LeseX()==X && HilfsTurm.LeseY()==Y)
            {
                Ergebnis = HilfsTurm.LeseTyp();
            }
        }
        return Ergebnis;
    }

    public void Laden()
    {
        try{
            String Pfad = new File(SpielfeldName).getAbsolutePath();
            FileReader Datei = new FileReader(Pfad);
            BufferedReader Leser = new BufferedReader(Datei);
            String Zeile = Leser.readLine();
            String[] Puffer = new String[8]; //ArrayList<String> Puffer = new ArrayList<String>();
            for(int a = 0; a < 8; a++)
            {
                if(Zeile.length() > 0 && Zeile != null) //wenn die moentane Zeile nicht leer ist und existiert
                {
                    Puffer[a] = Zeile; //"\n"
                }
                Zeile = Leser.readLine();
            }
            String[][] Temp = new String[10][10];

            for(int i = 0; i < 8; i++) //jetzt geht's ans auslesen
            {
                String[] Teile = Puffer[i].split(",");
                for(int j = 0; j < 8; j++)
                {
                    Temp[j+1][i+1] = Teile[j];
                    if(j == 0)
                    {
                        Temp[j][i+1] = "0";
                    }
                    if(j == 7)
                    {
                        Temp[j+2][i+1] = "0";
                    }
                    if(i == 0)
                    {
                        Temp[j+1][i] = "0";
                    }
                    if(i == 7)
                    {
                        Temp[j+1][i+2] = "0";
                    }
                }
            }

            for(int i = 0; i < 8; i++) //Für die Grafik
            {
                for(int j = 0; j < 8; j++)
                {
                    if(Temp[i+1][j+1].equals("0"))
                    {
                        Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Frei", 0);
                    }
                    else
                    {
                        if(Temp[i+1][j+1].equals("1"))
                        {
                            if(Temp[i][j+1].equals("0") != true && Temp[i+2][j+1].equals("0") != true)
                            {
                                Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Straße Längs", 90);
                            }
                            else
                            {
                                if(Temp[i+1][j].equals("0") != true && Temp[i+1][j+2].equals("0") != true)
                                {
                                    Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Straße Längs", 0);
                                }
                                else
                                {
                                    if(Temp[i+1][j].equals("0") != true && Temp[i][j+1].equals("0") != true)
                                    {
                                        Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Straße Ecke", 90);
                                    }
                                    else
                                    {
                                        if(Temp[i+1][j+2].equals("0") != true && Temp[i+2][j+1].equals("0") != true)
                                        {
                                            Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Straße Ecke", 270);
                                        }
                                        else
                                        {
                                            if(Temp[i+1][j+2].equals("0") != true && Temp[i][j+1].equals("0") != true)
                                            {
                                                Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Straße Ecke", 180);
                                            }
                                            else
                                            {
                                                if(Temp[i+1][j].equals("0") != true && Temp[i+2][j+1].equals("0") != true)
                                                {
                                                    Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Kachel Straße Ecke", 0);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Temp[i+1][j+1].equals("2"))
                            {
                                if(Temp[i][j+1].equals("1"))
                                {
                                    Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Spieler Links", 0);
                                }
                                else
                                {
                                    if(Temp[i+2][j+1].equals("1"))
                                    {
                                        Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Spieler Rechts", 0);
                                    }
                                    else
                                    {
                                        if(Temp[i+1][j].equals("1"))
                                        {
                                            Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Spieler Oben", 0);
                                        }
                                        else
                                        {
                                            if(Temp[i+1][j+2].equals("1"))
                                            {
                                                Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Spieler Unten", 0);
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if(Temp[i+1][j+1].equals("3"))
                                {
                                    if(Temp[i][j+1].equals("1"))
                                    {
                                        Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Gegner Links", 0);
                                    }
                                    else
                                    {
                                        if(Temp[i+2][j+1].equals("1"))
                                        {
                                            Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Gegner Rechts", 0);
                                        }
                                        else
                                        {
                                            if(Temp[i+1][j].equals("1"))
                                            {
                                                Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Gegner Oben", 0);
                                            }
                                            else
                                            {
                                                if(Temp[i+1][j+2].equals("1"))
                                                {
                                                    Kacheln[i][j] = new KACHEL(64*i+VerschiebungX,64*j+VerschiebungY,"Basis Gegner Unten", 0);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Vermutlich existiert die Datei " + SpielfeldName + " nicht...");
        }
    }
}
