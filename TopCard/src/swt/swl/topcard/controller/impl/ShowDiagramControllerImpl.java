package swt.swl.topcard.controller.impl;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.ShowDiagramController;
import swt.swl.topcard.logic._impl.DatabaseHelper;
import swt.swl.topcard.logic._impl.StatisticsHelper;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;

public class ShowDiagramControllerImpl implements Observer, Controller, ShowDiagramController {

	private RequirementCardController mainController;
	private String userName;

	@FXML
	private Tab tab1, tab2, tab3;
	@FXML
	private BorderPane tab2BorderPane;
	@FXML
	private Button showPieChartButton, closeTab1Button, showLineChartButton, closeTab2Button, executeButton;
	@FXML
	private TextField equalsTextField, whereStringTextField, whereIntegerTextField, deleteFromTextField;

	@FXML
	private ChoiceBox<String> optionChoiceBox, voteChoiceBox;

	@FXML
	private PieChart pieChart;
	private LineChart<Number, Number> lineChart;

	@FXML
	private Label voteResultsAmountLabel, descriptionPreciseVoteResultLabel, descriptionUnderstandableVoteResultLabel,
			descriptionCorrectVoteResultLabel, descriptionCompleteVoteResultLabel, descriptionAtomicVoteResultLabel,
			rationalePreciseVoteResultLabel, rationaleUnderstandableVoteResultLabel, rationaleTraceableVoteResultLabel,
			rationaleCompleteVoteResultLabel, rationaleConsistentVoteResultLabel, fitCriterionCompleteVoteResultLabel;

	@FXML
	void closeTab1Clicked(ActionEvent event) {
		cancel(event);
	}

	@FXML
	void closeTab2Clicked(ActionEvent event) {
		cancel(event);
	}

	@FXML
	void showLineChartClicked(ActionEvent event) {

		initLineChart(voteChoiceBox.getSelectionModel().getSelectedItem());
	}

	@FXML
	void showPieChartClicked(ActionEvent event) {

		ObservableList<PieChart.Data> statisticViewData = FXCollections.observableArrayList(
				new PieChart.Data("Team1", 5), new PieChart.Data("Team2", 2), new PieChart.Data("Team3", 7));

		pieChart.setData(statisticViewData);

	}

	@FXML
	void executeDelete() {

		if (whereIntegerTextField.getText().isEmpty() && whereStringTextField.getText().isEmpty()) {

			new Alert(AlertType.WARNING, "Input fields should not be empty.").showAndWait();
		}

		if (!whereStringTextField.getText().isEmpty()) {

			DatabaseHelper.deleteXFromDatabaseByName(deleteFromTextField.getText(), equalsTextField.getText());
			new Alert(AlertType.INFORMATION, "Deletion complete.").showAndWait();
		}

		if (!whereIntegerTextField.getText().isEmpty()) {

			DatabaseHelper.deleteXFromDatabaseByID(deleteFromTextField.getText(),
					optionChoiceBox.getSelectionModel().getSelectedItem(),
					Integer.parseInt(optionChoiceBox.getSelectionModel().getSelectedItem()));

			new Alert(AlertType.INFORMATION, "Deletion complete.").showAndWait();

		}
	}

	private void initFXNodes() {

		voteChoiceBox.setItems(FXCollections.observableArrayList("DescriptionPrecise", "DescriptionUnderstandable",
				"DescriptionCorrect", "DescriptionComplete", "DescriptionAtomic", "RationalePrecise",
				"RationaleComplete", "RationaleUnderstandable", "RationaleTraceable", "RationaleConsistent",
				"FitCriterionComplete"));

		SubmittedVoteSimple vote = StatisticsHelper.generateAverageVoteResult(DatabaseHelper.getAllVoteResults());

		this.setGlobalStatistics(vote);
	}

	private void initLineChart(String option) {

		// null-check
		if (option == null) {
			throw new IllegalArgumentException("Option null.");
		}
		// define the 2 axis:
		final NumberAxis xAxis = new NumberAxis();

		// optional for displaying votes sorted by month
		// final CategoryAxis catAx = new CategoryAxis();

		xAxis.setLabel("Time");

		final NumberAxis yAxis = new NumberAxis();
		yAxis.setUpperBound(9);
		yAxis.setLabel("Vote Scores");

		// instanciate line-chart
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		// fetch from statistics helper and add it to our LineChart
		XYChart.Series<Number, Number> series = StatisticsHelper.getSeriesWithXYChartData(option, userName);

		lineChart.getData().add(series);

		// set the LineChart to the center of the tab
		tab2BorderPane.setCenter(lineChart);
	}

	private void setGlobalStatistics(SubmittedVoteSimple vote) {

		this.descriptionPreciseVoteResultLabel.setText(String.valueOf(vote.getDescriptionPrecise()));
		this.descriptionUnderstandableVoteResultLabel.setText(String.valueOf(vote.getDescriptionUnderstandable()));
		this.rationalePreciseVoteResultLabel.setText(String.valueOf(vote.getRationalePrecise()));
		this.rationaleUnderstandableVoteResultLabel.setText(String.valueOf(vote.getRationaleUnderstandable()));
		this.descriptionCorrectVoteResultLabel.setText(String.valueOf(vote.getDescriptionCorrect()));
		this.descriptionCompleteVoteResultLabel.setText(String.valueOf(vote.getDescriptionComplete()));
		this.descriptionAtomicVoteResultLabel.setText(String.valueOf(vote.getDescriptionAtomic()));
		this.rationaleTraceableVoteResultLabel.setText(String.valueOf(vote.getRationaleTraceable()));
		this.rationaleCompleteVoteResultLabel.setText(String.valueOf(vote.getRationaleComplete()));
		this.rationaleConsistentVoteResultLabel.setText(String.valueOf(vote.getRationaleConsistent()));
		this.fitCriterionCompleteVoteResultLabel.setText(String.valueOf(vote.getFitCriterionComplete()));
	}

	@Override
	public void cancel(ActionEvent event) {
		mainController.repaint();
		event.consume();
	}

	@Override
	public void checkEmpty() {

	}

	public void setData(RequirementCardController requirementCardController, String userName) {

		setMainController(requirementCardController);
		this.userName = userName;
		initFXNodes();
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

	@Override
	public void update(Observable o, Object arg) {
	}

	@Override
	public void registerOnModel() {
		// model instance replaced with static class StatisticsHelper
		throw new UnsupportedOperationException("Unsupported.");
	}

}
