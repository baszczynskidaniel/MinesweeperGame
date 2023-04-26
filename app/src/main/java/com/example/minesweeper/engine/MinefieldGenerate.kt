package com.example.minesweeper.engine

import com.example.minesweeper.Constants
import kotlin.random.Random

class MinefieldGenerate {
    private var initData: GenerateInitData
    private var minefield: Array<Array<Field>> = emptyArray()

    constructor (initData: GenerateInitData)
    {
        this.initData = initData
        minefield = Array(initData.rowNumber) {Array<Field>(initData.colNumber){ Field() } }
        initIds()
        initCoordinates()
    }
    fun getMinefield(): Array<Array<Field>>
    { return minefield }

    fun initCoordinates()
    {
        for(row in 0 until minefield.size)
            for(col in 0 until minefield[0].size) {
                minefield[row][col].row = row
                minefield[row][col].col = col
            }
    }

    private fun initIds()
    {
        var uniqueId = 0
        for(x in 0 until minefield.size)
            for(y in 0 until minefield[0].size) {
                minefield[x][y].id = uniqueId++
            }
    }

    private fun placeBombsInMinefield(availableIndexesForBomb: MutableList<Int>)
    {
        for(i in 0 until initData.bombNumber) {
            val randomIndex = Random.nextInt(availableIndexesForBomb.size)
            val randomIndexValue = availableIndexesForBomb[randomIndex]

            minefield[randomIndexValue / initData.colNumber][randomIndexValue % initData.colNumber].value = 9
            availableIndexesForBomb.remove(randomIndexValue)
        }
    }
    private fun isBombAtIndex(x: Int, y: Int, ): Boolean
    {
        if(isIndexInMatrix(x, y, minefield))
            if(minefield[x][y].value == Constants.BOMB_VALUE)
                return true
        return false
    }
    private fun getNumberOfBombsAroundIndex(x: Int, y: Int): Int
    {
        var sumOfBombs = 0
        for(i in (x - 1) .. (x + 1))
            for(j in (y - 1) .. (y + 1))
                if(isBombAtIndex(i, j))
                    sumOfBombs++

        if(isBombAtIndex(x, y))
            sumOfBombs--

        return sumOfBombs
    }

    private fun updateNumOfBombsAround()
    {
        for(row in 0 until minefield.size)
        {
            for(col in 0 until minefield[0].size)
            {
                if(minefield[row][col].value == Constants.BOMB_VALUE)
                    continue
                minefield[row][col].value = getNumberOfBombsAroundIndex(row, col)
            }
        }
    }
    private fun removeFirstUncoverFieldInOrderListOfAllIndexes(indexes: MutableList<Int>)
    {
        if(initData.firstUncoverField == null)
            return
        indexes.remove(initData.firstUncoverField!!.x * initData.colNumber + initData.firstUncoverField!!.y)
    }
    fun generate()
    {
        minefield = Array(initData.rowNumber) {Array<Field>(initData.colNumber){ Field() } }
        initIds()
        initCoordinates()

        var numberOfFields = initData.rowNumber * initData.colNumber
        var indexes = MutableList<Int>(numberOfFields){i -> i}

        removeFirstUncoverFieldInOrderListOfAllIndexes(indexes)
        placeBombsInMinefield(indexes)
        updateNumOfBombsAround()
    }

    fun generate(initData: GenerateInitData)
    {
        this.initData = initData
        generate()
    }

    private fun isIndexInMatrix(x: Int, y: Int, matrix: Array<Array<Field>>):Boolean
    {
        if(x < 0 || y < 0)
            return false
        if(x >= matrix.size)
            return false
        if(y >= matrix[0].size)
            return false
        return true
    }

}