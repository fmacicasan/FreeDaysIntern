package freedays.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;

/**
 * 
 * Class that helps sorting list of entities based on some RegularUser
 * discriminators
 * 
 * @author osuciu
 * 
 */

public class RegularUserUtils {

	public static enum RegularUserUtilsDiscriminators {
		LASTNAME, FIRSTNAME
	};

	public static List<RegularUser> sortAscending(List<RegularUser> list,
			RegularUserUtilsDiscriminators discriminator) {

		switch (discriminator) {

		case FIRSTNAME:
			Collections.sort(list, new SortRegularUserByFirstName(true));
			break;
		default:
			Collections.sort(list, new SortRegularUserByFirstName(true));
			break;
		}

		return list;

	}

	public static  List<? extends ApplicationRegularUser> sortAscendingApplicationRegularUsers(
			List<? extends ApplicationRegularUser> list,
			RegularUserUtilsDiscriminators discriminator) {

		switch (discriminator) {

		case FIRSTNAME:
			Collections.sort(list, new SortApplicationRegularUserByFirstName(true));
			break;
		default:
			Collections.sort(list, new SortApplicationRegularUserByFirstName(true));
			break;
		}

		return list;
		
	}

	public static void sortDescending(List<RegularUser> list, int discriminator) {

	}

	private static class SortRegularUserByFirstName implements
			Comparator<RegularUser> {

		private boolean ascending;

		public SortRegularUserByFirstName(boolean ascending) {
			this.ascending = ascending;
		}

		@Override
		public int compare(RegularUser o1, RegularUser o2) {
			return ascending ? o1.getFirstname().compareTo(o2.getFirstname())
					: o2.getFirstname().compareTo(o1.getFirstname());
		}

	}

	private static class SortApplicationRegularUserByFirstName implements
			Comparator<ApplicationRegularUser> {

		private boolean ascending;

		public SortApplicationRegularUserByFirstName(boolean ascending) {
			this.ascending = ascending;
		}

		@Override
		public int compare(ApplicationRegularUser o1, ApplicationRegularUser o2) {
			return ascending ? o1.getRegularUser().getFirstname()
					.compareTo(o2.getRegularUser().getFirstname()) : o2
					.getRegularUser().getFirstname()
					.compareTo(o1.getRegularUser().getFirstname());
		}

	}

}
