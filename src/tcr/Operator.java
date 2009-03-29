package tcr;

import java.util.Hashtable;
import java.util.Vector;

import tcr.view.CodeView;
import tcr.view.HelpView;
import tcr.view.TextView;
import tcr.view.View;

import com.nttdocomo.device.CodeReader;
import com.nttdocomo.system.InterruptedOperationException;
import com.nttdocomo.ui.Display;
import com.nttdocomo.ui.IApplication;

public class Operator {
	private CodeView viewCode;
	private TextView viewText;
	private HelpView viewHelp;

	private CodeReader reader;
	private Vector codes;

	public Operator() {
	}

	public void exit() {
		IApplication.getCurrentApp().terminate();
	}

	public void initialize(Hashtable views, CodeReader reader, Vector codes) {
		this.reader = reader;
		this.codes = codes;

		viewCode = (CodeView)views.get("CodeView");
		viewText = (TextView)views.get("TextView");
		viewHelp = (HelpView)views.get("HelpView");

		int[] items = reader.getAvailableCodes();
		Object formats[][] = new Object[items.length][2];
		for (int i = 0, m = items.length; i < m; ++i) {
			formats[i] = CodeParser.getFormat(items[i]);
		}
		viewCode.setFormats(formats);
	}

	public boolean read(int format) throws InterruptedOperationException {
		reader.setCode(format);
		reader.read();

		Code code = CodeParser.parse(reader.getResultType(), reader
				.getResultCode(), reader.getBytes());
		if (code == null) {
			return false;
		}

		codes.addElement(code);
		viewCode.update();

		return true;
	}

	public void error(Exception e) {
		viewCode.notifyError(e);
	}

	public void showHelpView() {
		setView(viewHelp);
	}

	public void showCodeView() {
		setView(viewCode);
	}

	public void showTextView() {
		setView(viewText);
	}

	public void setView(View view) {
		view.update();
		Display.setCurrent(view);
	}

	public void removeAll() {
		codes.removeAllElements();
		((View) Display.getCurrent()).update();
	}

	public void removeAt(int index) {
		codes.removeElementAt(index);
		((View) Display.getCurrent()).update();
	}
}
