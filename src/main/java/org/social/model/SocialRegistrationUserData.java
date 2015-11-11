/**
 * 
 */
package org.social.model;

import java.io.Serializable;

/**
 * @author piotrek
 *
 */
public class SocialRegistrationUserData implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -8844019431660661748L;
	
	private Integer socialUserId;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;

}
