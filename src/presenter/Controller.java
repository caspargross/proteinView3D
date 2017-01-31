package presenter;

/**
 * Created by Caspar on 26.01.2017.
 * This is the controller class to hande stuff in the FXML file
 *
 */
import java.net.URL;
import java.util.ResourceBundle;

import blast.BlastService;
import blast.RemoteBlastClient;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.ProteinGraph;
import view.MainView;
import view.SequenceView;




public class Controller {

    // Additional View, Presenter and Model elements
    // Some parts of this run still as MVP-Model
    public ProteinGraph model;
    public MainView root;
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
    private AnchorPane proteinViewPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TitledPane x5;

    @FXML
    private TitledPane x6;

    /** Events to the corresponding buttons **/
    // Menu events
    @FXML
    void menuOpenEvent(ActionEvent event) {
        presenter.readPDB();
        System.out.println("Open File Button pressed");
    }

    // Accordeon events
    @FXML
    void showAtomEvent(ActionEvent event) {
        model.showAtoms(lEshowAtoms.isSelected());
    }

    @FXML
    void showBondsEvent(ActionEvent event) {
        model.showBonds(lEshowBonds.isSelected());
    }

    @FXML
    void showRibbonViewEvent(ActionEvent event) {
    }

    @FXML
    void showSecondaryStructureEvent(ActionEvent event) {
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

        //blastService.remoteBlastClient.setProgram(lEBlastVersionCbx.getValue());
        //System.out.println(lEBlastVersionCbx.getValue().toString());
        //System.out.println(lEdatabaseName.getText());
        //System.out.println(blastService.getQuerySequence());


        //blastService.startBlastQuery();
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
        root = new MainView(model);

        // Create Presenter
        presenter = new Presenter(root, model);

        lEProteinViewTab.setContent(root);

        // Initialize Chechbox with available blast models
        lEBlastVersionCbx.getItems().setAll(RemoteBlastClient.BlastProgram.values());
        lEBlastVersionCbx.getSelectionModel().select(RemoteBlastClient.BlastProgram.blastp);





        SequenceView sequenceView = new SequenceView(root.proteinView);
        scrollPane.setContent(sequenceView);

        // Bind buttons to the Properties



        }

    }

