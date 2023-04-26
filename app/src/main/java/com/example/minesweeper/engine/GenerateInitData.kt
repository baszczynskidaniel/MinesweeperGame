package com.example.minesweeper.engine

import android.graphics.Point

data class GenerateInitData(
    var rowNumber: Int = 10,
    var colNumber: Int = 10,
    var percentOfBomb: Int = 20,
    var firstUncoverField: Point? = null,
    val MAX_ROW_NUMBER: Int = 100,
    val MAX_COL_NUMBER: Int = 100,

    var numberOfFields: Int = rowNumber * colNumber,
    var bombNumber: Int = (( rowNumber * colNumber) * (percentOfBomb / 100f)).toInt()
)
{
    init {
        require(rowNumber > 0)
        {"rowNumber must be bigger that 0}"}
        require(colNumber > 0)
        {"colNumber must be bigger that 0}"}
        require(colNumber <= MAX_ROW_NUMBER)
        {"colNumber must be bigger that 0}"}
        require(rowNumber <= MAX_COL_NUMBER)
        {"colNumber must be bigger that 0}"}
        require(percentOfBomb < 100)
        {"number of bomb must be smaller that number of fields"}
        require(firstUncoverField == null || firstUncoverField!!.x > 0 && firstUncoverField!!.x < rowNumber)
        {"firstUncoverField out of row range"}
        require(firstUncoverField == null || firstUncoverField!!.y > 0 && firstUncoverField!!.y < rowNumber)
        {"firstUncoverField out of column range"}

    }

}
