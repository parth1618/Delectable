package web.module.report;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import web.module.report.Report801_802.InvalidDateParametersForReport801_802Exception;
import web.module.report.Report803.InvalidDateParametersForReport803Exception;
import web.module.report.Report804.InvalidDateParametersForReport804Exception;

public class ReportManager implements ReportBoundaryInterface, ReportList {

	private static List<Report> reportList = new ArrayList<>();

	static {
		reportList.add(new OrderDeliveryReport(RID_801, RNAME_801));
		reportList.add(new OrderDeliveryReport(RID_802, RNAME_802));
		reportList.add(new RevenueReport(RID_803, RNAME_803));
		reportList.add(new OrderDeliveryReport(RID_804, RNAME_804));
	}

	@Override
	public List<Report> getReportList() {
		return reportList;
	}

	@Override
	public Report getReport(int rid) {
		Iterator<Report> iterator = reportList.listIterator();
		while (iterator.hasNext()) {
			Report rt = iterator.next();
			if (rt.matchesId(rid))
				return (rt);
		}
		return (new NullReport());
	}

	@Override
	public Report generateReport(int rid, String startDate, String endDate) throws ParseException {
		try {
			if (rid == RID_801 || rid == RID_802) {
				Report801_802 report = new Report801_802();
				return report.generateReport(getReport(rid), startDate, endDate);
			} else if (rid == RID_803) {
				Report803 report = new Report803();
				return report.generateReport(getReport(rid), startDate, endDate);
			} else {
				Report804 report = new Report804();
				return report.generateReport(getReport(rid), startDate, endDate);
			}
		} catch (InvalidDateParametersForReport801_802Exception | InvalidDateParametersForReport803Exception
				| InvalidDateParametersForReport804Exception e4) {
			throw new InvalidDateParametersForReportException();
		}
	}

	public class InvalidDateParametersForReportException extends RuntimeException {
		private static final long serialVersionUID = 1L;

	}

}
