package com.qualityeclipse.genealogy.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

public class CenterAnchor extends AbstractConnectionAnchor{

	public CenterAnchor(IFigure owner){
		super(owner);
	}
	
	@Override
	public Point getLocation(Point reference) {
		return getOwner().getBounds().getCenter();
	}

}
