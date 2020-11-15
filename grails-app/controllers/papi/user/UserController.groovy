package papi.user


import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured("permitAll()")
class UserController {
    UserService userService
    SpringSecurityService springSecurityService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model: [userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.get(id)
    }

    @Secured("isFullyAuthenticated()")
    def showUsername() {
        User user = springSecurityService.getCurrentUser() as User
        String username = params.username
        println username
        respond view: "show", userService.get(username, user.role == Role.User)
    }


    def save(User user) {
        if (user == null) {
            render status: NOT_FOUND
            return
        }

        try {
            println user.errors
            userService.saveEnhanced(user, params)
        } catch (ValidationException ignored) {
            respond user.errors, view: 'create'
            return
        }

        respond user, [status: CREATED, view: "show"]
    }

    def update(User user) {
        if (user == null) {
            render status: NOT_FOUND
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException ignored) {
            respond user.errors, view: 'edit'
            return
        }

        respond user, [status: OK, view: "show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        userService.delete(id)

        render status: NO_CONTENT
    }
}
