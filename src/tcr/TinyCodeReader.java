package tcr;

import java.util.Hashtable;
import java.util.Vector;

import tcr.view.CodeView;
import tcr.view.DetailView;
import tcr.view.HelpView;
import tcr.view.TextView;

import com.nttdocomo.device.CodeReader;
import com.nttdocomo.ui.IApplication;

public class TinyCodeReader extends IApplication {

	public void start() {
		CodeReader reader = CodeReader.getCodeReader(0);
		Vector codes = new Vector();
		Operator operator = new Operator();

		CodeView viewCode = new CodeView();
		viewCode.setOperator(operator);
		viewCode.setCodes(codes);

		TextView viewText = new TextView();
		viewText.setOperator(operator);
		viewText.setCodes(codes);

		DetailView viewDetail= new DetailView();
		viewDetail.setOperator(operator);

		HelpView viewHelp = new HelpView();
		viewHelp.setOperator(operator);

		Hashtable views = new Hashtable();
		views.put("CodeView", viewCode);
		views.put("TextView", viewText);
		views.put("DetailView", viewDetail);
		views.put("HelpView", viewHelp);

		operator.initialize(views, reader, codes);
		operator.showCodeView();
	}
}
