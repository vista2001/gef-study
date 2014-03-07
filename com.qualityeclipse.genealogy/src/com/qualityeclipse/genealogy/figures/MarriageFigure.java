package com.qualityeclipse.genealogy.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolygonShape;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.qualityeclipse.genealogy.anchors.MarriageAnchor;
import com.qualityeclipse.genealogy.listener.FigureMover;

public class MarriageFigure extends PolygonShape {
	public static final int RADIUS = 26;
	private static final PointList ARROWHEAD = new PointList(
			new int[] { 0, 0, -2, 2, -2, 0, -2, -2, 0, 0 });
	
	/** ´´½¨marriageÍ¼ÐÎ */
	public MarriageFigure(int year) {
		Rectangle r = new Rectangle(0, 0, 50, 50);
		setStart(r.getTop());
		addPoint(r.getTop());
		addPoint(r.getLeft());
		addPoint(r.getBottom());
		addPoint(r.getRight());
		addPoint(r.getTop());
		setEnd(r.getTop());
		setFill(true);
		setBackgroundColor(ColorConstants.lightGray);
		setPreferredSize(r.getSize());
		// setBorder(new LineBorder(1));
		setLayoutManager(new StackLayout());
		add(new Label(Integer.toString(year)){

			@Override
			public boolean containsPoint(int x, int y) {
				return false;
			}
			
		});
		new FigureMover(this);
	}

	public PolylineConnection addParent(IFigure figure) {
		PolylineConnection connection = new PolylineConnection();
		connection.setSourceAnchor(new ChopboxAnchor(figure));
		connection.setTargetAnchor(new MarriageAnchor(this));
		
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		decoration.setBackgroundColor(ColorConstants.darkGray);
		connection.setTargetDecoration(decoration);
		
		return connection;
	}

	public PolylineConnection addChild(IFigure figure) {
		PolylineConnection connection = new PolylineConnection();
		connection.setSourceAnchor(new MarriageAnchor(this));
		connection.setTargetAnchor(new ChopboxAnchor(figure));
		
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		decoration.setBackgroundColor(ColorConstants.white);
		connection.setTargetDecoration(decoration);
		
		return connection;
	}
}
