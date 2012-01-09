/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package
{
import com.yzh44yzh.scalaAmf.Game;
import com.yzh44yzh.scalaAmf.Service;

import flash.display.Shape;

import flash.display.Sprite;
import flash.display.StageAlign;
import flash.display.StageScaleMode;

import mx.logging.ILogger;
import mx.logging.Log;
import mx.logging.LogEventLevel;
import mx.logging.targets.TraceTarget;

public class SampleClient extends Sprite
{
	private var log : ILogger = Log.getLogger("SampleClient");

	private var game : Game;
	private var service : Service;

	public function SampleClient()
	{
		stage.scaleMode = StageScaleMode.NO_SCALE;
		stage.align = StageAlign.TOP_LEFT;

		initLogging();

		var canvas : Sprite = new Sprite();
		addChild(canvas);
		game = new Game(stage, canvas);

		service = new Service(game);
		connect();
	}

	private function initLogging() : void
	{
		var logTarget:TraceTarget = new TraceTarget();
		//logTarget.filters=["mx.rpc.*","mx.messaging.*"];
		logTarget.level = LogEventLevel.ALL;
		//logTarget.includeDate = true;
		logTarget.includeTime = true;
		logTarget.includeCategory = true;
		logTarget.includeLevel = true;

		Log.addTarget(logTarget);
	}
	
	private function connect() : void
	{
		trace("connect");
		service.connect("localhost", 2244);
	}
}
}
