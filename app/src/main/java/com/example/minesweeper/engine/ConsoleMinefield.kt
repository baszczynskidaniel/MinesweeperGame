package com.example.minesweeper.engine

fun printMatrixInt(minefield: Array<Array<Field>>)
{
    for(row in 0 until minefield.size)
    {
        for(col in 0 until minefield[0].size)
        {
            print(minefield[row][col].value)
        }
        println()
    }
}
fun printUncoverFieldsInMinefield(minefield: Array<Array<Field>>)
{
    for(row in 0 until minefield.size)
    {
        for(col in 0 until minefield[0].size)
        {
            if(minefield[row][col].state == FieldState.UNCOVER)
                print(minefield[row][col].value)
            else
                print("?")
        }
        println()
    }
}
fun printCoverFieldsInMinefield(minefield: Array<Array<Field>>) {
    for (row in 0 until minefield.size) {
        for (col in 0 until minefield[0].size) {
            if (minefield[row][col].state == FieldState.UNCOVER)
                print("?")
            else
                print(minefield[row][col].value)

        }
        println()
    }
}
fun printIds(minefield: Array<Array<Field>>)
{
    for (row in 0 until minefield.size) {
        for (col in 0 until minefield[0].size)
            {
                print(minefield[row][col].id)
                print(" ")
            }
        println()
        }
}

fun main()
{
    /*
    var minefield = MinefieldGenerate(GenerateInitData(bombNumber = 80, rowNumber = 40, colNumber = 10))
    var minefieldManager = MinefieldManager(minefield.getMinefield())
    while(true)
    {
        printIds(minefieldManager.minefield)
        //printCoverFieldsInMinefield(minefieldManager.minefield)
        //println()
        var x = readLine()?.toIntOrNull()
        var y = readLine()?.toIntOrNull()


        minefieldManager.uncoverField(x!!, y!!)




    }

     */

}