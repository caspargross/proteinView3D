package model;

import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.Group;

import java.util.Arrays;

/**
 * Created by Caspar on 01.02.2017.
 */
public class MyBarChart extends Group {

    ProteinGraph model;
    XYChart.Series<String, Number> data = new XYChart.Series<>();
    final private CategoryAxis xAxis = new CategoryAxis();
    final private NumberAxis yAxis = new NumberAxis();

    int specialCount = 0;
    int hydrophobicCount = 0;
    int basicCount = 0;
    int acidCount = 0;
    int polarCount = 0;

    String special = "Special";
    String hydrophobic = "Hydrophobic";
    String acidic = "Acidic";
    String basic = "Basic";
    String polar = "Polar";


    private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    public void initialize(ProteinGraph model) {
        this.model = model;
        xAxis.setLabel("Functional Amino Acid Classification");
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList(special, hydrophobic, acidic, basic, polar)));
        yAxis.setLabel("#Amino Acids");

        for (String aminoAcid : model.getAminoAcidSequence().split("")) {

            String type = AminoAcidTools.getAminoAcidType(aminoAcid);
            switch (type) {
                case "Special":
                    specialCount++;
                case "Hydrophobic":
                    hydrophobicCount++;
                case "Acidic":
                    acidCount++;
                case "Polar":
                    polarCount++;
                case "Basic":
                    basicCount++;
            }
        }


        data.getData().add(new XYChart.Data(special, specialCount));
        data.getData().add(new XYChart.Data(hydrophobic, hydrophobicCount));
        data.getData().add(new XYChart.Data(polar, polarCount));
        data.getData().add(new XYChart.Data(basic, basicCount));
        data.getData().add(new XYChart.Data(acidic, acidCount));

        barChart.getData().addAll(data);
        barChart.setMinWidth(200);
        barChart.setMinWidth(200);
        this.getChildren().add(barChart);
    }

}
