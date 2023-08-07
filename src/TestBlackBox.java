import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBlackBox
{

    private final SaveData saver;

    public TestBlackBox(SaveData saver) {
        this.saver = saver;
    }

    public void testingNames()
    {
        assertTrue(saver.saveDish("TEST","polpette","","",true,false));
        assertFalse(saver.saveDish("TEST","polpette","","",true,false)); //esiste già un piatto con lo stesso nome
        assertFalse(saver.saveDish("Polpette","polpette","","",true,false));    //nome usato già da una recipe
        assertFalse(saver.saveDish("Tonno e Polpette","polpette","","",true,false)); //tonno e polpette è il nome di un menù esistente
        assertFalse(saver.saveDish("","polpette","","",true,false)); //nome vuoto
    }

    public void testingDates() {
        assertFalse(saver.saveDish("prova1","polpette","-02/03/2021","04/05/2022",false,false)); //giorno sbagliato startdate
        assertFalse(saver.saveDish("prova1","polpette","02/03/2021","89/05/2022",false,false)); //giorno sbagliato enddate
        assertFalse(saver.saveDish("prova2","polpette","01/01/2026","01/01/2025",false,false)); //start before end
        assertFalse(saver.saveDish("prova3","polpette","01/01/2023","01/01/2025",true,true));// sia permanente che stagionale
        assertFalse(saver.saveDish("prova4","polpette","01/80/2020","01/01/2025",false,false)); //mese sbagliato startdate
        assertFalse(saver.saveDish("prova5","polpette","02/03/2021","02/36/2022",false,false)); //mese sbagliato enddate
        assertFalse(saver.saveDish("prova6","polpette","01/aa/2022","01/01/2025",false,false)); //formato errato stardate
        assertFalse(saver.saveDish("prova7","polpette","01/01/2022","01/01/test",false,false)); //formato errato stardate
        assertFalse(saver.saveDish("prova8","polpette","","01/01/2025",false,false)); //startdate vuota
        assertFalse(saver.saveDish("prova9","polpette","01/01/2025","",false,false)); //endate vuota
        assertTrue(saver.saveDish("prova10","polpette","10/10/2010","14/05/2012",false,false)); //caso giusto no perm no season
        assertTrue(saver.saveDish("prova11","polpette","10/10/2013","14/05/2012",false,true)); //caso season start before end, dovrebbe ignorare gli anni
        assertTrue(saver.saveDish("prova12","polpette","10/10/2011","14/05/2013",false,true)); //caso season giusto
        assertTrue(saver.saveDish("prova13","polpette","aaaaaaa","bbbbbbbbb",true,false)); //caso perm dovrebbe ignorare le date
    }

    public void testingRecipes() {
        assertFalse(saver.saveDish("prova1","recipeInesistente","10/11/2025","10/01/2026",false,true)); //ricetta inesistente
        assertFalse(saver.saveDish("prova2","","10/11/2025","10/01/2026",false,true)); //ricetta vuota
        assertTrue(saver.saveDish("prova3","polpette","10/11/2025","10/01/2026",false,true)); //ricetta giusta
    }

}
