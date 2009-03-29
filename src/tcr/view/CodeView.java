package tcr.view;

import java.util.Enumeration;
import java.util.Vector;

import tcr.Code;
import tcr.CodeParser;

import com.nttdocomo.ui.Dialog;
import com.nttdocomo.ui.Display;
import com.nttdocomo.ui.KeyListener;
import com.nttdocomo.ui.Label;
import com.nttdocomo.ui.ListBox;
import com.nttdocomo.ui.Panel;
import com.nttdocomo.ui.SoftKeyListener;

public class CodeView extends View {
	private Vector codes;
	private ListBox lstCode;
	private ListBox lstFormat;
	private CodeViewEventHandler handler;

	protected void initialize() {
		int width = Display.getWidth();
		int height = Display.getHeight();

		Label lblFormat = new Label();
		lblFormat.setLocation(0, 0);
		lblFormat.setText("Code: ");
		add(lblFormat);

		lstFormat = new ListBox(ListBox.CHOICE);
		lstFormat.setLocation(lblFormat.getWidth(), 0);
		lstFormat.setSize(width - lblFormat.getWidth() - 16, lstFormat
				.getHeight());
		add(lstFormat);

		lstCode = new ListBox(ListBox.SINGLE_SELECT);
		lstCode.setLocation(lblFormat.getHeight(), 0);
		lstCode.setSize(width, height - lblFormat.getHeight() - 16);
		add(lstCode);

		setSoftLabel(Panel.SOFT_KEY_1, "Read");
		setSoftLabel(Panel.SOFT_KEY_2, "Help");

		handler = new CodeViewEventHandler(lstCode, lstFormat);
		setEventHandler(handler);
		setSoftKeyListener(handler);
		setKeyListener(handler);
	}

	public void update() {
		lstCode.removeAll();
		if (codes == null) {
			return;
		}
		for (Enumeration en = codes.elements(); en.hasMoreElements();) {
			lstCode.append(CodeParser.getString((Code) en.nextElement()));
		}
	}

	public void notifyError(Exception e) {
		e.printStackTrace();
		View.showDialog(Dialog.DIALOG_ERROR, "Error", e.getMessage());
	}

	public void setFormats(Object[][] formats) {
		handler.setFormats(formats);
		lstFormat.removeAll();
		if (formats == null) {
			return;
		}
		String items[] = new String[formats.length];
		for (int i = 0, m = formats.length; i < m; ++i) {
			items[i] = (String) formats[i][1];
		}
		lstFormat.setItems(items);
	}

	public void setCodes(Vector codes) {
		this.codes = codes;
	}
}

class CodeViewEventHandler extends EventHandler implements SoftKeyListener,
		KeyListener {
	private ListBox lstCode;
	private ListBox lstFormat;
	private Object[][] formats;

	public CodeViewEventHandler(ListBox lstCode, ListBox lstFormat) {
		this.lstCode = lstCode;
		this.lstFormat = lstFormat;
	}

	public void setFormats(Object[][] formats) {
		this.formats = formats;
	}

	public void softKeyPressed(int softKey) {
		// do nothing
	}

	public void softKeyReleased(int softKey) {
		try {
			switch (softKey) {
			case Panel.SOFT_KEY_1:
				if (formats != null) {
					int idx = lstFormat.getSelectedIndex();
					if (idx >= 0) {
						operator.read(((Integer) formats[idx][0]).intValue());
					}
				}
				break;
			case Panel.SOFT_KEY_2:
				operator.showHelpView();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			operator.error(e);
		}
	}

	public void keyPressed(Panel panel, int key) {
		// do nothing
	}

	public void keyReleased(Panel panel, int key) {
		switch (key) {
		case 1:
			operator.showTextView();
			break;
		case 2:
			int idx = lstCode.getSelectedIndex();
			if (idx >= 0) {
				operator.removeAt(idx);
			}
			break;
		case 3:
			operator.removeAll();
			break;
		case 9:
			operator.exit();
			break;
		default:
			break;
		}
	}
}
