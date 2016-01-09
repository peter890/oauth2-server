/**
 * 
 */
package org.social.api;

/**
 * @author piotrek
 *
 */
public interface IOAuthProcessorFactory {
	/**
	 * Probiera procesor zgodnie z typem.
	 * @param name Typ procesora
	 * @return instancja processora albo null.
	 */
	IOAuthProcessor createProcessorByName(final String name);
}
