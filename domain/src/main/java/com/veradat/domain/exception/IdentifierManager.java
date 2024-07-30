
package com.veradat.domain.exception;

import java.util.HashMap;
import java.util.Map;

public class IdentifierManager {
	/**
	 * IdentifierManager
	 */
	private IdentifierManager() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * identifierMap
	 */
	private static final Map<Class<?>, String> identifierMap = new HashMap<>();

	/**
	 * registerIdentifier
	 * 
	 * @param clazz
	 * @param identifier
	 */
	public static void registerIdentifier(Class<?> clazz, String identifier) {
		identifierMap.put(clazz, identifier);
	}

	/**
	 * getIdentifier
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getIdentifier(Class<?> clazz) {
		String identifier = identifierMap.get(clazz);
		if (identifier == null) {
			// Si no se encuentra la clase directamente anotada, buscamos en las interfaces
			// implementadas
			for (Class<?> implementedInterface : clazz.getInterfaces()) {
				identifier = identifierMap.get(implementedInterface);
				if (identifier != null) {
					return identifier;
				}
			}
		}
		// el valor PLOC999 es por defecto para las excepciones de clases sin id
		return identifier == null ? "PLOC999" : identifier;
	}
}
