package com.zkxy.xmoa.word.demoChart;

import com.zkxy.xmoa.word.util.ChartUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author ccw
 * @date 2014-6-11
 *       <p>
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
public class PieChart {
	public PieChart() {
	}

	public DefaultPieDataset createDataset() {
		String[] categories = { "Bananas", "Kiwi", "Mixed nuts", "Oranges", "Apples", "Pears", "Clementines", "Reddish (bag)", "Grapes (bunch)", };
		Object[] datas = { 8, 3, 1, 6, 8, 4, 4, 0, 0 };
		DefaultPieDataset dataset = ChartUtils.createDefaultPieDataset(categories, datas);
		return dataset;
	}

	public ChartPanel createChart() {
		// 2：创建Chart[创建不同图形]
		JFreeChart chart = ChartFactory.createPieChart("每周水果的分析报告图", createDataset(), true, true, true);
		// 3:设置抗锯齿，防止字体显示不清楚
		ChartUtils.setAntiAlias(chart);// 抗锯齿
		// 4:对柱子进行渲染[创建不同图形]
		ChartUtils.setPieRender(chart.getPlot());
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionPaint("Bananas", Color.RED);
		plot.setSectionPaint("Kiwi", Color.GREEN);
		plot.setSectionPaint("Mixed nuts", Color.YELLOW);
		plot.setSectionPaint("Oranges", Color.BLUE);

//		plot.setLabelFont(new Font("宋体", 0, 12));
//		plot.setNoDataMessage("无数据");
//		plot.setCircular(true);
//		plot.setLabelGap(0.02D);
		//设置链接线 把百分比设置为2位小数
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} {2}",
				NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		/**
		 * 可以注释测试
		 */
		// plot.setSimpleLabels(true);//简单标签,不绘制线条
		// plot.setLabelGenerator(null);//不显示数字
		// 设置标注无边框
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		// 标注位于右侧
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		// 6:使用chartPanel接收
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
				ChartPanel chartPanel = new PieChart().createChart();
				frame.getContentPane().add(chartPanel);
				frame.setVisible(true);
			}
		});

	}

}
