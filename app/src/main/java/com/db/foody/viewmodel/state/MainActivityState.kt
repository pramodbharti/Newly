package com.db.foody.viewmodel.state

data class MainActivityState private constructor(val status:Status,val error:Throwable?=null){

    companion object{
        fun loading():MainActivityState{
            return MainActivityState(Status.LOADING)
        }


        fun success():MainActivityState{
            return MainActivityState(Status.SUCCESS)
        }


        fun complete():MainActivityState{
            return MainActivityState(Status.COMPLETE)
        }


        fun shortQuery():MainActivityState{
            return MainActivityState(Status.SHORT_QUERY)
        }


        fun error(e:Throwable):MainActivityState{
            return MainActivityState(Status.ERROR,e)
        }
    }

}