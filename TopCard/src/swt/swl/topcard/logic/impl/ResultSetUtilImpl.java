package swt.swl.topcard.logic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import swt.swl.topcard.logic.ResultSetUtil;

public class ResultSetUtilImpl implements ResultSetUtil {

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
