/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

package com.yzh44yzh.scalaAmf
{
public class Circle
{
	public var x : int;
	public var y : int;
	public var radius : int;

	static public function create(x : int, y : int, radius : int) : Circle
	{
		var circle : Circle = new Circle();
		circle.x = x;
		circle.y = y;
		circle.radius = radius;
		return circle;
	}
}
}
