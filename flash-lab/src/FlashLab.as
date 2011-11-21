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

        /*
        var location : Object = {country:"Belarus", city:"Minks"};
        var obj : Object = {name:"Yura", location:location};

        var arr1 : Array = [1, 2, 3];
        var arr2 : Array = [4, 5, 6];
        var arr : Array = [arr1, arr2, arr1, arr1, arr2];
        */

        /*
        var obj1 : Object = {aaa:1, bbb:2};
        var obj2 : Object = {aaa:3, bbb:4};
        var obj : Object = {aa1:obj1, aa2:obj1, bb1:obj2, bb2:obj2};
        */

        /*
        var obj1 : Object = {aaa:1, bbb:2};
        var arr : Array = [1, 2, 3];
        var obj : Object = {aa1:true, aa2:"Hello", aa3:obj1, aa4:"Hello", aa5:123, aa6:arr, aa7:obj1, aa8:arr};
        */

        // var arr : Array = [ {room:"R1"}, {}, {room:"R2"}, {}, {}, [1,2,3], [], [] ];

        var d1 : Date = null;
        var d2 : Date = null;
        var arr : Array = [d1, d2];

        var buf : ByteArray = new ByteArray();
        buf.writeObject(arr);

        showBuf(buf);
    }

    private function showNum(num : Number) : void
    {
        trace(num.toString(16));
        trace(new Number(num).toString(2));
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
