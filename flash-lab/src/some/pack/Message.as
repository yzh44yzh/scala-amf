/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package some.pack
{
import flash.net.registerClassAlias;

public class Message
{
    registerClassAlias("some.pack.Message", Message);
    
    public var sender : User;
    public var content : String;
}
}
