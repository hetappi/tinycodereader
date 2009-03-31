package tcr.view;

import tcr.Code;
import tcr.CodeParser;

import com.nttdocomo.ui.Display;
import com.nttdocomo.ui.Label;
import com.nttdocomo.ui.SoftKeyListener;
import com.nttdocomo.ui.TextBox;

public class DetailView extends View {

	private TextBox txtFormat;
	private TextBox txtType;
	private TextBox txtValue;

	protected void initialize() {
		int width = Display.getWidth();
		int height = Display.getHeight();

		Label lblFormat = new Label();
		lblFormat.setText("Format: ");
		lblFormat.setLocation(0, 0);
		lblFormat.setSize(50, lblFormat.getHeight());
		add(lblFormat);

		txtFormat = new TextBox("", 40, 1, TextBox.DISPLAY_ANY);
		txtFormat.setLocation(lblFormat.getWidth(), 0);
		txtFormat.setSize(width - lblFormat.getWidth() - 16, txtFormat
				.getHeight());
		txtFormat.setEditable(false);
		add(txtFormat);

		Label lblType = new Label();
		lblType.setText("Type:   ");
		lblType.setLocation(0, lblType.getHeight());
		lblType.setSize(lblFormat.getWidth(), lblType.getHeight());
		add(lblType);

		txtType = new TextBox("", 40, 1, TextBox.DISPLAY_ANY);
		txtType.setLocation(lblType.getWidth(), lblType.getHeight());
		txtType.setSize(width - lblType.getWidth() - 16, txtType.getHeight());
		txtType.setEditable(false);
		add(txtType);

		Label lblValue = new Label();
		lblValue.setText("Value:  ");
		lblValue.setLocation(0, lblValue.getHeight() * 2);
		lblValue.setSize(lblFormat.getWidth(), lblValue.getHeight());
		add(lblValue);

		txtValue = new TextBox("", 40, 15, TextBox.DISPLAY_ANY);
		txtValue.setLocation(lblValue.getWidth(), lblValue.getHeight());
		txtValue.setSize(width - lblValue.getWidth() - 16, height
				- txtFormat.getHeight() - txtType.getHeight() - 16);
		txtValue.setEditable(false);
		add(txtValue);

		setSoftLabel(SOFT_KEY_1, "Close");

		DetailEventHandler handler = new DetailEventHandler();
		setEventHandler(handler);
		setSoftKeyListener(handler);
	}

	public void update() {
		// do nothing
	}

	public void setCode(Code code) {
		txtFormat.setText(CodeParser.getFormatName(code.format));
		txtType.setText(CodeParser.getTypeName(code.type));
		txtValue.setText(CodeParser.getString(code));
	}
}

class DetailEventHandler extends EventHandler implements SoftKeyListener {
	public void softKeyPressed(int softKey) {
		// do nothing
	}

	public void softKeyReleased(int softKey) {
		operator.showCodeView();
	}
}