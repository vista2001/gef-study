package com.qualityeclipse.genealogy.misc;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.qualityeclipse.genealogy.listener.FigureMover;

public class BasicDecorations {
	public static void main(String[] args) {
		BasicDecorations mgr = new BasicDecorations();
		Shell shell = mgr.initShell();
		Canvas canvas = mgr.createDiagram(shell);
		canvas.setLayoutData(new org.eclipse.swt.layout.GridData(
				org.eclipse.swt.layout.GridData.FILL_BOTH));
		mgr.runShell(shell);
	}

	private Shell initShell() {
		Shell shell = new Shell(new Display());
		shell.setSize(380, 375);
		shell.setText("Basic Draw2D Decorations");
		shell.setLayout(new org.eclipse.swt.layout.GridLayout());
		return shell;
	}

	private void runShell(Shell shell) {
		Display display = shell.getDisplay();
		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Canvas createDiagram(Composite parent) {

		// Create a root figure and simple layout to contain all other figures
		IFigure root = new Figure();
		root.setFont(parent.getFont());
		root.setLayoutManager(new XYLayout());

		addSmallPolygonArrowheads(root);
		addSmallPolylineArrowhead(root);

		// Create the canvas and LightweightSystem
		// and use it to show the root figure in the shell
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);
		return canvas;
	}

	private void addSmallPolygonArrowheads(IFigure root) {
		PolylineConnection conn = newFigureAndConnection(root,
				"SmallPolygonArrowheads", 10, 10);
		// Set the source decoration
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setBackgroundColor(ColorConstants.blue);
		PointList points=new PointList();
		points.addPoint(0, 0);
		points.addPoint(-1, 1);
		points.addPoint(-2, 0);
		points.addPoint(-1, -1);
		points.addPoint(0, 0);
		decoration.setTemplate(points);
		//decoration.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
		conn.setSourceDecoration(decoration);
		// Set the target decoration
		PolygonDecoration decorationT = new PolygonDecoration();
		decorationT.setBackgroundColor(ColorConstants.yellow);
		PointList pointsT=new PointList();
		pointsT.addPoint(0, 0);
		pointsT.addPoint(-1, 1);
		pointsT.addPoint(-2, 0);
		pointsT.addPoint(-1, -1);
		pointsT.addPoint(0, 0);
		decorationT.setTemplate(pointsT);
		conn.setTargetDecoration(decorationT);
	}
	
	private void addSmallPolylineArrowhead(IFigure root) {
		PolylineConnection conn = newFigureAndConnection(root,
				"SmallPolylineArrowheads", 10, 110);
		// Set the target decoration
		conn.setTargetDecoration(new PolylineDecoration());
	}
	
	private PolylineConnection newFigureAndConnection(IFigure root,
			String text, int x, int y) {
		Ellipse ellipse = new Ellipse();
		ellipse.setBorder(new MarginBorder(10));
		ellipse.setLayoutManager(new GridLayout());
		ellipse.add(new Label(text));
		ellipse.setFont(root.getFont());
		root.add(
				ellipse,
				new Rectangle(
						new Point(x + 30, y + 20), 
						ellipse.getPreferredSize()));

		new FigureMover(ellipse);
		PolylineConnection conn = new PolylineConnection();
		conn.setSourceAnchor(new XYAnchor(new Point(x, y)));
		conn.setTargetAnchor(new EllipseAnchor(ellipse));
		root.add(conn);
		return conn;
	}
	
}
