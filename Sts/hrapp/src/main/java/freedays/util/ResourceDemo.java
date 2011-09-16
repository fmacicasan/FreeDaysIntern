package freedays.util;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceDemo implements ApplicationContextAware {

	private ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = applicationContext;
	}
	
	public void openResource(){
		Resource res1 = ctx.getResource("classpath:import.sql");
		displayInfo(res1);
	}
	
	public void displayInfo(Resource r){
		System.out.println(r.getClass());
		try {
			System.out.println(r.getURL().getContent());
			System.out.println(r.contentLength());
			System.out.println(r.getFilename());
			System.out.println(r.isOpen());
			System.out.println(r.getInputStream().read());
			//System.out.println(r.);
		} catch (IOException e) {
			System.out.println("crazy error!");
		}
	}
	
	public static void main(String[] args){
		ResourceDemo rd = new ResourceDemo();
		rd.openResource();
	}

}
