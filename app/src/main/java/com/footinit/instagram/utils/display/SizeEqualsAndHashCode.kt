package com.footinit.instagram.utils.display

internal object SizeEqualsAndHashCode {

    fun equals(a: ISize, o: Any?): Boolean {
        if (a === o) return true
        if (o == null) return false

        if (o is ISize) {
            val b = o as ISize?
            return if (a.width() != b!!.width()) false else a.height() == b.height()
        } else {
            return false
        }
    }

    fun hashCode(size: ISize): Int {
        var result = size.width()
        result = 31 * result + size.height()
        return result
    }


}