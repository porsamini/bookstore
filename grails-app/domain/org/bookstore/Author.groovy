package org.bookstore

class Author {
	String fullName

	static hasMany = [books: Book]

    static constraints = {
    }

    String toString(){
    	return fullName
    }
}
