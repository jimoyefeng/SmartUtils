package com.example.user.testkotlin.RetrfitDemo

import java.io.Serializable

/**
 * Created by admin on 2018/3/21.
 */

class Appconfigbean : Serializable {


    var config: Configbean? = null

    inner class Configbean {

        var url: String? = null
        var version: String? = null
        var code: String? = null
        var status: String? = null
        var message: String? = null

    }
}
