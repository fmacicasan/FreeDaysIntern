package freedays.app;

/**
 * Factory used for creation of FreeDay child based on their type.
 * @author fmacicasan
 *
 */
public class FreeDayFactory {

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