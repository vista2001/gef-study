package com.qualityeclipse.genealogy.misc;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FrameBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GroupBoxBorder;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.SimpleEtchedBorder;
import org.eclipse.draw2d.SimpleLoweredBorder;
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BasicBorders {
	public static void main(String[] args) {
		new BasicBorders().run();
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

		// CompoundBorder
		Label cmpoundBorderlabel = new Label("CompoundBorder\n"
				+ "with GroupBoxBorder and MarginBorder");
		cmpoundBorderlabel.setBorder(new CompoundBorder(new GroupBoxBorder(
				"My Group"), new MarginBorder(10)));
		root.add(cmpoundBorderlabel, new Rectangle(10, 150, 350, 120));

		// FrameBorder
		Label frameBorderlabel = new Label("FrameBorder");
		frameBorderlabel.setBorder(new FrameBorder("My Title"));
		root.add(frameBorderlabel, new Rectangle(270, 10, 100, 60));

		// GroupBoxBorder
		Label groupBoxBorderlabel = new Label("GroupBoxBorder");
		groupBoxBorderlabel.setBorder(new GroupBoxBorder("My Group"));
		root.add(groupBoxBorderlabel, new Rectangle(120, 10, 140, 60));

		// LineBorder
		Label lineBorderlabel = new Label("LineBorder");
		lineBorderlabel.setBorder(new LineBorder(ColorConstants.blue, 3,
				Graphics.LINE_DASH));
		root.add(lineBorderlabel, new Rectangle(10, 10, 100, 60));

		// SimpleEtchedBorder
		Label simpleEtchedBorderlabel = new Label("SimpleEtchedBorder");
		simpleEtchedBorderlabel.setBorder(SimpleEtchedBorder.singleton);
		root.add(simpleEtchedBorderlabel, new Rectangle(10, 80, 150, 60));

		// SimpleLoweredBorder
		Label simpleLoweredBorderlabel = new Label("SimpleLoweredBorder");
		simpleLoweredBorderlabel.setBorder(new SimpleLoweredBorder(3));
		root.add(simpleLoweredBorderlabel, new Rectangle(170, 80, 150, 60));

		// SimpleRaisedBorder
		Label simpleRaisedBorderlabel = new Label("SimpleRaisedBorder");
		simpleRaisedBorderlabel.setBorder(new SimpleRaisedBorder(3));
		root.add(simpleRaisedBorderlabel, new Rectangle(330, 80, 150, 60));

		// TitleBarBorder
		Label titleBarBorderlabel = new Label("TitleBarBorder");
		titleBarBorderlabel.setBorder(new TitleBarBorder("My Title"));
		root.add(titleBarBorderlabel, new Rectangle(380, 10, 100, 60));

		// 设定画布相关的内容
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);
		return canvas;
	}
}
