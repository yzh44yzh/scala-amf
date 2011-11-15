package com.yzh44yzh.scalaAmf

import java.util.{Date, HashMap}


/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */

class Ref
{
    private var nextId = 0;
    private var cache1 = new HashMap[Int, Any]
    private var cache2 = new HashMap[Any, Int]

    def store(value : Any) : Int = {
        nextId += 1
        val id = nextId
        cache1.put(id, value)
        cache2.put(value, id)
        id
    }

    def get(id : Int) : Any = {
        cache1.get(id)
    }

    def getKey(value : Any) : Int = {
        cache2.get(value)
    }
}
