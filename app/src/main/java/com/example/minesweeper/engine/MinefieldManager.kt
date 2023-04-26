package com.example.minesweeper.engine

import android.graphics.Point
import android.util.Log
import com.example.minesweeper.Constants

class MinefieldManager {
    var minefield: Array<Array<Field>>
    var generateInitData: GenerateInitData
    var remainingBombs: Int = 0
    var isBombHit: Boolean = false
    var numberOfFieldsToUncover: Int = 0
    var fieldAtId: MutableMap<Int, Field> = mutableMapOf()
    constructor(generateInitData: GenerateInitData)
    {
        this.generateInitData = generateInitData
        var generator = MinefieldGenerate(generateInitData)
        this.minefield = generator.getMinefield()
        remainingBombs = this.generateInitData.bombNumber
        initFieldAtId()
    }
    private fun initFieldAtId()
    {
        var id = 0
        for(x in 0 until minefield.size)
            for(y in 0 until minefield[0].size) {
                fieldAtId[id] = minefield[x][y]
                id++
            }
    }
    fun getFileAtId(id: Int): Field
    {
        var x = id / minefield[0].size
        var y = id % minefield[0].size
        return minefield[x][y]
    }
    fun idToPoint(id: Int):Point
    {
        var x = id / minefield[0].size
        var y = id % minefield[0].size
        return Point(x, y)
    }
    private fun isIndexInMatrix(x: Int, y: Int):Boolean
    {
        if(x < 0|| y <0 )
            return false
        if(x >= minefield.size)
            return false
        if(y >= minefield[0].size)
            return false
        return true
    }
    fun toggleFlag(x: Int, y: Int)
    {
        when(minefield[x][y].state)
        {
            FieldState.FLAGGED -> {
                minefield[x][y].state = FieldState.COVER
                remainingBombs++}
            FieldState.COVER -> {
                minefield[x][y].state = FieldState.FLAGGED
                remainingBombs--
            }
            FieldState.UNCOVER -> {}
        }
    }
    fun placeFlag(x: Int, y: Int)
    {
        when(minefield[x][y].state)
        {
            FieldState.FLAGGED -> {}

            FieldState.COVER -> {
                minefield[x][y].state = FieldState.FLAGGED
                remainingBombs--
            }
            FieldState.UNCOVER -> {}
        }
    }
    fun removeFlag(x: Int, y: Int)
    {
        when(minefield[x][y].state)
        {
            FieldState.FLAGGED -> {minefield[x][y].state = FieldState.COVER
                remainingBombs++}
            FieldState.COVER -> {}
            FieldState.UNCOVER -> {}
        }
    }

    fun generate(x: Int, y: Int)
    {
        numberOfFieldsToUncover = generateInitData.numberOfFields - generateInitData.bombNumber
        generateInitData.firstUncoverField = Point(x, y)

        var generator = MinefieldGenerate(generateInitData)
        generator.generate()
        minefield = generator.getMinefield()
        initFieldAtId()
        remainingBombs = generateInitData.bombNumber
        isBombHit = false

    }
    fun countFlagsAroundField(x: Int, y: Int): Int{
        var sumOfFlagsAround = 0
        for(i in (x - 1) .. (x + 1))
            for(j in (y - 1) .. (y + 1))
            {
                if(isIndexInMatrix(i, j))
                    if(minefield[i][j].state == FieldState.FLAGGED)
                        sumOfFlagsAround++
            }
        if(minefield[x][y].state == FieldState.FLAGGED)
            sumOfFlagsAround--
        return sumOfFlagsAround
    }

    fun uncoverAroundField(x: Int, y: Int)
    {
        for(i in (x - 1) .. (x + 1))
            for(j in (y - 1) .. (y + 1))
            {
                if(isIndexInMatrix(i, j))
                
                    uncoverField(i, j)
            }
    }
    fun uncoverAroundField(id: Int)
    {
        var indexes: Point = idToPoint(id)
        uncoverAroundField(indexes.x, indexes.y)
    }

    fun uncoverField(x: Int, y: Int)
    {
        if(minefield[x][y].state != FieldState.COVER)
            return

        minefield[x][y].state = FieldState.UNCOVER
        numberOfFieldsToUncover--;
        if(minefield[x][y].value == Constants.BOMB_VALUE)
            isBombHit = true
        if(minefield[x][y].value != 0)
            return

        for(i in (x - 1) .. (x + 1))
            for(j in (y - 1) .. (y + 1))
            {
                if(isIndexInMatrix(i, j)) {

                    uncoverField(i, j)
                }
            }
    }

    fun uncoverField(id: Int)
    {
        var indexes: Point = idToPoint(id)
        uncoverField(indexes.x, indexes.y)
    }
    fun isNumberOfFlagsAroundEqualsFieldValue(x: Int, y: Int): Boolean
    {
        var numberOfFlagsAroundField = countFlagsAroundField(x, y)
        if(numberOfFlagsAroundField == minefield[x][y].value)
            return true

        return false
    }
    fun isNumberOfFlagsAroundEqualsFieldValue(id: Int): Boolean
    {
        var indexes: Point = idToPoint(id)
        return isNumberOfFlagsAroundEqualsFieldValue(indexes.x, indexes.y)
    }
}