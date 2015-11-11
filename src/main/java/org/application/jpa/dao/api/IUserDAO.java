/**
 * 
 */
package org.application.jpa.dao.api;

import org.application.jpa.model.User;

/**
 * @author piotrek
 *
 */
public interface IUserDAO extends IGenericDAO<User>{
	User findByEmail(String email);

}
