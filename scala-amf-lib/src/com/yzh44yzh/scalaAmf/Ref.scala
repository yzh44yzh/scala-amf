package com.yzh44yzh.scalaAmf

import java.util.{ArrayList, Date, HashMap}

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */


class Ref
{
    val dates = new TRef[Date]
    val strings = new TRef[String]
    val arrays = new TRef[ArrayList[Any]]
    val objects = new TRef[AmfClass]
}

class TRef[T]
{
    private var nextId = 0;
    private val cache1 = new HashMap[Int, T]
    private val cache2 = new HashMap[T, Int]

    def store(value : T) : Int = {
        val id = nextId
        nextId += 1
        cache1.put(id, value)
        cache2.put(value, id)
        id
    }

    def get(id : Int) : T = cache1.get(id)

    def hasValue(value : T) : Boolean = cache2.containsKey(value)

    def getKey(value : T) : Int = cache2.get(value)

    def empty() : Boolean = cache1.size == 0
}
