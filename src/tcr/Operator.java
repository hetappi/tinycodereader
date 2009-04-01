package tcr;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import tcr.util.FileUtil;
import tcr.view.CodeView;
import tcr.view.DetailView;
import tcr.view.HelpView;
import tcr.view.TextView;
import tcr.view.View;

import com.nttdocomo.device.CodeReader;
import com.nttdocomo.device.StorageDevice;
import com.nttdocomo.fs.DoJaAccessToken;
import com.nttdocomo.fs.DoJaStorageService;
import com.nttdocomo.fs.Folder;
import com.nttdocomo.system.InterruptedOperationException;
import com.nttdocomo.ui.Display;
import com.nttdocomo.ui.IApplication;

public class Operator {
	private CodeView viewCode;
	private TextView viewText;
	private DetailView viewDetail;
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

		if (false) {
			codes.addElement(CodeParser.parse(CodeReader.TYPE_NUMBER,
					CodeReader.CODE_JAN13, "1234567890123".getBytes()));
			codes.addElement(CodeParser.parse(CodeReader.TYPE_NUMBER,
					CodeReader.CODE_JAN13, "2234567890123".getBytes()));
			codes.addElement(CodeParser.parse(CodeReader.TYPE_NUMBER,
					CodeReader.CODE_JAN13, "3234567890123".getBytes()));
		}

		viewCode = (CodeView) views.get("CodeView");
		viewText = (TextView) views.get("TextView");
		viewDetail= (DetailView) views.get("DetailView");
		viewHelp = (HelpView) views.get("HelpView");

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
		View.showErrorDialog(e);
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

	public void showDetailView(int index) {
		viewDetail.setCode((Code)codes.elementAt(index));
		setView(viewDetail);
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

	public void save() throws IOException {
		DoJaAccessToken token = DoJaStorageService.getAccessToken(0,
				DoJaStorageService.SHARE_APPLICATION);
		Folder folder = StorageDevice.getInstance("/ext0").getFolder(token);
		String contents = CodeParser.concat(codes, "\r\n");
		FileUtil.createNewFile(folder, "codes.txt", contents);
	}
}
