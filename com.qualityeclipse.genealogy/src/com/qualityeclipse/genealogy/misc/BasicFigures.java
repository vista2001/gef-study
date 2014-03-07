package com.qualityeclipse.genealogy.misc;

import org.eclipse.draw2d.ArrowButton;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.CheckBox;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ImageUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Triangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BasicFigures {
	public static void main(String[] args) {
		new BasicFigures().run();
	}

	private void run() {
		// 创建shell并设置布局，这里的布局使用swt的布局
		Shell shell = new Shell(new Display());
		shell.setSize(365, 280);
		shell.setText("Genealogy");
		shell.setLayout(new GridLayout());

		// 创建基于shell的画布
		Canvas canvas = createDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		Display display = shell.getDisplay();
		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Canvas createDiagram(Composite parent) {
		// 创建一个root图形，这个图形用来容纳其他所有图形
		Figure root = new Figure();
		root.setFont(parent.getFont());
		// root的图形设定为XYLayout方式
		root.setLayoutManager(new XYLayout());

		// 创建椭圆
		Ellipse ellipse = new Ellipse();
		ellipse.setBackgroundColor(ColorConstants.orange);
		ellipse.setPreferredSize(60, 40);
		root.add(ellipse,
				new Rectangle(new Point(150, 10), ellipse.getPreferredSize()));

		// 创建多边形
		Polygon polygon = new Polygon();
		polygon.addPoint(new Point(290, 10));
		polygon.addPoint(new Point(350, 10));
		polygon.addPoint(new Point(390, 50));
		polygon.addPoint(new Point(330, 50));
		polygon.setFill(true);
		polygon.setBackgroundColor(ColorConstants.darkGreen);
		root.add(polygon,
				new Rectangle(polygon.getStart(), polygon.getPreferredSize()));

		// 创建折线
		Polyline polyline = new Polyline();
		polyline.addPoint(new Point(400, 10));
		polyline.addPoint(new Point(460, 10));
		polyline.addPoint(new Point(440, 50));
		polyline.addPoint(new Point(500, 50));
		root.add(polyline,
				new Rectangle(polyline.getStart(), polyline.getPreferredSize()));

		// 创建矩形
		RectangleFigure rectangleFigure = new RectangleFigure();
		rectangleFigure.setBackgroundColor(ColorConstants.cyan);
		rectangleFigure.setPreferredSize(60, 40);
		root.add(rectangleFigure, new Rectangle(new Point(10, 10),
				rectangleFigure.getPreferredSize()));

		// 创建圆角矩形
		RoundedRectangle roundedRectangle = new RoundedRectangle();
		roundedRectangle.setCornerDimensions(new Dimension(10, 10));
		roundedRectangle.setPreferredSize(60, 40);
		root.add(roundedRectangle, new Rectangle(new Point(80, 10),
				roundedRectangle.getPreferredSize()));

		// 创建三角形
		Triangle triangle = new Triangle();
		root.add(triangle, new Rectangle(220, 10, 60, 40));

		// 创建箭头按钮
		ArrowButton arrowButton = new ArrowButton(PositionConstants.EAST);
		root.add(arrowButton, new Rectangle(10, 60, 20, 20));

		// 创建按钮
		Button button = new Button("This is a button");
		root.add(button, new Rectangle(40, 60, 100, 20));

		// 创建多选盒
		CheckBox checkbox = new CheckBox("This is a checkbox");
		root.add(checkbox, new Rectangle(150, 60, 150, 20));

		//创建图像图形
		final Image image=ImageUtilities.createRotatedImageOfString("Rotated Text", new Font(Display.getCurrent(),"Courier New",12,SWT.NORMAL), ColorConstants.black, ColorConstants.white);
		parent.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				image.dispose();
			}
		});
		ImageFigure imageFigure=new ImageFigure(image);
		root.add(imageFigure,new Rectangle(10,120,100,200));
		
		//创建标签
		Label label = new Label("This is a label");
		label.setBorder(new LineBorder(1));
		root.add(label, new Rectangle(10, 90, 100, 20));
		
		// 设定画布相关的内容
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);
		return canvas;
	}
}
