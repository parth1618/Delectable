package web.module.report;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReportManager implements ReportBoundaryInterface, ReportList {
	
	private static List<Report> reportList = new ArrayList<>();
	
	static{
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
		while(iterator.hasNext()){
			Report rt = iterator.next();
			if(rt.matchesId(rid)) return(rt);
		}
		return (new NullReport());
	}

	@Override
	public Report generateReport(int rid, String startDate, String endDate) throws ParseException {
		if(rid == RID_801 || rid == RID_802){
			Report801_802 report = new Report801_802();
			return report.generateReport(getReport(rid), "Not Applicable", "Not Applicable");
		}
		else if(rid == RID_803){
			Report803 report = new Report803();
			return report.generateReport(getReport(rid), startDate, endDate);
		}
		else{
			Report804 report = new Report804();
			return report.generateReport(getReport(rid), startDate, endDate);
		}
	}

}
