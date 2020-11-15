package papi.user

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

interface UserServiceInterface {

    User get(Serializable id)

    List<User> list(Map args)

    Long count()

    void delete(Serializable id)

    User save(User user)

}


@Service(User)
abstract class UserService implements UserServiceInterface {

    @Transactional
    User saveEnhanced(User user, Map params) {
        user.save(flush: true)
        String role = "ROLE_" + user.role.name().toUpperCase()

        UserAuthority.create(user, Authority.findByAuthority(role))
        user
    }

    User get(String username, boolean requestVerification) {
        User user = User.findByUsername(username)
        println user

        /* if (requestVerification) {
             if (user != null && user.role == Role.Customer && !user.phoneVerified) {
                 SMS sms = new SMS()
                 sms.addRecpt(user.username)
                 sms.setOTP(user)
                 String fnam = user.names.toString().split(' ')[0]
                 sms.setMsg("Hi ${fnam}, your HBlend activation code is ${SMSOTP.findByUser(user).otp}")
                 sms.send()
             }
         }*/

        user
    }

    void delete(Serializable id) {
        User user1 = User.get(id)
        if (user1 != null) {
            user1.enabled = false;
            user1.accountLocked = true;

            user1.save()
        }
    }

}