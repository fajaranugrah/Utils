package com.android.fajar.utils

import android.app.AlertDialog
import android.widget.EditText
import android.util.Patterns
import kotlin.jvm.JvmOverloads
import android.content.DialogInterface
import androidx.annotation.StringRes
import java.lang.Exception
import java.lang.NullPointerException

open class EditTextValidator {
    open fun checkAll(var0: Array<EditTextSet>): Boolean {
        Helper.checkPermission()
        return checkAll(var0, true)
    }

    open fun checkAll(var0: Array<EditTextSet>, var1: Boolean): Boolean {
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

    open fun checkAll(var0: List<EditTextSet?>): Boolean {
        Helper.checkPermission()
        return checkAll(var0, true)
    }

    open fun checkAll(var0: List<EditTextSet?>, var1: Boolean): Boolean {
        Helper.checkPermission()
        var var2 = true
        val var3: Iterator<*> = var0.iterator()
        while (var3.hasNext()) {
            val var4 = var3.next() as EditTextSet
            if (!var4.check()) {
                var2 = false
                if (var1) {
                    return var2
                }
            }
        }
        return var2
    }

    @Deprecated("")
    fun check(var0: EditText?, var1: Int): Boolean {
        Helper.checkPermission()
        if ((var1 and 1) == 1) {
            if (var0 == null) {
                return false
            }
            try {
                if ((var0.text.toString() == null) || var0.text.toString()
                        .isEmpty() || (var0.text.toString().length <= 0)
                ) {
                    return false
                }
            } catch (var7: NullPointerException) {
                return false
            }
        }
        if ((var1 and 16) == 16) {
            if (var0 == null) {
                return false
            }
            try {
                if (var0.text.toString() == null || var0.text.toString()
                        .trim { it <= ' ' }.length <= 0
                ) {
                    return false
                }
            } catch (var6: NullPointerException) {
                return false
            }
        }
        if ((var1 and 256) == 256) {
            if (var0 == null) {
                return false
            }
            try {
                if (!Patterns.EMAIL_ADDRESS.matcher(var0.text).matches()) {
                    return false
                }
            } catch (var5: NullPointerException) {
                return false
            }
        }
        if ((var1 and 4096) == 4096) {
            if (var0 == null) {
                return false
            }
            try {
                if (!Patterns.PHONE.matcher(var0.text).matches()) {
                    return false
                }
            } catch (var4: NullPointerException) {
                return false
            }
        }
        if ((var1 and 65536) == 65536) {
            if (var0 == null) {
                return false
            }
            try {
                val var2 = var0.text.toString()
                if (!var2.matches("^[0-9]*$".toRegex())) {
                    return false
                }
            } catch (var3: NullPointerException) {
                return false
            }
        }
        return true
    }

    class RegexEditTextSet : EditTextSet {
        var pattern: String

        constructor(
            var1: EditText,
            var2: String,
            @StringRes var3: Int,
            var4: Boolean,
            var5: ActionType?
        ) : super(var1, 1, var3, var4, var5) {
            pattern = var2
        }

        constructor(var1: EditText, var2: String, @StringRes var3: Int, var4: Boolean) : super(
            var1,
            1,
            var3,
            var4
        ) {
            pattern = var2
        }

        constructor(var1: EditText, var2: String, @StringRes var3: Int) : super(var1, 1, var3) {
            pattern = var2
        }

        constructor(
            var1: EditText?,
            var2: String,
            var3: String?,
            var4: Boolean,
            var5: ActionType?
        ) : super(var1, 1, var3, var4, var5) {
            pattern = var2
        }

        constructor(var1: EditText?, var2: String, var3: String?, var4: Boolean) : super(
            var1,
            1,
            var3,
            var4
        ) {
            pattern = var2
        }

        constructor(var1: EditText?, var2: String, var3: String?) : super(var1, 1, var3) {
            pattern = var2
        }

        override fun check(): Boolean {
            Helper.checkPermission()
            if (editText != null && editText!!.text != null) {
                val var1: String = editText!!.text.toString()
                if (var1 != null && var1.matches(pattern.toRegex())) {
                    return true
                }
            }
            doAction()
            return false
        }
    }

    class MatchingEditTextSet : EditTextSet {
        var editText1: EditText?
        var editText2: EditText?
        var a: Boolean

        constructor(
            var1: EditText,
            var2: EditText?,
            @StringRes var3: Int,
            var4: Boolean,
            var5: Boolean,
            var6: ActionType?
        ) : super(var1, 1, var3, var5, var6) {
            editText1 = var1
            editText2 = var2
            a = var4
        }

        constructor(
            var1: EditText,
            var2: EditText?,
            @StringRes var3: Int,
            var4: Boolean,
            var5: Boolean
        ) : super(var1, 1, var3, var5) {
            editText1 = var1
            editText2 = var2
            a = var4
        }

        constructor(var1: EditText, var2: EditText?, @StringRes var3: Int, var4: Boolean) : super(
            var1,
            1,
            var3
        ) {
            editText1 = var1
            editText2 = var2
            a = var4
        }

        constructor(var1: EditText, var2: EditText?, @StringRes var3: Int) : super(var1, 1, var3) {
            editText1 = var1
            editText2 = var2
            a = true
        }

        constructor(
            var1: EditText?,
            var2: EditText?,
            var3: String?,
            var4: Boolean,
            var5: Boolean,
            var6: ActionType?
        ) : super(var1, 1, var3, var5, var6) {
            editText1 = var1
            editText2 = var2
            a = var4
        }

        constructor(
            var1: EditText?,
            var2: EditText?,
            var3: String?,
            var4: Boolean,
            var5: Boolean
        ) : super(var1, 1, var3, var5) {
            editText1 = var1
            editText2 = var2
            a = var4
        }

        constructor(var1: EditText?, var2: EditText?, var3: String?, var4: Boolean) : super(
            var1,
            1,
            var3
        ) {
            editText1 = var1
            editText2 = var2
            a = var4
        }

        constructor(var1: EditText?, var2: EditText?, var3: String?) : super(var1, 1, var3) {
            editText1 = var1
            editText2 = var2
            a = true
        }

        override fun check(): Boolean {
            Helper.checkPermission()
            if ((editText1 != null) && (editText2 != null) && (editText1!!.text != null) && (editText2!!.text != null)) {
                var var1: String = editText1!!.text.toString()
                var var2: String = editText2!!.text.toString()
                if (var1 != null && var2 != null) {
                    if (!a) {
                        var1 = var1.toUpperCase()
                        var2 = var2.toUpperCase()
                    }
                    if ((var1 == var2)) {
                        return true
                    }
                }
            }
            doAction()
            return false
        }
    }

    class MinMaxLengthEditTextSet : EditTextSet {
        var minLength = 0L
        var maxLength = 9223372036854775807L

        constructor(
            var1: EditText,
            var2: Long,
            var4: Long,
            @StringRes var6: Int,
            var7: Boolean,
            var8: ActionType?
        ) : super(var1, 1, var6, var7, var8) {
            minLength = var2
            maxLength = var4
        }

        constructor(
            var1: EditText,
            var2: Long,
            var4: Long,
            @StringRes var6: Int,
            var7: Boolean
        ) : super(var1, 1, var6, var7) {
            minLength = var2
            maxLength = var4
        }

        constructor(var1: EditText, var2: Long, var4: Long, @StringRes var6: Int) : super(
            var1,
            1,
            var6
        ) {
            minLength = var2
            maxLength = var4
        }

        constructor(
            var1: EditText?,
            var2: Long,
            var4: Long,
            var6: String?,
            var7: Boolean,
            var8: ActionType?
        ) : super(var1, 1, var6, var7, var8) {
            minLength = var2
            maxLength = var4
        }

        constructor(var1: EditText?, var2: Long, var4: Long, var6: String?, var7: Boolean) : super(
            var1,
            1,
            var6,
            var7
        ) {
            minLength = var2
            maxLength = var4
        }

        constructor(var1: EditText?, var2: Long, var4: Long, var6: String?) : super(var1, 1, var6) {
            minLength = var2
            maxLength = var4
        }

        override fun check(): Boolean {
            Helper.checkPermission()
            if (editText != null && editText!!.text != null) {
                val var1: String = editText!!.text.toString()
                if ((var1 != null) && (var1.length.toLong() >= minLength) && (var1.length.toLong() <= maxLength)) {
                    return true
                }
            }
            doAction()
            return false
        }
    }

    open class EditTextSet {
        var editText: EditText?
        var rule: Int
        var withAction: Boolean
        var statusMessage: String?
        var actionType: ActionType?

        @JvmOverloads
        constructor(
            var1: EditText,
            var2: Int,
            @StringRes var3: Int,
            var4: Boolean = true,
            var5: ActionType? = DEFAULT_ACTION_TYPE
        ) {
            withAction = true
            actionType = ActionType.ACTION_TYPE_TOAST_AND_SHAKE_VIEW
            editText = var1
            rule = var2
            statusMessage = var1.context.getString(var3)
            withAction = var4
            actionType = var5
        }

        @JvmOverloads
        constructor(
            var1: EditText?,
            var2: Int,
            var3: String?,
            var4: Boolean = true,
            var5: ActionType? = DEFAULT_ACTION_TYPE
        ) {
            withAction = true
            actionType = ActionType.ACTION_TYPE_TOAST_AND_SHAKE_VIEW
            editText = var1
            rule = var2
            statusMessage = var3
            withAction = var4
            actionType = var5
        }

        open fun check(): Boolean {
            Helper.checkPermission()
            if ((rule and 1) == 1) {
                if (editText == null) {
                    doAction()
                    return false
                }
                try {
                    if ((editText!!.text.toString() == null) || editText!!.text.toString()
                            .isEmpty() || (editText!!.text.toString().length <= 0)
                    ) {
                        doAction()
                        return false
                    }
                } catch (var6: NullPointerException) {
                    doAction()
                    return false
                }
            }
            if ((rule and 16) == 16) {
                if (editText == null) {
                    doAction()
                    return false
                }
                try {
                    if (editText!!.text.toString() == null || editText!!.text.toString()
                            .trim { it <= ' ' }.length <= 0
                    ) {
                        doAction()
                        return false
                    }
                } catch (var5: NullPointerException) {
                    doAction()
                    return false
                }
            }
            if ((rule and 256) == 256) {
                if (editText == null) {
                    doAction()
                    return false
                }
                try {
                    if (!Patterns.EMAIL_ADDRESS.matcher(editText!!.text).matches()) {
                        doAction()
                        return false
                    }
                } catch (var4: NullPointerException) {
                    doAction()
                    return false
                }
            }
            if ((rule and 4096) == 4096) {
                if (editText == null) {
                    doAction()
                    return false
                }
                try {
                    if (!Patterns.PHONE.matcher(editText!!.text).matches()) {
                        doAction()
                        return false
                    }
                } catch (var3: NullPointerException) {
                    doAction()
                    return false
                }
            }
            if ((rule and 65536) == 65536) {
                if (editText == null) {
                    doAction()
                    return false
                }
                try {
                    val var1 = editText!!.text.toString()
                    if (!var1.matches("^[0-9]*$".toRegex())) {
                        doAction()
                        return false
                    }
                } catch (var2: NullPointerException) {
                    doAction()
                    return false
                }
            }
            return true
        }

        fun doAction() {
            if (withAction) {
                when (actionType) {
                    ActionType.ACTION_TYPE_TOAST_AND_SHAKE_VIEW -> {
                        try {
                            Helper.shakeView(editText)
                        } catch (var5: Exception) {
                            var5.printStackTrace()
                        }
                        try {
                            Helper.toast(editText!!.context, statusMessage)
                        } catch (var4: Exception) {
                            var4.printStackTrace()
                        }
                    }
                    ActionType.ACTION_TYPE_TOAST -> try {
                        Helper.toast(editText!!.context, statusMessage)
                    } catch (var4: Exception) {
                        var4.printStackTrace()
                    }
                    ActionType.ACTION_TYPE_SHOW_DIALOG_AND_SHAKE_VIEW -> {
                        try {
                            Helper.shakeView(editText)
                        } catch (var3: Exception) {
                            var3.printStackTrace()
                        }
                        try {
                            val builder = AlertDialog.Builder(
                                editText!!.context
                            )
                            builder.setMessage(statusMessage)
                                .setNegativeButton(
                                    "Cancel",
                                    DialogInterface.OnClickListener { dialog, id -> // User cancelled the dialog
                                        dialog.dismiss()
                                    })
                            // Create the AlertDialog object and return it
                            builder.create()
                        } catch (var2: Exception) {
                            var2.printStackTrace()
                        }
                    }
                    ActionType.ACTION_TYPE_SHOW_DIALOG -> try {
                        val builder = AlertDialog.Builder(
                            editText!!.context
                        )
                        builder.setMessage(statusMessage)
                            .setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, id: Int) {
                                    dialog.dismiss()
                                }
                            })
                        builder.create()
                    } catch (var2: Exception) {
                        var2.printStackTrace()
                    }
                }
            }
        }

        companion object {
            var DEFAULT_ACTION_TYPE: ActionType? = null
            val DEFAULT_WITH_ACTION = true
            fun create(vararg var0: EditTextSet?): Array<out EditTextSet?> {
                val var1: Array<EditTextSet?>
                if (var0.size > 0) {
                    var1 = arrayOfNulls(var0.size)
                    System.arraycopy(var0, 0, var1, 0, var0.size)
                    return var0
                } else {
                    var1 = arrayOfNulls(0)
                    return var1
                }
            }

            init {
                DEFAULT_ACTION_TYPE = ActionType.ACTION_TYPE_TOAST_AND_SHAKE_VIEW
            }
        }

        enum class ActionType() {
            ACTION_TYPE_TOAST, ACTION_TYPE_SHOW_DIALOG, ACTION_TYPE_TOAST_AND_SHAKE_VIEW, ACTION_TYPE_SHOW_DIALOG_AND_SHAKE_VIEW
        }
    }

    object Rule {
        val NOT_EMPTY = 1
        val TRIM_NOT_EMPTY = 16
        val EMAIL = 256
        val PHONE = 4096
        val NUMBER = 65536
    }
}