package swt.swl.topcard.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetUtil {

	public static Boolean contains(ResultSet rs, int colidx, String value) {

		try {
			while (rs.next()) {

				if (rs.getString(colidx).equals(value)) {
					return true;
				}
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
