/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package
{
import flash.display.Sprite;
import flash.utils.ByteArray;

import some.pack.Message;
import some.pack.User;

public class FlashLab extends Sprite
{
    public function FlashLab()
    {
        trace("FlashLab");

        // var arr : Array = [1, "Hello", 35.25, true];
        //var arr : Array = [[1,2,3], ["a", "b", "c"], ["Hello"]];

        /*
        var msg : Message = new Message();
        msg.id = 25;
        msg.sender = "Bob";
        msg.content = "Hello";

        var obj = {action:"sendMessage", message:msg, request:24};
        */

        var user : User = new User();
        user.id = 2;
        user.name = "John";
        user.age = 44;
        user.admin = true;

        var msg : Message = new Message();
        msg.sender = user;
        msg.content = "How are you? :)";

        var buf : ByteArray = new ByteArray();
        buf.writeObject(msg);

        showBuf(buf);
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
