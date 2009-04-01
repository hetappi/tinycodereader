package tcr.view;

import com.nttdocomo.ui.HTMLLayout;
import com.nttdocomo.ui.Label;
import com.nttdocomo.ui.SoftKeyListener;

public class HelpView extends View {

	private final static String[] MENUS = new String[] { "1: Text", "2: Clear",
			"3: Clear All", "4: Detail", "5: Save", "9: Exit" };

	protected void initialize() {
		HTMLLayout lm = new HTMLLayout();
		setLayoutManager(lm);

		for (int i = 0, m = MENUS.length; i < m; ++i) {
			Label label = new Label();
			label.setText(MENUS[i]);
			add(label);
			lm.br();
		}

		setSoftLabel(SOFT_KEY_1, "Close");

		HelpEventHandler handler = new HelpEventHandler();
		setEventHandler(handler);
		setSoftKeyListener(handler);
	}

	public void update() {
		// do nothing
	}
}

class HelpEventHandler extends EventHandler implements SoftKeyListener {
	public void softKeyPressed(int softKey) {
		// do nothing
	}

	public void softKeyReleased(int softKey) {
		operator.showCodeView();
	}
}