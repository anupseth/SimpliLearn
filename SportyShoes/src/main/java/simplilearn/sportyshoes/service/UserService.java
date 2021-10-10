package simplilearn.sportyshoes.service;

import simplilearn.sportyshoes.entities.User;

public interface UserService {
	
	public boolean singUpUser(User user);
	
	public boolean singInUser(User user);

}
