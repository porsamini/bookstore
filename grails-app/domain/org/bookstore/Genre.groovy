package org.bookstore

class Genre {
	String name
	String desc
	Boolean eighteenPlus

	static hasMany = [book: Book]

    static constraints = {
    }

    String toString(){
    	name
    }
}
