package com.yzh44yzh.scalaAmf
/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

import java.util.{Date, HashMap}

class Ref
{
    var dates = new TRef[Date]
    var strings = new TRef[String]
}

class TRef[T]
{
    private var nextId = 0;
    private var cache1 = new HashMap[Int, T]
    private var cache2 = new HashMap[T, Int]

    def store(value : T) : Int = {
        nextId += 1
        val id = nextId
        cache1.put(id, value)
        cache2.put(value, id)
        id
    }

    def get(id : Int) : T = {
        cache1.get(id)
    }

    def getKey(value : T) : Int = {
        cache2.get(value)
    }
}
