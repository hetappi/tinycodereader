package tcr.view;

import tcr.Operator;

public abstract class EventHandler {
	protected Operator operator;

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
