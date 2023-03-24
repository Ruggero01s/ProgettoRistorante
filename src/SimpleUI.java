import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimpleUI extends JFrame implements ErrorSetter, GUI
{


    // Enum for the different states of the frame
    private enum State {
        PASSWORD,
        LOGIN,
        MANAGER,
        EMPLOYEE,
        WAREHOUSE_WORKER
    }
    private SaveData saver;
    private Login loginner;
    private DataManagement dataManager;
    // Current state of the frame
    private State state;

    boolean MenuPermaRadio = false;
    boolean MenuSeasRadio = false;
    boolean DishPermaRadio = false;
    boolean DishSeasRadio = false;

    private final int WIDTH = 400;

    private final int HEIGHT = 400;
    //GENERAL
    private JButton managerButton = new JButton("Manager");
    private JButton employeeButton = new JButton("Employee");
    private JButton warehouseWorkerButton = new JButton("Warehouse Worker");
    private JPanel loginPanel = new JPanel(new GridBagLayout());
    private JTabbedPane cfgTabbedPane = new JTabbedPane();
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
    JButton buttonBack14 = new JButton("Back");
    JButton buttonBack15 = new JButton("Back");
    
    //------------------------------------------------------------------------------------------
    //PASSWORD
    private JTabbedPane passTabbedPane = new JTabbedPane();
    private JPanel passLoginPanel = new JPanel(new GridBagLayout());
    private JPanel passSavePanel = new JPanel(new GridBagLayout());
    private JLabel passLoginTitle= new JLabel("Inserisci username e password per accedere");
    private JLabel passLoginUserLabel = new JLabel("Inserisci username: ");
    private JButton passExitButton = new JButton("Esci");
    private JButton passWriteButton = new JButton("Salva tutto");
    JTextArea passLoginUserText = new JTextArea();
    private JLabel passLoginPasswordLabel = new JLabel("Inserisci password: ");
    JPasswordField passLoginPasswordField = new JPasswordField();
    private JButton passLoginButton = new JButton("Login");
    private JLabel passSaveTitle= new JLabel("Creazione nuovo utente");
    private JLabel passSaveUsernameLabel = new JLabel("Inserisci username: ");
    JTextArea passSaveUserText = new JTextArea();
    private JLabel passSavePasswordLabel = new JLabel("Inserisci password: ");
    JPasswordField passSavePasswordField = new JPasswordField();
    private JLabel passSavePassword2Label = new JLabel("Conferma password: ");
    JPasswordField passSavePassword2Field = new JPasswordField();

    JCheckBox passManCheck = new JCheckBox("Manager");
    JCheckBox passEmpCheck = new JCheckBox("Employee");
    JCheckBox passWareCheck = new JCheckBox("Warehouse Worker");

    private JButton passSaveButton = new JButton("Salva");
    
    
    //------------------------------------------------------------------------------------------
    //CONFIG_BASE
    JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    JLabel cfgBaseIndiviualWorkloadAreaText = new JLabel("Carico lavoro max:");
    JLabel cfgBaseDateText = new JLabel("Inserisci data odierna:");
    JLabel cfgBaseSurplusText = new JLabel("Surplus da comprare (%):");
    JButton cfgBaseSendButton = new JButton("Conferma");
    JButton cfgBaseNextDayButton = new JButton("Prossimo giorno");
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
    JLabel cfgRecipeTextIngredients = new JLabel("Inserisci ingredienti (ingredienti:quantità:unità): ");
    JTextArea cfgRecipeIngredientsInput = new JTextArea();
    JLabel cfgRecipeTextWorkLoad = new JLabel("Inserisci workload/person: ");
    JTextArea cfgRecipeWorkLoadInput = new JTextArea();
    JLabel cfgRecipeTextOut = new JLabel("Elenco ricette: ");
    JTextArea cfgRecipeAreaOut = new JTextArea();
    JButton cfgRecipeSendButton = new JButton("Conferma ricetta");
    JScrollPane cfgRecipeScroll = new JScrollPane(cfgRecipeAreaOut);
    
    public void setRecipeList(String list) {
        cfgRecipeAreaOut.setText(list);
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
    
    JLabel cfgMenuTextTitle = new JLabel("Inserisci dati menu");
    JLabel cfgMenuTextName = new JLabel("Inserisci nome: ");
    JLabel cfgMenuTextOut = new JLabel("Elenco menu inseriti: ");
    JTextArea cfgMenuAreaOut = new JTextArea();
    JScrollPane cfgMenuAreaScroll = new JScrollPane(cfgMenuAreaOut);
    JLabel cfgMenuTextDish = new JLabel("Seleziona od inserisci piatti: ");
    JLabel cfgMenuTextDate = new JLabel("Inserisci data di inizio e fine: ");
    JButton cfgMenuSendButton = new JButton("Conferma menu");

    public void setMenuList(String list) {
        //------------------------------------------------------------------------------------------
        //CONFIG_MENUS
        cfgMenuAreaOut.setText(list);
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
    JLabel cfgResText = new JLabel("Resoconto:");
    JLabel cfgResBaseText = new JLabel("Dati ristorante:");
    JLabel cfgResDrinksText = new JLabel("Dati bevande:");
    JLabel cfgResFoodsText = new JLabel("Dati cibi extra:");
    JLabel cfgResRecipesText = new JLabel("Dati ricette:");
    JLabel cfgResDishesText = new JLabel("Dati piatti:");
    JLabel cfgResMenuCartaText = new JLabel("Menù alla carta:");
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
    JTextArea cfgResMenuCartaOut = new JTextArea();
    JScrollPane cfgResMenuCartaScroll = new JScrollPane(cfgResMenuCartaOut);
    //------------------------------------------------------------------------------------------
    //CONFIG_WRITING AND CLEAR
    JButton cfgClearButton = new JButton("Clear All");
    JButton cfgWriteButton = new JButton("Salva");
    //============================================================================================
//============================================================================================
//============================================================================================
    //EMPLOYEE
    //EMPLOYEE GENERAL
    JTabbedPane empTabbedPane = new JTabbedPane();
    JPanel empSeeBookingsPanel = new JPanel(new GridBagLayout());
     JPanel empNewBookingPanel = new JPanel(new GridBagLayout());
    //-------------------------------------------------------------------------------------------
    //EMPLOY SEE BOOKINGS
    JLabel empSeeBookText = new JLabel("Prenotazioni:");
    JTextArea empSeeBookBookedDates = new JTextArea();
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
    JButton empSeeBookClear = new JButton("Svuota prenotazioni di questa data");
    JButton empSeeBookClearAll = new JButton("Svuota prenotazioni future");
    
    
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
    JLabel wareReturnListOutText = new JLabel("Lista fine giornata: ");
    JTextArea wareReturnListOut = new JTextArea();
    JScrollPane wareReturnListOutScroll = new JScrollPane(wareReturnListOut);
    JButton wareReturnListSend = new JButton("Conferma");
    JLabel wareReturnListInText = new JLabel("Modifiche (ingrediente:delta): ");
    JTextArea wareReturnListIn = new JTextArea();
    JScrollPane wareReturnListInScroll = new JScrollPane(wareReturnListIn);
//===============================================================================================
//===============================================================================================
    Border border = BorderFactory.createLineBorder(Color.GRAY, 1);


    public SimpleUI(SaveData saver, Login loginner, DataManagement dataManager) {
        this.saver = saver;
        this.loginner = loginner;
        this.dataManager = dataManager;
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
    ActionListener backToLogin = e -> {
        state = State.PASSWORD;
        updateUI();
    };

    public void init(String today) {
        c.insets = new Insets(5, 5, 5, 5); // Top, left, bottom, right padding
        titlePadding.insets = new Insets(0, 5, 20, 5);
        endPadding.insets = new Insets(20, 5, 0, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        endPadding.fill = GridBagConstraints.HORIZONTAL;
    
        passInit();
        logInit();
        cfgInit();
        empInit();
        wareInit(today);
        // Set the initial state
        state = State.PASSWORD;
        updateUI();
    }

    private void passInit()
    {
        
        c.gridx = 0;
        c.gridy = 0;
        passLoginPanel.add(passLoginTitle, c);
        c.gridx = 0;
        c.gridy = 1;
        passLoginPanel.add(passLoginUserLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        passLoginPanel.add(passLoginUserText, c);
        c.gridx = 0;
        c.gridy = 2;
        passLoginPanel.add(passLoginPasswordLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        passLoginPanel.add(passLoginPasswordField, c);
        c.gridx = 1;
        c.gridy = 3;
        passLoginPanel.add(passLoginButton, c);
        c.gridx = 1;
        c.gridy = 4;
        passLoginPanel.add(passExitButton,c);
        c.gridx = 0;
        c.gridy = 4;
        passLoginPanel.add(passWriteButton,c);
        passWriteButton.addActionListener(e -> {
            dataManager.writeManager();
            dataManager.writeBookings();
            dataManager.writeRegister();
        });

        passExitButton.addActionListener(e -> System.exit(1));
        
        c.gridx = 0;
        c.gridy = 0;
        passSavePanel.add(passSaveTitle,c);
        c.gridx = 0;
        c.gridy = 1;
        passSavePanel.add(passSaveUsernameLabel,c);
        c.gridx = 1;
        c.gridy = 1;
        passSavePanel.add(passSaveUserText,c);
        c.gridx = 0;
        c.gridy = 2;
        passSavePanel.add(passSavePasswordLabel,c);
        c.gridx = 1;
        c.gridy = 2;
        passSavePanel.add(passSavePasswordField,c);
        c.gridx = 0;
        c.gridy = 3;
        passSavePanel.add(passSavePassword2Label,c);
        c.gridx = 1;
        c.gridy = 3;
        passSavePanel.add(passSavePassword2Field,c);
        c.gridx = 0;
        c.gridy = 4;
        passSavePanel.add(passManCheck,c);
        c.gridx = 1;
        c.gridy = 4;
        passSavePanel.add(passEmpCheck,c);
        c.gridx = 2;
        c.gridy = 4;
        passSavePanel.add(passWareCheck,c);
        c.gridx = 1;
        c.gridy = 5;
        passSavePanel.add(passSaveButton,c);


       
        passLoginUserText.setLineWrap(true);
        passSaveUserText.setLineWrap(true);
        passLoginUserText.setBorder(border);
        passSaveUserText.setBorder(border);
        passTabbedPane.addTab("Login",passLoginPanel);
        passTabbedPane.addTab("Sign Up", passSavePanel);
        passSaveButton.addActionListener(e -> {
            if(loginner.saveUser(passSaveUserText.getText().trim(),Arrays.toString(passSavePasswordField.getPassword()).trim(),Arrays.toString(passSavePassword2Field.getPassword()).trim(),passManCheck.isSelected(),passEmpCheck.isSelected(),passWareCheck.isSelected()))
            {
                passSaveUserText.setText(Model.CLEAR);
                passSavePasswordField.setText(Model.CLEAR);
                passSavePassword2Field.setText(Model.CLEAR);
            }
        });
        passLoginButton.addActionListener(e ->{
            if(loginner.login(passLoginUserText.getText().trim(), Arrays.toString(passLoginPasswordField.getPassword()).trim()))
            {
                passLoginPasswordField.setText(Model.CLEAR);
                passLoginUserText.setText(Model.CLEAR);
            }
        });
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
    
        c.gridx = 1;
        c.gridy = 2;
        loginPanel.add(buttonBack14, c);

        managerButton.addActionListener(e -> {
            if(loginner.checkPermission("manager"))
            {
                state = State.MANAGER;
                updateUI();
            }
            else
                errorSetter(Controller.NO_PERMISSION);
        });
        employeeButton.addActionListener(e -> {
            if(loginner.checkPermission("employee"))
            {
                state = State.EMPLOYEE;
                updateUI();
            }
            else
                errorSetter(Controller.NO_PERMISSION);
        });
        warehouseWorkerButton.addActionListener(e -> {
            if(loginner.checkPermission("warehouse worker"))
            {
                state = State.WAREHOUSE_WORKER;
                updateUI();
            }
            else
                errorSetter(Controller.NO_PERMISSION);
        });
        buttonBack14.addActionListener(backToLogin);
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
        cfgResMenuCartaOut.setBorder(border);
        cfgBaseInputSurplus.setBorder(border);


        cfgResBaseScroll.setPreferredSize(new Dimension(400, 70));
        cfgResDrinksScroll.setPreferredSize(new Dimension(400, 100));
        cfgResFoodsScroll.setPreferredSize(new Dimension(400, 100));
        cfgResRecipesScroll.setPreferredSize(new Dimension(300, 100));
        cfgResDishesScroll.setPreferredSize(new Dimension(300, 100));
        cfgResMenuScroll.setPreferredSize(new Dimension(300, 100));
        cfgResDatiMenuOut.setPreferredSize(new Dimension(300, 100));
        cfgFoodsAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgDrinksAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgRecipeScroll.setPreferredSize(new Dimension(300, 100));
        cfgDishAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgMenuAreaScroll.setPreferredSize(new Dimension(300, 100));
        cfgResMenuCartaScroll.setPreferredSize(new Dimension(400, 70));

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
        cfgResMenuCartaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        cfgResMenuCartaOut.setEditable(false);


        cfgWriteButton.addActionListener(e -> dataManager.writeManager());
        cfgClearButton.addActionListener(e -> dataManager.clearInfo());
        cfgClearButton.setBackground(Color.RED);

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
        cfgBaseSendButton.addActionListener(e -> saver.saveConfig(cfgBaseInputCap.getText(),
                cfgBaseInputIndWork.getText(),
                cfgBaseInputSurplus.getText(),
                cfgBaseInputDate.getText().trim()));
        cfgBasePanel.add(cfgBaseSendButton, c);
        c.gridx = 2;
        c.gridy = 5;
        cfgBaseNextDayButton.addActionListener(e -> dataManager.nextDay());
        cfgBasePanel.add(cfgBaseNextDayButton, c);

        // Drinks & Food

        c.gridx = 0;
        c.gridy = 0;
        cfgDrinksFoodsPanel.add(cfgDrinksText, c);
        c.gridx = 1;
        c.gridy = 0;
        cfgDrinksFoodsPanel.add(cfgDrinksInput, c);
        c.gridx = 2;
        c.gridy = 0;
        cfgDrinksSendButton.addActionListener(e -> saver.saveDrinks(cfgDrinksInput.getText()));
        cfgDrinksFoodsPanel.add(cfgDrinksSendButton, c);
        c.gridx = 0;
        c.gridy = 1;
        cfgDrinksFoodsPanel.add(cfgFoodText, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgDrinksFoodsPanel.add(cfgFoodsInput, c);
        c.gridx = 2;
        c.gridy = 1;
        cfgFoodSendButton.addActionListener(e -> saver.saveFoods(cfgFoodsInput.getText()));
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
        cfgRecipeSendButton.addActionListener(e -> saver.saveRecipe(cfgRecipeNameInput.getText(),
                                                     cfgRecipeIngredientsInput.getText(),
                                                     cfgRecipePortionsInput.getText(),
                                                     cfgRecipeWorkLoadInput.getText()));
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
        cfgDishSendButton.addActionListener(e -> saver.saveDish(cfgDishNameInput.getText(),
                Objects.requireNonNull(cfgDishComboBox.getSelectedItem()).toString().split("-")[0].trim(),
                cfgDishSDateInput.getText(),
                cfgDishEDateInput.getText(),
                cfgDishPermanentRadio.isSelected(),
                cfgDishSeasonalRadio.isSelected()));

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
            String selectedItem = ((String) Objects.requireNonNull(cfgMenuComboBox.getSelectedItem())).split("-")[0].trim();
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
        cfgMenuSendButton.addActionListener(e -> saver.saveMenu(cfgMenuNameInput.getText(),
                cfgMenuDishesInput.getText(),
                cfgMenuSDateInput.getText(),
                cfgMenuEDateInput.getText(),
                cfgMenuPermanentRadio.isSelected(),
                cfgMenuSeasonalRadio.isSelected()));

        //Resconto tab
        titlePadding.gridx = 0;
        titlePadding.gridy = 0;
        cfgResPanel.add(cfgResText, titlePadding);
        c.gridx = 0;
        c.gridy = 1;
        cfgResPanel.add(cfgResBaseText, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgResPanel.add(cfgResBaseScroll, c);
        c.gridx = 0;
        c.gridy = 2;
        cfgResPanel.add(cfgResDrinksText, c);
        c.gridx = 1;
        c.gridy = 2;
        cfgResPanel.add(cfgResDrinksScroll, c);
        c.gridx = 0;
        c.gridy = 3;
        cfgResPanel.add(cfgResFoodsText, c);
        c.gridx = 1;
        c.gridy = 3;
        cfgResPanel.add(cfgResFoodsScroll, c);
        c.gridx = 0;
        c.gridy = 4;
        cfgResPanel.add(cfgResRecipesText, c);
        c.gridx = 1;
        c.gridy = 4;
        cfgResPanel.add(cfgResRecipesScroll, c);
        c.gridx = 0;
        c.gridy = 5;
        cfgResPanel.add(cfgResDishesText, c);
        c.gridx = 1;
        c.gridy = 5;
        cfgResPanel.add(cfgResDishesScroll, c);
        c.gridx = 0;
        c.gridy = 6;
        cfgResPanel.add(cfgResMenuText, c);
        c.gridx = 1;
        c.gridy = 6;
        cfgResPanel.add(cfgResMenuScroll, c);
        c.gridx = 0;
        c.gridy = 7;
        cfgResPanel.add(cfgResMenuCartaText, c);
        c.gridx = 1;
        c.gridy = 7;
        cfgResPanel.add(cfgResMenuCartaScroll, c);
        c.gridx = 0;
        c.gridy = 8;
        cfgResPanel.add(cfgResDatiMenuBox, c);
        cfgResDatiMenuBox.addActionListener(e ->
                dataManager.writeMenuComp((String) cfgResDatiMenuBox.getSelectedItem()));
        c.gridx = 1;
        c.gridy = 8;
        cfgResPanel.add(cfgResDatiMenuScroll, c);
        c.gridx = 4;
        c.gridy = 9;
        cfgResPanel.add(buttonBack6, c);


        // Write and Save
        c.gridx = 0;
        c.gridy = 0;
        cfgWriteClearPanel.add(cfgClearButton, c);
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
        empSeeBookingsPanel.add(empSeeBookBookedDates, c);
        c.gridx = 0;
        c.gridy = 2;
        empSeeBookingsPanel.add(empSeeBookDateText, c);
        c.gridx = 1;
        c.gridy = 2;
        empSeeBookingsPanel.add(empSeeBookDateInput, c);
        c.gridx = 0;
        c.gridy = 3;
        empSeeBookingsPanel.add(empSeeBookNameText, c);
        c.gridx = 1;
        c.gridy = 3;
        empSeeBookingsPanel.add(empSeeBookNumText, c);
        c.gridx = 2;
        c.gridy = 3;
        empSeeBookingsPanel.add(empSeeBookWorkloadText, c);
        c.gridx = 0;
        c.gridy = 4;
        empSeeBookingsPanel.add(empSeeBookNameAreaOut, c);
        c.gridx = 1;
        c.gridy = 4;
        empSeeBookingsPanel.add(empSeeBookNumAreaOut, c);
        c.gridx = 2;
        c.gridy = 4;
        empSeeBookingsPanel.add(empSeeBookWorkloadAreaOut, c);
        c.gridx = 0;
        c.gridy = 5;
        empSeeBookingsPanel.add(empSeeBookCapacityTotalText, c);
        c.gridx = 1;
        c.gridy = 5;
        empSeeBookingsPanel.add(empSeeBookCapacityTotalOut, c);
        c.gridx = 0;
        c.gridy = 6;
        empSeeBookingsPanel.add(empSeeBookWorkloadTotalText, c);
        c.gridx = 1;
        c.gridy = 6;
        empSeeBookingsPanel.add(empSeeBookWorkloadTotalOut, c);
        c.gridx = 0;
        c.gridy = 7;
        empSeeBookingsPanel.add(buttonBack8, c);
        c.gridx = 1;
        c.gridy = 7;
        empSeeBookingsPanel.add(empSeeBookSend, c);
        empSeeBookSend.addActionListener(e -> {
            String s = empSeeBookDateInput.getText().trim();
            if (Controller.checkDate(s)) {
                dataManager.seeBookings(dataManager.inputToDate(s));
            } else {
                errorSetter(Controller.INVALID_DATE);
            }
        });
        c.gridx = 2;
        c.gridy = 7;
        empSeeBookingsPanel.add(empSeeBookWrite, c);
        empSeeBookWrite.addActionListener(e -> dataManager.writeBookings());

        c.gridx = 1;
        c.gridy = 8;
        empSeeBookingsPanel.add(empSeeBookClear, c);
        empSeeBookClear.addActionListener(e ->
                {
                    if(dataManager.clearBookings(dataManager.inputToDate(empSeeBookDateInput.getText())))
                        empSeeBookDateInput.setText("");
                }
        );
        c.gridx = 2;
        c.gridy = 8;
        empSeeBookingsPanel.add(empSeeBookClearAll, c);
        empSeeBookClearAll.addActionListener(e -> dataManager.clearBookings());
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
        empNewBookSend.addActionListener(e ->
        {
            if(saver.saveBooking(empNewBookNameInput.getText().trim(),empNewBookDateInput.getText().trim(),Integer.parseInt(empNewBookNumInput.getText().trim()),empNewBookOrderInput.getText().trim()))
            {
                empNewBookNameInput.setText("");
                empNewBookDateInput.setText("");
                empNewBookNumInput.setText("");
                empNewBookOrderInput.setText("");
            }
        });

        c.gridx = 0;
        c.gridy = 6;
        empNewBookingPanel.add(buttonBack9, c);


        empSeeBookDateInput.setLineWrap(true);
        empSeeBookBookedDates.setLineWrap(true);
        empSeeBookNameAreaOut.setLineWrap(true);
        empSeeBookNumAreaOut.setLineWrap(true);
        empSeeBookWorkloadAreaOut.setLineWrap(true);
        empSeeBookWorkloadTotalOut.setLineWrap(true);
        empSeeBookCapacityTotalOut.setLineWrap(true);
        empNewBookOrderInput.setLineWrap(true);
        empNewBookNameInput.setLineWrap(true);
        empNewBookDateInput.setLineWrap(true);
        empNewBookNumInput.setLineWrap(true);

        empSeeBookBookedDates.setBorder(border);
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

        empSeeBookBookedDates.setEditable(false);
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

    private void wareInit(String today) {
        wareListText.setText("Lista aggiornata al " + today);
        wareListMagText.setText("Magazzino aggiornato al " + today);

        wareListScroll.setPreferredSize(new Dimension(400, 100));
        wareListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    
        wareListMagScroll.setPreferredSize(new Dimension(400, 100));
        wareListMagScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        wareReturnListOutScroll.setPreferredSize(new Dimension(400, 100));
        wareReturnListOutScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        wareReturnListInScroll.setPreferredSize(new Dimension(400, 100));
        wareReturnListInScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        wareListOut.setEditable(false);
        wareListMagOut.setEditable(false);
        wareReturnListOut.setEditable(false);

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
        wareListSend.addActionListener(e-> dataManager.writeRegister());
        wareListPanel.add(wareListSend, c);

        //RETURNLIST PANEL
        c.gridx = 0;
        c.gridy = 0;
        wareReturnListPanel.add(wareReturnListOutText, c);
        c.gridx = 0;
        c.gridy = 1;
        wareReturnListOut.setText(dataManager.setToString(dataManager.getModel().getRegistroAfterMeal()));
        wareReturnListPanel.add(wareReturnListOutScroll, c);
        c.gridx = 0;
        c.gridy = 2;
        wareReturnListPanel.add(wareReturnListInText, c);
        c.gridx = 0;
        c.gridy = 3;
        wareReturnListPanel.add(wareReturnListInScroll, c);
        c.gridx = 0;
        c.gridy = 4;
        wareReturnListPanel.add(buttonBack13, c);
        c.gridx = 1;
        c.gridy = 4;
        wareReturnListSend.addActionListener(e->
        {
            if (dataManager.warehouseChanges(wareReturnListIn.getText().trim()))
            {
                wareReturnListIn.setText("");
            }
        });
        wareReturnListPanel.add(wareReturnListSend, c);


        wareTabbedPane.add("Lista della spesa", wareListPanel);
        wareTabbedPane.add("Lista fine giornata:", wareReturnListPanel);
    }

    // Method to update the UI based on the current state
    private void updateUI() {
        // Clear the frame
        getContentPane().removeAll();

        // Add the appropriate components based on the current state
        switch (state)
        {
            case PASSWORD -> getContentPane().add(passTabbedPane);
            case LOGIN -> getContentPane().add(loginPanel);
            case MANAGER -> getContentPane().add(cfgTabbedPane);
            case EMPLOYEE -> getContentPane().add(empTabbedPane);
            case WAREHOUSE_WORKER -> getContentPane().add(wareTabbedPane);
        }

        // Refresh the frame
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void updateConfig(List<String> configState) {
        cfgResBaseOut.setText(configState.get(0));
        cfgBaseInputCap.setText(configState.get(1));
        cfgBaseInputIndWork.setText(configState.get(2));
        cfgBaseInputDate.setText(configState.get(3));
        cfgBaseInputSurplus.setText(configState.get(4));
    }

    public void updateDrinks(String drinks) {
        setDrinkList(drinks);
        cfgResDrinksOut.setText(drinks);
    }

    public void updateFoods(String foods) {
        setFoodsList(foods);
        cfgResFoodsOut.setText(foods);
    }

    public void updateRecipes(String[] recipes) {
        cfgRecipeNameInput.setText(Model.CLEAR);
        cfgRecipeIngredientsInput.setText(Model.CLEAR);
        cfgRecipePortionsInput.setText(Model.CLEAR);
        cfgRecipeWorkLoadInput.setText(Model.CLEAR);
        DefaultComboBoxModel<String> model;
        if(recipes.length==0)
        {
            String[] noRecipe = {"Non ci sono ricette inserite"};
            model = new DefaultComboBoxModel<>(noRecipe);
            cfgDishComboBox.setModel(model);
            cfgResRecipesOut.setText(noRecipe[0]);
            setRecipeList(Model.CLEAR);
        }
        else{
            model = new DefaultComboBoxModel<>(recipes);
            cfgDishComboBox.setModel(model);

            StringBuilder compactedArray = new StringBuilder();
            for (String s : recipes) {
                compactedArray.append(s).append("\n");
            }
            cfgResRecipesOut.setText(compactedArray.toString().trim());
            setRecipeList(compactedArray.toString().trim());
        }
    }
    
    
    public void updateDishes(String[] dishes) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dishes);
        cfgMenuComboBox.setModel(model);
    
        StringBuilder compactedArray = new StringBuilder();
        for (String s : dishes)
        {
            compactedArray.append(s).append("\n");
        }
        cfgResDishesOut.setText(compactedArray.toString().trim());
        setDishList(compactedArray.toString().trim());
        cfgDishNameInput.setText(Model.CLEAR);
        cfgDishSDateInput.setText(Model.CLEAR);
        cfgDishEDateInput.setText(Model.CLEAR);
    }
    
    public void updateMenuCarta(String menuCarta) {
        cfgResMenuCartaOut.setText(menuCarta);
    }
    
    public void updateMenus(String menus) {
        cfgResMenuOut.setText(menus);
        setMenuList(menus);
        
        cfgMenuNameInput.setText(Model.CLEAR);
        cfgMenuDishesInput.setText(Model.CLEAR);
        cfgMenuSDateInput.setText(Model.CLEAR);
        cfgMenuEDateInput.setText(Model.CLEAR);
    }
    
    public void updateMenuBoxes(String[] menus) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(menus);
        empNewBookMenuBox.setModel(model);
        cfgResDatiMenuBox.setModel(model);
    }
    
    /**
     * Cambio lo satto della GUI dopo il login
     */
    public void login()
    {
        state=State.LOGIN;
        updateUI();
    }
    
    /**
     * Metodo che aggiorna i dati nel cambio di giorno
     * @param today data di oggi
     */
    public void nextDay(String today)
    {
        cfgBaseInputDate.setText(today);
        wareListText.setText("Lista aggiornata al " + today);
        wareListMagText.setText("Magazzino aggiornato al " + today);
        wareReturnListOut.setText("");
    }
    
    /**
     * Metodo che aggiorna il magazzino
     * @param out magazzino sotto forma di stringa
     */
    public void updateWareReturnList (String out)
    {
        wareReturnListOut.setText(out);
    }
    
    /**
     * Metodo che aggiorna il magazzino e la lista della spesa
     * @param groceryList lista della spesa sotto forma di testo
     * @param register magazzino sotto forma di testo
     */
    public void updateWare(String groceryList, String register)
    {
        wareListOut.setText(groceryList);
        wareListMagOut.setText(register);
    }
    
    /**
     * Stampa un elenco di prenotazioni
     * @param name elenco di nomi
     * @param number elenco di coperti
     * @param work elenco di workload
     * @param capacity capacità rimasta in quel giorno
     * @param workload workload rimasto per quel giorno
     */
    public void updateBooking (String name, String number, String work, String capacity, String workload)
    {
        //elenco di stringhe da stampare in GUI
        empSeeBookNameAreaOut.setText(name);
        empSeeBookNumAreaOut.setText(number);
        empSeeBookWorkloadAreaOut.setText(work);
        //capacity e workload rimanenti per quel giorno
        empSeeBookCapacityTotalOut.setText(capacity);
        empSeeBookWorkloadTotalOut.setText(workload);
    }

    public void updateBookedDates(String dates)
    {
        empSeeBookBookedDates.setText(dates);
    }

    /**
     * Metodo che stampa i piatti di un menu
     * @param menu elenco di piatti
     */
    public void selectedMenu(String menu)
    {
        cfgResDatiMenuOut.setText(menu);
    }
    
    public void errorSetter(int code)
    {
        switch (code)
        {
            case 0 -> JOptionPane.showMessageDialog(getContentPane(), "Numero inserito < 0", "Err", JOptionPane.ERROR_MESSAGE);
            case 1 -> JOptionPane.showMessageDialog(getContentPane(), "Formato incorretto", "Err", JOptionPane.ERROR_MESSAGE);
            case 2 -> JOptionPane.showMessageDialog(getContentPane(), "Inserisci prima una ricetta!", "Err", JOptionPane.ERROR_MESSAGE);
            case 3 -> JOptionPane.showMessageDialog(getContentPane(), "Piatto non trovato", "Err", JOptionPane.ERROR_MESSAGE);
            case 4 -> JOptionPane.showMessageDialog(getContentPane(), "Inserisci almeno un piatto!", "Err", JOptionPane.ERROR_MESSAGE);
            case 5 -> JOptionPane.showMessageDialog(getContentPane(), "Data non valida","Err", JOptionPane.ERROR_MESSAGE);
            case 6 -> JOptionPane.showMessageDialog(getContentPane(), "Ristorante pieno o troppo carico", "Err", JOptionPane.ERROR_MESSAGE);
            case 7 -> JOptionPane.showMessageDialog(getContentPane(), "Il menù è troppo impegnativo, riduci il suo carico", "Err", JOptionPane.ERROR_MESSAGE);
            case 8 -> JOptionPane.showMessageDialog(getContentPane(), "Non è possibile avere un omonimia tra piatti e menu", "Err", JOptionPane.ERROR_MESSAGE);
            case 9 -> JOptionPane.showMessageDialog(getContentPane(), "Piatto o menù non trovato", "Err", JOptionPane.ERROR_MESSAGE);
            case 10 -> JOptionPane.showMessageDialog(getContentPane(), "Nessuna prenotazione trovata", "Err", JOptionPane.ERROR_MESSAGE);
            case 11 -> JOptionPane.showMessageDialog(getContentPane(), "Quantità di un elemento non valida", "Err", JOptionPane.ERROR_MESSAGE);
            case 12 -> JOptionPane.showMessageDialog(getContentPane(), "Menù o piatto non disponibile in questa data", "Err", JOptionPane.ERROR_MESSAGE);
            case 13 -> JOptionPane.showMessageDialog(getContentPane(), "Nome già in uso", "Err", JOptionPane.ERROR_MESSAGE);
            case 14 -> JOptionPane.showMessageDialog(getContentPane(), "Surplus troppo grande, max 10%", "Err", JOptionPane.ERROR_MESSAGE);
            case 15 -> JOptionPane.showMessageDialog(getContentPane(), "Ingrediente non trovato", "Err", JOptionPane.ERROR_MESSAGE);
            case 16 -> JOptionPane.showMessageDialog(getContentPane(), "Quantità non valida", "Err", JOptionPane.ERROR_MESSAGE);
            case 17 -> JOptionPane.showMessageDialog(getContentPane(), "Non è possibile prenotare per questa data", "Err", JOptionPane.ERROR_MESSAGE);
            case 18 -> JOptionPane.showMessageDialog(getContentPane(), "Workload troppo alto", "Err", JOptionPane.ERROR_MESSAGE);
            case 19 -> JOptionPane.showMessageDialog(getContentPane(), "Deve esserci almeno un piatto o menù per persona", "Err", JOptionPane.ERROR_MESSAGE);
            case 20 -> JOptionPane.showMessageDialog(getContentPane(), "Password non corretta", "Err", JOptionPane.ERROR_MESSAGE);
            case 21 -> JOptionPane.showMessageDialog(getContentPane(), "Username non corretto", "Err", JOptionPane.ERROR_MESSAGE);
            case 22 -> JOptionPane.showMessageDialog(getContentPane(), "Seleziona almeno un ruolo", "Err", JOptionPane.ERROR_MESSAGE);
            case 23 -> JOptionPane.showMessageDialog(getContentPane(), "Non hai i permessi per entrare in questa finestra", "Err", JOptionPane.ERROR_MESSAGE);
            case 24 -> JOptionPane.showMessageDialog(getContentPane(), "L'input è vuoto", "Err", JOptionPane.ERROR_MESSAGE);
            case 25 -> JOptionPane.showMessageDialog(getContentPane(), "Unità di misura errata", "Err", JOptionPane.ERROR_MESSAGE);
            default -> JOptionPane.showMessageDialog(getContentPane(), "Errore", "Err", JOptionPane.ERROR_MESSAGE);
        }
        getContentPane().repaint();
    }
}