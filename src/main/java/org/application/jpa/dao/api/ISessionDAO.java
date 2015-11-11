/**
 * 
 */
package org.application.jpa.dao.api;

import org.application.jpa.model.Session;

/**
 * @author piotrek
 *
 */
public interface ISessionDAO  extends IGenericDAO<Session>{
	Session findBySsnId(String ssnId);
	

}
