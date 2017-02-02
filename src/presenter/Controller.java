package presenter;

/**
 * Created by Caspar on 26.01.2017.
 * This is the controller handles layout Elements and Events in the FXML File
 *
 */
import java.net.URL;
import java.util.ResourceBundle;

import blast.BlastService;
import blast.RemoteBlastClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.AminoAcidTools;
import model.MyBarChart;
import model.ProteinGraph;
import view.MainView;


public class Controller {

    /**
     * Declaration of main parts of the application
     * model is used to store and compute data extracted from the PDB file
     *
     * */
    public ProteinGraph model;
    public MainView mainView;
    public Presenter presenter;
    public BlastService blastService;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    // Blast Search Controls
    @FXML
    private ComboBox<RemoteBlastClient.BlastProgram> lEBlastVersionCbx;

    @FXML
    private ScrollPane lEalignment;

    @FXML
    private TextField lEdatabaseName;

    @FXML
    private Button lEstartQuery;

    @FXML
    private TextField lEstatus;

    // Accordeon elements

    @FXML
    private Slider lEbondSize;

    @FXML
    private Slider lEAtomSize;

    @FXML
    private RadioButton lEshowBonds;

    @FXML
    private RadioButton lEshowRibbons;

    @FXML
    private RadioButton lEshowSecondaryStructure;

    // Pane elements
    @FXML
    private Tab lEBlastSearchTab;

    @FXML
    private Tab lEProteinViewTab;

    @FXML
    private AnchorPane lEScrollPane;

    @FXML
    private Tab lEStatisticsTab;

    @FXML
    private RadioButton lEshowAtoms;


    // Menu elements
    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuQuit;

    @FXML
    private AnchorPane proteinViewPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TitledPane x5;

    @FXML
    private TitledPane x6;

    // Status bar elements
    @FXML
    private Label lEleftLabel;

    @FXML
    private Label lErightLabel;

    // Bar Chart
    @FXML
    private AnchorPane lEStatisticsPane;

    /**
     * Events to the corresponding buttons
     **/
    // Menu events
    @FXML
    void menuOpenEvent(ActionEvent event) {
        presenter.readPDB();
        System.out.println("Open File Button pressed");
    }

    @FXML
    void menuQuitEvent(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }


    // Blast search events
    @FXML
    void startQueryEvent(ActionEvent event) {
        System.out.println("Start Query Button pressed");
        blastService = new BlastService();
        blastService.setQuerySequence(model.getAminoAcidSequence());
        blastService.setBlastProgram(lEBlastVersionCbx.getValue());
        blastService.setDataBase(lEdatabaseName.getText());
        blastService.start();
        lEstatus.textProperty().bind(blastService.statusProperty);

        blastService.setOnSucceeded(event1 -> {
            lEalignment.setContent(new TextArea(blastService.getValue()));
        });
    }

    @FXML
    void selectBlastEvent(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert lEbondSize != null : "fx:id=\"bondSize\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEAtomSize != null : "fx:id=\"lEAtomSize\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEBlastSearchTab != null : "fx:id=\"lEBlastSearchTab\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEProteinViewTab != null : "fx:id=\"lEProteinViewTab\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEScrollPane != null : "fx:id=\"lEScrollPane\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEStatisticsTab != null : "fx:id=\"lEStatisticsTab\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEshowAtoms != null : "fx:id=\"lEshowAtoms\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEshowBonds != null : "fx:id=\"lEshowBonds\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEshowRibbons != null : "fx:id=\"lEshowRibbons\" was not injected: check your FXML file 'Layout.fxml'.";
        assert lEshowSecondaryStructure != null : "fx:id=\"lEshowSecondaryStructure\" was not injected: check your FXML file 'Layout.fxml'.";
        assert menuOpen != null : "fx:id=\"menuOpen\" was not injected: check your FXML file 'Layout.fxml'.";
        assert proteinViewPane != null : "fx:id=\"proteinViewPane\" was not injected: check your FXML file 'Layout.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'Layout.fxml'.";
        assert x5 != null : "fx:id=\"x5\" was not injected: check your FXML file 'Layout.fxml'.";
        assert x6 != null : "fx:id=\"x6\" was not injected: check your FXML file 'Layout.fxml'.";


        // Create Model
        model = new ProteinGraph();

        // Create View
        mainView = new MainView(model);

        // Create Presenter
        presenter = new Presenter(mainView, model);

        lEProteinViewTab.setContent(mainView);

        // Initialize Chechbox with available blast models
        lEBlastVersionCbx.getItems().setAll(RemoteBlastClient.BlastProgram.values());
        lEBlastVersionCbx.getSelectionModel().select(RemoteBlastClient.BlastProgram.blastp);

        // Initialize elements in status bar
        lEleftLabel.textProperty().bind(model.header);
        lErightLabel.textProperty().bind(mainView.proteinView.atomInfo);


        scrollPane.setContent(mainView.sequenceView);

        // Bind buttons to the Properties
        // Show view elements
        lEshowAtoms.selectedProperty().bindBidirectional(mainView.proteinView.showAtomsProperty);
        lEshowBonds.selectedProperty().bindBidirectional(mainView.proteinView.showBondsProperty);
        lEshowRibbons.selectedProperty().bindBidirectional(mainView.proteinView.showRibbonsProperty);
        lEshowSecondaryStructure.selectedProperty().bindBidirectional(mainView.proteinView.showSecondaryStructureProperty);
        lEshowAtoms.selectedProperty().setValue(Boolean.TRUE);
        lEshowBonds.selectedProperty().setValue(Boolean.TRUE);
        lEshowSecondaryStructure.selectedProperty().setValue(Boolean.FALSE);
        lEshowRibbons.selectedProperty().setValue(Boolean.FALSE);
        // Change view size
        lEAtomSize.valueProperty().bindBidirectional(mainView.proteinView.atomSizeProperty);
        lEbondSize.valueProperty().bindBidirectional(mainView.proteinView.bondSizeProperty);

        // Add Bar Chart

        lEStatisticsPane.getChildren().addAll(mainView.barChart.getChildren());

        //lEBarChart = new BarChartData(new CategoryAxis(AminoAcidTools.AminoAcidType.values()), new NumberAxis());

        }

    }

