/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package
{
import flash.display.Sprite;
import flash.utils.ByteArray;

import some.pack.Message;

public class FlashLab extends Sprite
{
    public function FlashLab()
    {
        trace("FlashLab");

        // var arr : Array = [1, "Hello", 35.25, true];
        //var arr : Array = [[1,2,3], ["a", "b", "c"], ["Hello"]];

        var msg : Message = new Message();
        msg.id = 25;
        msg.sender = "Bob";
        msg.content = "Hello";
        msg.isPrivate = true;
        msg.some = 5;

        var buf : ByteArray = new ByteArray();
        buf.writeObject(msg);

        showBuf(buf);

        trace(new Number(0x23).toString(2));
        trace(new Number(0x33).toString(2));
        trace(new Number(0x43).toString(2));
        trace(new Number(0x53).toString(2));
    }

    private function showBuf(buf : ByteArray) : void
    {
        var res : String = "";

        try
        {
            buf.position = 0;

            while(buf.bytesAvailable)
            {
                var byte : Number = buf.readByte();
                res += ", 0x" + byte.toString(16);
            }
        }
        catch(e : Error)
        {
            trace(e);
        }

        trace(res);
    }
}
}
