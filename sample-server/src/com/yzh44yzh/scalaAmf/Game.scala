package com.yzh44yzh.scalaAmf

import java.util.{Date, Random}


/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */


object Game
{
	val colorLimit = scala.math.pow(2, 24).toInt
	val rand = new Random

	def getColor() : Int =
	{
		rand.setSeed(new Date().getTime)
		rand.nextInt(colorLimit)
	}
}