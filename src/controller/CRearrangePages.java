package controller;

import java.awt.event.ActionEvent;


import javax.swing.AbstractAction;


import models.MDocument;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;


public class CRearrangePages extends AbstractAction {

	private static Integer buttonCount = 1;
	private Integer thisCount;
	private MDocument mDocument;
	private int inRow;
	

	private CRearrangePages(MDocument mDocument) {
		super(buttonCount.toString());
		this.mDocument = mDocument;
		inRow = buttonCount;
		thisCount = buttonCount;

		new AbstractActionTranslator(this, "", "pagesInRowS");
		
		buttonCount++;
	}

	public static CRearrangePages getInstance(MDocument mDocument) {
		return new CRearrangePages(mDocument);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		mDocument.setPagesInRow(inRow);
	}

	public static void resetCounter() {
		buttonCount = 1;
	}
}
