package tcr.view;

import java.util.Vector;

import tcr.CodeParser;

import com.nttdocomo.ui.SoftKeyListener;
import com.nttdocomo.ui.TextBox;

public class TextView extends View {
	private Vector codes;
	private TextBox box;

	protected void initialize() {
		box = new TextBox("", 40, 20, TextBox.DISPLAY_ANY);
		box.setSize(getWidth(), getHeight());
		add(box);

		setSoftLabel(SOFT_KEY_1, "Close");

		TextEventHandler handler = new TextEventHandler();
		setEventHandler(handler);
		setSoftKeyListener(handler);
	}

	public void setCodes(Vector codes) {
		this.codes = codes;
	}

	public void update() {
		box.setText(CodeParser.concat(codes, "\n"));
	}
}

class TextEventHandler extends EventHandler implements SoftKeyListener {
	public void softKeyPressed(int softKey) {
		// do nothing
	}

	public void softKeyReleased(int softKey) {
		operator.showCodeView();
	}
}