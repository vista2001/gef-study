package com.qualityeclipse.genealogy.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FrameBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

import com.qualityeclipse.genealogy.borders.NoteBorder;
import com.qualityeclipse.genealogy.listener.FigureMover;

public class PersonFigure extends RectangleFigure {
	public static final Image MALE = new Image(Display.getCurrent(),
			PersonFigure.class.getResourceAsStream("male.png"));
	public static final Image FEMALE = new Image(Display.getCurrent(),
			PersonFigure.class.getResourceAsStream("female.png"));

	/** 创建人物图形 */
	public PersonFigure(String name, Image image, int birthYear, int deathYear) {
		// setBackgroundColor(ColorConstants.lightGray);
		final ToolbarLayout layout = new ToolbarLayout();
		layout.setSpacing(1);
		setLayoutManager(layout);
		setPreferredSize(100, 100);
		setBorder(new CompoundBorder(
				new LineBorder(1),
				new MarginBorder(2, 2, 2, 2)));
		add(new Label(name));
		// Display the image to the left of the name/date
		IFigure imageNameDates = new Figure();
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 1;
		imageNameDates.setLayoutManager(gridLayout);
		add(imageNameDates);
		imageNameDates.add(new ImageFigure(image));
		
		// Display the name and date to right of image
		IFigure nameDates = new Figure();
		nameDates.setLayoutManager(new ToolbarLayout());
		imageNameDates.add(nameDates, new GridData(GridData.FILL_HORIZONTAL));
		nameDates.add(new Label(name));

		// Display the year of birth and death
		String datesText = birthYear + " -";
		if (deathYear != -1)
		datesText += " " + deathYear;
		nameDates.add(new Label(datesText)); 
		
		/*
		 * Label noteLabel = new Label(note); noteLabel.setBorder(new
		 * NoteBorder());
		 */
		// add(new NoteFigure(note));

		new FigureMover(this);
	}

	@Override
	public void paintFigure(Graphics graphics) {
		Rectangle r = getBounds();
		graphics.setBackgroundPattern(new Pattern(Display.getCurrent(), r.x,
				r.y, r.x + r.width, r.y + r.height, ColorConstants.white,
				ColorConstants.lightGray));
		graphics.fillRectangle(r);
	}
}
