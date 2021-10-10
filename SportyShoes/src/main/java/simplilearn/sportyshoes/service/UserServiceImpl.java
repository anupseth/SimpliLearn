package simplilearn.sportyshoes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import simplilearn.sportyshoes.entities.User;
import simplilearn.sportyshoes.repository.UserRepository;
import simplilearn.sportyshoes.util.PasswordEncoderDecoderUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepo;

	@Override
	public boolean singUpUser(User user) {

		User findByUsername = userRepo.findByUsername(user.getUsername());

		if (findByUsername == null) {
			userRepo.save(user);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean singInUser(User user) {
		
		User userFromDb = userRepo.findByUsername(user.getUsername());
		
		if(userFromDb == null)
			return false;
		
		String decodedPass = PasswordEncoderDecoderUtil.decodePassword(userFromDb.getPassword());
		
		logger.info("-------------- decoded password "+ decodedPass);
		
		
		
		if(userFromDb.getUsername().equals(user.getUsername()) 
				&& decodedPass.equals(user.getPassword())) {
			return true;
		}
		
		return false;
	}

}
