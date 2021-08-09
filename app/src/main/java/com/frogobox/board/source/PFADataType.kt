package com.frogobox.board.source

class PFADataType {

    var iD = 0
    var dOMAIN: String? = null
    var uSERNAME: String? = null

    var lENGTH = 0

    constructor() {}

    constructor(ID: Int, DOMAIN: String?, USERNAME: String?, LENGTH: Int) {
        iD = ID
        dOMAIN = DOMAIN
        uSERNAME = USERNAME
        lENGTH = LENGTH
    }

}