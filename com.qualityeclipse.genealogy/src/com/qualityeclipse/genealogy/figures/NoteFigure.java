package com.qualityeclipse.genealogy.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

import com.qualityeclipse.genealogy.borders.NoteBorder;

public class NoteFigure extends Label {
	public NoteFigure(String note) {
		super(note);
		/*setOpaque(true);
		setBackgroundColor(ColorConstants.white);*/
		setBorder(new NoteBorder());
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		graphics.setBackgroundColor(ColorConstants.white);
		Rectangle b = getBounds();
		final int fold = NoteBorder.FOLD;
		graphics.fillRectangle(b.x + fold, b.y, b.width - fold, fold);
		graphics.fillRectangle(b.x, b.y + fold, b.width, b.height - fold);
		super.paintFigure(graphics);
	}
	
}
