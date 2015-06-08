package org.bookstore

class Book {
	String title
	String description
	static belongsTo = [author: Author, library: Library, genre: Genre]
	static hasMany = [genre: Genre]

    static constraints = {
    }
}
