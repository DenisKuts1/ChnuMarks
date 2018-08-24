package com.chnumarks.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * Created by denak on 27.02.2018.
 */
class SelectGroupDialogFragment : DialogFragment() {
    lateinit var title: String
    lateinit var list: List<CharSequence>
    lateinit var checked: BooleanArray
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        checked = kotlin.BooleanArray(list.size) { _ -> true }
        builder.setTitle(title)
                .setMultiChoiceItems(list.toTypedArray(), checked) { _, i, b -> checked[i] = b }
                .setPositiveButton("Confirm") { _, _ ->
                    run {

                    }
                }
        return builder.create()
    }
}