package com.example.swipetodeletelib

import android.view.View


/**
 * Created by AlexZandR on 4/24/17.
 */

interface ISwipeToDeleteHolder<K> {

    var listener: UndoClickListener<K>

    var isPendingDelete: Boolean
    /**
     *  get() =
     *  if (isPendingDelete!!) undoContainer
     *  else itemContainer
     *
     */
    val topContainer: View

    var key: K

//    var deletedName = view.user_name_deleted
//    var name = view.user_name
//    var phone = view.user_phone_number
//    var progressIndicator = view.progress_indicator
//    var itemContainer = view.contact_container
//    var undoContainer = view.undo_container
//    var undoData = view.undo_data

//    init {
//        view.button_undo.setOnClickListener { listener.onUndoClick(adapterPosition) }
//    }

//    val topContainer: View
//        get() =
//        if (isPendingDelete) undoContainer
//        else itemContainer


}