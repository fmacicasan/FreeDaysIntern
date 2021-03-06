package freedays.util;

import java.security.SecureRandom;


/**
 * Utility class responsible for the generation of random passwords.
 * @author fmacicasan
 *
 */
public class PhraseUtils {
	
	public static final int MIN_LENGTH = 7;
	public static final int MAX_VARIATION = 4;

	private static final char[] GOOD_LOWER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n','p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	private static final char[] GOOD_SPECIAL = {'+', '-', '@'};
	
	private static final int PASS_CHAR_TYPES = 4;
	private enum CHAR_TYPES{
		LOWER {
			@Override
			char getChar() {
				return PhraseUtils.GOOD_LOWER[this.getIndexRandom()];
			}

			@Override
			int getMaxRange() {
				return PhraseUtils.GOOD_LOWER.length;
			}
		},
		UPPER {
			@Override
			char getChar() {
				return Character.toUpperCase(LOWER.getChar());
			}

			@Override
			int getMaxRange() {
				return LOWER.getMaxRange();
			}
		},
		DIGGIT {
			@Override
			char getChar() {
				return Character.forDigit(getIndexRandom(), 10);
			}

			@Override
			int getMaxRange() {
				return 10;
			}
		},
		SPECIAL {
			@Override
			char getChar() {
				return PhraseUtils.GOOD_SPECIAL[getIndexRandom()];
			}

			@Override
			int getMaxRange() {
				return PhraseUtils.GOOD_SPECIAL.length;
			}
		};
		abstract char getChar();
		int getIndexRandom(){
			return PhraseUtils.rand.nextInt(getMaxRange());
		}
		abstract int getMaxRange();
	};
	
	private static final SecureRandom rand = new SecureRandom();
	
	/**
	 * Generates a random password with a random length.
	 * The guaranteed minimal length is MIN_LENGTH with
	 * a positive variation of [0 ... MAX_VARIATION]
	 * @return the random password
	 */
	public static String getRandomPhrase(){
		int length = PhraseUtils.MIN_LENGTH + PhraseUtils.rand.nextInt(MAX_VARIATION);
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<length;i++){
			int discriminator = PhraseUtils.rand.nextInt(PhraseUtils.PASS_CHAR_TYPES);//all have same probability
			//TODO: change choice probability for the characters
			sb.append(PhraseUtils.CHAR_TYPES.values()[discriminator].getChar());
		}
		return sb.toString();
	}
	
	/**
	 * Generates a random password with maximal length.
	 * @return the password with maximal length.
	 */
	public static int getPasswordMaxLength(){
		return PhraseUtils.MIN_LENGTH+PhraseUtils.MAX_VARIATION;
	}
	
	
	

}
