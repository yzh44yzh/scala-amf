/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package
{
import flash.display.Sprite;
import flash.utils.ByteArray;

import some.pack.Message;
import some.pack.RUser;
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

        /*
        var user : User = new User();
        user.id = 2;
        user.name = "John";
        user.age = 44;
        user.admin = true;

        var msg : Message = new Message();
        msg.sender = user;
        msg.content = "How are you? :)";
        */

        var user1 : Object = {id:1, name:"Bob"};
        var user2 : Object = {id:2, name:"Bill", age:25};
        var user3 : RUser = new RUser(); user3.id = 3; user3.name = "Helen"; user3.gender = 1;
        var arr = [true, user1, user2, user3, null, "Hello", 128];

        var buf : ByteArray = new ByteArray();
        buf.writeObject(arr);

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
                var b : Number = buf.readByte();
                res += ", 0x" + b.toString(16);
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
