package com.example.part2poe.ui.add_category

data class Category(val categoryname: String, val description: String )
{
    fun main() {
        // Create instances of Category
        val categoryname1 = Category("Alice", "C1")
        val categoryname2 = Category("Bob", "C2")
        val categoryname3 = Category("Charlie", "C3")

        // Store the instances in an array
        val categoryArray = arrayOf(categoryname1, categoryname2, categoryname3)

        // Access values from the array
        for (category in categoryArray) {
            println("Category Name: ${category.categoryname}, Description: ${category.description}")
        }
    }
}
