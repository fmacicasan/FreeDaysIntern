package freedays.app;

import freedays.app.form.FreeDayRequest;

/**
 * Factory used for creation of FreeDay child based on their type.
 * @author fmacicasan
 *
 */
public class FreeDayFactory {

	/**
	 * Factory method used for the creation of Free days based on the
	 * requested type.
	 * @param fdr
	 * @return
	 */
	public static FreeDay create(FreeDayRequest fdr){
		FreeDay ret;
		switch(fdr.getReqtype()){
			case L:
				ret = new FreeDayL();
				break;
			case C:
				ret = new FreeDayC();
				break;
			case R:
				ret = new FreeDayR();
				break;
			case M:
				ret = new FreeDayM();
				break;
			case NP:
				ret = new FreeDayNP();
				break;
			case V:
				ret = new FreeDayVacation();
				break;
			default:
				throw new IllegalArgumentException("The request type is not supported!");	
		}
		return ret;
//		if(rt == RequestType.L){
//			ret = new FreeDayL();
//			return ret;
//		}
//		if(rt == RequestType.C){
//			ret = new FreeDayC();
//			return ret;
//		}
//		if(rt == RequestType.R){
//			ret = new FreeDayR();
//			return ret;
//		}
//		throw new IllegalArgumentException("The request type is not supported");
	}
}
