package controller;

import controller.AddActions.CAddDocument;
import controller.AddActions.CAddGraphSlot;
import controller.AddActions.CAddPage;
import controller.AddActions.CAddProject;
import controller.AddActions.CAddTextSlot;
import controller.Alignment.CCascadeAlignment;
import controller.Alignment.CHorizontalAlignment;
import controller.Alignment.CMatrixAlignment;
import controller.Alignment.CVerticalAlignment;
import controller.settings.COpenSettingsDialog;
import models.MDocument;
import models.MElement;
import models.MPage;
import models.MProject;
import models.MSlot;
import models.MWorkspace;
import utilities.FileActions;
import utilities.Observer;

public class ActionManager implements Observer {
	private static ActionManager instance;

	private CWindowResize cWindowResize;

	private CChangeWorkspace cChangeWorkspace;

	private CMoveUp cMoveUp;
	private CMoveDown cMoveDown;

	private CAddProject cAddProject;
	private CAddDocument cAddDocument;
	private CAddPage cAddPage;
	private CAddGraphSlot cAddGraphSlot;
	private CAddTextSlot cAddTextSlot;

	private CUndoCommand cUndoCommand;
	private CRedoCommand cRedoCommand;

	private CDeleteElements cDeleteElements;

	private CSelectState cSelectState;
	private CSquareState cSquareState;
	private CCircleState cCircleState;
	private CTriangleState cTriangleState;

	private CCopyElements cCopyElements;
	private CPasteElement cPasteElement;
	private CCutElement cCutElement;

	private CRotateLeft cRotateLeft;
	private CRotateRight cRotateRight;

	public CCopyElements getcCopyElements() {
		return cCopyElements;
	}

	public CTriangleState getcTriangleState() {
		return cTriangleState;
	}

	private CEditMode cEditMode;

	private CSaveProject cSaveProject;
	private CSaveAs cSaveAs;
	private CSaveAll cSaveAll;

	public CSaveAll getcSaveAll() {
		return cSaveAll;
	}

	private COpenProject cOpenProject;

	public COpenProject getcOpenProject() {
		return cOpenProject;
	}

	private CRemoveObject cProjectRemove;
	private CTree cTree;
	private CPopupMenu cPopupMenu;
	private CAbout cAbout;

	private CChangeLanguage cChangeLanguage;

	private CCascadeAlignment cCascadeAlignment;
	private CHorizontalAlignment cHorizontalAlignment;
	private CVerticalAlignment cVerticalAlignment;
	private CMatrixAlignment cMatrixAlignment;
	private CDocumentSelect cDocumentSelect;
	private CMouseListener cMouseListener;

	private COpenSettingsDialog cOpenSettingsDialog;

	private ActionManager() {
		initActions();
		MWorkspace.getInstance().addObserver(this);
	}

	public static ActionManager getInstance() {
		if (instance == null) {
			instance = new ActionManager();
		}
		return instance;
	}

	public CMoveUp getcMoveUp() {
		return cMoveUp;
	}

	public CMoveDown getcMoveDown() {
		return cMoveDown;
	}

	private void initActions() {
		cWindowResize = new CWindowResize();

		cChangeWorkspace = new CChangeWorkspace();

		cAddProject = new CAddProject();
		cProjectRemove = new CRemoveObject();
		cAddDocument = new CAddDocument();
		cAddPage = new CAddPage();

		cAddGraphSlot = new CAddGraphSlot();
		cAddTextSlot = new CAddTextSlot();

		cUndoCommand = new CUndoCommand();
		cRedoCommand = new CRedoCommand();

		cDeleteElements = new CDeleteElements();

		cSelectState = new CSelectState();
		cSquareState = new CSquareState();
		cCircleState = new CCircleState();
		cTriangleState = new CTriangleState();

		cEditMode = new CEditMode();

		cChangeLanguage = new CChangeLanguage();

		cTree = new CTree();
		cPopupMenu = new CPopupMenu();
		cAbout = new CAbout();

		cMoveUp = new CMoveUp();
		cMoveDown = new CMoveDown();

		cSaveProject = new CSaveProject();
		cSaveAs = new CSaveAs();
		cSaveAll = new CSaveAll();
		cOpenProject = new COpenProject();

		cCascadeAlignment = new CCascadeAlignment();
		cHorizontalAlignment = new CHorizontalAlignment();
		cVerticalAlignment = new CVerticalAlignment();
		cMatrixAlignment = new CMatrixAlignment();

		// setAlignmentsState(false);

		cDocumentSelect = new CDocumentSelect();
		cMouseListener = new CMouseListener();

		cOpenSettingsDialog = new COpenSettingsDialog();
		// cOpenProject.setEnabled(true);

		cCopyElements = new CCopyElements();
		cPasteElement = new CPasteElement();
		cCutElement = new CCutElement();

		cRotateLeft = new CRotateLeft();
		cRotateRight = new CRotateRight();
	}

	public CRotateLeft getcRotateLeft() {
		return cRotateLeft;
	}

	public CRotateRight getcRotateRight() {
		return cRotateRight;
	}

	public CUndoCommand getcUndoCommand() {
		return cUndoCommand;
	}

	public CRedoCommand getcRedoCommand() {
		return cRedoCommand;
	}

	public CPasteElement getcPasteElement() {
		return cPasteElement;
	}

	public CChangeLanguage getcChangeLanguage() {
		return cChangeLanguage;
	}

	public CDeleteElements getcDeleteElements() {
		return cDeleteElements;
	}

	public void setcDeleteElements(CDeleteElements cDeleteElements) {
		this.cDeleteElements = cDeleteElements;
	}

	public CEditMode getcEditMode() {
		return cEditMode;
	}

	public CSquareState getcSquareState() {
		return cSquareState;
	}

	public CCircleState getcCircleState() {
		return cCircleState;
	}

	public CAddGraphSlot getcAddGraphSlot() {
		return cAddGraphSlot;
	}

	public CAddTextSlot getcAddTextSlot() {
		return cAddTextSlot;
	}

	public CAbout getcAbout() {
		return cAbout;
	}

	public CSelectState getcSelectState() {
		return cSelectState;
	}

	public CChangeWorkspace getcChangeWorkspace() {
		return cChangeWorkspace;
	}

	public CAddProject getcAddProject() {
		return cAddProject;
	}

	public CAddDocument getcAddDocument() {
		return cAddDocument;
	}

	public CRemoveObject getcProjectRemove() {
		return cProjectRemove;
	}

	public CTree getcTree() {
		return cTree;
	}

	public CCascadeAlignment getcCascadeAlignment() {
		return cCascadeAlignment;
	}

	public CHorizontalAlignment getcHorizontalAlignment() {
		return cHorizontalAlignment;
	}

	public CVerticalAlignment getcVerticalAlignment() {
		return cVerticalAlignment;
	}

	public CMatrixAlignment getcMatrixAlignment() {
		return cMatrixAlignment;
	}

	public CDocumentSelect getcDocumentSelect() {
		return cDocumentSelect;
	}

	public CWindowResize getcWindowResize() {
		return cWindowResize;
	}

	public COpenSettingsDialog getcOpenSettingsDialog() {
		return cOpenSettingsDialog;
	}

	public CAddPage getcAddPage() {
		return cAddPage;
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		cSaveAll.setEnabled(true);
		if (command.equals("focusChanged")) {
			cOpenProject.setEnabled(true);
			if (arg instanceof MDocument) {
				cAddPage.setEnabled(true);
			} else {
				cAddPage.setEnabled(false);
			}

			if (arg instanceof MProject) {

				cAddDocument.setEnabled(true);
			} else {
				cAddDocument.setEnabled(false);
				// cSaveProject.setEnabled(false);
			}

			if (arg instanceof MPage) {
				// cAddSlot.setEnabled(true);
				cAddGraphSlot.setEnabled(true);
				cAddTextSlot.setEnabled(true);
			} else {
				// cAddSlot.setEnabled(false);
				cAddGraphSlot.setEnabled(false);
				cAddTextSlot.setEnabled(false);
			}

			if (arg instanceof MSlot) {
				cEditMode.setEnabled(true);
			} else {
				cEditMode.setEnabled(false);
			}

			if (arg instanceof FileActions) {
				cProjectRemove.setEnabled(true);
				cSaveProject.setEnabled(true);
				cSaveAs.setEnabled(true);
			} else {
				cProjectRemove.setEnabled(false);
				cSaveProject.setEnabled(false);
				cSaveAs.setEnabled(false);
			}

			if (arg instanceof MElement) {
				cProjectRemove.setEnabled(true);
				cSaveProject.setEnabled(false);
				cSaveAs.setEnabled(false);
			}
		}
	}

	public CSaveAs getcSaveAs() {
		return cSaveAs;
	}

	public CSaveProject getcSaveProject() {
		return cSaveProject;
	}

	public CMouseListener getcMouseListener() {
		return cMouseListener;
	}

	public CPopupMenu getcPopupMenu() {
		return cPopupMenu;
	}

	public CCutElement getcCutElement() {
		return cCutElement;
	}

	public CRearrangePages getcRearrangePages(MDocument mDocument) {
		return CRearrangePages.getInstance(mDocument);
	}
}
