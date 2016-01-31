package swt.swl.topcard.logic._impl;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import swt.swl.topcard.logic.entitiy.OverallVoteScore;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;
import swt.swl.topcard.logic.entity.impl.OverallVoteScoreImpl;
import swt.swl.topcard.logic.entity.impl.SubmittedVoteSimpleImpl;

/**
 * 
 * @author Steve
 *
 */
public interface StatisticsHelper {

	static Series<Number, Number> getSeriesWithXYChartData(String option, String userName) {

		// null-check:
		if (option == null) {
			throw new IllegalArgumentException("option null");
		}
		if (userName == null) {
			throw new IllegalArgumentException("userName null");
		}

		// instanciate series
		Series<Number, Number> series = new XYChart.Series<Number, Number>();

		// fetch all current RQ-IDs from user
		ArrayList<Integer> rqIDs = DatabaseHelper.getAllRQIDsFromUser(userName);

		// votesArray where all votes are stored
		ArrayList<SubmittedVoteSimple> votesArray = new ArrayList<>();

		// for every RQ-ID (that means every !UNIQUE! ID a user has created
		// during the livetime of the application
		for (Integer id : rqIDs) {

			if (!DatabaseHelper.isFrozen(id)) {

				// if not frozen, add averageVoteResult from all votes of each
				// RQ-ID to the ArrayList
				SubmittedVoteSimple vote = StatisticsHelper
						.generateAverageVoteResult(DatabaseHelper.getVoteResultsFrom(id));

				votesArray.add(vote);

				System.err.println("adding vote with ID=" + vote.getID());
			}
		}

		// fill the series with the requested data..
		series = fillSeriesWithXYChartData(series, option, votesArray);

		// .. and return it
		return series;
	}

	static Series<Number, Number> fillSeriesWithXYChartData(Series<Number, Number> series, String option,
			ArrayList<SubmittedVoteSimple> votesArray) {

		switch (option) {

		case "DescriptionPrecise":

			for (int xAxis = 0; xAxis < votesArray.size(); xAxis++) {

				XYChart.Data<Number, Number> data = new XYChart.Data<Number, Number>(xAxis,
						votesArray.get(xAxis).getDescriptionPrecise());

				System.err.println("x,y: " + data.getXValue() + "," + data.getYValue());

				series.getData().add(data);
			}

			return series;

		case "DescriptionUnderstandable":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData()
						.add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getDescriptionUnderstandable()));
			}
			return series;

		case "DescriptionCorrect":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getDescriptionCorrect()));
			}
			return series;

		case "DescriptionComplete":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getDescriptionComplete()));
			}
			return series;

		case "DescriptionAtomic":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getDescriptionAtomic()));
			}
			return series;

		case "RationalePrecise":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getRationalePrecise()));
			}
			return series;

		case "RationaleUnderstandable":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData()
						.add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getRationaleUnderstandable()));
			}
			return series;

		case "RationaleComplete":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getRationaleComplete()));
			}
			return series;

		case "RationaleTraceable":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getRationaleTraceable()));
			}
			return series;

		case "RationaleConsistent":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getRationaleConsistent()));
			}
			return series;

		case "FitCriterionComplete":

			for (int i = 0; i < votesArray.size(); i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, votesArray.get(i).getFitCriterionComplete()));
			}
			return series;

		default:

			throw new IllegalArgumentException("Invalid input value.");
		}
	}

	/**
	 * 
	 * @param voteResults
	 * @returns Object[] -> [0]=RequirementCardSimple everageVote;
	 *          [1]=submittedVotesAmountInt
	 */
	static SubmittedVoteSimple generateAverageVoteResult(ArrayList<SubmittedVoteSimple> voteResults) {

		if (voteResults.isEmpty()) {
			throw new IllegalArgumentException("voteResults empty.");
		}

		OverallVoteScore[] results = generateOverallVoteScore(voteResults);

		return new SubmittedVoteSimpleImpl.VoteBuilderImpl().setOverallVoteScore(results).buildVote();
	}

	static OverallVoteScore[] generateOverallVoteScore(ArrayList<SubmittedVoteSimple> voteResults) {

		OverallVoteScore[] overallVoteScores = new OverallVoteScoreImpl[11];

		overallVoteScores[0] = getOverallVoteScore("DescriptionPrecise", voteResults);
		System.out.println(overallVoteScores[0].getTotalAmount());
		overallVoteScores[1] = getOverallVoteScore("DescriptionUnderstandable", voteResults);
		overallVoteScores[2] = getOverallVoteScore("RationalePrecise", voteResults);
		overallVoteScores[3] = getOverallVoteScore("RationaleUnderstandable", voteResults);
		overallVoteScores[4] = getOverallVoteScore("DescriptionCorrect", voteResults);
		overallVoteScores[5] = getOverallVoteScore("DescriptionComplete", voteResults);
		overallVoteScores[6] = getOverallVoteScore("DescriptionAtomic", voteResults);
		overallVoteScores[7] = getOverallVoteScore("RationaleTraceable", voteResults);
		overallVoteScores[8] = getOverallVoteScore("RationaleComplete", voteResults);
		overallVoteScores[9] = getOverallVoteScore("RationaleConsistent", voteResults);
		overallVoteScores[10] = getOverallVoteScore("FitCriterionComplete", voteResults);

		return overallVoteScores;
	}

	public static OverallVoteScore getOverallVoteScore(String toPerform, ArrayList<SubmittedVoteSimple> voteResults) {

		double yesCounter = 0, noCounter = 0, dontKnowCounter = 0;

		DecimalFormat getDecimal = new DecimalFormat("#0.00");

		switch (toPerform) {

		case "DescriptionPrecise":

			for (int i = 0; i < voteResults.size(); i++) {

				yesCounter += voteResults.get(i).getDescriptionPrecise();
			}
			return new OverallVoteScoreImpl(Double.parseDouble(getDecimal.format(yesCounter / voteResults.size())));

		case "DescriptionUnderstandable":

			for (int i = 0; i < voteResults.size(); i++) {

				yesCounter += voteResults.get(i).getDescriptionUnderstandable();
			}
			return new OverallVoteScoreImpl(Double.parseDouble(getDecimal.format(yesCounter / voteResults.size())));

		case "DescriptionCorrect":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getDescriptionCorrect() == 1) {
					yesCounter++;
				} else if (vote.getDescriptionCorrect() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		case "DescriptionComplete":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getDescriptionComplete() == 1) {
					yesCounter++;
				} else if (vote.getDescriptionComplete() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		case "DescriptionAtomic":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getDescriptionAtomic() == 1) {
					yesCounter++;
				} else if (vote.getDescriptionAtomic() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		case "RationalePrecise":

			for (int i = 0; i < voteResults.size(); i++) {

				yesCounter += voteResults.get(i).getRationalePrecise();
			}
			return new OverallVoteScoreImpl(Double.parseDouble(getDecimal.format(yesCounter / voteResults.size())));

		case "RationaleUnderstandable":

			for (int i = 0; i < voteResults.size(); i++) {

				yesCounter += voteResults.get(i).getRationaleUnderstandable();
			}
			return new OverallVoteScoreImpl(Double.parseDouble(getDecimal.format(yesCounter / voteResults.size())));

		case "RationaleComplete":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getRationaleComplete() == 1) {
					yesCounter++;
				} else if (vote.getRationaleComplete() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		case "RationaleTraceable":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getRationaleTraceable() == 1) {
					yesCounter++;
				} else if (vote.getRationaleTraceable() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		case "RationaleConsistent":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getRationaleConsistent() == 1) {
					yesCounter++;
				} else if (vote.getRationaleConsistent() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		case "FitCriterionComplete":

			for (SubmittedVoteSimple vote : voteResults) {

				if (vote.getFitCriterionComplete() == 1) {
					yesCounter++;
				} else if (vote.getFitCriterionComplete() == 0) {
					noCounter++;
				} else {
					dontKnowCounter++;
				}
			}
			return new OverallVoteScoreImpl((int) yesCounter, (int) noCounter, (int) dontKnowCounter);

		default:
			throw new IllegalArgumentException(
					"Value for input string 'toPerform' is not valid. Value was: " + toPerform);
		}
	}
}
