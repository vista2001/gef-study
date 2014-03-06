package com.qualityeclipse.genealogy.view;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolygonShape;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import com.qualityeclipse.genealogy.listener.FigureMover;

public class GenealogyView extends ViewPart{
	/**添加一个快速测试视图的方法*/
	public static void main(String[] args){
		new GenealogyView().run();
	}
	private void run(){
		//创建shell并设置布局，这里的布局使用swt的布局
		Shell shell=new Shell(new Display());
		shell.setSize(365,280);
		shell.setText("Genealogy");
		shell.setLayout(new GridLayout());

		//创建基于shell的画布
		Canvas canvas=createDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		Display display=shell.getDisplay();
		shell.open();
		while(!shell.isDisposed()){
			while(!display.readAndDispatch()){
				display.sleep();
			}
		}
	}
	
	/**创建图*/
	private Canvas createDiagram(Composite parent){
		//创建一个root图形，这个图形用来容纳其他所有图形
		Figure root=new Figure();
		root.setFont(parent.getFont());
		//root的图形设定为XYLayout方式
		root.setLayoutManager(new XYLayout());
		
		//创建andy、betty、carl、marrage的图形并在root中添加他们
		IFigure andy=createPersonFigure("Andy");
		root.add(andy,new Rectangle(new Point(10,10),andy.getPreferredSize()));
		
		IFigure betty=createPersonFigure("Betty");
		root.add(betty,new Rectangle(new Point(230,10),betty.getPreferredSize()));
		
		IFigure carl=createPersonFigure("Carl");
		root.add(carl,new Rectangle(new Point(120,120),carl.getPreferredSize()));
		
		IFigure marriage = crea1teMarriageFigure();
		root.add(marriage, new Rectangle(new Point(145, 35), marriage.getPreferredSize()));
		
		//在root中为andy、betty和carl添加链接到marriage
		root.add(connection(andy,marriage));
		root.add(connection(betty,marriage));
		root.add(connection(carl,marriage));
		
		//设定画布相关的内容
		Canvas canvas=new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws=new LightweightSystem(canvas);
		lws.setContents(root);
		return canvas;
	}
	/**创建marriage图形*/
	private IFigure crea1teMarriageFigure() {
		Rectangle r = new Rectangle(0, 0, 50, 50);
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setStart(r.getTop());
		polygonShape.addPoint(r.getTop());
		polygonShape.addPoint(r.getLeft());
		polygonShape.addPoint(r.getBottom());
		polygonShape.addPoint(r.getRight());
		polygonShape.addPoint(r.getTop());
		polygonShape.setEnd(r.getTop());
		polygonShape.setFill(true);
		polygonShape.setBackgroundColor(ColorConstants.lightGray);
		polygonShape.setPreferredSize(r.getSize());
		new FigureMover(polygonShape);
		return polygonShape;
	}
	/**创建人物图形*/
	private IFigure createPersonFigure(String name) {
		RectangleFigure rectangleFigure = new RectangleFigure();
		rectangleFigure.setBackgroundColor(ColorConstants.lightGray);
		rectangleFigure.setLayoutManager(new ToolbarLayout());
		rectangleFigure.setPreferredSize(100, 100);
		rectangleFigure.add(new Label(name));
		new FigureMover(rectangleFigure);
		return rectangleFigure;
		}
	/**创建连线图形*/
	private Connection connection(IFigure figure1,IFigure figure2){
		PolylineConnection connection=new PolylineConnection();
		connection.setSourceAnchor(new ChopboxAnchor(figure1));
		connection.setTargetAnchor(new ChopboxAnchor(figure2));
		return connection;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		//调用createDiagram方法创建图
		createDiagram(parent);
	}
	@Override
	public void setFocus() {
		
	}
	
}
