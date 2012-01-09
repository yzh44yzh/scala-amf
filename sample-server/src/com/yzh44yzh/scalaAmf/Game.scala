package com.yzh44yzh.scalaAmf

import java.util.Random

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */


object Game
{
	val colorLimit = scala.math.pow(2, 24).toInt
	val rand = new Random

	def getColor() : Int =
	{
		rand.nextInt(colorLimit)
	}
}