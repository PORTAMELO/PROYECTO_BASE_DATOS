package co.edu.unbosque.Proyecto_William.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.buf.CharsetUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import co.edu.unbosque.Proyecto_William.model.Empleado;
import co.edu.unbosque.Proyecto_William.model.Novedad;
import co.edu.unbosque.Proyecto_William.repository.BookDAO;
import co.edu.unbosque.Proyecto_William.repository.EmpleadoDAO;
import co.edu.unbosque.Proyecto_William.repository.NovedadDAO;
import co.edu.unbosque.Proyecto_William.repository.PeliculaDAO;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("PDFController")
@Transactional
public class PDF {

	@Autowired
	BookDAO bookDAO;

	@Autowired
	EmpleadoDAO empleDAO;

	@Autowired
	NovedadDAO novedadDAO;

	@Autowired
	PeliculaDAO peliculaDAO;

	@GetMapping(value = "/PDF1", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> obtenerPDF1(@RequestParam(name = "op1") Integer op1,
			@RequestParam(name = "op2") Integer op2, @RequestParam(name = "op3") Integer op3) {
		try {

			ArrayList<Empleado> lst1 = empleDAO.findAll();
			if (op1 == 0) {
				mergeSortPorNombre(lst1);
			} else {
				mergeSortPorNombre(lst1);
				Collections.reverse(lst1);
			}

			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Font headerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
			Font cellFont = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
			BaseColor colorTitulo = new BaseColor(100, 149, 237);
			BaseColor colorCeldas = new BaseColor(240, 248, 255);
			BaseColor colorTituloTexto = new BaseColor(25, 25, 112);

			document.open();

			Paragraph titulo = new Paragraph("Reporte (Nomina)",
					new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, colorTitulo));
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(10f);
			document.add(titulo);

			Paragraph val1 = new Paragraph("A)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val1.setAlignment(Element.ALIGN_LEFT);
			val1.setSpacingAfter(5f);
			document.add(val1);

			Paragraph val2 = new Paragraph("Numero de empleados = " + lst1.size(),
					new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, colorTituloTexto));
			val2.setAlignment(Element.ALIGN_LEFT);
			val2.setSpacingAfter(5f);
			document.add(val2);

			PdfPTable tabla = new PdfPTable(3);
			tabla.setWidthPercentage(100);
			tabla.setSpacingBefore(10f);
			tabla.setSpacingAfter(10f);

			String[] titulos = { "ID", "Nombre completo", "Codigo" };

			for (int i = 0; i < titulos.length; i++) {
				PdfPCell celda = new PdfPCell();
				celda.setBackgroundColor(colorTitulo);
				celda.setPadding(5);
				celda.setPhrase(new Phrase(titulos[i], headerFont));
				tabla.addCell(celda);
			}

			for (int i = 0; i < lst1.size(); i++) {
				tabla.addCell(crearCelda((i + 1) + "", cellFont, colorCeldas));
				tabla.addCell(crearCelda(lst1.get(i).getNombreEmpleado(), cellFont, colorCeldas));
				tabla.addCell(crearCelda(lst1.get(i).getCodigo() + "", cellFont, colorCeldas));
			}

			document.add(tabla);
			document.newPage();

			Paragraph val3 = new Paragraph("B)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val3.setAlignment(Element.ALIGN_LEFT);
			val3.setSpacingAfter(5f);
			document.add(val3);

			ArrayList<Empleado> aux2 = empleDAO.findAll();

			ArrayList<String> lst2 = new ArrayList<String>();

			for (int i = 0; i < aux2.size(); i++) {
				if (!lst2.contains(aux2.get(i).getDependencia())) {
					lst2.add(aux2.get(i).getDependencia());
				}
			}

			Collections.sort(lst2);

			if (op2 == 0) {
				mergeSortPorNombre(lst1);
			} else {
				mergeSortPorNombre(lst1);
				Collections.reverse(lst1);
			}

			for (int i = 0; i < lst2.size(); i++) {
				Paragraph val4 = new Paragraph("Dependencia = " + lst2.get(i),
						new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, colorTituloTexto));
				val4.setAlignment(Element.ALIGN_LEFT);
				val4.setSpacingAfter(5f);
				document.add(val4);

				PdfPTable tabla2 = new PdfPTable(3);
				tabla2.setWidthPercentage(100);
				tabla2.setSpacingBefore(10f);
				tabla2.setSpacingAfter(10f);

				String[] titulos2 = { "Codigo", "Nombre completo", "Cargo" };

				for (int k = 0; k < titulos2.length; k++) {
					PdfPCell celda = new PdfPCell();
					celda.setBackgroundColor(colorTitulo);
					celda.setPadding(5);
					celda.setPhrase(new Phrase(titulos2[k], headerFont));
					tabla2.addCell(celda);
				}

				for (int k = 0; k < lst1.size(); k++) {
					if (lst2.get(i).equals(lst1.get(k).getDependencia())) {
						tabla2.addCell(crearCelda(lst1.get(k).getCodigo() + "", cellFont, colorCeldas));
						tabla2.addCell(crearCelda(lst1.get(k).getNombreEmpleado(), cellFont, colorCeldas));
						tabla2.addCell(crearCelda(lst1.get(k).getCargo(), cellFont, colorCeldas));
					}
				}

				document.add(tabla2);
			}

			document.newPage();

			Paragraph val4 = new Paragraph("C)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val4.setAlignment(Element.ALIGN_LEFT);
			val4.setSpacingAfter(5f);
			document.add(val4);

			if (op3 == 0) {
				mergeSortPorNombre(lst1);
			} else {
				mergeSortPorNombre(lst1);
				Collections.reverse(lst1);
			}

			ArrayList<String> lst3 = new ArrayList<String>();

			for (int i = 0; i < lst2.size(); i++) {
				Paragraph val5 = new Paragraph("Dependencia = " + lst2.get(i),
						new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, colorTituloTexto));
				val5.setAlignment(Element.ALIGN_LEFT);
				val5.setSpacingAfter(5f);
				document.add(val5);

				for (int j = 0; j < lst1.size(); j++) {
					if (!lst3.contains(lst1.get(j).getCargo())) {
						lst3.add(lst1.get(j).getCargo());
					}
				}

				Collections.sort(lst3);

				for (int j = 0; j < lst3.size(); j++) {
					Paragraph val6 = new Paragraph("Cargo = " + lst3.get(j),
							new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC, colorTituloTexto));
					val6.setAlignment(Element.ALIGN_LEFT);
					val6.setSpacingAfter(5f);

					PdfPTable tabla2 = new PdfPTable(2);
					tabla2.setWidthPercentage(100);
					tabla2.setSpacingBefore(10f);
					tabla2.setSpacingAfter(10f);

					String[] titulos2 = { "Codigo", "Nombre completo" };

					for (int k = 0; k < titulos2.length; k++) {
						PdfPCell celda = new PdfPCell();
						celda.setBackgroundColor(colorTitulo);
						celda.setPadding(5);
						celda.setPhrase(new Phrase(titulos2[k], headerFont));
						tabla2.addCell(celda);
					}

					boolean flag = false;

					for (int k = 0; k < lst1.size(); k++) {
						if (lst2.get(i).equals(lst1.get(k).getDependencia())
								&& lst3.get(j).equals(lst1.get(k).getCargo())) {
							flag = true;
							tabla2.addCell(crearCelda(lst1.get(k).getCodigo() + "", cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst1.get(k).getNombreEmpleado(), cellFont, colorCeldas));
						}
					}
					if (flag) {
						document.add(val6);
						document.add(tabla2);
					}

				}
			}

			document.newPage();

			int[] tonosGrises = { 0xECEFF1, 0xCFD8DC, 0xB0BEC5, 0x90A4AE, 0x78909C, 0x607D8B, 0x546E7A, 0x455A64,
					0x37474F, 0x263238, 0xC5CAE9, 0x9FA8DA, 0x7986CB, 0x5C6BC0, 0x3F51B5, 0x3949AB, 0x303F9F, 0x283593,
					0x1A237E, 0x0D47A1 };

			Paragraph val7 = new Paragraph("D)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val7.setAlignment(Element.ALIGN_LEFT);
			val7.setSpacingAfter(5f);
			document.add(val7);

			DefaultPieDataset dataset = new DefaultPieDataset();
			for (int j = 0; j < lst2.size(); j++) {
				String depen = lst2.get(j);
				int cont = 0;
				for (int i = 0; i < lst1.size(); i++) {
					if (lst1.get(i).getDependencia().equals(depen)) {
						cont++;
					}
				}
				dataset.setValue(depen, cont);
			}

			JFreeChart chart = ChartFactory.createPieChart("Gráfico de Pastel", dataset, true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			int cont = 0;
			for (int i = 0; i < lst2.size(); i++) {
				if (cont == tonosGrises.length - 1) {
					cont = 0;
					plot.setSectionPaint(lst2.get(i), new Color(tonosGrises[cont]));
				} else {
					plot.setSectionPaint(lst2.get(i), new Color(tonosGrises[cont]));
					cont++;
				}
			}

			DecimalFormat df = new DecimalFormat("0.00%");
			StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0}: ({2})", df,
					NumberFormat.getPercentInstance());
			plot.setLabelGenerator(labelGenerator);

			int width = 320;
			int height = 240;
			BufferedImage bufferedImage = chart.createBufferedImage(width, height);

			Image resizedImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = resizedBufferedImage.createGraphics();
			g2d.drawImage(resizedImage, 0, 0, null);
			g2d.dispose();

			ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
			ImageIO.write(resizedBufferedImage, "png", chartOut);
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(chartOut.toByteArray());

			img.scaleToFit(520, 400);
			document.add(img);

			document.newPage();
			Paragraph val8 = new Paragraph("E)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val8.setAlignment(Element.ALIGN_LEFT);
			val8.setSpacingAfter(5f);
			document.add(val8);

			for (int i = 0; i < lst2.size(); i++) {

				DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
				boolean flag = false;
				for (int j = 0; j < lst3.size(); j++) {
					String cargo = lst3.get(j);
					int cont2 = 0;

					for (int k = 0; k < lst1.size(); k++) {
						if (lst2.get(i).equals(lst1.get(k).getDependencia())
								&& lst3.get(j).equals(lst1.get(k).getCargo())) {
							cont2++;
							flag = true;
						}
					}
					if (cont2 != 0) {
						barDataset.addValue(cont2, "", cargo);
					}

				}

				JFreeChart barChart = ChartFactory.createBarChart("Dependencia " + lst2.get(i), "Cargo",
						"Cantidad de empleados", barDataset, PlotOrientation.VERTICAL, true, true, false);

				CategoryPlot barPlot = (CategoryPlot) barChart.getPlot();
				for (int k = 0; k < lst2.size(); k++) {
					barPlot.getRenderer().setSeriesPaint(k, new Color(tonosGrises[k % tonosGrises.length]));
				}

				width = 480;
				height = 360;
				BufferedImage barBufferedImage = barChart.createBufferedImage(width, height);

				ByteArrayOutputStream barChartOut = new ByteArrayOutputStream();
				ImageIO.write(barBufferedImage, "png", barChartOut);
				com.itextpdf.text.Image barImg = com.itextpdf.text.Image.getInstance(barChartOut.toByteArray());

				barImg.scaleToFit(480, 360);
				document.add(barImg);
			}

			document.close();

			ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=informe.pdf")
					.contentType(MediaType.APPLICATION_PDF).contentLength(resource.contentLength()).body(resource);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@GetMapping(value = "/PDF2", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> obtenerPDF2() {
		try {

			ArrayList<Empleado> empleadoList = empleDAO.findAll();
			ArrayList<Novedad> novedadList = novedadDAO.findAll();

			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			Font headerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
			Font cellFont = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
			BaseColor colorTitulo = new BaseColor(100, 149, 237);
			BaseColor colorCeldas = new BaseColor(240, 248, 255);
			BaseColor colorTituloTexto = new BaseColor(25, 25, 112);

			document.open();

			Paragraph titulo = new Paragraph("Reporte (Informacion Personal)",
					new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, colorTituloTexto));
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(10f);
			document.add(titulo);

			PdfPTable tabla = new PdfPTable(8);
			tabla.setWidthPercentage(100);
			tabla.setSpacingBefore(10f);
			tabla.setSpacingAfter(10f);

			String[] titulos = { "Nombre completo", "Dependencia", "Cargo", "Fecha de ingreso", "EPS", "Aporte Pensión",
					"Salario", "Discriminación de Novedades" };

			for (int i = 0; i < titulos.length; i++) {
				PdfPCell celda = new PdfPCell();
				celda.setBackgroundColor(colorTitulo);
				celda.setPadding(5);
				celda.setPhrase(new Phrase(titulos[i], headerFont));
				tabla.addCell(celda);
			}

			for (int i = 0; i < empleadoList.size(); i++) {
				tabla.addCell(crearCelda(
						empleadoList.get(i).getNombreEmpleado() + " (" + empleadoList.get(i).getCodigo() + ")",
						cellFont, colorCeldas));
				tabla.addCell(crearCelda(empleadoList.get(i).getDependencia(), cellFont, colorCeldas));
				tabla.addCell(crearCelda(empleadoList.get(i).getCargo(), cellFont, colorCeldas));
				tabla.addCell(crearCelda(empleadoList.get(i).getFecha(), cellFont, colorCeldas));
				tabla.addCell(crearCelda(empleadoList.get(i).getEps(), cellFont, colorCeldas));
				tabla.addCell(
						crearCelda(((empleadoList.get(i).getSueldo() * 0.16) * 0.25) + "", cellFont, colorCeldas));
				tabla.addCell(crearCelda(empleadoList.get(i).getSueldo() + "", cellFont, colorCeldas));
				tabla.addCell(crearCelda(cantidadNovedadesEmpleado(empleadoList.get(i).getCodigo(), novedadList) + "",
						cellFont, colorCeldas));
			}

			document.add(tabla);
			document.close();

			ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=informe.pdf")
					.contentType(MediaType.APPLICATION_PDF).contentLength(resource.contentLength()).body(resource);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	@GetMapping(value = "/PDF3", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> obtenerPDF3(@RequestParam(name = "op1") Integer op1,
			@RequestParam(name = "op2") Integer op2) {
		try {

			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Font headerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
			Font cellFont = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
			BaseColor colorTitulo = new BaseColor(100, 149, 237);
			BaseColor colorCeldas = new BaseColor(240, 248, 255);
			BaseColor colorTituloTexto = new BaseColor(25, 25, 112);

			document.open();

			Paragraph titulo = new Paragraph("Reporte (Salud y Pension)",
					new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, colorTitulo));
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(10f);
			document.add(titulo);
			int[] tonosGrises = { 0xECEFF1, 0xCFD8DC, 0xB0BEC5, 0x90A4AE, 0x78909C, 0x607D8B, 0x546E7A, 0x455A64,
					0x37474F, 0x263238, 0xC5CAE9, 0x9FA8DA, 0x7986CB, 0x5C6BC0, 0x3F51B5, 0x3949AB, 0x303F9F, 0x283593,
					0x1A237E, 0x0D47A1 };
			// Trabajar desde aqui

			Paragraph val = new Paragraph("A)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val.setAlignment(Element.ALIGN_LEFT);
			val.setSpacingAfter(5f);
			document.add(val);

			ArrayList<Empleado> lst = empleDAO.findAll();
			ArrayList<String> aux = new ArrayList<String>();

			for (int i = 0; i < lst.size(); i++) {
				if (!aux.contains(lst.get(i).getEps())) {
					aux.add(lst.get(i).getEps());
				}
			}

			DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
			boolean flag = false;
			for (int j = 0; j < aux.size(); j++) {
				String eps = aux.get(j);
				int cont = 0;
				for (int k = 0; k < lst.size(); k++) {
					if (aux.get(j).equals(lst.get(k).getEps())) {
						cont++;
						flag = true;
					}
				}
				if (cont != 0) {
					barDataset.addValue(cont, "", eps);
				}

			}

			JFreeChart barChart = ChartFactory.createBarChart("FRECUENCIA EPS", "EPS", "Cantidad de empleados",
					barDataset, PlotOrientation.VERTICAL, true, true, false);

			CategoryPlot barPlot = (CategoryPlot) barChart.getPlot();
			for (int k = 0; k < aux.size(); k++) {
				barPlot.getRenderer().setSeriesPaint(k, new Color(tonosGrises[k % tonosGrises.length]));
			}

			BufferedImage barBufferedImage = barChart.createBufferedImage(480, 360);

			ByteArrayOutputStream barChartOut = new ByteArrayOutputStream();
			ImageIO.write(barBufferedImage, "png", barChartOut);
			com.itextpdf.text.Image barImg = com.itextpdf.text.Image.getInstance(barChartOut.toByteArray());

			barImg.scaleToFit(480, 360);
			document.add(barImg);

			// B
			document.newPage();
			Paragraph val2 = new Paragraph("B)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val2.setAlignment(Element.ALIGN_LEFT);
			val2.setSpacingAfter(5f);
			document.add(val2);

			ArrayList<String> aux2 = new ArrayList<String>();

			for (int i = 0; i < lst.size(); i++) {
				if (!aux2.contains(lst.get(i).getPension())) {
					aux2.add(lst.get(i).getPension());
				}
			}

			DefaultCategoryDataset barDataset2 = new DefaultCategoryDataset();
			boolean flag2 = false;
			for (int j = 0; j < aux2.size(); j++) {
				String pension = aux2.get(j);
				int cont2 = 0;
				for (int k = 0; k < lst.size(); k++) {
					if (aux2.get(j).equals(lst.get(k).getPension())) {
						cont2++;
						flag2 = true;
					}
				}
				if (cont2 != 0) {
					barDataset2.addValue(cont2, "", pension);
				}

			}

			JFreeChart barChart2 = ChartFactory.createBarChart("FRECUENCIA PENSION", "Fondo de pensiones",
					"Cantidad de empleados", barDataset2, PlotOrientation.VERTICAL, true, true, false);

			CategoryPlot barPlot2 = (CategoryPlot) barChart2.getPlot();
			for (int k = 0; k < aux.size(); k++) {
				barPlot2.getRenderer().setSeriesPaint(k, new Color(tonosGrises[k % tonosGrises.length]));
			}

			BufferedImage barBufferedImage2 = barChart2.createBufferedImage(480, 360);

			ByteArrayOutputStream barChartOut2 = new ByteArrayOutputStream();
			ImageIO.write(barBufferedImage2, "png", barChartOut2);
			com.itextpdf.text.Image barImg2 = com.itextpdf.text.Image.getInstance(barChartOut2.toByteArray());

			barImg2.scaleToFit(480, 360);
			document.add(barImg2);

			// C
			document.newPage();
			Paragraph val8 = new Paragraph("C)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val8.setAlignment(Element.ALIGN_LEFT);
			val8.setSpacingAfter(5f);
			document.add(val8);

			ArrayList<String> aux4 = new ArrayList<String>();

			for (int i = 0; i < lst.size(); i++) {
				if (!aux4.contains(lst.get(i).getDependencia())) {
					aux4.add(lst.get(i).getDependencia());
				}
			}

			for (int i = 0; i < aux4.size(); i++) {

				DefaultCategoryDataset barDataset3 = new DefaultCategoryDataset();
				boolean flag4 = false;
				for (int j = 0; j < aux.size(); j++) {
					String cargo = aux.get(j);
					int cont3 = 0;

					for (int k = 0; k < lst.size(); k++) {
						if (aux4.get(i).equals(lst.get(k).getDependencia()) && aux.get(j).equals(lst.get(k).getEps())) {
							cont3++;
							flag4 = true;
						}
					}
					if (cont3 != 0) {
						barDataset3.addValue(cont3, "", cargo);
					}

				}

				JFreeChart barChart3 = ChartFactory.createBarChart("Dependencia " + aux4.get(i), "EPS",
						"Cantidad de empleados", barDataset3, PlotOrientation.VERTICAL, true, true, false);

				CategoryPlot barPlot3 = (CategoryPlot) barChart3.getPlot();
				for (int k = 0; k < aux4.size(); k++) {
					barPlot3.getRenderer().setSeriesPaint(k, new Color(tonosGrises[k % tonosGrises.length]));
				}

				int width = 480;
				int height = 360;
				BufferedImage barBufferedImage3 = barChart3.createBufferedImage(width, height);

				ByteArrayOutputStream barChartOut3 = new ByteArrayOutputStream();
				ImageIO.write(barBufferedImage3, "png", barChartOut3);
				com.itextpdf.text.Image barImg3 = com.itextpdf.text.Image.getInstance(barChartOut3.toByteArray());

				barImg3.scaleToFit(480, 360);
				document.add(barImg3);
			}
			// D

			document.newPage();
			Paragraph va8 = new Paragraph("D)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			va8.setAlignment(Element.ALIGN_LEFT);
			va8.setSpacingAfter(5f);
			document.add(va8);

			for (int i = 0; i < aux4.size(); i++) {

				DefaultCategoryDataset barDataset3 = new DefaultCategoryDataset();
				boolean flag4 = false;
				for (int j = 0; j < aux2.size(); j++) {
					String cargo = aux2.get(j);
					int cont3 = 0;

					for (int k = 0; k < lst.size(); k++) {
						if (aux4.get(i).equals(lst.get(k).getDependencia())
								&& aux2.get(j).equals(lst.get(k).getPension())) {
							cont3++;
							flag4 = true;
						}
					}
					if (cont3 != 0) {
						barDataset3.addValue(cont3, "", cargo);
					}

				}

				JFreeChart barChart3 = ChartFactory.createBarChart("Dependencia " + aux4.get(i), "FONDO DE PENSIONES",
						"Cantidad de empleados", barDataset3, PlotOrientation.VERTICAL, true, true, false);

				CategoryPlot barPlot3 = (CategoryPlot) barChart3.getPlot();
				for (int k = 0; k < aux4.size(); k++) {
					barPlot3.getRenderer().setSeriesPaint(k, new Color(tonosGrises[k % tonosGrises.length]));
				}

				int width = 480;
				int height = 360;
				BufferedImage barBufferedImage3 = barChart3.createBufferedImage(width, height);

				ByteArrayOutputStream barChartOut3 = new ByteArrayOutputStream();
				ImageIO.write(barBufferedImage3, "png", barChartOut3);
				com.itextpdf.text.Image barImg3 = com.itextpdf.text.Image.getInstance(barChartOut3.toByteArray());

				barImg3.scaleToFit(480, 360);
				document.add(barImg3);
			}

			// E
			document.newPage();

			Paragraph val4 = new Paragraph("E)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val4.setAlignment(Element.ALIGN_LEFT);
			val4.setSpacingAfter(5f);
			document.add(val4);

			if (op1 == 0) {
				mergeSortPorNombre(lst);
			} else {
				mergeSortPorNombre(lst);
				Collections.reverse(lst);
			}
			ArrayList<String> aux3 = new ArrayList<String>();
			for (int i = 0; i < lst.size(); i++) {
				if (!aux3.contains(lst.get(i).getCargo())) {
					aux3.add(lst.get(i).getCargo());
				}
			}

			for (int i = 0; i < aux.size(); i++) {
				Paragraph val5 = new Paragraph(aux.get(i),
						new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, colorTituloTexto));
				val5.setAlignment(Element.ALIGN_LEFT);
				val5.setSpacingAfter(5f);
				document.add(val5);

				for (int j = 0; j < lst.size(); j++) {
					if (!aux3.contains(lst.get(j).getCargo())) {
						aux3.add(lst.get(j).getCargo());
					}
				}

				Collections.sort(aux3);

				for (int j = 0; j < aux3.size(); j++) {
					Paragraph val6 = new Paragraph(aux3.get(j),
							new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, colorTituloTexto));
					val6.setAlignment(Element.ALIGN_LEFT);
					val6.setSpacingAfter(5f);

					PdfPTable tabla2 = new PdfPTable(4);
					tabla2.setWidthPercentage(100);
					tabla2.setSpacingBefore(10f);
					tabla2.setSpacingAfter(10f);

					String[] titulos2 = { "Codigo", "Nombre completo", "Pension", "Cargo" };

					for (int k = 0; k < titulos2.length; k++) {
						PdfPCell celda = new PdfPCell();
						celda.setBackgroundColor(colorTitulo);
						celda.setPadding(5);
						celda.setPhrase(new Phrase(titulos2[k], headerFont));
						tabla2.addCell(celda);
					}

					boolean flag3 = false;

					for (int k = 0; k < lst.size(); k++) {
						if (aux.get(i).equals(lst.get(k).getEps()) && aux3.get(j).equals(lst.get(k).getCargo())) {
							flag3 = true;
							tabla2.addCell(crearCelda(lst.get(k).getCodigo() + "", cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst.get(k).getNombreEmpleado(), cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst.get(k).getPension(), cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst.get(k).getCargo(), cellFont, colorCeldas));

						}
					}
					if (flag3) {
						document.add(val6);
						document.add(tabla2);
					}

				}
			}

			// F
			document.newPage();
			Paragraph va4 = new Paragraph("F)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			va4.setAlignment(Element.ALIGN_LEFT);
			va4.setSpacingAfter(5f);
			document.add(va4);

			if (op2 == 0) {
				mergeSortPorNombre(lst);
			} else {
				mergeSortPorNombre(lst);
				Collections.reverse(lst);
			}

			for (int i = 0; i < aux2.size(); i++) {
				Paragraph val5 = new Paragraph(aux2.get(i),
						new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, colorTituloTexto));
				val5.setAlignment(Element.ALIGN_LEFT);
				val5.setSpacingAfter(5f);
				document.add(val5);

				for (int j = 0; j < lst.size(); j++) {
					if (!aux3.contains(lst.get(j).getCargo())) {
						aux3.add(lst.get(j).getCargo());
					}
				}

				Collections.sort(aux3);

				for (int j = 0; j < aux3.size(); j++) {
					Paragraph val6 = new Paragraph(aux3.get(j),
							new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, colorTituloTexto));
					val6.setAlignment(Element.ALIGN_LEFT);
					val6.setSpacingAfter(5f);

					PdfPTable tabla2 = new PdfPTable(4);
					tabla2.setWidthPercentage(100);
					tabla2.setSpacingBefore(10f);
					tabla2.setSpacingAfter(10f);

					String[] titulos2 = { "Codigo", "Nombre completo", "Cargo", "Pension" };

					for (int k = 0; k < titulos2.length; k++) {
						PdfPCell celda = new PdfPCell();
						celda.setBackgroundColor(colorTitulo);
						celda.setPadding(5);
						celda.setPhrase(new Phrase(titulos2[k], headerFont));
						tabla2.addCell(celda);
					}

					boolean flag3 = false;

					for (int k = 0; k < lst.size(); k++) {
						if (aux2.get(i).equals(lst.get(k).getPension()) && aux3.get(j).equals(lst.get(k).getCargo())) {
							flag3 = true;
							tabla2.addCell(crearCelda(lst.get(k).getCodigo() + "", cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst.get(k).getNombreEmpleado(), cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst.get(k).getCargo(), cellFont, colorCeldas));
							tabla2.addCell(crearCelda(lst.get(k).getPension(), cellFont, colorCeldas));

						}
					}
					if (flag3) {
						document.add(val6);
						document.add(tabla2);
					}

				}
			}

			//

			document.close();

			ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=informe.pdf")
					.contentType(MediaType.APPLICATION_PDF).contentLength(resource.contentLength()).body(resource);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@GetMapping(value = "/PDF4", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> obtenerPDF4(@RequestParam(name = "fechaInicio") String fechaInicio,
			@RequestParam(name = "fechaFin") String fechaFin) {
		try {

			ArrayList<Novedad> lst = novedadDAO.findAll();

			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Font headerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
			Font cellFont = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
			BaseColor colorTitulo = new BaseColor(100, 149, 237);
			BaseColor colorCeldas = new BaseColor(240, 248, 255);
			BaseColor colorTituloTexto = new BaseColor(25, 25, 112);

			document.open();

			Paragraph titulo = new Paragraph("Reporte (Novedades)",
					new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, colorTitulo));
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(10f);
			document.add(titulo);

			Paragraph val1 = new Paragraph("A)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val1.setAlignment(Element.ALIGN_LEFT);
			val1.setSpacingAfter(5f);
			document.add(val1);

			PdfPTable tabla = new PdfPTable(8);
			tabla.setWidthPercentage(100);
			tabla.setSpacingBefore(10f);
			tabla.setSpacingAfter(10f);

			String[] titulos = { "CODIGO", "NOMBRE", "TIPO NOVEDAD", "DIAS TRABAJADOS", "DIAS INCAPACIDAD",
					"DIAS VACACIONES", "FECHA INICIO", "FECHA FIN" };

			for (int i = 0; i < titulos.length; i++) {
				PdfPCell celda = new PdfPCell();
				celda.setBackgroundColor(colorTitulo);
				celda.setPadding(5);
				celda.setPhrase(new Phrase(titulos[i], headerFont));
				tabla.addCell(celda);
			}

			for (int i = 0; i < lst.size(); i++) {
				if (!lst.get(i).getInicioIncapacidad().contentEquals("")
						&& !lst.get(i).getFinIncapacidad().contentEquals("")) {
					boolean op1 = verificarFechaEnRango(fechaInicio, fechaFin, lst.get(i).getInicioIncapacidad());
					boolean op2 = verificarFechaEnRango(fechaInicio, fechaFin, lst.get(i).getFinIncapacidad());

					if (op1 && op2) {
						tabla.addCell(crearCelda(lst.get(i).getEmpleado().getCodigo() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getEmpleado().getNombreEmpleado(), cellFont, colorCeldas));
						tabla.addCell(crearCelda("Incapacidad", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getDiasTrabajados() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getDiasIncapacidad() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getDiasVacaciones() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getInicioIncapacidad(), cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getFinIncapacidad(), cellFont, colorCeldas));
					}

				}

				if (!lst.get(i).getInicioVacaciones().contentEquals("")
						&& !lst.get(i).getFinVacaciones().contentEquals("")) {
					boolean op1 = verificarFechaEnRango(fechaInicio, fechaFin, lst.get(i).getInicioVacaciones());
					boolean op2 = verificarFechaEnRango(fechaInicio, fechaFin, lst.get(i).getFinVacaciones());

					if (op1 && op2) {
						tabla.addCell(crearCelda(lst.get(i).getEmpleado().getCodigo() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getEmpleado().getNombreEmpleado(), cellFont, colorCeldas));
						tabla.addCell(crearCelda("Vacaciones", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getDiasTrabajados() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getDiasIncapacidad() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getDiasVacaciones() + "", cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getInicioVacaciones(), cellFont, colorCeldas));
						tabla.addCell(crearCelda(lst.get(i).getFinVacaciones(), cellFont, colorCeldas));
					}

				}

			}
			document.add(tabla);

			Paragraph val2 = new Paragraph("B)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, colorTituloTexto));
			val2.setAlignment(Element.ALIGN_LEFT);
			val2.setSpacingAfter(5f);
			document.add(val2);

			ArrayList<Empleado> aux2 = empleDAO.findAll();
			mergeSortPorNombre(aux2);

			ArrayList<String> lst2 = new ArrayList<String>();

			for (int i = 0; i < aux2.size(); i++) {
				if (!lst2.contains(aux2.get(i).getDependencia())) {
					lst2.add(aux2.get(i).getDependencia());
				}
			}

			Collections.sort(lst2);

			document.close();

			ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=informe.pdf")
					.contentType(MediaType.APPLICATION_PDF).contentLength(resource.contentLength()).body(resource);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	public boolean verificarFechaEnRango(String fechaInicioStr, String fechaFinStr, String fechaStr) {
		try {
			LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
			LocalDate fechaFin = LocalDate.parse(fechaFinStr);
			LocalDate fecha = LocalDate.parse(fechaStr);

			return (fecha.isAfter(fechaInicio) || fecha.isEqual(fechaInicio))
					&& (fecha.isBefore(fechaFin) || fecha.isEqual(fechaFin));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void mergeSortPorDependencia(List<Empleado> arr) {
		if (arr.size() > 1) {
			int mid = arr.size() / 2;
			List<Empleado> left = new ArrayList<>(arr.subList(0, mid));
			List<Empleado> right = new ArrayList<>(arr.subList(mid, arr.size()));

			mergeSortPorNombre(left);
			mergeSortPorNombre(right);

			mergePorNombre(arr, left, right);
		}
	}

	public static void mergePorDependencia(List<Empleado> arr, List<Empleado> left, List<Empleado> right) {
		int i = 0, j = 0, k = 0;

		while (i < left.size() && j < right.size()) {
			if (left.get(i).getDependencia().compareTo(right.get(j).getDependencia()) < 0) {
				arr.set(k++, left.get(i++));
			} else {
				arr.set(k++, right.get(j++));
			}
		}

		while (i < left.size()) {
			arr.set(k++, left.get(i++));
		}

		while (j < right.size()) {
			arr.set(k++, right.get(j++));
		}
	}

	public static void mergeSortPorNombre(List<Empleado> arr) {
		if (arr.size() > 1) {
			int mid = arr.size() / 2;
			List<Empleado> left = new ArrayList<>(arr.subList(0, mid));
			List<Empleado> right = new ArrayList<>(arr.subList(mid, arr.size()));

			mergeSortPorNombre(left);
			mergeSortPorNombre(right);

			mergePorNombre(arr, left, right);
		}
	}

	public static void mergePorNombre(List<Empleado> arr, List<Empleado> left, List<Empleado> right) {
		int i = 0, j = 0, k = 0;

		while (i < left.size() && j < right.size()) {
			if (left.get(i).getNombreEmpleado().compareTo(right.get(j).getNombreEmpleado()) < 0) {
				arr.set(k++, left.get(i++));
			} else {
				arr.set(k++, right.get(j++));
			}
		}

		while (i < left.size()) {
			arr.set(k++, left.get(i++));
		}

		while (j < right.size()) {
			arr.set(k++, right.get(j++));
		}
	}

	public int cantidadNovedadesEmpleado(long codigo, ArrayList<Novedad> lst) {
		int salida = 0;
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getEmpleado().getCodigo() == codigo) {
				salida++;
			}
		}
		return salida;
	}

	private PdfPCell crearCelda(String texto, Font tipo_fuente, BaseColor colorCelda) {
		PdfPCell cell = new PdfPCell(new Phrase(texto, tipo_fuente));
		cell.setBackgroundColor(colorCelda);
		cell.setPadding(5);
		return cell;
	}

}
