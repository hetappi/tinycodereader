package tcr.view;

import tcr.Operator;

import com.nttdocomo.ui.Dialog;
import com.nttdocomo.ui.Panel;

public abstract class View extends Panel {
	private EventHandler handler;

	public View() {
		initialize();
	}

	protected abstract void initialize();

	public abstract void update();

	public static int showDialog(int type, String title, String msg) {
		Dialog dialog = new Dialog(type, title);
		dialog.setText(msg);
		return dialog.show();
	}

	public static int showErrorDialog(String msg) {
		return showDialog(Dialog.DIALOG_ERROR, "Error", msg);
	}

	public static int showErrorDialog(Exception e) {
		e.printStackTrace();
		return showErrorDialog(e.getMessage());
	}

	public void setOperator(Operator operator) {
		handler.setOperator(operator);
	}

	public void setEventHandler(EventHandler handler) {
		this.handler = handler;
	}
}
