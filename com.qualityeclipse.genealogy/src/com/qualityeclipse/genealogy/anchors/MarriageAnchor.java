package com.qualityeclipse.genealogy.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;

import static com.qualityeclipse.genealogy.figures.MarriageFigure.RADIUS;;

public class MarriageAnchor extends AbstractConnectionAnchor{

	public MarriageAnchor(IFigure owner){
		super(owner);
	}
	
	@Override
	public Point getLocation(Point originalReference) {
		PrecisionPoint reference = new PrecisionPoint(originalReference);
		PrecisionPoint origin = new PrecisionPoint(getOwner().getBounds().getCenter());
		getOwner().translateToAbsolute(origin);
		
		double Ax = Math.abs(reference.preciseX() - origin.preciseX());
		double Ay = Math.abs(reference.preciseY() - origin.preciseY());
		double divisor = Ax + Ay;
		if (divisor == 0.0D)
			return origin;
		
		PrecisionDimension radius=new PrecisionDimension(RADIUS,RADIUS);
		
		if (reference.preciseX() < origin.preciseX())
			radius.setPreciseWidth( 1.0D - radius.preciseWidth());
			if (reference.preciseY() < origin.preciseY())
			radius.setPreciseHeight( 1.0D - radius.preciseHeight());
			
		getOwner().translateToAbsolute(radius);
		
		double x = (radius.preciseWidth() * Ax) / divisor;
		double y = (radius.preciseHeight() * Ay) / divisor;
		
	/*	if (reference.preciseX() < origin.preciseX())
			x = -x;
			if (reference.preciseY() < origin.preciseY())
			y = -y;*/
		return new PrecisionPoint(origin.preciseX()+x,origin.preciseY()+y);
		
		/*Point origin = getOwner().getBounds().getCenter();
		getOwner().translateToAbsolute(origin);
		
		Dimension radius = new Dimension(RADIUS, RADIUS);
		getOwner().translateToAbsolute(radius);
		
		int Ax = Math.abs(originalReference.x - origin.x);
		int Ay = Math.abs(originalReference.y - origin.y);
		int divisor = Ax + Ay;
		if (divisor == 0)
			return origin;
		int x = (radius.width * Ax) / divisor;
		int y = (radius.height* Ay) / divisor;
		if (originalReference.x < origin.x)
		x = -x;
		if (originalReference.y < origin.y)
		y = -y;
		return new Point(origin.x + x, origin.y + y);*/
			
	}
}
