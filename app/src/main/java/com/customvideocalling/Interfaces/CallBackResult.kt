package com.customvideocalling.Interfaces

import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.SlotListResponse
import com.customvideocalling.model.TokenHistoryListResponse
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

    interface PlanListCallBack {
        fun onGetPlanListSuccess(response: PlanListResponse)
        fun onGetPlanListFailed(message: String)
    }

    interface TokenHistoryCallBack {
        fun onGetTokenHistorySuccess(response: TokenHistoryListResponse)
        fun onGetTokenHistoryFailed(message: String)
    }

    interface PaymentCallBack {
        fun onPaymentSuccess(response: CommonModel)
        fun onPaymentFailed(message: String)
    }

    interface AddDeviceTokenCallBack {
        fun onAddDeviceTokenSuccess(response: CommonModel)
        fun onAddDeviceTokenFailed(message: String)
    }

    interface AddQuestionnaireCallBack {
        fun onAddQuestionnaire(info: String, grade: String, need: String, help: String, superhero: String)
    }

    interface SelectedPlanCallBack {
        fun selectedPlan(pos: Int, planId: String, amount: String)
    }
}