package swt.swl.topcard.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.ShowDiagramController;
import swt.swl.topcard.logic.impl.DatabaseHelper;
import swt.swl.topcard.logic.impl.StatisticsHelper;

public class ShowDiagramControllerImpl implements Controller, ShowDiagramController {

	private RequirementCardController mainController;
	private String userName;

	@FXML
	private Tab tab1, tab2, tab3;

	@FXML
	private Button showButton1, closeButton1, showButton2, closeButton2, executeButton;
	@FXML
	private TextField equalsTextField, whereStringTextField, whereIntegerTextField, deleteFromTextField;

	@FXML
	private ChoiceBox<String> optionChoiceBox, voteChoiceBox;

	@FXML
	private PieChart pieChart;
	@FXML
	private LineChart<Number, Double> lineChart;

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

		initLineChart(voteChoiceBox.getSelectionModel().getSelectedItem());
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
		optionChoiceBox.setItems(FXCollections.observableArrayList("=", "<", ">"));
		voteChoiceBox.setItems(FXCollections.observableArrayList("DescriptionPrecise", "DescriptionUnderstandable",
				"DescriptionCorrect", "DescriptionComplete", "DescriptionAtomic", "RationalePrecise",
				"RationaleComplete", "RationaleUnderstandable", "RationaleTraceable", "RationaleConsistent",
				"FitCriterionComplete"));
	}

	private void initLineChart(String option) {

		lineChart.getXAxis().setLabel("Time");
		lineChart.getYAxis().setLabel("Vote Scores");

		XYChart.Series<Number, Double> series = StatisticsHelper.modifyObservableList(option, userName);

		if (lineChart.getData().isEmpty()) {
			lineChart.getData().add(series);
		} else {
			lineChart.getData().set(0, series);
		}

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

}
