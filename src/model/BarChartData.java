package model;

import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.List;

/**
 * Created by Caspar on 01.02.2017.
 */
public class BarChartData {

    ProteinGraph model;
    BarChart.Data data;

    public BarChartData(ProteinGraph model) {
        this.model = model;
    }

}
