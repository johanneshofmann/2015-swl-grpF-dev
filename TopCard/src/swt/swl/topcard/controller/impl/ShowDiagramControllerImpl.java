package swt.swl.topcard.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.ShowDiagramController;

public class ShowDiagramControllerImpl implements Controller, ShowDiagramController {

	private RequirementCardController mainController;

	@FXML
	private Tab tab1, tab2;

	@FXML
	private Button showButton1, closeButton1, showButton2, closeButton2;
	@FXML
	private PieChart pieChart;
	@FXML
	private LineChart<Integer, Integer> lineChart;

	private void initLineChart() {

		lineChart.getXAxis().setLabel("Time");
		lineChart.getYAxis().setLabel("Vote Scores");

		XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
		series.setName("RequirementVotes");

		// TODO: model.getVoteResultsFromUserFrom(Date begin,Date end);
		series.getData().add(new XYChart.Data<Integer, Integer>(1, 23));
		series.getData().add(new XYChart.Data<Integer, Integer>(2, 11));
		series.getData().add(new XYChart.Data<Integer, Integer>(3, 3));
		series.getData().add(new XYChart.Data<Integer, Integer>(4, 22));
		series.getData().add(new XYChart.Data<Integer, Integer>(5, 16));
		series.getData().add(new XYChart.Data<Integer, Integer>(6, 23));
		series.getData().add(new XYChart.Data<Integer, Integer>(7, 29));
		series.getData().add(new XYChart.Data<Integer, Integer>(8, 3));
		series.getData().add(new XYChart.Data<Integer, Integer>(9, 8));
		series.getData().add(new XYChart.Data<Integer, Integer>(10, 10));
		series.getData().add(new XYChart.Data<Integer, Integer>(11, 15));
		series.getData().add(new XYChart.Data<Integer, Integer>(12, 23));

		lineChart.getData().add(series);
	}

	@FXML
	void closeButton1Clicked(ActionEvent event) {
		cancel(event);
	}

	@FXML
	void closeButton2Clicked(ActionEvent event) {
		cancel(event);
	}

	@FXML
	void showButton1Clicked(ActionEvent event) {

		ObservableList<PieChart.Data> statisticViewData = FXCollections.observableArrayList(
				new PieChart.Data("Team1", 5), new PieChart.Data("Team2", 2), new PieChart.Data("Team3", 7));
		pieChart.setData(statisticViewData);
	}

	@FXML
	void showButton2Clicked(ActionEvent event) {
		initLineChart();
	}

	@Override
	public void cancel(ActionEvent event) {
		mainController.repaint();
		event.consume();
	}

	@Override
	public void checkEmpty() {

	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

}
