package seamcarving

import java.awt.Color
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import kotlin.math.pow


fun Int.squared(): Int {
    return this * this
}

fun Triple<Int, Int, Int>.square(): Triple<Int, Int, Int> {
    return Triple(
        this.first.squared(),
        this.second.squared(),
        this.third.squared()
    )
}

fun Triple<Int, Int, Int>.sum(): Int {
    return this.first + this.second + this.third
}

fun Triple<Int, Int, Int>.sumOfSquares(): Int {
    return this.square().sum()
}

fun calculateMaxEnergy(inputImage: BufferedImage): Double {
    var maxEnergyFound = 0.0
    for (y in 0 until inputImage.height) {
        for (x in 0 until inputImage.width) {
            val energy = dualGradientEnergy(x, y, inputImage)
            if (energy > maxEnergyFound) {
                maxEnergyFound = energy
            }
        }
    }
    return maxEnergyFound
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    if (this.isEmpty() || this[0].isEmpty()) return emptyList()
    return this[0].indices.map { col -> this.map { row -> row[col] } }
}

fun Array<DoubleArray>.transpose(): Array<DoubleArray> {
    if (this.isEmpty() || this[0].isEmpty()) return emptyArray()
    val transposeArray = Array(this[0].size) { DoubleArray(this.size) }
    for (i in this[0].indices) {
        for (j in this.indices) {
            transposeArray[i][j] = this[j][i]
        }
    }
    return transposeArray
}


fun calcSeamEnergy(x: Int, y: Int, energyMatrix: Array<DoubleArray>): Double {

    if (y == 0) {
        return energyMatrix[y][x]
    }

    val seamEnergy = energyMatrix[y][x] + minOf(
        if (y > 0 && x > 0) energyMatrix[y - 1][x - 1] else Double.MAX_VALUE,
        if (y > 0) energyMatrix[y - 1][x] else Double.MAX_VALUE,
        if (y > 0 && x < energyMatrix[y - 1].size - 1) energyMatrix[y - 1][x + 1] else Double.MAX_VALUE
    )


    return seamEnergy
}

fun transposeBufferedImage(image: BufferedImage): BufferedImage {
    val transposedImage = BufferedImage(image.height, image.width, image.type)
    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val rgb = image.getRGB(x, y)
            transposedImage.setRGB(y, x, rgb)
        }
    }
    return transposedImage
}


fun main(args: Array<String>) {
    val isInput = args[0] == "-in"
    val inputFilepath = if (isInput) args[1] else args[3]
    val outputFilepath = if (isInput) args[3] else args[1]

    val seamCarveTimes =
        (if (args[4] == "-width") args[5].toIntOrNull() else if (args[6] == "-width") args[7].toIntOrNull() else null)
            ?: 0
    val seamCarveHorizontalTimes =
        (if (args[4] == "-height") args[5].toIntOrNull() else if (args[6] == "-height") args[7].toIntOrNull() else null)
            ?: 0

    val inputFile = File(inputFilepath)
    val outputFile = File(outputFilepath)
    val inputImage = ImageIO.read(inputFile)

    val calculateEnergyMatrix: (BufferedImage) -> Array<DoubleArray> = { inputImage ->
        Array(inputImage.height) { y ->
            DoubleArray(inputImage.width) { x ->
                dualGradientEnergy(x, y, inputImage)
            }
        }
    }

    val initSeamEnergyMatrixFn: (Array<DoubleArray>) -> Array<DoubleArray> = { energyMatrix ->
        Array(energyMatrix.size) { y ->
            DoubleArray(energyMatrix[y].size) { x ->
                // Initialize with the same values as the energyMatrix.
                energyMatrix[y][x]
            }
        }
    }

    val initSeamMatrixFn: (BufferedImage) -> Array<IntArray> = { inputImage ->
        Array(inputImage.height) { y ->
            IntArray(inputImage.width) { x ->
                0
            }
        }
    }

    val calculateSeamEnergyMatrixFn: (Array<DoubleArray>) -> Array<DoubleArray> = { seamEnergyMatrix ->
        Array(seamEnergyMatrix.size) { y ->
            DoubleArray(seamEnergyMatrix[0].size) { x ->
                // Initialize with the same values as the energyMatrix.
                calcSeamEnergy(x, y, seamEnergyMatrix)
            }
        }
    }


    // temporary switch to AI version
    val drawSeamMatrixFn: (Array<IntArray>, Array<DoubleArray>) -> Array<IntArray> =
        { drawSeamMatrix, seamEnergyMatrix ->
            // Get the initial seam starting point from the last row with the minimum energy
            var lastCoord = seamEnergyMatrix[seamEnergyMatrix.size - 1]
                .indices.minByOrNull { seamEnergyMatrix[seamEnergyMatrix.size - 1][it] }
                ?: throw IllegalArgumentException("Seam energy matrix row is empty!")

            // Traverse upwards from the bottom of the matrix
            for (y in seamEnergyMatrix.size - 1 downTo 0) {
                // Mark the current seam in the drawSeamMatrix
                drawSeamMatrix[y][lastCoord] = 1

                // Don't trace beyond the first row
                if (y == 0) break

                // Determine the range of valid neighboring columns for the previous row
                val start = (lastCoord - 1).coerceAtLeast(0)
                val end = (lastCoord + 1).coerceAtMost(seamEnergyMatrix[y - 1].lastIndex)

                // Find the column with the minimum energy in the previous row within range
                lastCoord = (start..end).minByOrNull { seamEnergyMatrix[y - 1][it] } ?: lastCoord
            }

            drawSeamMatrix
        }


    val bufferedImage = BufferedImage(
        inputImage.colorModel,
        inputImage.copyData(null),
        inputImage.isAlphaPremultiplied,
        null
    )

    val redrawWithoutSeam: (BufferedImage, Array<IntArray>) -> BufferedImage = { img, seam ->
        BufferedImage(
            img.width - 1,
            img.height,
            img.type
        ).apply {
            (0 until height).forEach { y ->
                val indexOfSeam = seam[y].indexOf(1)
                (0 until width).forEach { x ->
                    if (x < indexOfSeam) {
                        setRGB(x, y, img.getRGB(x, y))
                    } else {
                        setRGB(x, y, img.getRGB(x + 1, y))
                    }
                }

            }
        }
    }

    var outputImage = bufferedImage
    for (times in 1..seamCarveTimes) {
        var energyMatrix = calculateEnergyMatrix(outputImage)
        var seamEnergyMatrix = initSeamEnergyMatrixFn(energyMatrix)
        seamEnergyMatrix.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                // Modify the seamEnergyMatrix based on calculations while leaving energyMatrix untouched
                seamEnergyMatrix[y][x] = calcSeamEnergy(x, y, seamEnergyMatrix)
            }
        }
        var drawSeamMatrixInitial = initSeamMatrixFn(outputImage)
        var drawSeamMatrix = drawSeamMatrixFn(drawSeamMatrixInitial, seamEnergyMatrix)
        outputImage = redrawWithoutSeam(outputImage, drawSeamMatrix)
    }

    if (seamCarveHorizontalTimes > 0) {
        outputImage = transposeBufferedImage(outputImage)
        for (times in 1..seamCarveHorizontalTimes) {
            var energyMatrix = calculateEnergyMatrix(outputImage)
            var seamEnergyMatrix = initSeamEnergyMatrixFn(energyMatrix)
            seamEnergyMatrix.forEachIndexed { y, row ->
                row.forEachIndexed { x, _ ->
                    // Modify the seamEnergyMatrix based on calculations while leaving energyMatrix untouched
                    seamEnergyMatrix[y][x] = calcSeamEnergy(x, y, seamEnergyMatrix)
                }
            }
            var drawSeamMatrixInitial = initSeamMatrixFn(outputImage)
            var drawSeamMatrix = drawSeamMatrixFn(drawSeamMatrixInitial, seamEnergyMatrix)
            outputImage = redrawWithoutSeam(outputImage, drawSeamMatrix)
        }
        outputImage = transposeBufferedImage(outputImage)
    }

    saveImage(outputImage, outputFile, inputFile.extension)
}

fun intensity(energy: Double, maxEnergyValue: Double): Int {
    return (255.0 * energy / maxEnergyValue).toInt()
}

fun getDualGradientPixelsCord(cord: Int, imgSize: Int): Pair<Int, Int> {
    return when (true) {
        (cord == 0) -> Pair(0, 2)
        (cord == imgSize - 1) -> Pair(imgSize - 3, imgSize - 1)
        else -> Pair(cord - 1, cord + 1)
    }
}

fun dualGradientEnergy(x: Int, y: Int, inputImage: BufferedImage): Double {
    val dgX = getDualGradientPixelsCord(x, inputImage.width)
    val dgY = getDualGradientPixelsCord(y, inputImage.height)
    val rgbDgXFirst = Color(inputImage.getRGB(dgX.first, y))
    val rgbDgXSecond = Color(inputImage.getRGB(dgX.second, y))
    val diffsX = Triple(
        rgbDgXFirst.red - rgbDgXSecond.red,
        rgbDgXFirst.green - rgbDgXSecond.green,
        rgbDgXFirst.blue - rgbDgXSecond.blue,
    )
    val deltaX = diffsX.sumOfSquares()

    val rgbDgYFirst = Color(inputImage.getRGB(x, dgY.first))
    val rgbDgYSecond = Color(inputImage.getRGB(x, dgY.second))
    val diffsY = Triple(
        rgbDgYFirst.red - rgbDgYSecond.red,
        rgbDgYFirst.green - rgbDgYSecond.green,
        rgbDgYFirst.blue - rgbDgYSecond.blue,
    )
    val deltaY = diffsY.sumOfSquares()

    return (deltaX + deltaY).toDouble().pow(0.5)
}

fun saveImage(image: BufferedImage, imageFile: File, ext: String) {
    ImageIO.write(image, ext, imageFile)
}