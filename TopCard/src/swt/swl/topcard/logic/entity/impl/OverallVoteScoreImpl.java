package swt.swl.topcard.logic.entity.impl;

import swt.swl.topcard.logic.entitiy.OverallVoteScore;

public class OverallVoteScoreImpl implements OverallVoteScore {

	private int yes, no, dontKnow;
	double totalAmount;

	public OverallVoteScoreImpl(int yes, int no, int dontKnow) {

		this.yes = yes;
		this.no = no;
		this.dontKnow = dontKnow;
		this.setTotalAmount(yes + no + dontKnow);
	}

	public OverallVoteScoreImpl(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getYes() {
		return yes;
	}

	public void setYes(int yes) {
		this.yes = yes;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getDontKnow() {
		return dontKnow;
	}

	public void setDontKnow(int dontKnow) {
		this.dontKnow = dontKnow;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

}
