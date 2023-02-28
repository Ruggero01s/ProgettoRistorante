import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

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

    boolean MenuPermaRadio = false;
    boolean MenuSeasRadio = false;
    boolean DishPermaRadio = false;
    boolean DishSeasRadio = false;

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
    JButton buttonBack12 = new JButton("Back");
    JButton buttonBack13 = new JButton("Back");

    //------------------------------------------------------------------------------------------
    //CONFIG_BASE
    JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    JLabel cfgBaseIndiviualWorkloadAreaText = new JLabel("Carico lavoro max:");
    JLabel cfgBaseDateText = new JLabel("Inserisci data odierna:");
    JLabel cfgBaseSurplusText = new JLabel("Surplus da comprare (%):");
    JButton cfgBaseSendButton = new JButton("Conferma");
    JTextArea cfgBaseInputCap = new JTextArea();
    JTextArea cfgBaseInputIndWork = new JTextArea();
    JTextArea cfgBaseInputDate = new JTextArea();
    JTextArea cfgBaseInputSurplus = new JTextArea();
    public void setDrinkList(String drinkList) {
        this.drinkList = drinkList;
        cfgDrinksAreaOut.setText(this.drinkList);
    }

    public void setFoodsList(String foodsList) {
        this.foodsList = foodsList;
        cfgFoodsAreaOut.setText(this.foodsList);
    }

    //------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
    private String drinkList;
    JLabel cfgDrinksText = new JLabel("Inserisci dati bevanda: (nome : quantità (L))");
    JLabel cfgDrinksTextOut = new JLabel("Elenco dati bevande: (nome : quantità (L))");
    JTextArea cfgDrinksAreaOut = new JTextArea(drinkList);
    JScrollPane cfgDrinksAreaScroll = new JScrollPane(cfgDrinksAreaOut);
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
    JScrollPane cfgFoodsAreaScroll = new JScrollPane(cfgFoodsAreaOut);
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
    JScrollPane cfgRecipeScroll = new JScrollPane(cfgRecipeAreaOut);
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
    JScrollPane cfgDishAreaScroll = new JScrollPane(cfgDishAreaOut);
    JButton cfgDishSendButton = new JButton("Conferma piatto");

    JRadioButton cfgDishPermanentRadio = new JRadioButton("Permanente");
    JRadioButton cfgDishSeasonalRadio = new JRadioButton("Stagionale");
    ButtonGroup cfgDishGroup = new ButtonGroup();

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
    JScrollPane cfgMenuAreaScroll = new JScrollPane(cfgMenuAreaOut);
    JLabel cfgMenuTextDish = new JLabel("Seleziona od inserisci piatti: ");
    JLabel cfgMenuTextDate = new JLabel("Inserisci data di inizio e fine: ");
    JButton cfgMenuSendButton = new JButton("Conferma menu");

    public void setMenuList(String list) {
        this.menuList = list;
        cfgMenuAreaOut.setText(this.menuList);
    }

    JRadioButton cfgMenuPermanentRadio = new JRadioButton("Permanente");
    JRadioButton cfgMenuSeasonalRadio = new JRadioButton("Stagionale");
    ButtonGroup cfgMenuGroup = new ButtonGroup();

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
    JScrollPane cfgResBaseScroll = new JScrollPane(cfgResBaseOut);
    JScrollPane cfgResDrinksScroll = new JScrollPane(cfgResDrinksOut);
    JScrollPane cfgResFoodsScroll = new JScrollPane(cfgResFoodsOut);
    JScrollPane cfgResRecipesScroll = new JScrollPane(cfgResRecipesOut);
    JScrollPane cfgResDishesScroll = new JScrollPane(cfgResDishesOut);
    JScrollPane cfgResMenuScroll = new JScrollPane(cfgResMenuOut);
    JComboBox cfgResDatiMenuBox = new JComboBox();
    JTextArea cfgResDatiMenuOut = new JTextArea();
    JScrollPane cfgResDatiMenuScroll = new JScrollPane(cfgResDatiMenuOut);
    //------------------------------------------------------------------------------------------
    //CONFIG_WRITING AND CLEAR
    JButton cfgBaseClearButton = new JButton("Clear Cap&IndWork");
    JButton cfgDrinksClearButton = new JButton("Clear Drinks");
    JButton cfgFoodClearButton = new JButton("Clear ExtraFoods");
    JButton cfgRecipeClearButton = new JButton("Clear Recipes");
    JButton cfgDishClearButton = new JButton("Clear Dishes");
    JButton cfgMenuClearButton = new JButton("Clear Menus");
    JButton cfgWriteButton = new JButton("Salva ed esci");
    //============================================================================================
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
    JLabel empSeeBookDateText = new JLabel("Data da cercare:");
    JTextArea empSeeBookDateInput = new JTextArea();
    JLabel empSeeBookNameText = new JLabel("Nome:");
    JLabel empSeeBookNumText = new JLabel("Numero:");
    JLabel empSeeBookWorkloadText = new JLabel("Workload:");
    JTextArea empSeeBookNameAreaOut = new JTextArea();
    JTextArea empSeeBookNumAreaOut = new JTextArea();
    JTextArea empSeeBookWorkloadAreaOut = new JTextArea();
    JLabel empSeeBookCapacityTotalText = new JLabel("Posti disponibile:");
    JTextArea empSeeBookCapacityTotalOut = new JTextArea();
    JLabel empSeeBookWorkloadTotalText = new JLabel("Workload disponibile:");
    JTextArea empSeeBookWorkloadTotalOut = new JTextArea();
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

    //===============================================================================================
//===============================================================================================
//===============================================================================================
    //WAREHOUSE
    //WAREHOUSE LIST
    JTabbedPane wareTabbedPane = new JTabbedPane();
    JPanel wareListPanel = new JPanel(new GridBagLayout());
    JLabel wareListText = new JLabel("Lista aggiornata al: ");
    JTextArea wareListOut = new JTextArea();
    JScrollPane wareListScroll = new JScrollPane(wareListOut);
    JLabel wareListMagText = new JLabel("Magazzino al: ");
    JTextArea wareListMagOut = new JTextArea();
    JScrollPane wareListMagScroll = new JScrollPane(wareListMagOut);
    JButton wareListSend = new JButton("Scrivi magazzino");
    //-------------------------------------------------------------------------------------------
    //WAREHOUSE RETURNLIST
    JPanel wareReturnListPanel = new JPanel(new GridBagLayout());
    JLabel wareReturnListText = new JLabel("Lista ritorni: ");
    JTextArea wareReturnListOut = new JTextArea();
    JScrollPane wareReturnListScroll = new JScrollPane(wareReturnListOut);
    JButton wareReturnListSend = new JButton("Conferma");

    //===============================================================================================
//===============================================================================================
    Border border = BorderFactory.createLineBorder(Color.GRAY, 1);


    public SimpleUI(Controller ctrl) {
        this.ctrl = ctrl;
        // Set up the UI components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setVisible(true);
        getContentPane().setBackground(Color.GRAY); //TODO cambiare lo sfondo che fa schifo
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
        wareInit();
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
        cfgBaseInputDate.setLineWrap(true);
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
        cfgBaseInputSurplus.setLineWrap(true);
        cfgRecipeAreaOut.setLineWrap(true);

        cfgBaseInputCap.setBorder(border);
        cfgBaseInputIndWork.setBorder(border);
        cfgBaseInputDate.setBorder(border);
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
        cfgResDatiMenuOut.setBorder(border);
        cfgBaseInputSurplus.setBorder(border);


        cfgResBaseScroll.setPreferredSize(new Dimension(300, 70));
        cfgResDrinksScroll.setPreferredSize(new Dimension(300, 100));
        cfgResFoodsScroll.setPreferredSize(new Dimension(300, 100));
        cfgResRecipesScroll.setPreferredSize(new Dimension(300, 100));
        cfgResDishesScroll.setPreferredSize(new Dimension(300, 100));
        cfgResMenuScroll.setPreferredSize(new Dimension(300, 100));
        cfgResDatiMenuOut.setPreferredSize(new Dimension(300, 100));
        cfgFoodsAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgDrinksAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgRecipeScroll.setPreferredSize(new Dimension(300, 100));
        cfgDishAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgMenuAreaScroll.setPreferredSize(new Dimension(300, 100));

        cfgResBaseScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgResDrinksScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgResFoodsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgResRecipesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgResDishesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgResMenuScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgFoodsAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgDrinksAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgRecipeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgDishAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgMenuAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        cfgResDatiMenuScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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



        cfgWriteButton.addActionListener(e -> ctrl.writeManager());
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

        cfgDishGroup.add(cfgDishPermanentRadio);
        cfgDishGroup.add(cfgDishSeasonalRadio);
        cfgMenuGroup.add(cfgMenuSeasonalRadio);
        cfgMenuGroup.add(cfgMenuPermanentRadio);

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
        cfgBasePanel.add(cfgBaseDateText, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgBasePanel.add(cfgBaseInputDate, c);
        c.gridx = 0;
        c.gridy = 4;
        cfgBasePanel.add(cfgBaseSurplusText, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgBasePanel.add(cfgBaseInputSurplus, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgBasePanel.add(buttonBack1, c);
        c.gridx = 1;
        c.gridy = 5;
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
        cfgRecipesPanel.add(cfgRecipeScroll, c);
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
        c.gridx = 1;
        c.gridy = 4;
        cfgDishesPanel.add(cfgDishPermanentRadio, c);
        cfgDishPermanentRadio.addActionListener(e -> {
            if (DishPermaRadio) {
                DishPermaRadio = false;
                cfgDishGroup.clearSelection();
            } else {
                DishPermaRadio = true;
                DishSeasRadio = false;
            }
        });

        c.gridx = 2;
        c.gridy = 4;
        cfgDishesPanel.add(cfgDishSeasonalRadio, c);
        cfgDishSeasonalRadio.addActionListener(e -> {
            if (DishSeasRadio) {
                DishSeasRadio = false;
                cfgDishGroup.clearSelection();
            } else {
                DishSeasRadio = true;
                DishPermaRadio = false;
            }
        });
        c.gridx = 1;
        c.gridy = 3;
        cfgDishesPanel.add(cfgDishSDateInput, c);
        c.gridx = 2;
        c.gridy = 3;
        cfgDishesPanel.add(cfgDishEDateInput, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgDishesPanel.add(cfgDishTextOut, c);
        c.gridx = 1;
        c.gridy = 5;
        cfgDishesPanel.add(cfgDishAreaScroll, c);
        c.gridx = 0;
        c.gridy = 6;
        cfgDishesPanel.add(buttonBack4, c);
        c.gridx = 2;
        c.gridy = 6;
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
        cfgMenuComboBox.addActionListener(e ->
        {
            String selectedItem = ((String) cfgMenuComboBox.getSelectedItem()).split("-")[0].trim();
            cfgMenuDishesInput.setText(cfgMenuDishesInput.getText() + selectedItem + "\n");
        });

        c.gridx = 2;
        c.gridy = 2;
        cfgMenuPanel.add(cfgMenuDishesInput, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuTextDate, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgMenuPanel.add(cfgMenuPermanentRadio, c);
        cfgMenuPermanentRadio.addActionListener(e -> {
            if (MenuPermaRadio) {
                MenuPermaRadio = false;
                cfgMenuGroup.clearSelection();
            } else {
                MenuPermaRadio = true;
                MenuSeasRadio = false;
            }
        });

        c.gridx = 2;
        c.gridy = 4;
        cfgMenuPanel.add(cfgMenuSeasonalRadio, c);
        cfgMenuSeasonalRadio.addActionListener(e -> {
            if (MenuSeasRadio) {
                MenuSeasRadio = false;
                cfgMenuGroup.clearSelection();
            } else {
                MenuSeasRadio = true;
                MenuPermaRadio = false;
            }
        });

        c.gridx = 1;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuSDateInput, c);
        c.gridx = 2;
        c.gridy = 3;
        cfgMenuPanel.add(cfgMenuEDateInput, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgMenuPanel.add(cfgMenuTextOut, c);
        c.gridx = 1;
        c.gridy = 5;
        cfgMenuPanel.add(cfgMenuAreaScroll, c);
        c.gridx = 0;
        c.gridy = 6;
        cfgMenuPanel.add(buttonBack5, c);
        c.gridx = 2;
        c.gridy = 6;
        cfgMenuPanel.add(cfgMenuSendButton, c);
        cfgMenuSendButton.addActionListener(e -> {
            ctrl.saveMenu();
        });

        //Resconto tab
        titlePadding.gridx = 0;
        titlePadding.gridy = 0;
        cfgResPanel.add(cfgResText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgResPanel.add(cfgResBaseText, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        cfgResPanel.add(cfgResBaseScroll, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        cfgResPanel.add(cfgResDrinksText, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 4;
        cfgResPanel.add(cfgResDrinksScroll, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        cfgResPanel.add(cfgResFoodsText, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = GridBagConstraints.REMAINDER;
        cfgResPanel.add(cfgResFoodsScroll, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        cfgResPanel.add(cfgResRecipesText, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = GridBagConstraints.REMAINDER;
        cfgResPanel.add(cfgResRecipesScroll, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        cfgResPanel.add(cfgResDishesText, c);
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = GridBagConstraints.REMAINDER;
        cfgResPanel.add(cfgResDishesScroll, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 6;
        cfgResPanel.add(cfgResMenuText, c);
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = GridBagConstraints.REMAINDER;
        cfgResPanel.add(cfgResMenuScroll, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 7;
        cfgResPanel.add(cfgResDatiMenuBox, c);
        cfgResDatiMenuBox.addActionListener(e ->
        {
            ctrl.writeMenuComp((String) cfgResDatiMenuBox.getSelectedItem());
        });
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = GridBagConstraints.REMAINDER;
        cfgResPanel.add(cfgResDatiMenuScroll, c);
        c.gridwidth = 1;
        c.gridx = 4;
        c.gridy = 8;
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
        //SeeBooking
        titlePadding.gridx = 0;
        titlePadding.gridy = 0;
        empSeeBookingsPanel.add(empSeeBookText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        empSeeBookingsPanel.add(empSeeBookDateText, c);
        c.gridx = 1;
        c.gridy = 1;
        empSeeBookingsPanel.add(empSeeBookDateInput, c);
        c.gridx = 0;
        c.gridy = 2;
        empSeeBookingsPanel.add(empSeeBookNameText, c);
        c.gridx = 1;
        c.gridy = 2;
        empSeeBookingsPanel.add(empSeeBookNumText, c);
        c.gridx = 2;
        c.gridy = 2;
        empSeeBookingsPanel.add(empSeeBookWorkloadText, c);
        c.gridx = 0;
        c.gridy = 3;
        empSeeBookingsPanel.add(empSeeBookNameAreaOut, c);
        c.gridx = 1;
        c.gridy = 3;
        empSeeBookingsPanel.add(empSeeBookNumAreaOut, c);
        c.gridx = 2;
        c.gridy = 3;
        empSeeBookingsPanel.add(empSeeBookWorkloadAreaOut, c);
        c.gridx = 0;
        c.gridy = 4;
        empSeeBookingsPanel.add(empSeeBookCapacityTotalText, c);
        c.gridx = 1;
        c.gridy = 4;
        empSeeBookingsPanel.add(empSeeBookCapacityTotalOut, c);
        c.gridx = 0;
        c.gridy = 5;
        empSeeBookingsPanel.add(empSeeBookWorkloadTotalText, c);
        c.gridx = 1;
        c.gridy = 5;
        empSeeBookingsPanel.add(empSeeBookWorkloadTotalOut, c);
        c.gridx = 0;
        c.gridy = 6;
        empSeeBookingsPanel.add(buttonBack8, c);
        c.gridx = 1;
        c.gridy = 6;
        empSeeBookingsPanel.add(empSeeBookSend, c);
        empSeeBookSend.addActionListener(e -> {
            String s = empSeeBookDateInput.getText().trim();
            if (Controller.checkDate(s)) {
                ctrl.seeBookings(ctrl.inputToDate(s));
            } else {
                errorSetter("invalidDate");
            }
        });
        c.gridx = 0;
        c.gridy = 7;
        empSeeBookingsPanel.add(empSeeBookWrite, c);
        empSeeBookWrite.addActionListener(e -> {
            ctrl.writeBookings();
        });

        c.gridx = 1;
        c.gridy = 7;
        empSeeBookingsPanel.add(empSeeBookClear, c);
        empSeeBookClear.addActionListener(e -> {
            ctrl.clearInfo("bookings");
        });

        // NewBooking prenotazioni
        titlePadding.gridx = 0;
        titlePadding.gridy = 0;
        empNewBookingPanel.add(empNewBookText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        empNewBookingPanel.add(empNewBookDateText, c);
        c.gridx = 1;
        c.gridy = 1;
        empNewBookingPanel.add(empNewBookDateInput, c);
        c.gridx = 0;
        c.gridy = 2;
        empNewBookingPanel.add(empNewBookNameText, c);
        c.gridx = 1;
        c.gridy = 2;
        empNewBookingPanel.add(empNewBookNameInput, c);
        c.gridx = 0;
        c.gridy = 3;
        empNewBookingPanel.add(empNewBookNumText, c);
        c.gridx = 1;
        c.gridy = 3;
        empNewBookingPanel.add(empNewBookNumInput, c);
        c.gridx = 0;
        c.gridy = 4;
        empNewBookingPanel.add(empNewBookOrderText, c);
        c.gridx = 1;
        c.gridy = 4;
        empNewBookingPanel.add(empNewBookMenuBox, c);
        empNewBookMenuBox.addActionListener(e ->
        {
            String selectedItem = (String) empNewBookMenuBox.getSelectedItem();
            empNewBookOrderInput.setText(empNewBookOrderInput.getText() + selectedItem + ":1\n");
        });
        c.gridx = 2;
        c.gridy = 4;
        empNewBookingPanel.add(empNewBookOrderInput, c);
        c.gridx = 2;
        c.gridy = 6;
        empNewBookingPanel.add(empNewBookSend, c);
        empNewBookSend.addActionListener(e -> {
            ctrl.saveBooking();
        });

        c.gridx = 0;
        c.gridy = 6;
        empNewBookingPanel.add(buttonBack9, c);


        empSeeBookDateInput.setLineWrap(true);
        empSeeBookNameAreaOut.setLineWrap(true);
        empSeeBookNumAreaOut.setLineWrap(true);
        empSeeBookWorkloadAreaOut.setLineWrap(true);
        empSeeBookWorkloadTotalOut.setLineWrap(true);
        empSeeBookCapacityTotalOut.setLineWrap(true);
        empNewBookOrderInput.setLineWrap(true);
        empNewBookNameInput.setLineWrap(true);
        empNewBookDateInput.setLineWrap(true);
        empNewBookNumInput.setLineWrap(true);


        empSeeBookWorkloadTotalOut.setBorder(border);
        empSeeBookCapacityTotalOut.setBorder(border);
        empSeeBookDateInput.setBorder(border);
        empSeeBookNameAreaOut.setBorder(border);
        empSeeBookNumAreaOut.setBorder(border);
        empSeeBookWorkloadAreaOut.setBorder(border);
        empNewBookOrderInput.setBorder(border);
        empNewBookNameInput.setBorder(border);
        empNewBookDateInput.setBorder(border);
        empNewBookNumInput.setBorder(border);

        empSeeBookWorkloadTotalOut.setEditable(false);
        empSeeBookCapacityTotalOut.setEditable(false);
        empSeeBookNameAreaOut.setEditable(false);
        empSeeBookNumAreaOut.setEditable(false);
        empSeeBookWorkloadAreaOut.setEditable(false);


        buttonBack8.addActionListener(back);
        buttonBack9.addActionListener(back);
        buttonBack10.addActionListener(back);
        buttonBack11.addActionListener(back);

        empTabbedPane.add("Bookings", empSeeBookingsPanel);
        empTabbedPane.add("New Bookings", empNewBookingPanel);
    }

    private void wareInit() {
        wareListText.setText(wareListText.getText() + ctrl.getTodayString());
        wareListMagText.setText(wareListMagText.getText() + ctrl.getTodayString());

        wareListScroll.setPreferredSize(new Dimension(300, 100));
        wareListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    
        wareListMagScroll.setPreferredSize(new Dimension(300, 100));
        wareListMagScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        wareReturnListScroll.setPreferredSize(new Dimension(300, 100));
        wareReturnListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        buttonBack12.addActionListener(back);
        buttonBack13.addActionListener(back);

        //LIST PANEL
        c.gridx = 0;
        c.gridy = 0;
        wareListPanel.add(wareListText, c);
        c.gridx = 0;
        c.gridy = 1;
        wareListPanel.add(wareListScroll, c);
        c.gridx = 0;
        c.gridy = 2;
        wareListPanel.add(wareListMagText, c);
        c.gridx = 0;
        c.gridy = 3;
        wareListPanel.add(wareListMagScroll, c);
        c.gridx = 0;
        c.gridy = 4;
        wareListPanel.add(buttonBack12, c);
        c.gridx = 1;
        c.gridy = 4;
        wareListSend.addActionListener(e->{ctrl.writeRegister();});
        wareListPanel.add(wareListSend, c);

        //RETURNLIST PANEL
        c.gridx = 0;
        c.gridy = 0;
        wareReturnListPanel.add(wareReturnListText, c);
        c.gridx = 0;
        c.gridy = 1;
        wareReturnListPanel.add(wareReturnListScroll, c);
        c.gridx = 0;
        c.gridy = 2;
        wareReturnListPanel.add(buttonBack13, c);
        c.gridx = 1;
        c.gridy = 2;
        //wareReturnListSend.addActionListener(e->ctrl.ritorna()); //todo
        wareReturnListPanel.add(wareReturnListSend, c);


        wareTabbedPane.add("Lista della spesa", wareListPanel);
        wareTabbedPane.add("Lista ritorni:", wareReturnListPanel);  //todo cambiare nome
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
            getContentPane().add(wareTabbedPane);
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
            case "noBookings":
                JOptionPane.showMessageDialog(getContentPane(), "Nessuna prenotazione trovata",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "noQuantity":
                JOptionPane.showMessageDialog(getContentPane(), "Quantità di un elemento non valida",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "outOfDate":
                JOptionPane.showMessageDialog(getContentPane(), "Menù o piatto non disponibile in questa data",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "existingName":
                JOptionPane.showMessageDialog(getContentPane(), "Nome già in uso",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
            case "surplusTooGreat":
                JOptionPane.showMessageDialog(getContentPane(), "Surplus troppo grande, max 10%",
                        "Err", JOptionPane.ERROR_MESSAGE);
                break;
        }
        getContentPane().repaint();
    }
}