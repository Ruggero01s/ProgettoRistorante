import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI
{
    static ArrayList<Person> listP = new ArrayList<>();
    enum STATE{
        TITLE,
        CONFIG,


    }
    static STATE state = STATE.TITLE;
    //static ArrayList<JPanel> panels = new ArrayList<>();
    public static void panel()
    {
        JFrame frame = new JFrame("Restaurant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        JLabel titleText = new JLabel("Title");
        JLabel subTitleText = new JLabel("Subtitle");
        switch (state)
        {
            case TITLE:
                frame.getContentPane().removeAll();
                frame.add(BorderLayout.NORTH, titleText);
                frame.add(BorderLayout.SOUTH, subTitleText);
                break;
            case CONFIG:

                break;
        }

      // Person p = new Person("Mario", false , false ,true);
      // listP.add(p);
      // Writer.writeOutput("Test", listP);
        listP.addAll(Reader.readPeople("Test.xml"));
        System.out.println(listP.get(0).getName());
    }
}
