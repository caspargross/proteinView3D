package model;

import javafx.scene.chart.*;
import javafx.scene.Group;

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

    public MyBarChart(ProteinGraph model) {
        this.model = model;
    }

    private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    public void initialize() {

        xAxis.setLabel("Function Amino Acid Classification");
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


        data.getData().add(new XYChart.Data("Special", specialCount));
        data.getData().add(new XYChart.Data("Hydrophobic", hydrophobicCount));
        data.getData().add(new XYChart.Data("Acidic", acidCount));
        data.getData().add(new XYChart.Data("Polar", polarCount));
        data.getData().add(new XYChart.Data("Basic", basicCount));

        barChart.getData().addAll(data);
        barChart.setMinWidth(200);
        barChart.setMinWidth(200);
        this.getChildren().add(barChart);
    }

}
