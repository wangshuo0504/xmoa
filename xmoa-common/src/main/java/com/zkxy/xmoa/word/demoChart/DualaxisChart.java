package com.zkxy.xmoa.word.demoChart;


import com.zkxy.xmoa.word.util.ChartUtils;
import com.zkxy.xmoa.word.util.Serie;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 *
 * @author ccw
 * @date 2014-6-11
 *       <p>
 *       2个Y轴图形<br/>
 *       创建图表步骤：<br/>
 *       1：创建数据集合<br/>
 *       2：创建Chart：<br/>
 *       3:设置抗锯齿，防止字体显示不清楚<br/>
 *       4:对柱子进行渲染，<br/>
 *       5:对其他部分进行渲染<br/>
 *       6:使用chartPanel接收<br/>
 *
 *       </p>
 */

public class DualaxisChart {
	public DualaxisChart() {
	}

	public ChartPanel createChart() {
		String[] categories = { "1999年12月31日", "2000-12-31", "2001-12-31", "2002-12-31", "2003-12-31", "2004-12-31", "2005-12-31", "2006-12-31", "2007-12-31",
				"2008-12-31", "2009-12-31", "2010-12-31", "2011-12-31", "2012-12-31", "2013-12-31" };
//		for (int i = 0; i < categories.length; i++) {
//			categories[i]=categories[i].substring(0, 4);
//		}
		Vector<Serie> seriesNetProfit = new Vector<Serie>();
		// 净利润
		Object[] netProfit = { 92669.20, 95790.47, 106187.80, 128530.88, 156608.82, 193003.08, 255800.38, 335302.66, 549877.54, 1251596.81, 1321658.11,
				1917721.09, 2728598.10, 3418600.00, 4092200.00 };
		Object[] netProfit2 = { 9266.20, 9579.47, 10618.80, 12830.88, 16608.82, 13003.08, 25800.38, 35302.66, 54877.54, 121596.81, 121658.11,
				117721.09, 228598.10, 318600.00, 492200.00 };
		// 股利支付率
		Object[] payoutRatio = { "39.01", "--", "45.39", "30.46", "27.50", "24.34", "19.90", "19.48", "12.67", "10.40", "10.02", "11.97", "20.51", "30.01",
				" --" };

		// 股利支付数目
		Object[] payoutNum = {"300", "--", "500", "300", "250", "250", "200", "200", "100", "100", "100", "100", "200", "300",
				" --"};

		seriesNetProfit.add(new Serie("净利润", netProfit));
		seriesNetProfit.add(new Serie("净利润2", netProfit2));

		Vector<Serie> seriesPayoutRatio = new Vector<Serie>();
		seriesPayoutRatio.add(new Serie("股利支付率", payoutRatio));
//		seriesPayoutRatio.add(new Serie("股利支付数目", payoutNum));

		Vector<Serie> seriesPayoutNum = new Vector<Serie>();
		seriesPayoutNum.add(new Serie("股利支付数目", payoutNum));

		DefaultCategoryDataset datasetNetProfit = ChartUtils.createDefaultCategoryDataset(seriesNetProfit, categories);
		JFreeChart chart = ChartFactory.createBarChart("", "", "净利润(万元)", datasetNetProfit, PlotOrientation.VERTICAL, true, true, true);
		ChartUtils.setAntiAlias(chart);// 抗锯齿
		ChartUtils.setBarRenderer(chart.getCategoryPlot(), false);
		// 设置坐标轴
		ChartUtils.setXAixs(chart.getCategoryPlot());
		ChartUtils.setYAixs(chart.getCategoryPlot());
		// linechart
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		DefaultCategoryDataset datasetPayoutRatio = ChartUtils.createDefaultCategoryDataset(seriesPayoutRatio, categories);
		categoryplot.setDataset(1, datasetPayoutRatio);
		categoryplot.mapDatasetToRangeAxis(1, 1);

		// linechart2
		CategoryPlot categoryplot2 = (CategoryPlot) chart.getPlot();
		DefaultCategoryDataset datasetPayoutNum = ChartUtils.createDefaultCategoryDataset(seriesPayoutNum, categories);
		categoryplot2.setDataset(2, datasetPayoutNum);
		categoryplot2.mapDatasetToRangeAxis(2, 2);


		// X轴刻度
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		// 右侧Y轴
		NumberAxis numberaxis = new NumberAxis("股利支付率(%)");
		categoryplot.setRangeAxis(1, numberaxis);
		NumberAxis numberaxis2 = new NumberAxis("股利支付数量");
		categoryplot.setRangeAxis(2, numberaxis2);
		// 隐藏Y刻度
		numberaxis.setAxisLineVisible(false);
		numberaxis.setTickMarksVisible(false);

		numberaxis2.setAxisLineVisible(false);
		numberaxis2.setTickMarksVisible(false);

		// 设置折线图样式
		LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
		lineRenderer.setSeriesPaint(0, new Color(255, 185, 1));
		lineRenderer.setBaseShapesVisible(true);// 数据点绘制形状
		categoryplot.setRenderer(1, lineRenderer);

		//样式2
		lineRenderer = new LineAndShapeRenderer();
		lineRenderer.setSeriesPaint(0, new Color(0, 185, 1));
		lineRenderer.setBaseShapesVisible(true);// 数据点绘制形状
		categoryplot.setRenderer(2, lineRenderer);

		categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);// 绘制Z-index, 将折线图在前面
		chart.getLegend().setPosition(RectangleEdge.TOP);//标注在顶部
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 420);
		frame.setLocationRelativeTo(null);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// 创建图形
				ChartPanel chartPanel = new DualaxisChart().createChart();
				frame.getContentPane().add(chartPanel);
				frame.setVisible(true);
			}
		});

	}

}
