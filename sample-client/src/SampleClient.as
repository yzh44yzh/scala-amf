/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package
{
import com.yzh44yzh.scalaAmf.Service;

import flash.display.Sprite;

import mx.logging.ILogger;
import mx.logging.Log;
import mx.logging.LogEventLevel;
import mx.logging.targets.TraceTarget;

public class SampleClient extends Sprite
{
	private var log : ILogger = Log.getLogger("SampleClient");

	private var service : Service = new Service();

	public function SampleClient()
	{
		initLogging();
		connect();
	}

	private function initLogging():void
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
