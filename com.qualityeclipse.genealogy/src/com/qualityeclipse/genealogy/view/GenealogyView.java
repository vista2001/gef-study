package com.qualityeclipse.genealogy.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.graph.ShortestPathRouter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import com.qualityeclipse.genealogy.figures.MarriageFigure;
import com.qualityeclipse.genealogy.figures.NoteFigure;
import com.qualityeclipse.genealogy.figures.PersonFigure;
import static com.qualityeclipse.genealogy.figures.PersonFigure.MALE;
import static com.qualityeclipse.genealogy.figures.PersonFigure.FEMALE;

public class GenealogyView extends ViewPart {
	FreeformLayeredPane root;
	FreeformLayer primary;
	ConnectionLayer connectionLayer;

	/** 添加一个快速测试视图的方法 */
	public static void main(String[] args) {
		new GenealogyView().run();
	}

	private void run() {
		// 创建shell并设置布局，这里的布局使用swt的布局
		Shell shell = new Shell(new Display());
		shell.setSize(365, 280);
		shell.setText("Genealogy");
		shell.setLayout(new GridLayout());

		FigureCanvas canvas = createDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));

		createMenuBar(shell);

		// 创建基于shell的画布
		Display display = shell.getDisplay();
		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createMenuBar(Shell shell) {
		final Menu menubar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menubar);
		MenuItem zoomMenuItem = new MenuItem(menubar, SWT.CASCADE);
		zoomMenuItem.setText("Zoom");
		Menu zoomMenu = new Menu(shell, SWT.DROP_DOWN);
		zoomMenuItem.setMenu(zoomMenu);
		
		createFixedZoomMenuItem(zoomMenu, "50%", 0.5);
		createFixedZoomMenuItem(zoomMenu, "100%", 1);
		createFixedZoomMenuItem(zoomMenu, "150%", 1.5);
		createFixedZoomMenuItem(zoomMenu, "200%", 2);
		createFixedZoomMenuItem(zoomMenu, "300%", 3);
		createFixedZoomMenuItem(zoomMenu, "400%", 4);
		createFixedZoomMenuItem(zoomMenu, "500%", 5);
		createFixedZoomMenuItem(zoomMenu, "600%", 6);
		createFixedZoomMenuItem(zoomMenu, "700%", 7);
		
		createScaleToFitMenuItem(zoomMenu);
	}

	private void createFixedZoomMenuItem(Menu zoomMenu, String text,
			final double scale) {
		MenuItem menuItem;
		menuItem = new MenuItem(zoomMenu, SWT.NULL);
		menuItem.setText(text);
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				((ScalableFreeformLayeredPane) root).setScale(scale);
			}
		});
	}

	/** 创建图 */
	private FigureCanvas createDiagram(Composite parent) {
		root = new ScalableFreeformLayeredPane();
		root.setFont(parent.getFont());
		primary = new FreeformLayer();
		/*
		 * primary=new Layer(){
		 * 
		 * @Override public boolean containsPoint(int x, int y) { // TODO
		 * Auto-generated method stub return false; } };
		 */
		primary.setLayoutManager(new FreeformLayout());
		root.add(primary, "Primary");

		connectionLayer = new ConnectionLayer();
		connectionLayer.setConnectionRouter(new ShortestPathConnectionRouter(
				primary));
		root.add(connectionLayer, "Connections");

		// 创建andy、betty、carl、marrage的图形并在root中添加他们
		IFigure andy = new PersonFigure("Andy", MALE, 1922, 2002);
		andy.add(new NoteFigure("Andy was a\ngood man."));
		primary.add(andy,
				new Rectangle(new Point(10, 10), andy.getPreferredSize()));

		IFigure betty = new PersonFigure("Betty", FEMALE, 1924, 2006);
		betty.add(new NoteFigure("Betty was a\ngood woman."));
		primary.add(betty,
				new Rectangle(new Point(230, 10), betty.getPreferredSize()));

		IFigure carl = new PersonFigure("Carl", MALE, 1947, -1);
		carl.add(new NoteFigure("Carl is a\ngood man."));
		primary.add(carl,
				new Rectangle(new Point(120, 120), carl.getPreferredSize()));

		MarriageFigure marriage = new MarriageFigure(1944);
		primary.add(marriage,
				new Rectangle(new Point(145, 35), marriage.getPreferredSize()));

		// Add a "loose" note
		NoteFigure note = new NoteFigure("Smith Family");
		note.setFont(parent.getFont());
		Dimension noteSize = note.getPreferredSize();
		primary.add(note, new Rectangle(new Point(10, 220 - noteSize.height),
				noteSize));

		// Second "loose" note
		note = new NoteFigure("Another note");
		note.setFont(parent.getFont());
		noteSize = note.getPreferredSize();
		primary.add(note, new Rectangle(new Point(10, 170), noteSize));

		// 在root中为andy、betty和carl添加链接到marriage
		connectionLayer.add(marriage.addParent(andy));
		connectionLayer.add(marriage.addParent(betty));
		connectionLayer.add(marriage.addChild(carl));

		// 设定画布相关的内容
		FigureCanvas canvas = new FigureCanvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		canvas.setViewport(new FreeformViewport());
		canvas.setContents(root);
		return canvas;
	}

	/** 创建连线图形 */
	private Connection connection(IFigure figure1, IFigure figure2) {
		PolylineConnection connection = new PolylineConnection();
		connection.setSourceAnchor(new ChopboxAnchor(figure1));
		connection.setTargetAnchor(new ChopboxAnchor(figure2));
		return connection;
	}

	@Override
	public void createPartControl(Composite parent) {
		// 调用createDiagram方法创建图
		createDiagram(parent);
	}

	@Override
	public void setFocus() {

	}

	private void createScaleToFitMenuItem(Menu menu) {
		MenuItem menuItem = new MenuItem(menu, SWT.NULL);
		menuItem.setText("Scale to fit");
		menuItem.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				scaleToFit();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	private void scaleToFit() {
		FreeformViewport viewport = (FreeformViewport) root.getParent();
		Rectangle viewArea = viewport.getClientArea();
		((ScalableFreeformLayeredPane)root).setScale(1);
		Rectangle extent = root.getFreeformExtent().union(0, 0);
		double wScale = ((double) viewArea.width) / extent.width;
		double hScale = ((double) viewArea.height) / extent.height;
		double newScale = Math.min(wScale, hScale);
		((ScalableFreeformLayeredPane)root).setScale(newScale);
		}
}
