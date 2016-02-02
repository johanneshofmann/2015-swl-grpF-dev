package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.scene.chart.XYChart.Series;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;

/**
 * 
 * @author swt-041649
 *
 */
public interface StatisticsHelperDoc {

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param series
	 * @param option
	 * @param votesArray
	 * @return
	 */
	Series<Number, Double> switchOption(Series<Number, Double> series, String option,
			ArrayList<SubmittedVoteSimple> votesArray);
}
