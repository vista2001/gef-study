package com.qualityeclipse.genealogy.listener;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;

/**
 * A Draw2D mouse listener for dragging figures around the diagram. Listeners such as this
 * are useful for manipulating Draw2D diagrams, but are superseded by higher level GEF
 * functionality if the GEF framework is used.
 */
public class FigureMover implements MouseListener, MouseMotionListener
{
	private IFigure figure;
	private Point location;
	
	//把传入的figure作为被侦听的对象，添加FigureMover这个类作为监听者
	public FigureMover(IFigure figure) {
		this.figure=figure;
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);
	}
	//处理鼠标拖动
	@Override
	public void mouseDragged(MouseEvent me) {
		//校验位置和新移动的位置是否存在
		if(location==null){
			return;
		}
		Point newLocation=me.getLocation();
		if(newLocation==null){
			return;
		}
		//创建偏移量，如果偏移量为0，就返回
		Dimension offset=newLocation.getDifference(location);
		if(offset.width==0 && offset.height==0){
			return;
		}
		//设定新拖动的地址为当前地址
		location=newLocation;
		//获取更新管理器和布局管理器
		UpdateManager updateManager=figure.getUpdateManager();
		LayoutManager layoutManager=figure.getParent().getLayoutManager();
		//返回一个最小的包括这个图形的矩形
		Rectangle bounds=figure.getBounds();
		updateManager.addDirtyRegion(figure.getParent(), bounds);
		bounds = bounds.getCopy().translate(offset.width, offset.height);
		layoutManager.setConstraint(figure, bounds);
		figure.translate(offset.width, offset.height);
		updateManager.addDirtyRegion(figure.getParent(), bounds);
		me.consume();
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		
	}

	@Override
	public void mouseExited(MouseEvent me) {
		
	}

	@Override
	public void mouseHover(MouseEvent me) {
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		location = me.getLocation();
		me.consume();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if (location == null)
			return;
		location = null;
		me.consume();
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		
	}
}