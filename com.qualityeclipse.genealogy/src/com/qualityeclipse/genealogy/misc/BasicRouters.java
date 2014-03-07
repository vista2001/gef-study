package com.qualityeclipse.genealogy.misc;

import java.util.Arrays;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.qualityeclipse.genealogy.listener.FigureMover;

public class BasicRouters {
	private static final PointList ARROWHEAD = new PointList(
			new int[] { 0, 0, -2, 2, -2, 0, -2, -2, 0, 0 });
	
	public static void main(String[] args) {
		BasicRouters mgr = new BasicRouters();
		Shell shell = mgr.initShell();
		Canvas canvas = mgr.createDiagram(shell);
		canvas.setLayoutData(new org.eclipse.swt.layout.GridData(
				org.eclipse.swt.layout.GridData.FILL_BOTH));
		mgr.runShell(shell);
	}

	private Shell initShell() {
		Shell shell = new Shell(new Display());
		shell.setSize(380, 375);
		shell.setText("Basic Draw2D Anchors");
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

		addBendpointConnectionRouter(root);
		addFanRouter(root);
		addManhattanConnectionRouter(root);
		
		// Create the canvas and LightweightSystem
		// and use it to show the root figure in the shell
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);
		return canvas;
	}
	
	private PolylineConnection newFigureAndConnection(IFigure root, String text, int x, int y){
		Ellipse ellipse=new Ellipse();
		ellipse.setLayoutManager(new GridLayout());
		ellipse.setBorder(new MarginBorder(10));
		ellipse.add(new Label(text));
		ellipse.setFont(root.getFont());
		root.add(ellipse,new Rectangle(new Point(x+30,y+20),ellipse.getPreferredSize()));
		
		new FigureMover(ellipse);
		PolylineConnection conn = new PolylineConnection();
		conn.setSourceAnchor(new XYAnchor(new Point(x, y)));
		conn.setTargetAnchor(new EllipseAnchor(ellipse));
		
		PolygonDecoration decoration=new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		decoration.setBackgroundColor(ColorConstants.yellow);
		conn.setTargetDecoration(decoration);
		root.add(conn);
		return conn;
	}
	
	private void addBendpointConnectionRouter(IFigure root){
		PolylineConnection connection=newFigureAndConnection(root,"BendpointConnectionRouter",10,10);
		BendpointConnectionRouter router=new BendpointConnectionRouter();
		AbsoluteBendpoint bp1=new AbsoluteBendpoint(350, 10);
		RelativeBendpoint bp2=new RelativeBendpoint(connection);
		bp2.setRelativeDimensions(new Dimension(-50, 20), new Dimension(10, -40));
		RelativeBendpoint bp3 = new RelativeBendpoint(connection);
		bp3.setRelativeDimensions(new Dimension(0, 0), new Dimension(20, -45));
		bp3.setWeight(1);
		router.setConstraint(connection, 
				Arrays.asList(new Bendpoint[] { bp1, bp2, bp3 }));
				connection.setConnectionRouter(router);
	}
	private void addFanRouter(IFigure root){
		Ellipse ellipse=newFigure(root, "container", 60, 110);
		PolylineConnection connection;
		FanRouter router=new FanRouter();
		router.setNextRouter(ConnectionRouter.NULL);
		
		//connection=newConnection(root,30,40,ellipse);
		
		connection = newConnection(root, 10, 110, ellipse);
		connection.setConnectionRouter(router);
		
		ConnectionAnchor sourceAnchor = connection.getSourceAnchor();
		ConnectionAnchor targetAnchor = connection.getTargetAnchor();

		connection = newConnection(root, sourceAnchor, targetAnchor);
		connection.setConnectionRouter(router);
		
		connection = newConnection(root, sourceAnchor, targetAnchor);
		connection.setConnectionRouter(router);
		
		connection = newConnection(root, sourceAnchor, targetAnchor);
		connection.setConnectionRouter(router);
		
	}
	private void addManhattanConnectionRouter(IFigure container) {
		PolylineConnection connection = newFigureAndConnection(container,
				"ManhattanConnectionRouter", 225, 110);
		connection.setConnectionRouter(new ManhattanConnectionRouter());
	}
	private Ellipse newFigure(IFigure container, String text, int x, int y) {
		Ellipse ellipse = new Ellipse();
		ellipse.setBorder(new MarginBorder(10));
		ellipse.setLayoutManager(new GridLayout());
		ellipse.add(new Label(text));
		ellipse.setFont(container.getFont());
		container.add(ellipse,
				new Rectangle(new Point(x + 30, y + 30), ellipse
						.getPreferredSize()));
		new FigureMover(ellipse);
		return ellipse;
	}

	private PolylineConnection newConnection(IFigure container, int x, int y,
			Ellipse ellipse) {
		XYAnchor sourceAnchor = new XYAnchor(new Point(x, y));
		EllipseAnchor targetAnchor = new EllipseAnchor(ellipse);
		return newConnection(container, sourceAnchor, targetAnchor);
	}
	private PolylineConnection newConnection(IFigure container,
			ConnectionAnchor sourceAnchor, ConnectionAnchor targetAnchor) {
		PolylineConnection conn = new PolylineConnection();
		conn.setSourceAnchor(sourceAnchor);
		conn.setTargetAnchor(targetAnchor);
		container.add(conn);

		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		decoration.setBackgroundColor(ColorConstants.blue);
		conn.setTargetDecoration(decoration);
		return conn;
	}
}
