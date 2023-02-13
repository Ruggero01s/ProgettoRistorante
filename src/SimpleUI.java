import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class SimpleUI extends JFrame
{
    private GridBagConstraints c = new GridBagConstraints();
    private GridBagConstraints titlePadding = new GridBagConstraints();
    public JPanel panel1 = new JPanel(new GridBagLayout());
    public JPanel panel2 = new JPanel(new GridBagLayout());
    public JPanel panel3 = new JPanel(new GridBagLayout());
    public JPanel panel4 = new JPanel(new GridBagLayout());
    public JPanel panel5 = new JPanel(new GridBagLayout());
    public JPanel panel6 = new JPanel(new GridBagLayout());
    public JPanel panel7 = new JPanel(new GridBagLayout());
    JButton buttonBack1 = new JButton("Back");
    JButton buttonBack2 = new JButton("Back");
    JButton buttonBack3 = new JButton("Back");
    JButton buttonBack4 = new JButton("Back");
    JButton buttonBack5 = new JButton("Back");
    JButton buttonBack6 = new JButton("Back");
    JButton buttonBack7 = new JButton("Back");


    // Enum for the different states of the frame
    private enum State
    {
        LOGIN,
        MANAGER,
        EMPLOYEE,
        WAREHOUSE_WORKER
    }

    // Current state of the frame
    private State state;
    private Controller ctrl;

    JLabel label1 = new JLabel("Select your role:");
    JButton managerButton = new JButton("Manager");
    JButton employeeButton = new JButton("Employee");
    JButton warehouseWorkerButton = new JButton("Warehouse Worker");
    JPanel loginPanel = new JPanel(new GridBagLayout());
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel titleText = new JLabel("Title");
    JLabel titleSubText = new JLabel("Subtitle");
    JButton titleLogButton = new JButton("Login");
//------------------------------------------------------------------------------------------

    //CONFIG general

    JButton cfgWriteButton = new JButton("Salva ed esci");

    //------------------------------------------------------------------------------------------
    //CONFIG_BASE
    JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    JLabel cfgBaseIndiviualWorkloadAreaText = new JLabel("Carico lavoro max:");
    JButton cfgBaseSendButton = new JButton("Conferma");
    JButton cfgBaseClearButton = new JButton("Clear Cap&IndWork");
    JTextArea cfgBaseInputCap = new JTextArea();
    JTextArea cfgBaseInputIndWork = new JTextArea();

    public void setDrinkList(String drinkList)
    {
        this.drinkList = drinkList;
        cfgDrinksAreaOut.setText(this.drinkList);
    }
    
    public void setFoodsList(String foodsList)
    {
        this.foodsList = foodsList;
        cfgFoodsAreaOut.setText(this.foodsList);
    }
    
    //------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
    private String drinkList;
    JLabel cfgDrinksText = new JLabel("Inserisci dati bevanda: (nome : quantità (L))");
    JLabel cfgDrinksTextOut = new JLabel("Elenco dati bevande: (nome : quantità (L))");
    JTextArea cfgDrinksAreaOut = new JTextArea(drinkList);
    JButton cfgDrinksSendButton = new JButton("Inserisci");
    JButton cfgDrinksClearButton = new JButton("Clear Drinks");
    JTextArea cfgDrinksInput = new JTextArea();
    //------------------------------------------------------------------------------------------
    //CONFIG_EXTRAFOODS
    private String foodsList;
    JLabel cfgFoodText = new JLabel("Inserisci dati generi alimentari extra: (nome : quantità (Hg)");
    JLabel cfgFoodTextOut = new JLabel("Elenco dati generi alimentari extra: (nome : quantità (Hg)");
    JButton cfgFoodSendButton = new JButton("Inserisci");
    JButton cfgFoodClearButton = new JButton("Clear ExtraFoods");
    JTextArea cfgFoodsInput = new JTextArea();
    JLabel cfgFoodsTextOut = new JLabel("Elenco dati cibi extra: (nome : quantità (Hg))");
    JTextArea cfgFoodsAreaOut = new JTextArea(foodsList);
    //------------------------------------------------------------------------------------------
    //CONFIG_RECIPES
    JLabel cfgRecipeTextTitle = new JLabel("Inserisci dati ricetta");
    JLabel cfgRecipeTextName = new JLabel("Inserisci nome: ");
    JTextArea cfgRecipeNameInput = new JTextArea();
    JLabel cfgRecipeTextPortions = new JLabel("Inserisci porzioni: ");
    JTextArea cfgRecipePortionsInput = new JTextArea();
    JLabel cfgRecipeTextIngredients = new JLabel("Inserisci ingredienti (ingredienti:quantità (g)): ");
    JTextArea cfgRecipeIngredientsInput = new JTextArea();
    JLabel cfgRecipeTextWorkLoad = new JLabel("Inserisci workload/person: ");
    JTextArea cfgRecipeWorkLoadInput = new JTextArea();
    JButton cfgRecipeSendButton = new JButton("Conferma ricetta");
    //------------------------------------------------------------------------------------------
    //CONFIG_DISHES
    JLabel cfgDishTextTitle = new JLabel("Inserisci dati piatto");
    JLabel cfgDishTextName = new JLabel("Inserisci nome: ");
    JLabel cfgDishTextOut = new JLabel("Elenco piatti inseriti: ");
    JLabel cfgDishTextRecipe = new JLabel("Seleziona ricetta: ");
    JLabel cfgDishTextDate = new JLabel("Inserisci data di inizio e fine: ");
    JButton cfgDishSendButton = new JButton("Conferma piatto");

    JRadioButton cfgDishPermanentRadio = new JRadioButton("Permanente");
    JButton cfgDishClearButton = new JButton("Clear Dishes");

    JTextArea cfgDishNameInput = new JTextArea();

    String[] recipeString = {};

    JComboBox<String> cfgDishComboBox = new JComboBox<>(recipeString);

    JTextArea cfgDishSDateInput = new JTextArea();
    JTextArea cfgDishEDateInput = new JTextArea();

    //------------------------------------------------------------------------------------------
    //CONFIG_MENUS
    JLabel cfgMenuTextTitle = new JLabel("Inserisci dati menu");
    JLabel cfgMenuTextName = new JLabel("Inserisci nome: ");
    JLabel cfgMenuTextOut = new JLabel("Elenco menu inseriti: ");
    JLabel cfgMenuTextDish = new JLabel("Seleziona od inserisci piatti: ");
    JLabel cfgMenuTextDate = new JLabel("Inserisci data di inizio e fine: ");

    JButton cfgMenuSendButton = new JButton("Conferma menu");

    JRadioButton cfgMenuPermanentRadio = new JRadioButton("Permanente");
    JButton cfgMenuClearButton = new JButton("Clear Menus");

    String[] dishString = {};
    JComboBox<String> cfgMenuComboBox = new JComboBox<>(dishString);

    //------------------------------------------------------------------------------------------
    JTextArea cfgMenuNameInput = new JTextArea();
    JTextArea cfgMenuDishesInput = new JTextArea();
    JTextArea cfgMenuSDateInput = new JTextArea();
    JTextArea cfgMenuEDateInput = new JTextArea();

    public SimpleUI(Controller ctrl)
    {
        this.ctrl = ctrl;
        // Set up the UI components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        getContentPane().setBackground(Color.GRAY); //TODO
        setLayout(new BorderLayout());
    }

    public void init ()
    {
        cfgBaseInputCap.setLineWrap(true);
        cfgBaseInputIndWork.setLineWrap(true);
        cfgDrinksInput.setLineWrap(true);
        cfgFoodsInput.setLineWrap(true);
        cfgRecipeNameInput.setLineWrap(true);
        cfgRecipePortionsInput.setLineWrap(true);
        cfgRecipeIngredientsInput.setLineWrap(true);
        cfgRecipeWorkLoadInput.setLineWrap(true);
        cfgDishNameInput.setLineWrap(true);
        cfgDishSDateInput.setLineWrap(true);
        cfgDishEDateInput.setLineWrap(true);
        cfgMenuNameInput.setLineWrap(true);
        cfgMenuDishesInput.setLineWrap(true);
        cfgMenuSDateInput.setLineWrap(true);
        cfgMenuEDateInput.setLineWrap(true);
        cfgDrinksAreaOut.setLineWrap(true);
        cfgDrinksAreaOut.setEditable(false);
        cfgFoodsAreaOut.setLineWrap(true);
        cfgFoodsAreaOut.setEditable(false);

        cfgWriteButton.addActionListener(e -> ctrl.writeAll());
        cfgBaseClearButton.addActionListener(e -> ctrl.clearInfo("config.xml"));
        cfgFoodClearButton.addActionListener(e -> ctrl.clearInfo("extraFoods.xml"));
        cfgDrinksClearButton.addActionListener(e -> ctrl.clearInfo("drinks.xml"));
        cfgDishClearButton.addActionListener(e -> ctrl.clearInfo("dishes.xml"));
        cfgMenuClearButton.addActionListener(e -> ctrl.clearInfo("thematicMenu.xml"));

        cfgMenuComboBox.addActionListener(e -> {
            String selectedItem = (String) cfgDishComboBox.getSelectedItem();
            cfgMenuDishesInput.setText(cfgMenuDishesInput.getText() + selectedItem + "\n");
        });

        // Set the initial state
        state = State.LOGIN;

        // Set up the constraints for the GridBagLayout

        c.insets = new Insets(5, 5, 5, 5); // Top, left, bottom, right padding
        titlePadding.insets = new Insets(0, 5, 20, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Add the UI components to the login panel
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(titleText, c);

        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(managerButton, c);

        c.gridx = 1;
        c.gridy = 1;
        loginPanel.add(employeeButton, c);

        c.gridx = 2;
        c.gridy = 1;
        loginPanel.add(warehouseWorkerButton, c);

        // general config manager

        c.gridx = 0;
        c.gridy = 0;
        panel1.add(cfgBaseText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        panel1.add(cfgBaseCapacityText,c);
        c.gridx = 1;
        c.gridy = 1;
        panel1.add(cfgBaseInputCap,c);
        c.gridx = 0;
        c.gridy = 2;
        panel1.add(cfgBaseIndiviualWorkloadAreaText,c);
        c.gridx = 1;
        c.gridy = 2;
        panel1.add(cfgBaseInputIndWork,c);
        c.gridx = 0;
        c.gridy = 3;
        panel1.add(buttonBack1,c);
        c.gridx = 1;
        c.gridy = 3;
        cfgBaseSendButton.addActionListener(e -> ctrl.saveConfig());
        panel1.add(cfgBaseSendButton,c);

        // Drinks & Food

        c.gridx = 0;
        c.gridy = 0;
        panel2.add(cfgDrinksText, c);
        c.gridx = 1;
        c.gridy = 0;
        panel2.add(cfgDrinksInput,c);
        c.gridx = 2;
        c.gridy = 0;
        cfgDrinksSendButton.addActionListener(e -> ctrl.saveDrinks());
        panel2.add(cfgDrinksSendButton,c);
        c.gridx = 0;
        c.gridy = 1;
        panel2.add(cfgFoodText,c);
        c.gridx = 1;
        c.gridy = 1;
        panel2.add(cfgFoodsInput,c);
        c.gridx = 2;
        c.gridy = 1;
        cfgFoodSendButton.addActionListener(e -> ctrl.saveFoods());
        panel2.add(cfgFoodSendButton,c);
        c.gridx = 0;
        c.gridy = 2;
        panel2.add(cfgDrinksTextOut,c);
        cfgDrinksAreaOut.setMaximumSize(new Dimension(20,100));
        c.gridx = 1;
        c.gridy = 2;
        panel2.add(cfgDrinksAreaOut,c);
        c.gridx = 0;
        c.gridy = 3;
        panel2.add(cfgFoodsTextOut,c);
        cfgFoodsAreaOut.setMaximumSize(new Dimension(20,100));
        c.gridx = 1;
        c.gridy = 3;
        panel2.add(cfgFoodsAreaOut,c);
        c.gridx = 0;
        c.gridy = 4;
        panel2.add(buttonBack2,c);

        //Recipes panel
        c.gridx = 0;
        c.gridy = 0;
        panel3.add(cfgRecipeTextTitle, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        panel3.add(cfgRecipeTextName,c);
        c.gridx = 1;
        c.gridy = 1;
        panel3.add(cfgRecipeNameInput,c);
        c.gridx = 0;
        c.gridy = 2;
        panel3.add(cfgRecipeTextIngredients,c);
        c.gridx = 1;
        c.gridy = 2;
        panel3.add(cfgRecipeIngredientsInput,c);
        c.gridx = 0;
        c.gridy = 3;
        panel3.add(cfgRecipeTextPortions,c);
        c.gridx = 1;
        c.gridy = 3;
        panel3.add(cfgRecipePortionsInput,c);
        c.gridx = 0;
        c.gridy = 4;
        panel3.add(cfgRecipeTextWorkLoad,c);
        c.gridx = 1;
        c.gridy = 4;
        panel3.add(cfgRecipeWorkLoadInput,c);
        c.gridx = 0;
        c.gridy = 5;
        panel3.add(buttonBack3,c);
        c.gridx = 3;
        c.gridy = 5;
        panel3.add(cfgRecipeSendButton,c);
        cfgRecipeSendButton.addActionListener(e -> ctrl.saveRecipe());

        //Dishes panel
        c.gridx = 0;
        c.gridy = 0;
        panel4.add(cfgDishTextTitle, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        panel4.add(cfgDishTextName,c);
        c.gridx = 1;
        c.gridy = 1;
        panel4.add(cfgDishNameInput,c);
        c.gridx = 0;
        c.gridy = 2;
        panel4.add(cfgDishTextRecipe,c);
        c.gridx = 1;
        c.gridy = 2;
        panel4.add(cfgDishComboBox,c);
        c.gridx = 0;
        c.gridy = 3;
        panel4.add(cfgDishTextDate,c);
        c.gridx = 2;
        c.gridy = 3;
        panel4.add(cfgDishSDateInput,c);
        c.gridx = 3;
        c.gridy = 3;
        panel4.add(cfgDishEDateInput,c);
        c.gridx = 1;
        c.gridy = 3;
        panel4.add(cfgDishPermanentRadio,c);
        c.gridx = 0;
        c.gridy = 4;
        panel4.add(buttonBack4,c);
        c.gridx = 3;
        c.gridy = 4;
        panel4.add(cfgDishSendButton,c);
        cfgDishSendButton.addActionListener(e -> ctrl.saveDish());

        //Menu panel
        c.gridx = 0;
        c.gridy = 0;
        panel5.add(cfgMenuTextTitle, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        panel5.add(cfgMenuTextName,c);
        c.gridx = 1;
        c.gridy = 1;
        panel5.add(cfgMenuNameInput,c);
        c.gridx = 0;
        c.gridy = 2;
        panel5.add(cfgMenuTextDish,c);
        c.gridx = 1;
        c.gridy = 2;
        panel5.add(cfgMenuComboBox,c);
        c.gridx = 2;
        c.gridy = 2;
        panel5.add(cfgMenuDishesInput,c);
        c.gridx = 0;
        c.gridy = 3;
        panel5.add(cfgMenuTextDate,c);
        c.gridx = 1;
        c.gridy = 3;
        panel5.add(cfgMenuPermanentRadio,c);
        c.gridx = 2;
        c.gridy = 3;
        panel5.add(cfgMenuSDateInput,c);
        c.gridx = 3;
        c.gridy = 3;
        panel5.add(cfgMenuEDateInput,c);
        c.gridx = 0;
        c.gridy = 4;
        panel5.add(buttonBack5,c);
        c.gridx = 3;
        c.gridy = 4;
        panel5.add(cfgMenuSendButton,c);
        cfgMenuSendButton.addActionListener(e -> {
            try {
                ctrl.saveMenu();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add the UI components to the third panel
        c.gridx = 0;
        c.gridy = 0;
        panel7.add(cfgBaseClearButton,c);
        c.gridx = 0;
        c.gridy = 1;
        panel7.add(cfgDrinksClearButton,c);
        c.gridx = 0;
        c.gridy = 2;
        panel7.add(cfgFoodClearButton,c);
        c.gridx = 0;
        c.gridy = 3;
        panel7.add(cfgDishClearButton,c);
        c.gridx = 0;
        c.gridy = 4;
        panel7.add(cfgMenuClearButton,c);
        c.gridx = 1;
        c.gridy = 0;
        panel7.add(cfgWriteButton,c);
        c.gridx = 1;
        c.gridy = 1;
        panel7.add(buttonBack7,c);

        // Add the panels to the tabbed pane
        tabbedPane.addTab("Specifiche", panel1);
        tabbedPane.addTab("Drinks&Foods", panel2);
        tabbedPane.addTab("Recipes", panel3);
        tabbedPane.addTab("Dishes", panel4);
        tabbedPane.addTab("Menus", panel5);
        tabbedPane.addTab("Resoconto", panel6);
        tabbedPane.addTab("Write&Save", panel7);


       ActionListener back = e -> {
           state = State.LOGIN;
           updateUI();
       };
        //listener back button
        buttonBack1.addActionListener(back);
        buttonBack2.addActionListener(back);
        buttonBack3.addActionListener(back);
        buttonBack4.addActionListener(back);
        buttonBack5.addActionListener(back);
        buttonBack6.addActionListener(back);
        buttonBack7.addActionListener(back);
        // Set up the button actions
        managerButton.addActionListener(e -> {
            // Action for the manager button goes here
            // Change to the manager state
            state = State.MANAGER;
            updateUI();
        });
        employeeButton.addActionListener(e -> {
            // Action for the employee button goes here
            // Change to the employee state
            state = State.EMPLOYEE;
            updateUI();
        });
        warehouseWorkerButton.addActionListener(e -> {
            // Action for the warehouse worker button goes here
            // Change to the warehouse worker state
            state = State.WAREHOUSE_WORKER;
            updateUI();
        });

        // Set up the initial UI
        updateUI();
    }

    // Method to update the UI based on the current state
    private void updateUI() {
        // Clear the frame
        getContentPane().removeAll();

        // Add the appropriate components based on the current state
        if (state == State.LOGIN) {
            getContentPane().add(loginPanel);
        } else if (state == State.MANAGER) {
            getContentPane().add(tabbedPane);
        } else if (state == State.EMPLOYEE) {
            // Add the components for the employee state
        } else if (state == State.WAREHOUSE_WORKER) {
            // Add the components for the warehouse worker state
        }

        // Refresh the frame
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void errorSetter(String code){
        switch(code)
        {
            case "minZero":
                JOptionPane.showMessageDialog(getContentPane(), "Numero inserito < 0",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "NumberFormatException":
                JOptionPane.showMessageDialog(getContentPane(), "Formato incorretto",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "noRecipe":
                JOptionPane.showMessageDialog(getContentPane(), "Inserisci prima una ricetta!",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "noDish":
                JOptionPane.showMessageDialog(getContentPane(), "Piatto non trovato",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "0Dish":
                JOptionPane.showMessageDialog(getContentPane(), "Inserisci almeno un piatto!",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "invalidDate":
                JOptionPane.showMessageDialog(getContentPane(), "Date non valide",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
        }
        getContentPane().repaint();
    }
}