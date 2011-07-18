package freedays.util;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class PhraseUtilsTest {

	@Test
	public void testGetRandomPassGeneration(){
		String pass = PhraseUtils.getRandomPhrase();
		org.junit.Assert.assertNotNull("Pass generation error!", pass);
	}
	
	@Test
	public void testGenRandomPassLengthMin(){
		String pass = PhraseUtils.getRandomPhrase();
		org.junit.Assert.assertTrue("Pass generation to short password", pass.length()>=PhraseUtils.MIN_LENGTH);
	}
	
	@Test
	public void testGenRandomPassLengthMax(){
		String pass = PhraseUtils.getRandomPhrase();
		org.junit.Assert.assertTrue("Pass generation to long password", pass.length()<PhraseUtils.getPasswordMaxLength());
	}
	
	@Test
	public void testGenRandomPassUniqueness(){
		java.security.SecureRandom sr = new java.security.SecureRandom();
		int length = sr.nextInt(20);//TODO:some aggreed value for acceptance
		java.util.Set<String> set = new java.util.HashSet<String>();
		for(int i=length;i>0;i--){
			set.add(PhraseUtils.getRandomPhrase());
		}
		org.junit.Assert.assertEquals("Pass generation password uniqueness conflict - generated equal pass", length, set.size());
	}
	
}
