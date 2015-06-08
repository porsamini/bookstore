package org.bookstore

class Library {
	String name
	String city
	String locality

	static hasMany = [books: Book]
    static constraints = {
    }
}
