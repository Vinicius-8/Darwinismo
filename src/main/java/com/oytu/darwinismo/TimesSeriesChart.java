/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oytu.darwinismo;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Vinicius
 */
public class TimesSeriesChart extends ApplicationFrame{
    List<Individuo> listaGeracoes;
    public TimesSeriesChart(String title, List<Individuo> list) {
        super(title);
        this.listaGeracoes = list;
        ChartPanel chartPanel = (ChartPanel) createDemoPanel(); 
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMinimumSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart, false);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);        
        return panel; //chartPanel
    }
    
    private XYDataset createDataset() {//valores que aparecem na tela
        TimeSeries s1 = new TimeSeries("Gerações");
        //s1.add(new Second(01, new Minute()), 1);
        
  
        for (int i = 0; i < this.listaGeracoes.size(); i++) {
            s1.add(new Second(this.listaGeracoes.get(i).getGeracao(),new Minute()),this.listaGeracoes.get(i).getNotaAvaliacao());
        }

        // ******************************************************************
        //  More than 150 demo applications are included with the JFreeChart
        //  Developer Guide...for more information, see:
        //
        //  >   http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        //dataset.addSeries(s2);

        return dataset;

    }
    
    private JFreeChart createChart(XYDataset dataset) {        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Algoritimo Genético",  // title
            "Gerações",             // x-axis label
            "Melhor solução",   // y-axis label
            dataset);
        
        chart.setBackgroundPaint(Color.WHITE);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("ss"));
        
        return chart;

    }

    
}
