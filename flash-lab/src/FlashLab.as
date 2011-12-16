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

        var d : Date = new Date();
		var o1 : Object = {a:1, b:2};
		var o2 : Object = {a:3, b:4};
		var arr : Array = [1,2,3];
        var obj : Object = {arr:arr, d:d, o1:o1, dref:d, o1ref:o1, o2:o2, o2ref:o2, arrref:arr};

        var buf : ByteArray = new ByteArray();
        buf.writeObject(obj);

        showBuf(buf);
    }

	private function getHistory() : Object
	{
		var sender : Object = {};
		sender.id = "1";
		sender.name = "Bob";
		sender.gender = "male";
		sender._subscribePermissions = {};

		var msg1 : Object = {};
		msg1.senderID = "1";
		msg1.receiverID = "";
		msg1.content = "Hello";
		msg1.date = new Date();

		var history1 : Object = {};
		history1.message = msg1;
		history1.sender = sender;

		var msg2 : Object = {};
		msg2.senderID = "1";
		msg2.receiverID = "";
		msg2.content = "Hi";
		msg2.date = new Date();

		var history2 : Object = {};
		history2.message = msg2;
		history2.sender = sender;

		var res : Object = {};
		res.history = [history1, history2];
		res.roomID = "room1";

		return res;
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
