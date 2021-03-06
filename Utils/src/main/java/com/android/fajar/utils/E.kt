package com.android.fajar.utils

open class E {

    open fun checkAll(var0: Array<EditTextValidator.EditTextSet>): Boolean {
        Helper.checkPermission()
        return checkAll(var0, true)
    }

    private fun checkAll(var0: Array<EditTextValidator.EditTextSet>, var1: Boolean): Boolean {
        Helper.checkPermission()
        var var2 = true
        val var3 = var0
        val var4 = var0.size
        for (var5 in 0 until var4) {
            val var6 = var3[var5]
            if (!var6.check()) {
                var2 = false
                if (var1) {
                    return var2
                }
            }
        }
        return var2
    }

    open fun checkAll(var0: List<EditTextValidator.EditTextSet?>): Boolean {
        Helper.checkPermission()
        return checkAll(var0, true)
    }

    private fun checkAll(var0: List<EditTextValidator.EditTextSet?>, var1: Boolean): Boolean {
        Helper.checkPermission()
        var var2 = true
        val var3: Iterator<*> = var0.iterator()
        while (var3.hasNext()) {
            val var4 = var3.next() as EditTextValidator.EditTextSet
            if (!var4.check()) {
                var2 = false
                if (var1) {
                    return var2
                }
            }
        }
        return var2
    }
}