/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf
{
import flash.display.Shape;
import flash.display.Sprite;
import flash.display.Stage;
import flash.events.MouseEvent;
import flash.geom.Point;

public class Game
{
	public var color : int = 0xff0000;

	private var canvas : Sprite;
	private var marker : Shape;
	private var downPoint : Point;

	public function Game(stage : Stage, canvas : Sprite)
	{
		stage.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
		stage.addEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
		stage.addEventListener(MouseEvent.MOUSE_UP, onMouseUp);

		this.canvas = canvas;
		marker = new Shape();
		canvas.addChild(marker);
	}

	private function onMouseDown(e : MouseEvent) : void
	{
		downPoint = new Point(e.localX, e.localY);
	}

	private function onMouseMove(e : MouseEvent) : void
	{
		if(downPoint == null) return;

		with(marker.graphics)
		{
			clear();
			beginFill(color, 0.4);
			drawCircle(downPoint.x, downPoint.y,  getRadius(e.localX, e.localY));
			endFill();
		}
	}

	private function onMouseUp(e : MouseEvent) : void
	{
		with(canvas.graphics)
		{
			beginFill(color, 0.7);
			drawCircle(downPoint.x, downPoint.y,  getRadius(e.localX, e.localY));
			endFill();
		}

		downPoint = null;
	}

	private function getRadius(toX : Number, toY : Number) : int
	{
		var x : int = Math.abs(toX - downPoint.x);
		var y : int = Math.abs(toY - downPoint.y);
		var res : int = Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
		return Math.max(res, 20);
	}
}
}
