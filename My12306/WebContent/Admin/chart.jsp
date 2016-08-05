<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.jfree.chart.ChartFactory, org.jfree.chart.JFreeChart, org.jfree.chart.plot.PlotOrientation, org.jfree.chart.servlet.ServletUtilities, org.jfree.data.category.DefaultCategoryDataset" %>
<%@ page import="java.awt.Font, org.jfree.chart.plot.*, org.jfree.chart.axis.*" %>
<%@ page import="java.util.*,com.neuedu.my12306.usermgr.domain.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<%
Font font=new Font("微软雅黑",Font.BOLD,18);
DefaultCategoryDataset dataset = new DefaultCategoryDataset();

List<IpAddress> list = (ArrayList<IpAddress>)request.getAttribute("iplist");
for (IpAddress ipAddress : list) {
	dataset.addValue(ipAddress.getCnt(), "Count", ipAddress.getIp());
}

JFreeChart chart = ChartFactory.createBarChart3D("登录IP统计图",
                  "",
                  "",
                  dataset,
                  PlotOrientation.VERTICAL,
                  false,
                  false,
                  false);
chart.getTitle().setFont(font);

NumberAxis   axis   =   (NumberAxis)chart.getCategoryPlot().getRangeAxis();
axis.setTickUnit(new   NumberTickUnit(1));

String filename = ServletUtilities.saveChartAsPNG(chart, 420, 300, null, session);
String graphURL = request.getContextPath() + "/Admin/DisplayChart?filename=" + filename;
%>
<img src="<%= graphURL %>" width="420" height="300" border="0">

</body>
</html>