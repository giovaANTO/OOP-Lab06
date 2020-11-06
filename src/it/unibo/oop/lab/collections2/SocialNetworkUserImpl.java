package it.unibo.oop.lab.collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Instructions
 * 
 * This will be an implementation of
 * {@link it.unibo.oop.lab.collections2.SocialNetworkUser}:
 * 
 * 1) complete the definition of the methods by following the suggestions
 * included in the comments below.
 * 
 * @param <U>
 *            Specific user type
 */
public class SocialNetworkUserImpl<U extends User> extends UserImpl implements SocialNetworkUser<U> {

    /*
     * 
     * [FIELDS]
     * 
     * Define any necessary field
     * 
     * In order to save the people followed by a user organized in groups, adopt
     * a generic-type Map:
     * 
     * think of what type of keys and values would best suit the requirements
     */
	final Map<String , List<U>> followedUser;

    /*
     * [CONSTRUCTORS]
     * 
     * 1) Complete the definition of the constructor below, for building a user
     * participating in a social network, with 4 parameters, initializing:
     * 
     * - firstName - lastName - username - age and every other necessary field
     * 
     * 2) Define a further constructor where age is defaulted to -1
     */

    /**
     * Builds a new {@link SocialNetworkUserImpl}.
     * 
     * @param name
     *            the user firstname
     * @param surname
     *            the user lastname
     * @param userAge
     *            user's age
     * @param user
     *            alias of the user, i.e. the way a user is identified on an
     *            application
     */
    public SocialNetworkUserImpl(final String name, final String surname, final String user, final int userAge) {
        super(name, surname, user, userAge);
        this.followedUser = new HashMap<>();
    }

    /*
     * [METHODS]
     * 
     * Implements the methods below
     */

    /**
     * {@inheritDoc}
     */
    public boolean addFollowedUser(final String circle, final U user) {
    	
    	if(!this.userExist(user)) {
    		
    		// Create a new circle if it doesn't exists
    		if(!this.circleExists(circle)) {
    			this.followedUser.put(circle, new ArrayList<>());
    		}
    		
    		/*
    		 * Get the followed user circle list
    		 * and add the new user to this
    		 */
    		List<U> followedUserCircle = this.followedUser.get(circle);
    		followedUserCircle.add(user);
    	
    		return true;
    	}
    	
        return false;
    }
    
    /**
     * Check if a given user exists
     * @param user to be checked
     * @return true if an user exists in one of the listed circle
     */
    private boolean userExist(final U user) {
    	
    	for (List<U> userGroup : this.followedUser.values()) {
    		if(userGroup.contains(user)) {
    			return true;
    		}	
    	}
    	return false;
    }
    
    /**
     * {@inheritDoc}
     */
    public Collection<U> getFollowedUsersInGroup(final String groupName) {
    	
        return this.circleExists(groupName) ? this.followedUser.get(groupName) : List.of();
    }

    /**
     * Check if a given circle exists
     * @param circle to be checked
     * @return true if a circle exists 
     */ 
    private boolean circleExists(final String circle) {
    	return this.followedUser.containsKey(circle);
    }
    
    
    /**
     * {@inheritDoc}
     */
    public List<U> getFollowedUsers() {
        return (List<U>) this.followedUser.values();
    }

}
