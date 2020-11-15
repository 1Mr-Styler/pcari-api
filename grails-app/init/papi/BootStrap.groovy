package papi

import papi.user.Authority
import papi.user.Role
import papi.user.User
import papi.user.UserAuthority

class BootStrap {

    def init = { servletContext ->
        Authority authorityDealer = new Authority(authority: "ROLE_DEALER")
        Authority authorityAdmin = new Authority(authority: "ROLE_ADMIN")
        Authority authorityUser = new Authority(authority: "ROLE_USER")

        [authorityDealer, authorityUser, authorityAdmin].each { authority ->
            authority.save(flush: true)
        }

        User admin = new User(names: "Rose Clevel", username: "rose221", password: "pass", role: Role.Admin)
        User dealer1 = new User(names: "Utama Motors", username: "rose222", password: "pass",
                role: Role.Dealer, location: "Kuala Lumpur", avatar: "images/cooper.jpg")

        User dealer2 = new User(names: "Moscorp Sdn Bhd", username: "rose223", password: "pass",
                role: Role.Dealer, location: "Shah Alam", avatar: "images/cooper.jpg")

        User dealer3 = new User(names: "Apex Wheel Sdn Bhd", username: "rose224", password: "pass",
                role: Role.Dealer, location: "Shah Alam", avatar: "images/aw.jpg")
        User dealer4 = new User(names: "Trinity Car Autos", username: "rose225", password: "pass",
                role: Role.Dealer, location: "Kuala Lumpur", avatar: "images/ta.jpg")

        [admin, dealer1, dealer2, dealer3, dealer4].each { user ->
            user.save(flush: true)
        }

        UserAuthority.create admin, authorityAdmin
        UserAuthority.create dealer1, authorityDealer
        UserAuthority.create dealer2, authorityDealer
    }
    def destroy = {
    }
}
