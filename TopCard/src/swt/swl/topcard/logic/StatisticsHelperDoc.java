package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.scene.chart.XYChart.Series;
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
}
