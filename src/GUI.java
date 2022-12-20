import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI
{
    Controller ctrl;
    public GUI(Controller ctrl)
    {
        this.ctrl = ctrl;
    }
    ArrayList<Person> listP = new ArrayList<>();
    enum STATE{
        INIT,
        TITLE,
        LOGIN,
        CONFIG_MENU,
        CONFIG_BASE,
        CONFIG_DRINKS,
        CONFIG_EXTRAFOODS
    }
    STATE stateOld = STATE.INIT;
    JFrame frame = new JFrame("Restaurant");
    //------------------------------------------------------------------------------------------
    //TITLE
    JLabel titleText = new JLabel("Title");
    JLabel titleSubText = new JLabel("Subtitle");
    JButton titleLogButton = new JButton("Login");
    //------------------------------------------------------------------------------------------
    //LOGIN
    JLabel logText = new JLabel("Che ruolo hai?");
    JButton logManagerButton = new JButton("Manager");
//------------------------------------------------------------------------------------------
    //CONFIG
    JButton cfgBackButton = new JButton("Back");

    //CONFIG_MENU
    JLabel cfgMenuText = new JLabel("Cosa vuoi configurare?");
    JButton cfgMenuBaseButton = new JButton("Specifiche ristorante");
    JButton cfgMenuDrinksButton = new JButton("Bevande");
    JButton cfgMenuExtraFoodsButton = new JButton("Generi Extra");
    JButton cfgMenuBackToLogButton = new JButton("Back to Login");
    JButton cfgWriteButton = new JButton("Salva ed esci");

    //------------------------------------------------------------------------------------------
    //CONFIG_BASE
    JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    JLabel cfgBaseIndividualWorkloadText = new JLabel("Carico lavoro max:");
    JTextArea cfgBaseCapArea = new JTextArea();
    JTextArea cfgBaseIndivualWorkloadArea = new JTextArea();
    JButton cfgBaseSendButton = new JButton("Conferma");
//------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
    JLabel cfgDrinksText = new JLabel("Inserisci dati bevanda: (nome|quantità)");
    JTextArea cfgDrinksNameArea = new JTextArea();
    JTextArea cfgDrinksQuantityArea = new JTextArea();
    JButton cfgDrinksSendButton = new JButton("Inserisci");
//------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
    JLabel cfgFoodText = new JLabel("Inserisci dati generi alimentari extra: (nome|quantità)");
    JTextArea cfgFoodNameArea = new JTextArea();
    JTextArea cfgFoodQuantityArea = new JTextArea();
    JButton cfgFoodSendButton = new JButton("Inserisci");
//------------------------------------------------------------------------------------------




    public void init()
    {
    //frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.GRAY); //TODO
        frame.setLayout(new BorderLayout());
//------------------------------------------------------------------------------------------
    //TITLE
        //labels TITLE
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleText.setVerticalAlignment(SwingConstants.CENTER);

        titleSubText.setHorizontalAlignment(SwingConstants.CENTER);
        titleSubText.setVerticalAlignment(SwingConstants.TOP); //?
        titleSubText.setVerticalTextPosition(SwingConstants.TOP); //?

        //buttons TITLE
        titleLogButton.addActionListener(e -> stateChange(STATE.LOGIN));
//------------------------------------------------------------------------------------------
    //LOGIN
        //labels LOGIN
            //TODO set alignment

        //buttons LOGIN
        logManagerButton.addActionListener(e -> stateChange(STATE.CONFIG_MENU));

    /*  JButton logEmployeeButton = new JButton("Employee");      //TODO
        logManagerButton.addActionListener(e -> state=STATE.CONFIG);
        JButton logWarehouseButton = new JButton("Storage Worker");
        logManagerButton.addActionListener(e -> state=STATE.CONFIG);  */
//------------------------------------------------------------------------------------------
    //CONFIG
        cfgBackButton.addActionListener(e -> stateChange(STATE.CONFIG_MENU));
    //CONFIG_MENU
        cfgMenuBaseButton.addActionListener(e -> stateChange(STATE.CONFIG_BASE));
        cfgMenuDrinksButton.addActionListener(e -> stateChange(STATE.CONFIG_DRINKS));
        cfgMenuExtraFoodsButton.addActionListener(e -> stateChange(STATE.CONFIG_EXTRAFOODS));
        cfgMenuBackToLogButton.addActionListener(e -> stateChange(STATE.LOGIN));
        cfgWriteButton.addActionListener(e -> ctrl.writeAll());
//------------------------------------------------------------------------------------------
    //CONFIG_BASE
        //labels CONFIG_BASE
            //TODO setAlignment
        //Text Area CONFIG_BASE
        cfgBaseCapArea.setLineWrap(true);
        cfgBaseIndivualWorkloadArea.setLineWrap(true);

        //buttons CONFIG_BASE
        cfgBaseSendButton.addActionListener(e -> ctrl.saveConfig());
//------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
        //labels CONFIG_DRINKS
            //TODO alignments
        //text area CONFIG_DRINKS
        cfgDrinksNameArea.setLineWrap(true);
        cfgDrinksQuantityArea.setLineWrap(true);
        //buttons CONFIG_DRINKS
        cfgDrinksSendButton.addActionListener(e -> ctrl.saveDrinks()); //todo addDrink? vedere se cambiare nome
//------------------------------------------------------------------------------------------
    //CONFIG_EXTRAFOODS
        //labels CONFIG_EXTRAFOODS
        //TODO alignments
        //text area CONFIG_EXTRAFOODS
        cfgFoodNameArea.setLineWrap(true);
        cfgFoodQuantityArea.setLineWrap(true);
        //buttons CONFIG_EXTRAFOODS
        cfgFoodSendButton.addActionListener(e -> ctrl.saveFoods()); //todo addFood? vedere se cambiare nome
//------------------------------------------------------------------------------------------

        stateChange(STATE.TITLE);
    }

    public void  stateChange(STATE newState)
    {
        switch (newState) //TODO fare schermate guardabili
        {
            case TITLE:
                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());
                frame.add(BorderLayout.NORTH, titleText);
                frame.add(BorderLayout.CENTER, titleSubText);
                frame.add(BorderLayout.SOUTH, titleLogButton);
                frame.validate();
                frame.getContentPane().repaint();
                break;

            case LOGIN:
                frame.getContentPane().removeAll();
                frame.setLayout(new FlowLayout());
                frame.add(logText);
                frame.add(logManagerButton);
                frame.validate();
                frame.getContentPane().repaint();
                break;

            case CONFIG_MENU:
                frame.getContentPane().removeAll();
                frame.setLayout(new FlowLayout());
                frame.add(cfgMenuText);
                frame.add(cfgMenuBaseButton);
                frame.add(cfgMenuDrinksButton);
                frame.add(cfgMenuExtraFoodsButton);
                frame.add(cfgMenuBackToLogButton);
                frame.add(cfgWriteButton);
                frame.validate();
                frame.getContentPane().repaint();
                break;

            case CONFIG_BASE:
                frame.getContentPane().removeAll();
                frame.setLayout(new FlowLayout());
                frame.add(cfgBaseText);

                frame.add(cfgBaseCapacityText);
                frame.add(cfgBaseCapArea);

                frame.add(cfgBaseIndividualWorkloadText);
                frame.add(cfgBaseIndivualWorkloadArea);

                frame.add(cfgBaseSendButton);
                frame.add(cfgBackButton);
                frame.validate();
                frame.getContentPane().repaint();
                break;

            case CONFIG_EXTRAFOODS:
                frame.getContentPane().removeAll();
                frame.setLayout(new FlowLayout());
                frame.add(cfgFoodText);
                frame.add(cfgFoodNameArea);
                frame.add(cfgFoodQuantityArea);
                frame.add(cfgFoodSendButton);
                frame.add(cfgBackButton);
                frame.validate();
                frame.getContentPane().repaint();
                break;

            case CONFIG_DRINKS:
                frame.getContentPane().removeAll();
                frame.setLayout(new FlowLayout());
                frame.add(cfgDrinksText);
                frame.add(cfgDrinksNameArea);
                frame.add(cfgDrinksQuantityArea);
                frame.add(cfgDrinksSendButton);
                frame.add(cfgBackButton);
                frame.validate();
                frame.getContentPane().repaint();
                break;
        }
    }
}
