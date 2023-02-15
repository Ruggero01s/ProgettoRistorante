import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class SimpleUI extends JFrame {
    // Enum for the different states of the frame
    private enum State {
        LOGIN,
        MANAGER,
        EMPLOYEE,
        WAREHOUSE_WORKER
    }

    // Current state of the frame
    private State state;
    private Controller ctrl;


    //GENERAL
    JButton managerButton = new JButton("Manager");
    JButton employeeButton = new JButton("Employee");
    JButton warehouseWorkerButton = new JButton("Warehouse Worker");
    JPanel loginPanel = new JPanel(new GridBagLayout());
    JTabbedPane cfgTabbedPane = new JTabbedPane();
    JLabel titleText = new JLabel("Title");
    private GridBagConstraints c = new GridBagConstraints();
    private GridBagConstraints titlePadding = new GridBagConstraints();
    private GridBagConstraints endPadding = new GridBagConstraints();
    JPanel cfgBasePanel = new JPanel(new GridBagLayout());
    JPanel cfgDrinksFoodsPanel = new JPanel(new GridBagLayout());
    JPanel cfgRecipesPanel = new JPanel(new GridBagLayout());
    JPanel cfgDishesPanel = new JPanel(new GridBagLayout());
    JPanel cfgMenuPanel = new JPanel(new GridBagLayout());
    JPanel cfgResPanel = new JPanel(new GridBagLayout());
    JPanel cfgWriteClearPanel = new JPanel(new GridBagLayout());
    JButton buttonBack1 = new JButton("Back");
    JButton buttonBack2 = new JButton("Back");
    JButton buttonBack3 = new JButton("Back");
    JButton buttonBack4 = new JButton("Back");
    JButton buttonBack5 = new JButton("Back");
    JButton buttonBack6 = new JButton("Back");
    JButton buttonBack7 = new JButton("Back");
    JButton buttonBack8 = new JButton("Back");
    JButton buttonBack9 = new JButton("Back");
    JButton buttonBack10 = new JButton("Back");
    JButton buttonBack11 = new JButton("Back");

    //------------------------------------------------------------------------------------------
    //CONFIG_BASE
    JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    JLabel cfgBaseIndiviualWorkloadAreaText = new JLabel("Carico lavoro max:");
    JButton cfgBaseSendButton = new JButton("Conferma");
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
    JTextArea cfgDrinksInput = new JTextArea();
    //------------------------------------------------------------------------------------------
    //CONFIG_EXTRAFOODS
    private String foodsList;
    JLabel cfgFoodText = new JLabel("Inserisci dati generi alimentari extra: (nome : quantità (Hg)");
    JButton cfgFoodSendButton = new JButton("Inserisci");
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
    JLabel cfgRecipeTextOut = new JLabel("Elenco ricette: ");
    JTextArea cfgRecipeAreaOut = new JTextArea();
    JButton cfgRecipeSendButton = new JButton("Conferma ricetta");
    private String recipeList;//commento todo
    public void setRecipeList(String list) {
        this.recipeList = list;
        cfgRecipeAreaOut.setText(this.recipeList);
    }
    //------------------------------------------------------------------------------------------
    //CONFIG_DISHES
    JLabel cfgDishTextTitle = new JLabel("Inserisci dati piatto");
    JLabel cfgDishTextName = new JLabel("Inserisci nome: ");
    JLabel cfgDishTextOut = new JLabel("Elenco piatti inseriti: ");
    JLabel cfgDishTextRecipe = new JLabel("Seleziona ricetta: ");
    JLabel cfgDishTextDate = new JLabel("Inserisci data di inizio e fine: ");
    JTextArea cfgDishAreaOut = new JTextArea();
    JButton cfgDishSendButton = new JButton("Conferma piatto");
    JRadioButton cfgDishPermanentRadio = new JRadioButton("Permanente");
    JTextArea cfgDishNameInput = new JTextArea();
    String dishList;
    public void setDishList(String list) {
        this.dishList = list;
        cfgDishAreaOut.setText(this.dishList);
    }
    String[] recipeString = {};
    JComboBox<String> cfgDishComboBox = new JComboBox<>(recipeString);
    JTextArea cfgDishSDateInput = new JTextArea();
    JTextArea cfgDishEDateInput = new JTextArea();

    //------------------------------------------------------------------------------------------
    //CONFIG_MENUS
    private String menuList;
    JLabel cfgMenuTextTitle = new JLabel("Inserisci dati menu");
    JLabel cfgMenuTextName = new JLabel("Inserisci nome: ");
    JLabel cfgMenuTextOut = new JLabel("Elenco menu inseriti: ");
    JTextArea cfgMenuAreaOut = new JTextArea();
    JLabel cfgMenuTextDish = new JLabel("Seleziona od inserisci piatti: ");
    JLabel cfgMenuTextDate = new JLabel("Inserisci data di inizio e fine: ");
    JButton cfgMenuSendButton = new JButton("Conferma menu");
    public void setMenuList(String list) {
        this.menuList = list;
        cfgMenuAreaOut.setText(this.menuList);
    }
    JRadioButton cfgMenuPermanentRadio = new JRadioButton("Permanente");
    JTextArea cfgMenuNameInput = new JTextArea();
    JTextArea cfgMenuDishesInput = new JTextArea();
    JTextArea cfgMenuSDateInput = new JTextArea();
    JTextArea cfgMenuEDateInput = new JTextArea();
    String[] dishString = {};
    JComboBox<String> cfgMenuComboBox = new JComboBox<>(dishString);

    //------------------------------------------------------------------------------------------
    //CONFIG_RESOCONTO
    JLabel cfgResText = new JLabel("Reseconto:");
    JLabel cfgResBaseText = new JLabel("Dati ristorante:");
    JLabel cfgResDrinksText = new JLabel("Dati bevande:");
    JLabel cfgResFoodsText = new JLabel("Dati cibi extra:");
    JLabel cfgResRecipesText = new JLabel("Dati ricette:");
    JLabel cfgResDishesText = new JLabel("Dati piatti:");
    JLabel cfgResMenuText = new JLabel("Dati menu:");
    JTextArea cfgResBaseOut = new JTextArea();
    JTextArea cfgResDrinksOut = new JTextArea();
    JTextArea cfgResFoodsOut = new JTextArea();
    JTextArea cfgResRecipesOut = new JTextArea();
    JTextArea cfgResDishesOut = new JTextArea();
    JTextArea cfgResMenuOut = new JTextArea();

    //------------------------------------------------------------------------------------------
    //CONFIG_WRITING AND CLEAR
    JButton cfgBaseClearButton = new JButton("Clear Cap&IndWork");
    JButton cfgDrinksClearButton = new JButton("Clear Drinks");
    JButton cfgFoodClearButton = new JButton("Clear ExtraFoods");
    JButton cfgRecipeClearButton = new JButton("Clear Recipes");
    JButton cfgDishClearButton = new JButton("Clear Dishes");
    JButton cfgMenuClearButton = new JButton("Clear Menus");
    JButton cfgWriteButton = new JButton("Salva ed esci");
//------------------------------------------------------------------------------------------
//============================================================================================
//============================================================================================
    //EMPLOYEE
    //EMPLOYEE GENERAL
    JTabbedPane empTabbedPane = new JTabbedPane();
    public JPanel empSeeBookingsPanel = new JPanel(new GridBagLayout());
    public JPanel empNewBookingPanel = new JPanel(new GridBagLayout());
    public JPanel empStatsPanel = new JPanel(new GridBagLayout());
    public JPanel empExtra = new JPanel(new GridBagLayout());
//-------------------------------------------------------------------------------------------
    //EMPLOY SEE BOOKINGS
    JLabel empSeeBookText = new JLabel("Prenotazioni:");
    JTextArea empSeeBookDateInput = new JTextArea();
    JTextArea empSeeBookAreaOut = new JTextArea();
    JButton empSeeBookSend = new JButton("Vedi prenotazioni");
    JButton empSeeBookWrite = new JButton("Salva prenotazioni");
    JButton empSeeBookClear = new JButton("Svuota prenotazioni");

//-------------------------------------------------------------------------------------------
    //EMPLOY NEW BOOKING
    JLabel empNewBookText = new JLabel("Nuova prenotazione:");
    JLabel empNewBookDateText = new JLabel("Data:");
    JLabel empNewBookNameText = new JLabel("Nome:");
    JLabel empNewBookNumText = new JLabel("Numero:");
    JLabel empNewBookOrderText = new JLabel("Lista ordine:");

    JTextArea empNewBookDateInput = new JTextArea();
    JTextArea empNewBookNameInput = new JTextArea();
    JTextArea empNewBookNumInput = new JTextArea();
    JTextArea empNewBookOrderInput = new JTextArea();

    JComboBox empNewBookMenuBox = new JComboBox<>();

    JButton empNewBookSend = new JButton("Inserisci");

//-------------------------------------------------------------------------------------------

    Border border = BorderFactory.createLineBorder(Color.GRAY,1);


    public SimpleUI(Controller ctrl) {
        this.ctrl = ctrl;
        // Set up the UI components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        getContentPane().setBackground(Color.GRAY); //TODO
        setLayout(new BorderLayout());
    }

    ActionListener back = e -> {
        state = State.LOGIN;
        updateUI();
    };

    public void init() {
        c.insets = new Insets(5, 5, 5, 5); // Top, left, bottom, right padding
        titlePadding.insets = new Insets(0, 5, 20, 5);
        endPadding.insets = new Insets(20, 5, 0, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        endPadding.fill = GridBagConstraints.HORIZONTAL;

        logInit();
        cfgInit();
        empInit();
        // Set the initial state
        state = State.LOGIN;
        updateUI();
    }

    private void logInit() {
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

        managerButton.addActionListener(e -> {
            state = State.MANAGER;
            updateUI();
        });
        employeeButton.addActionListener(e -> {
            state = State.EMPLOYEE;
            updateUI();
        });
        warehouseWorkerButton.addActionListener(e -> {
            state = State.WAREHOUSE_WORKER;
            updateUI();
        });
    }

    private void cfgInit() {

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
        cfgResBaseOut.setLineWrap(true);
        cfgResDrinksOut.setLineWrap(true);
        cfgResFoodsOut.setLineWrap(true);
        cfgResRecipesOut.setLineWrap(true);
        cfgResDishesOut.setLineWrap(true);
        cfgResMenuOut.setLineWrap(true);
        cfgRecipeAreaOut.setLineWrap(true);
        cfgDishAreaOut.setLineWrap(true);
        cfgMenuAreaOut.setLineWrap(true);
        cfgFoodsAreaOut.setLineWrap(true);

        cfgBaseInputCap.setBorder(border);
        cfgBaseInputIndWork.setBorder(border);
        cfgDrinksInput.setBorder(border);
        cfgFoodsInput.setBorder(border);
        cfgRecipeNameInput.setBorder(border);
        cfgRecipePortionsInput.setBorder(border);
        cfgRecipeIngredientsInput.setBorder(border);
        cfgRecipeWorkLoadInput.setBorder(border);
        cfgDishNameInput.setBorder(border);
        cfgDishSDateInput.setBorder(border);
        cfgDishEDateInput.setBorder(border);
        cfgMenuNameInput.setBorder(border);
        cfgMenuDishesInput.setBorder(border);
        cfgMenuSDateInput.setBorder(border);
        cfgMenuEDateInput.setBorder(border);
        cfgDrinksAreaOut.setBorder(border);
        cfgResBaseOut.setBorder(border);
        cfgResDrinksOut.setBorder(border);
        cfgResFoodsOut.setBorder(border);
        cfgResRecipesOut.setBorder(border);
        cfgResDishesOut.setBorder(border);
        cfgResMenuOut.setBorder(border);
        cfgRecipeAreaOut.setBorder(border);
        cfgDishAreaOut.setBorder(border);
        cfgMenuAreaOut.setBorder(border);
        cfgFoodsAreaOut.setBorder(border);


        cfgResBaseOut.setEditable(false);
        cfgResDrinksOut.setEditable(false);
        cfgResFoodsOut.setEditable(false);
        cfgResRecipesOut.setEditable(false);
        cfgResDishesOut.setEditable(false);
        cfgResMenuOut.setEditable(false);
        cfgFoodsAreaOut.setEditable(false);
        cfgDrinksAreaOut.setEditable(false);
        cfgRecipeAreaOut.setEditable(false);
        cfgDishAreaOut.setEditable(false);
        cfgMenuAreaOut.setEditable(false);

        cfgWriteButton.addActionListener(e -> ctrl.writeAll());
        cfgBaseClearButton.addActionListener(e -> ctrl.clearInfo("config.xml"));
        cfgFoodClearButton.addActionListener(e -> ctrl.clearInfo("extraFoods.xml"));
        cfgRecipeClearButton.addActionListener(e -> ctrl.clearInfo("recipes.xml"));
        cfgDrinksClearButton.addActionListener(e -> ctrl.clearInfo("drinks.xml"));
        cfgDishClearButton.addActionListener(e -> ctrl.clearInfo("dishes.xml"));
        cfgMenuClearButton.addActionListener(e -> ctrl.clearInfo("thematicMenus.xml"));

        //listener back button
        buttonBack1.addActionListener(back);
        buttonBack2.addActionListener(back);
        buttonBack3.addActionListener(back);
        buttonBack4.addActionListener(back);
        buttonBack5.addActionListener(back);
        buttonBack6.addActionListener(back);
        buttonBack7.addActionListener(back);

        cfgMenuComboBox.addActionListener(e ->
        {
            String selectedItem = (String) cfgMenuComboBox.getSelectedItem();
            cfgMenuDishesInput.setText(cfgMenuDishesInput.getText() + selectedItem + "\n");
        });

        // general config manager

        c.gridx = 0;
        c.gridy = 0;
        cfgBasePanel.add(cfgBaseText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgBasePanel.add(cfgBaseCapacityText, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgBasePanel.add(cfgBaseInputCap, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgBasePanel.add(cfgBaseIndiviualWorkloadAreaText, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgBasePanel.add(cfgBaseInputIndWork, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgBasePanel.add(buttonBack1, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgBaseSendButton.addActionListener(e -> ctrl.saveConfig());
        cfgBasePanel.add(cfgBaseSendButton, c);

        // Drinks & Food

        c.gridx = 0;
        c.gridy = 0;
        cfgDrinksFoodsPanel.add(cfgDrinksText, c);
        c.gridx = 1;
        c.gridy = 0;
        cfgDrinksFoodsPanel.add(cfgDrinksInput, c);
        c.gridx = 2;
        c.gridy = 0;
        cfgDrinksSendButton.addActionListener(e -> ctrl.saveDrinks());
        cfgDrinksFoodsPanel.add(cfgDrinksSendButton, c);
        c.gridx = 0;
        c.gridy = 1;
        cfgDrinksFoodsPanel.add(cfgFoodText, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgDrinksFoodsPanel.add(cfgFoodsInput, c);
        c.gridx = 2;
        c.gridy = 1;
        cfgFoodSendButton.addActionListener(e -> ctrl.saveFoods());
        cfgDrinksFoodsPanel.add(cfgFoodSendButton, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgDrinksFoodsPanel.add(cfgDrinksTextOut, c);
        cfgDrinksAreaOut.setMaximumSize(new Dimension(20, 100));
        c.gridx = 1;
        c.gridy = 2;
        cfgDrinksFoodsPanel.add(cfgDrinksAreaOut, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgDrinksFoodsPanel.add(cfgFoodsTextOut, c);
        cfgFoodsAreaOut.setMaximumSize(new Dimension(20, 100));
        c.gridx = 1;
        c.gridy = 3;
        cfgDrinksFoodsPanel.add(cfgFoodsAreaOut, c);
        c.gridx = 0;
        c.gridy = 4;
        cfgDrinksFoodsPanel.add(buttonBack2, c);

        //Recipes panel
        c.gridx = 0;
        c.gridy = 0;
        cfgRecipesPanel.add(cfgRecipeTextTitle, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgRecipesPanel.add(cfgRecipeTextName, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgRecipesPanel.add(cfgRecipeNameInput, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgRecipesPanel.add(cfgRecipeTextIngredients, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgRecipesPanel.add(cfgRecipeIngredientsInput, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgRecipesPanel.add(cfgRecipeTextPortions, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgRecipesPanel.add(cfgRecipePortionsInput, c);
        c.gridx = 0;
        c.gridy = 4;
        cfgRecipesPanel.add(cfgRecipeTextWorkLoad, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgRecipesPanel.add(cfgRecipeWorkLoadInput, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgRecipesPanel.add(cfgRecipeTextOut, c);
        c.gridx = 1;
        c.gridy = 5;
        cfgRecipesPanel.add(cfgRecipeAreaOut, c);
        c.gridx = 0;
        c.gridy = 6;
        cfgRecipesPanel.add(buttonBack3, c);
        c.gridx = 3;
        c.gridy = 6;
        cfgRecipesPanel.add(cfgRecipeSendButton, c);
        cfgRecipeSendButton.addActionListener(e -> ctrl.saveRecipe());

        //Dishes panel
        c.gridx = 0;
        c.gridy = 0;
        cfgDishesPanel.add(cfgDishTextTitle, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgDishesPanel.add(cfgDishTextName, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgDishesPanel.add(cfgDishNameInput, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgDishesPanel.add(cfgDishTextRecipe, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgDishesPanel.add(cfgDishComboBox, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgDishesPanel.add(cfgDishTextDate, c);
        c.gridx = 2;
        c.gridy = 3;
        cfgDishesPanel.add(cfgDishSDateInput, c);
        c.gridx = 3;
        c.gridy = 3;
        cfgDishesPanel.add(cfgDishEDateInput, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgDishesPanel.add(cfgDishPermanentRadio, c);
        c.gridx = 0;
        c.gridy = 4;
        cfgDishesPanel.add(cfgDishTextOut, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgDishesPanel.add(cfgDishAreaOut, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgDishesPanel.add(buttonBack4, c);
        c.gridx = 3;
        c.gridy = 5;
        cfgDishesPanel.add(cfgDishSendButton, c);
        cfgDishSendButton.addActionListener(e -> ctrl.saveDish());

        //Menu panel
        c.gridx = 0;
        c.gridy = 0;
        cfgMenuPanel.add(cfgMenuTextTitle, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgMenuPanel.add(cfgMenuTextName, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgMenuPanel.add(cfgMenuNameInput, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgMenuPanel.add(cfgMenuTextDish, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgMenuPanel.add(cfgMenuComboBox, c);
        c.gridx = 2;
        c.gridy = 2;
        cfgMenuPanel.add(cfgMenuDishesInput, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuTextDate, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuPermanentRadio, c);
        c.gridx = 2;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuSDateInput, c);
        c.gridx = 3;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuEDateInput, c);
        c.gridx = 0;
        c.gridy = 4;
        cfgMenuPanel.add(cfgMenuTextOut, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgMenuPanel.add(cfgMenuAreaOut, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgMenuPanel.add(buttonBack5, c);
        c.gridx = 3;
        c.gridy = 5;
        cfgMenuPanel.add(cfgMenuSendButton, c);
        cfgMenuSendButton.addActionListener(e -> {
            try {
                ctrl.saveMenu();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Resconto tab
        c.gridx = 0;
        c.gridy = 0;
        cfgResPanel.add(cfgResText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgResPanel.add(cfgResBaseText, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgResPanel.add(cfgResBaseOut, c);

        c.gridx = 0;
        c.gridy = 2;
        cfgResPanel.add(cfgResDrinksText, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgResPanel.add(cfgResDrinksOut, c);

        c.gridx = 0;
        c.gridy = 3;
        cfgResPanel.add(cfgResFoodsText, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgResPanel.add(cfgResFoodsOut, c);

        c.gridx = 0;
        c.gridy = 4;
        cfgResPanel.add(cfgResRecipesText, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgResPanel.add(cfgResRecipesOut, c);

        c.gridx = 0;
        c.gridy = 5;
        cfgResPanel.add(cfgResDishesText, c);
        c.gridx = 1;
        c.gridy = 5;
        cfgResPanel.add(cfgResDishesOut, c);

        c.gridx = 0;
        c.gridy = 6;
        cfgResPanel.add(cfgResMenuText, c);
        c.gridx = 1;
        c.gridy = 6;
        cfgResPanel.add(cfgResMenuOut, c);

        c.gridx = 6;
        c.gridy = 7;
        cfgResPanel.add(buttonBack6, c);


        // Write and Save
        c.gridx = 0;
        c.gridy = 0;
        cfgWriteClearPanel.add(cfgBaseClearButton, c);
        c.gridx = 0;
        c.gridy = 1;
        cfgWriteClearPanel.add(cfgDrinksClearButton, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgWriteClearPanel.add(cfgFoodClearButton, c);
        c.gridx = 1;
        c.gridy = 0;
        cfgWriteClearPanel.add(cfgRecipeClearButton, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgWriteClearPanel.add(cfgDishClearButton, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgWriteClearPanel.add(cfgMenuClearButton, c);
        endPadding.gridx = 1;
        endPadding.gridy = 3;
        cfgWriteClearPanel.add(cfgWriteButton, endPadding);
        endPadding.gridx = 0;
        endPadding.gridy = 3;
        cfgWriteClearPanel.add(buttonBack7, endPadding);

        // Add the panels to the tabbed pane
        cfgTabbedPane.addTab("Specifiche", cfgBasePanel);
        cfgTabbedPane.addTab("Drinks&Foods", cfgDrinksFoodsPanel);
        cfgTabbedPane.addTab("Recipes", cfgRecipesPanel);
        cfgTabbedPane.addTab("Dishes", cfgDishesPanel);
        cfgTabbedPane.addTab("Menus", cfgMenuPanel);
        cfgTabbedPane.addTab("Resoconto", cfgResPanel);
        cfgTabbedPane.addTab("Write&Save", cfgWriteClearPanel);
    }

    private void empInit() {
        //output prenotazioni
        titlePadding.gridx = 0;
        titlePadding.gridy = 0;
        empSeeBookingsPanel.add(empSeeBookText, titlePadding);
        c.gridx = 1;
        c.gridy = 0;
        empSeeBookingsPanel.add(empSeeBookDateInput,c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth=2;
        empSeeBookingsPanel.add(empSeeBookAreaOut,c);
        c.gridwidth=1;
        c.gridx = 0;
        c.gridy = 5;
        empSeeBookingsPanel.add(buttonBack8,c);
        c.gridx = 0;
        c.gridy = 4;
        empSeeBookingsPanel.add(empSeeBookSend,c);
        c.gridx = 0;
        c.gridy = 6;
        empSeeBookingsPanel.add(empSeeBookWrite,c);
        c.gridx = 1;
        c.gridy = 6;
        empSeeBookingsPanel.add(empSeeBookClear,c);

        // input prenotazioni
        titlePadding.gridx = 0;
        titlePadding.gridy = 0;
        empNewBookingPanel.add(empNewBookText,titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        empNewBookingPanel.add(empNewBookDateText,c);
        c.gridx = 1;
        c.gridy = 1;
        empNewBookingPanel.add(empNewBookDateInput,c);
        c.gridx = 0;
        c.gridy = 2;
        empNewBookingPanel.add(empNewBookNameText,c);
        c.gridx = 1;
        c.gridy = 2;
        empNewBookingPanel.add(empNewBookNameInput,c);
        c.gridx = 0;
        c.gridy = 3;
        empNewBookingPanel.add(empNewBookNumText,c);
        c.gridx = 1;
        c.gridy = 3;
        empNewBookingPanel.add(empNewBookNumInput,c);
        c.gridx = 0;
        c.gridy = 4;
        empNewBookingPanel.add(empNewBookOrderText,c);
        c.gridx = 1;
        c.gridy = 4;
        empNewBookingPanel.add(empNewBookMenuBox,c);
        c.gridx = 2;
        c.gridy = 4;
        empNewBookingPanel.add(empNewBookOrderInput,c);
        c.gridx = 2;
        c.gridy = 6;
        empNewBookingPanel.add(empNewBookSend,c);
        c.gridx = 0;
        c.gridy = 6;
        empNewBookingPanel.add(buttonBack9,c);


        empSeeBookDateInput.setLineWrap(true);
        empSeeBookAreaOut.setLineWrap(true);
        empNewBookOrderInput.setLineWrap(true);
        empNewBookNameInput.setLineWrap(true);
        empNewBookDateInput.setLineWrap(true);
        empNewBookNumInput.setLineWrap(true);

        empSeeBookDateInput.setBorder(border);
        empSeeBookAreaOut.setBorder(border);
        empNewBookOrderInput.setBorder(border);
        empNewBookNameInput.setBorder(border);
        empNewBookDateInput.setBorder(border);
        empNewBookNumInput.setBorder(border);

        buttonBack8.addActionListener(back);
        buttonBack9.addActionListener(back);
        buttonBack10.addActionListener(back);
        buttonBack11.addActionListener(back);

        empSeeBookSend.addActionListener(e->{
            String s = empSeeBookDateInput.getText().trim();
            if(Controller.checkDate(s))
            {
                    ctrl.seeBookings(ctrl.inputToDate(s));
            }
        });

        empNewBookMenuBox.addActionListener(e ->
        {
            String selectedItem = (String) empNewBookMenuBox.getSelectedItem();
            empNewBookOrderInput.setText(empNewBookOrderInput.getText() + selectedItem + ":1\n");
        });

        empNewBookSend.addActionListener(e -> {
          ctrl.saveBooking();
        });

        empSeeBookWrite.addActionListener(e -> {
           ctrl.writeBookings();
        });

        empSeeBookWrite.addActionListener(e -> {
            ctrl.clearInfo("bookings");
        });

        empTabbedPane.add("Bookings", empSeeBookingsPanel);
        empTabbedPane.add("New Bookings", empNewBookingPanel);
    }

    // Method to update the UI based on the current state
    private void updateUI() {
        // Clear the frame
        getContentPane().removeAll();

        // Add the appropriate components based on the current state
        if (state == State.LOGIN) {
            getContentPane().add(loginPanel);
        } else if (state == State.MANAGER) {
            getContentPane().add(cfgTabbedPane);
        } else if (state == State.EMPLOYEE) {
            getContentPane().add(empTabbedPane);
        } else if (state == State.WAREHOUSE_WORKER) {
            // todo Add the components for the warehouse worker state
        }

        // Refresh the frame
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void errorSetter(String code) {
        switch (code) {
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
            case "fullRestaurant":
                JOptionPane.showMessageDialog(getContentPane(), "Ristorante pieno o troppo carico",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "thiccMenu":
                JOptionPane.showMessageDialog(getContentPane(), "Il menù è troppo impegnativo, riduci il suo carico",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "nameSameAsDish":
                JOptionPane.showMessageDialog(getContentPane(), "Il menù è troppo impegnativo, riduci il suo carico",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "notFound":
                JOptionPane.showMessageDialog(getContentPane(), "Piatto o menù non trovato",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
        }
        getContentPane().repaint();
    }
}