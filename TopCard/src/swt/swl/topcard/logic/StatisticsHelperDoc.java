package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.scene.chart.XYChart.Series;
import swt.swl.topcard.logic.entitiy.OverallVoteScore;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;

/**
 * The StatisticsHelper is responsible for generating statistics about the
 * requirements and votes of TopCard Application.
 * 
 * @author swt-041649
 *
 */
public interface StatisticsHelperDoc {

	/**
	 * 
	 * @param series
	 * @param option
	 * @param votesArray
	 * @return series for line chart
	 */
	Series<Number, Double> switchOption(Series<Number, Double> series, String option,
			ArrayList<SubmittedVoteSimple> votesArray);

	/**
	 * 
	 * 
	 * 
	 * @param series
	 * @param option
	 * @param votesArray
	 * @return
	 */
	Series<Number, Number> fillSeriesWithXYChartData(Series<Number, Number> series, String option,
			ArrayList<SubmittedVoteSimple> votesArray);

	/**
	 * generates the average vote result of an arrayList of SubmittetVoteSimples
	 * 
	 * @param voteResults
	 */
	void generateAverageVoteResult(ArrayList<SubmittedVoteSimple> voteResults);

	/**
	 * Generates the overall vote score over all existing vote scores.
	 * 
	 * @param voteResults
	 * @return
	 */
	OverallVoteScore[] generateOverallVoteScore(ArrayList<SubmittedVoteSimple> voteResults);

	/**
	 * 
	 * @param toPerform
	 * @param voteResults
	 * @return
	 */
	OverallVoteScore getOverallVoteScore(String toPerform, ArrayList<SubmittedVoteSimple> voteResults);

}
