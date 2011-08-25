package freedays.schedule;

/**
 * Interface describing the scheduled tasks
 * @author fmacicasan
 *
 */
public interface FreeDayScheduleService {

	/**
	 * Reports the current month's free day report status
	 */
	public void reportFreeDays();
	
	/**
	 * Denies all the requests that are under approval and their date passed.
	 */
	public void denyLateRequestsStillUnderApproval();
	
	public void generateTimesheets();
}
