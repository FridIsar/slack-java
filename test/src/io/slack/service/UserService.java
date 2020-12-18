package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class UserService {

	private final DAO<User> userDAO = DAOFactory.getUser();

	public Message create(String email, String password, String pseudo) {
		try {
			User user = userDAO.find(email);
			if (user != null) {
				return new Message(403);
			}
			if (!EmailUtils.isEmail(email) || !EmailUtils.isPassword(password)) {
				return new Message(403);
			}
			user = new User(email, password, pseudo);
			userDAO.insert(user);
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	/*
    public static boolean[] checkUserData(String email, String mdp){
        boolean[] res = {true, true};
        String[] message = new String[3];
        //email
        if(! EmailUtils.isEmail(email) ){
            res[0]=false;
            for(int i=0; i<message.length; i++){
                if(message[i]==null) message[i]="veuillez retaper un email au bon format [ exemple aaa@lip6.fr ]";
            }
        }
        if(DatabaseUser.getDatabase().existEmail(email)) {
            res[0]=false;
            for(int i=0; i<message.length; i++){
                if(message[i]==null) message[i]="email déjà utilisé";
            }
        }

        //password
        if(! EmailUtils.isPassword(mdp)){

            res[1]=false;
            for(int i=0; i<message.length; i++){
                if(message[i]==null) message[i]="veuillez taper un mot de passe au bon format  [ minimum 8 caractère : 1 maj | 1 min | 1 chiffre | 1 caractère spécial ]";
            }
        }

        if( ! res[0] || ! res[1] )
            Fenetre.getFenetre().affichePopup(message);

        return res;
    }*/


	public Message authenticate(String email, String password) {
		try {
			User user = userDAO.find(email);
			if (user == null) {
				return new Message(404);
			}
			if (!user.getPassword().equals(password)) {
				return new Message(403);
			}
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

}
