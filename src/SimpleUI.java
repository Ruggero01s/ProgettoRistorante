import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimpleUI extends JFrame implements ErrorSetter, GUI
{
    private final String BLANK = "";

    // Enum per gli stati della UI
    private enum State {
        PASSWORD,
        LOGIN,
        MANAGER,
        EMPLOYEE,
        WAREHOUSE_WORKER
    }

    //interfacce usate
    private final SaveData saver;
    private final Login loginner;
    private final DataManagement dataManager;

    // variabile per lo stato
    private State state;

    //usate per permettere deselezione di radioButtons
    boolean MenuPermaRadio = false;
    boolean MenuSeasRadio = false;
    boolean DishPermaRadio = false;
    boolean DishSeasRadio = false;

   //Inizio variabili "grafiche"
    //GENERAL
    private final JButton managerButton = new JButton("Manager");
    private final JButton employeeButton = new JButton("Employee");
    private final JButton warehouseWorkerButton = new JButton("Warehouse Worker");
    private final JPanel loginPanel = new JPanel(new GridBagLayout());

    private final JLabel roleText = new JLabel("Scegli il tuo ruolo");

    private final GridBagConstraints c = new GridBagConstraints();
    private final GridBagConstraints titlePadding = new GridBagConstraints();
    private final GridBagConstraints endPadding = new GridBagConstraints();

    //I vari bottoni "Back", servono uno per panel perchè non si può aggiungere lo stesso oggetto ad un tabbed pane in più tab diversi
    private final JButton buttonBack1 = new JButton("Back");
    private final JButton buttonBack2 = new JButton("Back");
    private final JButton buttonBack3 = new JButton("Back");
    private final JButton buttonBack4 = new JButton("Back");
    private final JButton buttonBack5 = new JButton("Back");
    private final JButton buttonBack6 = new JButton("Back");
    private final JButton buttonBack7 = new JButton("Back");
    private final JButton buttonBack8 = new JButton("Back");
    private final JButton buttonBack9 = new JButton("Back");
    private final JButton buttonBack10 = new JButton("Back");
    private final JButton buttonBack11 = new JButton("Back");
    private final JButton buttonBack12 = new JButton("Back");
    private final JButton buttonBack13 = new JButton("Back");
    private final JButton buttonBack14 = new JButton("Back");
    
    //------------------------------------------------------------------------------------------
    //PASSWORD
    private final JTabbedPane passTabbedPane = new JTabbedPane();
    private final JPanel passLoginPanel = new JPanel(new GridBagLayout());
    private final JPanel passSavePanel = new JPanel(new GridBagLayout());
    private final JLabel passLoginTitle= new JLabel("Inserisci username e password per accedere");
    private final JLabel passLoginUserLabel = new JLabel("Inserisci username: ");
    private final JButton passExitButton = new JButton("Esci");
    private final JButton passWriteButton = new JButton("Salva tutto");
    private final JTextArea passLoginUserText = new JTextArea();
    private final JLabel passLoginPasswordLabel = new JLabel("Inserisci password: ");
    private final JPasswordField passLoginPasswordField = new JPasswordField();
    private final JButton passLoginButton = new JButton("Login");
    private final JLabel passSaveTitle= new JLabel("Creazione nuovo utente");
    private final JLabel passSaveUsernameLabel = new JLabel("Inserisci username: ");
    private final JTextArea passSaveUserText = new JTextArea();
    private final JLabel passSavePasswordLabel = new JLabel("Inserisci password: ");
    private final JPasswordField passSavePasswordField = new JPasswordField();
    private final JLabel passSavePassword2Label = new JLabel("Conferma password: ");
    private final JPasswordField passSavePassword2Field = new JPasswordField();

    private final JCheckBox passManCheck = new JCheckBox("Manager");
    private final JCheckBox passEmpCheck = new JCheckBox("Employee");
    private final JCheckBox passWareCheck = new JCheckBox("Warehouse Worker");

    private final JButton passSaveButton = new JButton("Salva");
    
    
    //------------------------------------------------------------------------------------------
    //CONFIG BASE
    private final JTabbedPane cfgTabbedPane = new JTabbedPane();
    private final JPanel cfgBasePanel = new JPanel(new GridBagLayout());
    private final JPanel cfgDrinksFoodsPanel = new JPanel(new GridBagLayout());
    private final JPanel cfgRecipesPanel = new JPanel(new GridBagLayout());
    private final JPanel cfgDishesPanel = new JPanel(new GridBagLayout());
    private final JPanel cfgMenuPanel = new JPanel(new GridBagLayout());
    private final JPanel cfgResPanel = new JPanel(new GridBagLayout());
    private final JPanel cfgWriteClearPanel = new JPanel(new GridBagLayout());
    //CONFIG_BASE
    private final JLabel cfgBaseText = new JLabel("Inserisci dati ristorante:");
    private final JLabel cfgBaseCapacityText = new JLabel("Posti a sedere:");
    private final JLabel cfgBaseIndividualWorkloadAreaText = new JLabel("Carico lavoro individuale max:");
    private final JLabel cfgBaseDateText = new JLabel("Inserisci data odierna:");
    private final JLabel cfgBaseSurplusText = new JLabel("Surplus da comprare (%):");
    private final JButton cfgBaseSendButton = new JButton("Conferma");
    private final JButton cfgBaseNextDayButton = new JButton("Prossimo giorno");
    private final JTextArea cfgBaseInputCap = new JTextArea();
    private final JTextArea cfgBaseInputIndWork = new JTextArea();
    private final JTextArea cfgBaseInputDate = new JTextArea();
    private final JTextArea cfgBaseInputSurplus = new JTextArea();

    //------------------------------------------------------------------------------------------
    //CONFIG_DRINKS
    private final JLabel cfgDrinksText = new JLabel("Inserisci dati bevanda: (nome:quantità:unità): ");
    private final JLabel cfgDrinksTextOut = new JLabel("Elenco dati bevande: (nome:quantità:unità): ");
    private final JTextArea cfgDrinksAreaOut = new JTextArea();
    private final JScrollPane cfgDrinksAreaScroll = new JScrollPane(cfgDrinksAreaOut);
    private final JButton cfgDrinksSendButton = new JButton("Inserisci");
    private final JTextArea cfgDrinksInput = new JTextArea();
    //------------------------------------------------------------------------------------------
    //CONFIG_EXTRAFOODS
    private final JLabel cfgFoodText = new JLabel("Inserisci dati generi alimentari extra: (nome:quantità:unità): ");
    private final JButton cfgFoodSendButton = new JButton("Inserisci");
    private final JTextArea cfgFoodsInput = new JTextArea();
    private final JLabel cfgFoodsTextOut = new JLabel("Elenco dati cibi extra: (nome:quantità:unità): ");
    private final JTextArea cfgFoodsAreaOut = new JTextArea();
    private final JScrollPane cfgFoodsAreaScroll = new JScrollPane(cfgFoodsAreaOut);
    //------------------------------------------------------------------------------------------
    //CONFIG_RECIPES
    private final JLabel cfgRecipeTextTitle = new JLabel("Inserisci dati ricetta");
    private final JLabel cfgRecipeTextName = new JLabel("Inserisci nome: ");
    private final JTextArea cfgRecipeNameInput = new JTextArea();
    private final JLabel cfgRecipeTextPortions = new JLabel("Inserisci porzioni: ");
    private final JTextArea cfgRecipePortionsInput = new JTextArea();
    private final JLabel cfgRecipeTextIngredients = new JLabel("Inserisci ingredienti (ingredienti:quantità:unità): ");
    private final JTextArea cfgRecipeIngredientsInput = new JTextArea();
    private final JLabel cfgRecipeTextWorkLoad = new JLabel("Inserisci workload/person: ");
    private final JTextArea cfgRecipeWorkLoadInput = new JTextArea();
    private final JLabel cfgRecipeTextOut = new JLabel("Elenco ricette: ");
    private final JTextArea cfgRecipeAreaOut = new JTextArea();
    private final JButton cfgRecipeSendButton = new JButton("Conferma ricetta");
    private final JScrollPane cfgRecipeScroll = new JScrollPane(cfgRecipeAreaOut);

    //------------------------------------------------------------------------------------------
    //CONFIG_DISHES
    private final JLabel cfgDishTextTitle = new JLabel("Inserisci dati piatto");
    private final JLabel cfgDishTextName = new JLabel("Inserisci nome: ");
    private final JLabel cfgDishTextOut = new JLabel("Elenco piatti inseriti: ");
    private final JLabel cfgDishTextRecipe = new JLabel("Seleziona ricetta: ");
    private final JLabel cfgDishTextDate = new JLabel("Inserisci data di inizio e fine: ");
    private final JTextArea cfgDishAreaOut = new JTextArea();
    private final JScrollPane cfgDishAreaScroll = new JScrollPane(cfgDishAreaOut);
    private final JButton cfgDishSendButton = new JButton("Conferma piatto");

    private final JRadioButton cfgDishPermanentRadio = new JRadioButton("Permanente");
    private final JRadioButton cfgDishSeasonalRadio = new JRadioButton("Stagionale");
    private final ButtonGroup cfgDishGroup = new ButtonGroup();

    private final JTextArea cfgDishNameInput = new JTextArea();

    private final JComboBox<String> cfgDishComboBox = new JComboBox<>();
    private final JTextArea cfgDishSDateInput = new JTextArea();
    private final JTextArea cfgDishEDateInput = new JTextArea();
    
    private final JLabel cfgMenuTextTitle = new JLabel("Inserisci dati menu");
    private final JLabel cfgMenuTextName = new JLabel("Inserisci nome: ");
    private final JLabel cfgMenuTextOut = new JLabel("Elenco menu inseriti: ");
    private final JTextArea cfgMenuAreaOut = new JTextArea();
    private final JScrollPane cfgMenuAreaScroll = new JScrollPane(cfgMenuAreaOut);
    private final JLabel cfgMenuTextDish = new JLabel("Seleziona od inserisci piatti: ");
    private final JLabel cfgMenuTextDate = new JLabel("Inserisci data di inizio e fine: ");
    private final JButton cfgMenuSendButton = new JButton("Conferma menu");

    //------------------------------------------------------------------------------------------
    //CONFIG_MENUS
    private final JRadioButton cfgMenuPermanentRadio = new JRadioButton("Permanente");
    private final JRadioButton cfgMenuSeasonalRadio = new JRadioButton("Stagionale");
    private final ButtonGroup cfgMenuGroup = new ButtonGroup();

    private final JTextArea cfgMenuNameInput = new JTextArea();
    private final JTextArea cfgMenuDishesInput = new JTextArea();
    private final JTextArea cfgMenuSDateInput = new JTextArea();
    private final JTextArea cfgMenuEDateInput = new JTextArea();
    private final JComboBox<String> cfgMenuComboBox = new JComboBox<>();

    //------------------------------------------------------------------------------------------
    //CONFIG_RESOCONTO
    private final JLabel cfgResText = new JLabel("Resoconto:");
    private final JLabel cfgResBaseText = new JLabel("Dati ristorante:");
    private final JLabel cfgResDrinksText = new JLabel("Dati bevande:");
    private final JLabel cfgResFoodsText = new JLabel("Dati cibi extra:");
    private final JLabel cfgResRecipesText = new JLabel("Dati ricette:");
    private final JLabel cfgResDishesText = new JLabel("Dati piatti:");
    private final JLabel cfgResMenuCartaText = new JLabel("Menù alla carta:");
    private final JLabel cfgResMenuText = new JLabel("Dati menu:");
    private final JTextArea cfgResBaseOut = new JTextArea();
    private final JTextArea cfgResDrinksOut = new JTextArea();
    private final JTextArea cfgResFoodsOut = new JTextArea();
    private final JTextArea cfgResRecipesOut = new JTextArea();
    private final JTextArea cfgResDishesOut = new JTextArea();
    private final JTextArea cfgResMenuOut = new JTextArea();
    private final JScrollPane cfgResBaseScroll = new JScrollPane(cfgResBaseOut);
    private final JScrollPane cfgResDrinksScroll = new JScrollPane(cfgResDrinksOut);
    private final JScrollPane cfgResFoodsScroll = new JScrollPane(cfgResFoodsOut);
    private final JScrollPane cfgResRecipesScroll = new JScrollPane(cfgResRecipesOut);
    private final JScrollPane cfgResDishesScroll = new JScrollPane(cfgResDishesOut);
    private final JScrollPane cfgResMenuScroll = new JScrollPane(cfgResMenuOut);
    private final JComboBox<String> cfgResDatiMenuBox = new JComboBox<>();
    private final JTextArea cfgResDatiMenuOut = new JTextArea();
    private final JScrollPane cfgResDatiMenuScroll = new JScrollPane(cfgResDatiMenuOut);
    private final JTextArea cfgResMenuCartaOut = new JTextArea();
    private final JScrollPane cfgResMenuCartaScroll = new JScrollPane(cfgResMenuCartaOut);
    //------------------------------------------------------------------------------------------
    //CONFIG_WRITING AND CLEAR
    private final JButton cfgClearButton = new JButton("Clear All");
    private final JButton cfgWriteButton = new JButton("Salva");
//============================================================================================
//============================================================================================
//============================================================================================
    //EMPLOYEE
    //EMPLOYEE GENERAL
    private final JTabbedPane empTabbedPane = new JTabbedPane();
    private final JPanel empSeeBookingsPanel = new JPanel(new GridBagLayout());
    private final JPanel empNewBookingPanel = new JPanel(new GridBagLayout());
    //-------------------------------------------------------------------------------------------
    //EMPLOY SEE BOOKINGS
    private final JLabel empSeeBookText = new JLabel("Prenotazioni:");
    private final JTextArea empSeeBookBookedDates = new JTextArea();
    private final JLabel empSeeBookDateText = new JLabel("Data da cercare:");
    private final JTextArea empSeeBookDateInput = new JTextArea();
    private final JLabel empSeeBookNameText = new JLabel("Nome:");
    private final JLabel empSeeBookNumText = new JLabel("Numero:");
    private final JLabel empSeeBookWorkloadText = new JLabel("Workload:");
    private final JTextArea empSeeBookNameAreaOut = new JTextArea();
    private final JTextArea empSeeBookNumAreaOut = new JTextArea();
    private final JTextArea empSeeBookWorkloadAreaOut = new JTextArea();
    private final JLabel empSeeBookCapacityTotalText = new JLabel("Posti disponibile:");
    private final JTextArea empSeeBookCapacityTotalOut = new JTextArea();
    private final JLabel empSeeBookWorkloadTotalText = new JLabel("Workload disponibile:");
    private final JTextArea empSeeBookWorkloadTotalOut = new JTextArea();
    private final JButton empSeeBookSend = new JButton("Vedi prenotazioni");
    private final JButton empSeeBookWrite = new JButton("Salva prenotazioni");
    private final JButton empSeeBookClear = new JButton("Svuota prenotazioni di questa data");
    private final JButton empSeeBookClearAll = new JButton("Svuota prenotazioni future");

    //-------------------------------------------------------------------------------------------
    //EMPLOY NEW BOOKING
    private final JLabel empNewBookText = new JLabel("Nuova prenotazione:");
    private final JLabel empNewBookDateText = new JLabel("Data:");
    private final JLabel empNewBookNameText = new JLabel("Nome:");
    private final JLabel empNewBookNumText = new JLabel("Numero:");
    private final JLabel empNewBookOrderText = new JLabel("Lista ordine:");

    private final JTextArea empNewBookDateInput = new JTextArea();
    private final JTextArea empNewBookNameInput = new JTextArea();
    private final JTextArea empNewBookNumInput = new JTextArea();
    private final JTextArea empNewBookOrderInput = new JTextArea();

    private final JComboBox<String> empNewBookMenuBox = new JComboBox<>();

    private final JButton empNewBookSend = new JButton("Inserisci");

//===============================================================================================
//===============================================================================================
//===============================================================================================
    //WAREHOUSE
    //WAREHOUSE LIST
    private final JTabbedPane wareTabbedPane = new JTabbedPane();
    private final JPanel wareListPanel = new JPanel(new GridBagLayout());
    private final JLabel wareListText = new JLabel("Lista aggiornata al: ");
    private final JTextArea wareListOut = new JTextArea();
    private final JScrollPane wareListScroll = new JScrollPane(wareListOut);
    private final JLabel wareListMagText = new JLabel("Magazzino al: ");
    private final JTextArea wareListMagOut = new JTextArea();
    private final JScrollPane wareListMagScroll = new JScrollPane(wareListMagOut);
    private final JButton wareListSend = new JButton("Scrivi magazzino");
    //-------------------------------------------------------------------------------------------
    //WAREHOUSE RETURNLIST
    private final JPanel wareReturnListPanel = new JPanel(new GridBagLayout());
    private final JLabel wareReturnListOutText = new JLabel("Lista fine giornata: ");
    private final JTextArea wareReturnListOut = new JTextArea();
    private final JScrollPane wareReturnListOutScroll = new JScrollPane(wareReturnListOut);
    private final JButton wareReturnListSend = new JButton("Conferma");
    private final JLabel wareReturnListInText = new JLabel("Modifiche (ingrediente:delta:unità di misura): ");
    private final JTextArea wareReturnListIn = new JTextArea();
    private final JScrollPane wareReturnListInScroll = new JScrollPane(wareReturnListIn);
//===============================================================================================
//===============================================================================================
    //bordino per le caselle di testo
    private final Border border = BorderFactory.createLineBorder(Color.GRAY, 1);


    public SimpleUI(SaveData saver, Login loginner, DataManagement dataManager) {
        this.saver = saver;
        this.loginner = loginner;
        this.dataManager = dataManager;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);

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
        //Set the initial state
        state = State.PASSWORD;
        updateUI();
    }

    private void passInit()
    {
        //posizionamento dei componenti
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

        //actionlistener che chiama i metodi dell'interfaccia per scrivere
        passWriteButton.addActionListener(e -> {
            boolean ok;
            ok = dataManager.writeManager();
            ok = ok && dataManager.writeBookings();
            ok = ok && dataManager.writeRegister();
            if (ok) confirmSave();
            else errorSetter(Controller.ERROR_IN_WRITING); //errore in writer
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
                passSaveUserText.setText(BLANK);
                passSavePasswordField.setText(BLANK);
                passSavePassword2Field.setText(BLANK);
            }
        });
        passLoginButton.addActionListener(e ->{
            if(loginner.login(passLoginUserText.getText().trim(), Arrays.toString(passLoginPasswordField.getPassword()).trim()))
            {
                passLoginPasswordField.setText(BLANK);
                passLoginUserText.setText(BLANK);
            }
        });
    }
    private void logInit() {
        // aggiunta dei componenti grafici
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(roleText, c);

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

        //action listener che controllano i permessi dell'utente quando accede ad un ruolo
        managerButton.addActionListener(e -> {
            if(loginner.checkPermission("manager"))
            {
                state = State.MANAGER;
                updateUI();
            }
        });
        employeeButton.addActionListener(e -> {
            if(loginner.checkPermission("employee"))
            {
                state = State.EMPLOYEE;
                updateUI();
            }
        });
        warehouseWorkerButton.addActionListener(e -> {
            if(loginner.checkPermission("warehouse worker"))
            {
                state = State.WAREHOUSE_WORKER;
                updateUI();
            }
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

        cfgResBaseScroll.setPreferredSize(new Dimension(200, 100));
        cfgResDrinksScroll.setPreferredSize(new Dimension(200, 100));
        cfgResFoodsScroll.setPreferredSize(new Dimension(200, 100));
        cfgResRecipesScroll.setPreferredSize(new Dimension(200, 100));
        cfgResDishesScroll.setPreferredSize(new Dimension(200, 100));
        cfgResMenuScroll.setPreferredSize(new Dimension(200, 100));
        cfgResDatiMenuOut.setPreferredSize(new Dimension(200, 100));
        cfgFoodsAreaScroll.setPreferredSize(new Dimension(200, 100));
        cfgDrinksAreaScroll.setPreferredSize(new Dimension(200, 100));
        cfgRecipeScroll.setPreferredSize(new Dimension(200, 100));
        cfgDishAreaScroll.setPreferredSize(new Dimension(200, 100));
        cfgMenuAreaScroll.setPreferredSize(new Dimension(200, 100));
        cfgResMenuCartaScroll.setPreferredSize(new Dimension(200, 100));

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


        cfgWriteButton.addActionListener(e -> {
            if (dataManager.writeManager()) confirmSave();
            else errorSetter(Controller.ERROR_IN_WRITING);
        });
       //red button che azzera i dati
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
        cfgBasePanel.add(cfgBaseIndividualWorkloadAreaText, c);
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
        cfgBaseSendButton.addActionListener(e -> saver.saveConfig(cfgBaseInputCap.getText(),       //actionlister manda i contenuti delle caselle al metodo dell'interfaccia
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
        cfgDrinksSendButton.addActionListener(e -> saver.saveDrinks(cfgDrinksInput.getText())); //actionlister manda i contenuti delle caselle al metodo dell'interfaccia
        cfgDrinksFoodsPanel.add(cfgDrinksSendButton, c);
        c.gridx = 0;
        c.gridy = 1;
        cfgDrinksFoodsPanel.add(cfgFoodText, c);
        c.gridx = 1;
        c.gridy = 1;
        cfgDrinksFoodsPanel.add(cfgFoodsInput, c);
        c.gridx = 2;
        c.gridy = 1;
        cfgFoodSendButton.addActionListener(e -> saver.saveFoods(cfgFoodsInput.getText()));       //actionlister manda i contenuti delle caselle al metodo dell'interfaccia
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
        cfgRecipeSendButton.addActionListener(e -> saver.saveRecipe(cfgRecipeNameInput.getText(),       //actionlister manda i contenuti delle caselle al metodo dell'interfaccia
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

        cfgDishPermanentRadio.addActionListener(e -> { //permette di deselezionare un radio, in modo che si permetta che un piatto non sia ne permanent ne seasonal,
            if (DishPermaRadio) {                       // ma solo con un periodo di validità non periodico
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
        });//permette di deselezionare un radio, in modo che si permetta che un piatto non sia ne permanent ne seasonal,
        c.gridx = 1;                                         // ma solo con un periodo di validità non periodico
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
        cfgDishSendButton.addActionListener(e -> saver.saveDish(cfgDishNameInput.getText(),   //actionlister manda i contenuti delle caselle al metodo dell'interfaccia
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
        cfgMenuPermanentRadio.addActionListener(e -> { //permette di deselezionare un radio, in modo che si permetta che un menù non sia ne permanent ne seasonal
            if (MenuPermaRadio) {
                MenuPermaRadio = false;
                cfgMenuGroup.clearSelection();
            } else {
                MenuPermaRadio = true;
                MenuSeasRadio = false;
            }       // ma solo con un periodo di validità non periodico
        });

        c.gridx = 2;
        c.gridy = 4;
        cfgMenuPanel.add(cfgMenuSeasonalRadio, c);
        cfgMenuSeasonalRadio.addActionListener(e -> {  //permette di deselezionare un radio, in modo che si permetta che un menù non sia ne permanent ne seasonal
            if (MenuSeasRadio) {
                MenuSeasRadio = false;
                cfgMenuGroup.clearSelection();
            } else {
                MenuSeasRadio = true;
                MenuPermaRadio = false;
            }       // ma solo con un periodo di validità non periodico
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
        cfgMenuSendButton.addActionListener(e -> saver.saveMenu(cfgMenuNameInput.getText(), //actionlister manda i contenuti delle caselle al metodo dell'interfaccia
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
        c.gridx = 2;
        c.gridy = 1;
        cfgResPanel.add(cfgResDishesText, c);
        c.gridx = 3;
        c.gridy = 1;
        cfgResPanel.add(cfgResDishesScroll, c);
        c.gridx = 2;
        c.gridy = 2;
        cfgResPanel.add(cfgResMenuText, c);
        c.gridx = 3;
        c.gridy = 2;
        cfgResPanel.add(cfgResMenuScroll, c);
        c.gridx = 2;
        c.gridy = 3;
        cfgResPanel.add(cfgResMenuCartaText, c);
        c.gridx = 3;
        c.gridy = 3;
        cfgResPanel.add(cfgResMenuCartaScroll, c);
        c.gridx = 2;
        c.gridy = 4;
        cfgResPanel.add(cfgResDatiMenuBox, c);
        cfgResDatiMenuBox.addActionListener(e -> dataManager.writeMenuComp((String) cfgResDatiMenuBox.getSelectedItem())); //actionlister chiama il metodo dell'interfaccia che scrive i contenuti del menù selezionato
        c.gridx = 3;
        c.gridy = 4;
        cfgResPanel.add(cfgResDatiMenuScroll, c);
        c.gridx = 4;
        c.gridy = 5;
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

        // add i panel al tapped pane
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
            if (dataManager.checkDate(s)) {
                dataManager.seeBookings(dataManager.inputToDate(s));
            } else {
                errorSetter(Controller.INVALID_DATE);
            }
        });
        c.gridx = 2;
        c.gridy = 7;
        empSeeBookingsPanel.add(empSeeBookWrite, c);
        empSeeBookWrite.addActionListener(e -> {
            if (dataManager.writeBookings()) confirmSave();
            else errorSetter(Controller.ERROR_IN_WRITING);
        });

        c.gridx = 1;
        c.gridy = 8;
        empSeeBookingsPanel.add(empSeeBookClear, c);
        //actionlistener per pulire le prenotazioni di questa data
        empSeeBookClear.addActionListener(e ->
                {
                    if(dataManager.clearBookings(dataManager.inputToDate(empSeeBookDateInput.getText())))
                        empSeeBookDateInput.setText("");
                    else errorSetter(Controller.CANNOT_DELETE);
                }
        );
        c.gridx = 2;
        c.gridy = 8;
        empSeeBookingsPanel.add(empSeeBookClearAll, c);
        //actionlistener per pulire tutte le prenotazioni
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
        //actionlister che scrive nella casella vicina il menu selezionato
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
        empNewBookSend.addActionListener(e ->  //salva i dati della booking tramite l'interfaccia
        {
            if(saver.saveBooking(empNewBookNameInput.getText().trim(),empNewBookDateInput.getText().trim(), empNewBookNumInput.getText().trim(), empNewBookOrderInput.getText().trim()))
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
        //fa scrivere il registro attuale tramite interfaccia
        wareListSend.addActionListener(e-> {
            if (dataManager.writeRegister()) confirmSave();
            else errorSetter(Controller.ERROR_IN_WRITING);
        });
        wareListPanel.add(wareListSend, c);

        //RETURNLIST PANEL
        c.gridx = 0;
        c.gridy = 0;
        wareReturnListPanel.add(wareReturnListOutText, c);
        c.gridx = 0;
        c.gridy = 1;
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
        // prende gli ingredienti specificati dall'utente che sono avanzati e li rimette in magazzino
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

    /**
     * Metodo che aggiorna l'UI in base allo stato
     */
    private void updateUI() {
        // svuota il frame
        getContentPane().removeAll();

        // adda il tabbed pane appropriato
        switch (state)
        {
            case PASSWORD -> getContentPane().add(passTabbedPane);
            case LOGIN -> getContentPane().add(loginPanel);
            case MANAGER -> getContentPane().add(cfgTabbedPane);
            case EMPLOYEE -> getContentPane().add(empTabbedPane);
            case WAREHOUSE_WORKER -> getContentPane().add(wareTabbedPane);
        }
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Scrive nelle caselle di testo appropriate i dati ricevuti
     * @param configState lista di stringhe contenenti le varie informazioni
     *                    0 - stringa da inserire nel resoconto
     *                    1 - capacità del ristorante
     *                    2 - individual workload
     *                    3 - data di oggi
     *                    4 - incremento per gli ingredienti da comprare
     */
    public void updateConfig(List<String> configState) {
        cfgResBaseOut.setText(configState.get(0));
        cfgBaseInputCap.setText(configState.get(1));
        cfgBaseInputIndWork.setText(configState.get(2));
        cfgBaseInputDate.setText(configState.get(3));
        cfgBaseInputSurplus.setText(configState.get(4));
    }
    
    /**
     * Aggiorna le caselle di testo relative ai drinks
     * @param drinks stringa con tutti i drink
     */
    public void updateDrinks(String drinks) {
        cfgDrinksInput.setText("");
        cfgDrinksAreaOut.setText(drinks);
        cfgResDrinksOut.setText(drinks);
    }
    
    /**
     * Aggiorna le caselle di testo relative agli extraFoods
     * @param foods stringa con tutti gli extraFoods
     */
    public void updateFoods(String foods) {
        cfgFoodsInput.setText("");
        cfgResFoodsOut.setText(foods);
        cfgFoodsAreaOut.setText(foods);
    }
    
    /**
     * Aggiorna le caselle di testo relative alle recipes
     * @param recipes array con tutte le recipes
     */
    public void updateRecipes(String[] recipes) { //array perchè deve essere usato per la comboBox
        cfgRecipeNameInput.setText(BLANK);
        cfgRecipeIngredientsInput.setText(BLANK);
        cfgRecipePortionsInput.setText(BLANK);
        cfgRecipeWorkLoadInput.setText(BLANK);
        DefaultComboBoxModel<String> model;
        if(recipes.length==0)
        {
            String[] noRecipe = {"Non ci sono ricette inserite"};
            model = new DefaultComboBoxModel<>(noRecipe);
            cfgDishComboBox.setModel(model);
            cfgResRecipesOut.setText(noRecipe[0]);
            cfgRecipeAreaOut.setText(noRecipe[0]);
        }
        else{
            model = new DefaultComboBoxModel<>(recipes);
            cfgDishComboBox.setModel(model);

            StringBuilder compactedArray = new StringBuilder(); //compatta l'array in una stringa unica
            for (String s : recipes) {
                compactedArray.append(s).append("\n");
            }
            cfgResRecipesOut.setText(compactedArray.toString().trim());
            cfgRecipeAreaOut.setText(compactedArray.toString().trim());
        }
    }
    
    /**
     * Aggiorna le caselle di testo relative ai piatti
     * @param dishes array con tutti i piatti
     */
    public void updateDishes(String[] dishes) { //array perchè deve essere usato per la comboBox
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dishes);
        cfgMenuComboBox.setModel(model);
    
        StringBuilder compactedArray = new StringBuilder();
        for (String s : dishes)  //compatta l'array in una stringa unica
        {
            compactedArray.append(s).append("\n");
        }
        cfgResDishesOut.setText(compactedArray.toString().trim());
        cfgDishAreaOut.setText(compactedArray.toString().trim());
        cfgDishNameInput.setText(BLANK);
        cfgDishSDateInput.setText(BLANK);
        cfgDishEDateInput.setText(BLANK);
    }
    
    /**
     * Aggiorna la casella del menù con i piatti disponibili oggi
     * @param menuCarta stringa con tutti i piatti disponibili
     */
    public void updateMenuCarta(String menuCarta) {
        cfgResMenuCartaOut.setText(menuCarta);
    }
    
    /**
     * Aggiorna le caselle dei menù
     * @param menus stringa con tutti i menu
     */
    public void updateMenus(String menus) {
        cfgResMenuOut.setText(menus);
        cfgMenuAreaOut.setText(menus);
        cfgMenuNameInput.setText(BLANK);
        cfgMenuDishesInput.setText(BLANK);
        cfgMenuSDateInput.setText(BLANK);
        cfgMenuEDateInput.setText(BLANK);
    }
    
    /**
     * aggiorna i model delle comboBox dei menu
     * @param menus array con il nome per ogni menu
     */
    public void updateMenuBoxes(String[] menus) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(menus);
        empNewBookMenuBox.setModel(model);
        cfgResDatiMenuBox.setModel(model);
    }
    
    /**
     * Cambio lo stato della GUI dopo il login
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
     * @param afterMeal magazzino sotto forma di stringa
     */
    public void updateWareReturnList (String afterMeal)
    {
        wareReturnListOut.setText(afterMeal);
    }
    
    /**
     * Metodo che aggiorna il magazzino e la lista della spesa
     * @param groceryList lista della spesa sotto forma di testo
     * @param beforeMeal magazzino sotto forma di testo
     */
    public void updateWare(String groceryList, String beforeMeal)
    {
        wareListOut.setText(groceryList);
        wareListMagOut.setText(beforeMeal);
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
    
    /**
     * Aggiorna la casella delle date prenotate
     * @param dates date prenotate
     */
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

    public void logout()
    {
        state = State.PASSWORD;
        updateUI();
    }
    
    
    /**
     * Gestisce i vari errori con una finestra pop Up
     * @param code codice dell'errore
     */
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
            case 14 -> JOptionPane.showMessageDialog(getContentPane(), "Surplus errato, max 10%, min 0", "Err", JOptionPane.ERROR_MESSAGE);
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
            case 26 -> JOptionPane.showMessageDialog(getContentPane(), "Capacità o Workload troppo bassi per le prenotazioni salvate", "Err", JOptionPane.ERROR_MESSAGE);
            case 27 -> JOptionPane.showMessageDialog(getContentPane(), "Ingrediente doppio", "Err", JOptionPane.ERROR_MESSAGE);
            case 28 -> JOptionPane.showMessageDialog(getContentPane(), "Data non cancellabile", "Err", JOptionPane.ERROR_MESSAGE);
            default -> JOptionPane.showMessageDialog(getContentPane(), "Errore", "Err", JOptionPane.ERROR_MESSAGE);
        }
        getContentPane().repaint();
    }
}