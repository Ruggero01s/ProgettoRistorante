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
        CONFIG_CHOICE,
        CONFIG_BASE,
        CONFIG_DRINKS,
        CONFIG_EXTRAFOODS,
        CONFIG_RECIPES,
        CONFIG_DISHES,
        CONFIG_MENUS
    }
    STATE stateOld = STATE.INIT;
    JFrame frame = new JFrame("Restaurant");
    //GENERAL
    JTextArea cfgInputArea1 = new JTextArea();
    JTextArea cfgInputArea2 = new JTextArea();
    JTextArea cfgInputArea3 = new JTextArea();
    JTextArea cfgInputArea4 = new JTextArea();

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
    JButton cfgWriteButton = new JButton("Salva ed esci");

    //CONFIG_CHOICES
    JLabel cfgChoiceText = new JLabel("Cosa vuoi configurare?");
    JButton cfgChoiceBaseButton = new JButton("Specifiche ristorante");
    JButton cfgChoiceDrinksButton = new JButton("Bevande");
    JButton cfgChoiceExtraFoodsButton = new JButton("Generi Extra");
    JButton cfgChoiceRecipesButton = new JButton("Ricette");
    JButton cfgChoiceDishesButton = new JButton("Piatti");
    JButton cfgChoiceBackToLogButton = new JButton("Back to Login");
//------------------------------------------------------------------------------------------
    //CONFIG_BASE
    JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    JLabel cfgBaseIndiviualWorkloadAreaText = new JLabel("Carico lavoro max:");
    JButton cfgBaseSendButton = new JButton("Conferma");
//------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
    JLabel cfgDrinksText = new JLabel("Inserisci dati bevanda: (nome|quantità)");
    JButton cfgDrinksSendButton = new JButton("Inserisci");
//------------------------------------------------------------------------------------------
    //CONFIG_EXTRAFOODS
    JLabel cfgFoodText = new JLabel("Inserisci dati generi alimentari extra: (nome|quantità)");
    JButton cfgFoodSendButton = new JButton("Inserisci");
//------------------------------------------------------------------------------------------
    //CONFIG_RECIPES
    JLabel cfgRecipeText = new JLabel("Inserisci dati ricetta (nome|porzioni|lista ingredienti:quantità)");
    JButton cfgRecipeSendButton = new JButton("Inserisci");
    //------------------------------------------------------------------------------------------
    //CONFIG_DISHES
    JLabel cfgDishText = new JLabel("Inserisci dati piatto (nome|ricetta)");
    JButton cfgDishSendButton = new JButton("Inserisci");
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
    //GENERAL
        cfgInputArea1.setLineWrap(true);
        cfgInputArea2.setLineWrap(true);
        cfgInputArea3.setLineWrap(true);
        cfgInputArea4.setLineWrap(true);
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
        logManagerButton.addActionListener(e -> stateChange(STATE.CONFIG_CHOICE));

    /*  JButton logEmployeeButton = new JButton("Employee");      //TODO
        logManagerButton.addActionListener(e -> state=STATE.CONFIG);
        JButton logWarehouseButton = new JButton("Storage Worker");
        logManagerButton.addActionListener(e -> state=STATE.CONFIG);  */
//------------------------------------------------------------------------------------------
    //CONFIG
        cfgBackButton.addActionListener(e -> stateChange(STATE.CONFIG_CHOICE));
        cfgWriteButton.addActionListener(e -> ctrl.writeAll());
    //CONFIG_CHOICE
        cfgChoiceBaseButton.addActionListener(e -> stateChange(STATE.CONFIG_BASE));
        cfgChoiceDrinksButton.addActionListener(e -> stateChange(STATE.CONFIG_DRINKS));
        cfgChoiceExtraFoodsButton.addActionListener(e -> stateChange(STATE.CONFIG_EXTRAFOODS));
        cfgChoiceRecipesButton.addActionListener(e -> stateChange(STATE.CONFIG_RECIPES));
        cfgChoiceDishesButton.addActionListener(e -> stateChange(STATE.CONFIG_DISHES));
        cfgChoiceBackToLogButton.addActionListener(e -> stateChange(STATE.LOGIN));
//------------------------------------------------------------------------------------------
    //CONFIG_BASE
        //labels CONFIG_BASE
            //TODO setAlignment
        //buttons CONFIG_BASE
        cfgBaseSendButton.addActionListener(e -> ctrl.saveConfig());
//------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
        //labels CONFIG_DRINKS
            //TODO alignments
        //buttons CONFIG_DRINKS
        cfgDrinksSendButton.addActionListener(e -> ctrl.saveDrinks()); //todo addDrink? vedere se cambiare nome
//------------------------------------------------------------------------------------------
    //CONFIG_EXTRAFOODS
        //labels CONFIG_EXTRAFOODS
             //TODO alignments
        //buttons CONFIG_EXTRAFOODS
        cfgFoodSendButton.addActionListener(e -> ctrl.saveFoods()); //todo addFood? vedere se cambiare nome
//------------------------------------------------------------------------------------------
    //CONFIG_RECIPE
        //labels CONFIG_RECIPE
        //TODO alignments
        //buttons CONFIG_RECIPE
        cfgRecipeSendButton.addActionListener(e -> ctrl.saveRecipe()); //todo
//------------------------------------------------------------------------------------------
    //CONFIG_DISH
        //labels CONFIG_DISH
        //TODO alignments
        //buttons CONFIG_DISH
        cfgDishSendButton.addActionListener(e -> ctrl.saveDish()); //todo
//------------------------------------------------------------------------------------------
//==========================================================================================
        stateChange(STATE.TITLE);
    }

    public void  stateChange(STATE newState)
    {
        resetInputAreas();
        frame.getContentPane().removeAll();
        switch (newState) //TODO fare schermate guardabili
        {
            case TITLE:
                frame.setLayout(new BorderLayout());
                frame.add(BorderLayout.NORTH, titleText);
                frame.add(BorderLayout.CENTER, titleSubText);
                frame.add(BorderLayout.SOUTH, titleLogButton);
                break;

            case LOGIN:
                frame.setLayout(new FlowLayout());
                frame.add(logText);
                frame.add(logManagerButton);
                break;

            case CONFIG_CHOICE:
                frame.setLayout(new FlowLayout());
                frame.add(cfgChoiceText);
                frame.add(cfgChoiceBaseButton);
                frame.add(cfgChoiceDrinksButton);
                frame.add(cfgChoiceExtraFoodsButton);
                frame.add(cfgChoiceRecipesButton);
                frame.add(cfgChoiceDishesButton);
                frame.add(cfgChoiceBackToLogButton);
                frame.add(cfgWriteButton);
                break;

            case CONFIG_BASE:
                frame.setLayout(new FlowLayout());
                frame.add(cfgBaseText);

                frame.add(cfgBaseCapacityText);
                frame.add(cfgInputArea1);

                frame.add(cfgBaseIndiviualWorkloadAreaText);
                frame.add(cfgInputArea2);

                frame.add(cfgBaseSendButton);
                frame.add(cfgBackButton);
                break;

            case CONFIG_EXTRAFOODS:
                frame.setLayout(new FlowLayout());
                frame.add(cfgFoodText);
                frame.add(cfgInputArea1);
                frame.add(cfgInputArea2);
                frame.add(cfgFoodSendButton);
                frame.add(cfgBackButton);
                break;

            case CONFIG_DRINKS:
                frame.setLayout(new FlowLayout());
                frame.add(cfgDrinksText);
                frame.add(cfgInputArea1);
                frame.add(cfgInputArea2);
                frame.add(cfgDrinksSendButton);
                frame.add(cfgBackButton);
                break;

            case CONFIG_RECIPES:
                frame.setLayout(new FlowLayout());
                frame.add(cfgRecipeText);
                frame.add(cfgInputArea1);
                frame.add(cfgInputArea2);
                frame.add(cfgInputArea3);
                frame.add(cfgInputArea4);
                frame.add(cfgRecipeSendButton);
                frame.add(cfgBackButton);
                break;

            case CONFIG_DISHES:
                frame.setLayout(new FlowLayout());
                frame.add(cfgDishText);
                frame.add(cfgInputArea1);
                frame.add(cfgInputArea2);
                frame.add(cfgDishSendButton);
                frame.add(cfgBackButton);
                break;
        }
        frame.validate();
        frame.getContentPane().repaint();
    }

    public void resetInputAreas()
    {
        cfgInputArea1.setText("");
        cfgInputArea2.setText("");
        cfgInputArea3.setText("");
        cfgInputArea4.setText("");
    }

}
