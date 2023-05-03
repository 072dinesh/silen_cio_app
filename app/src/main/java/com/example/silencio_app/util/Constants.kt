package com.example.silencio_app.util




object Constants {

    interface LOGIN{
       companion object{

           const val BOTTOM_NAV_BROADCAST_NAME ="BOTTOM_NAV_BROADCAST_NAME"

       }
    }
        const val IS_LOGIN = "IS_LOGIN"

        const val IS_INTRO_COMPLETE = "IS_INTRO_COMPLETE"




    interface BRODCAST_RECIVER{
        companion object {

            const val SIDE_MENU = "SIDE_MENU"
            const val BUTTOM_NEV ="BUTTTOM_NEV"

        }
    }

    interface DO_NOT_SHOW_AGIN{

        companion object{

            const val HOW_TO_AGIN="HOW_TO_MEASURE"
            const val ASK_SILECIO="data"
        }
    }

    interface RESPONSE_CODE {

        companion object {
            const val RESPONSE_200 = 200
            const val RESPONSE_500 = 500
            const val RESPONSE_400 = 400
            const val RESPONSE_401 = 401

        }
    }

}