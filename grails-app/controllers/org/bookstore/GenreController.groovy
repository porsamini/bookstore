package org.bookstore

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GenreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Genre.list(params), model:[genreCount: Genre.count()]
    }

    def show(Genre genre) {
        respond genre
    }

    def create() {
        respond new Genre(params)
    }

    @Transactional
    def save(Genre genre) {
        if (genre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (genre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond genre.errors, view:'create'
            return
        }

        genre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'genre.label', default: 'Genre'), genre.id])
                redirect genre
            }
            '*' { respond genre, [status: CREATED] }
        }
    }

    def edit(Genre genre) {
        respond genre
    }

    @Transactional
    def update(Genre genre) {
        if (genre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (genre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond genre.errors, view:'edit'
            return
        }

        genre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'genre.label', default: 'Genre'), genre.id])
                redirect genre
            }
            '*'{ respond genre, [status: OK] }
        }
    }

    @Transactional
    def delete(Genre genre) {

        if (genre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        genre.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'genre.label', default: 'Genre'), genre.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'genre.label', default: 'Genre'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
