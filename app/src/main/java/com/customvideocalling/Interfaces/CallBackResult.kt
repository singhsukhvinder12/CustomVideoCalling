package com.customvideocalling.Interfaces

import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.SlotListResponse
import com.example.artha.model.CommonModel

class CallBackResult {

    interface ClassSubjectListCallBack {
        fun onGetClassSubjectListSuccess(response: ClassSubjectListResponse)
        fun onGetClassSubjectListFailed(message: String)
    }

    interface SlotListCallBack {
        fun onGetSlotListSuccess(response: SlotListResponse)
        fun onGetSlotListFailed(message: String)
    }

    interface AddBookingCallBack {
        fun onAddBookingSuccess(response: CommonModel)
        fun onAddBookingFailed(message: String)
    }
}