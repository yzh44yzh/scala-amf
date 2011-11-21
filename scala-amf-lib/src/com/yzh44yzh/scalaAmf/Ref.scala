package com.yzh44yzh.scalaAmf

import java.util.{ArrayList, Date, HashMap}

/**
 * @author Yura Zhloba <yzh44yzh@gmail.com>
 */


class Ref
{
    val dates = new TRef[Date]
    val strings = new TRef[String]
    val objects = new TRef[Any]

    var firstObj = true
}

class TRef[T]
{
    private var nextId = 0;
    private val cache1 = new HashMap[Int, IdentityWrapper]
    private val cache2 = new HashMap[IdentityWrapper, Int]

    def store(value : T) : Int = {
        val id = nextId
        nextId += 1

        val wrapper = new IdentityWrapper(value)
        cache1.put(id, wrapper)
        cache2.put(wrapper, id)
        id
    }

    def get(id : Int) : T = cache1.get(id).asInstanceOf[IdentityWrapper].obj.asInstanceOf[T]

    def hasValue(value : T) : Boolean = cache2.containsKey(new IdentityWrapper(value))

    def getKey(value : T) : Int = cache2.get(new IdentityWrapper(value))
}

class IdentityWrapper(val obj : Any)
{
    override def hashCode: Int = System.identityHashCode(obj)

    override def equals(other : Any): Boolean =
    {
        if(obj == null) return false
        if(other == null) return false

        if(other.isInstanceOf[IdentityWrapper])
        {
            if(other.asInstanceOf[IdentityWrapper].obj == null) return false
            return other.asInstanceOf[IdentityWrapper].hashCode == hashCode
        }
        false
    }
}
